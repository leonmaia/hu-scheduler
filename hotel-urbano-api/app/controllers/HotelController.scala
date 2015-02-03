package controllers

import java.util.UUID

import models.Hotel
import play.api.{Play, Configuration}
import play.api.data.validation.ValidationError
import play.api.libs.json.Json._
import play.api.libs.json._
import play.api.mvc._
import repositories._

import converters.ImplicitBSONConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import org.joda.time.LocalDate
import scala.concurrent.{Promise, Future}
import scala.util.{Failure, Success}

trait HotelController {
  this: Controller =>

  def repository: HotelRepository = ???

  def index(city: Option[String] = None, checkin: Option[String] = None, checkout: Option[String] = None)= Action.async {
    (checkin, checkout) match {
      case (Some(checkin), Some(checkout)) =>
        repository.list(city, Some(getDaysStream(checkin, checkout)))
          .map { hotelList => Ok(toJson(hotelList)) }
      case _ => repository.list(city).map { hotelList => Ok(toJson(hotelList)) }
    }
  }

  def get(id: String) = Action.async {
    withHotel(id) { hotel =>
      Future(Ok(toJson(hotel)))
    }
  }

  def create() = Action.async(parse.json) { request =>
    request.body.validate[Hotel]
                .fold(
    errors => Future {BadRequest(validationErrors(errors))},
    hotel => repository.insert(hotel)
                     .map(nothing => Created(obj("hotelId" -> hotel.id)))
                     .recover {
      case iae: IllegalArgumentException => BadRequest(obj("message" -> iae.getMessage))
      case error: Throwable => InternalServerError(obj("error" -> error.getMessage))
    }
   )
  }

  private def dayIterator(start: LocalDate, end: LocalDate) = Stream.iterate(start)(_ plusDays 1) takeWhile (_ isBefore end)

  private def validationErrors(errors: Seq[(JsPath, Seq[ValidationError])]): JsObject = {
    def toJsonString: Seq[JsString] = {
      errors.map {_._2}
            .flatten
            .map { error => JsString(error.message)}
    }
    if (errors.size > 1) {
      obj("messages" -> JsArray(toJsonString))
    }
    else {
      obj("message" -> toJsonString(0))
    }
  }

  private def withHotel(id: String)(f: Hotel => Future[Result]): Future[Result] = {
    try {
      val hotelId = UUID.fromString(id)
      val hotel: Future[Option[Hotel]] = repository.find(hotelId)
      hotel.flatMap { hotelOption =>
        hotelOption match {
          case Some(hotel) => f(hotel)
          case None => Future(NotFound(obj("message" -> s"There is no hotel with ${hotelId} identifier")))
        }
      }
    }
    catch {
      case e: IllegalArgumentException => Future(BadRequest(obj("message" -> e.getMessage)))
      case e: Throwable => Future(InternalServerError(obj("error" -> e.getMessage)))
    }
  }
}

object HotelController extends Controller with HotelController with MongoDBConnection {

  val collectionName = configuration.getString("mongo.hotels.collection")
                                    .getOrElse("hotels")

  override def repository = new MongoDBHotelRepository(db(collectionName))
}