package controllers

import java.util.UUID

import models.City
import play.api.{Play, Configuration}
import play.api.data.validation.ValidationError
import play.api.libs.json.Json._
import play.api.libs.json._
import play.api.mvc._
import repositories._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Promise, Future}
import scala.util.{Failure, Success}

trait CityController {
  this: Controller =>

  def repository: CityRepository = ???

  def index(name: Option[String] = None) = Action.async {
    repository.list(name)
              .map { cityList => Ok(toJson(cityList)) }
  }

  def get(id: String) = Action.async {
    withCity(id) { city =>
      Future(Ok(toJson(city)))
    }
  }

  def create() = Action.async(parse.json) { request =>
    request.body.validate[City]
                .fold(
    errors => Future {BadRequest(validationErrors(errors))},
    city => repository.insert(city)
                     .map(nothing => Created(obj("cityId" -> city.id)))
                     .recover {
      case iae: IllegalArgumentException => BadRequest(obj("message" -> iae.getMessage))
      case error: Throwable => InternalServerError(obj("error" -> error.getMessage))
    }
   )
  }

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

  private def withCity(id: String)(f: City => Future[Result]): Future[Result] = {
    try {
      val cityId = UUID.fromString(id)
      val city: Future[Option[City]] = repository.find(cityId)
      city.flatMap { cityOption =>
        cityOption match {
          case Some(city) => f(city)
          case None => Future(NotFound(obj("message" -> s"There is no city with ${cityId} identifier")))
        }
      }
    }
    catch {
      case e: IllegalArgumentException => Future(BadRequest(obj("message" -> e.getMessage)))
      case e: Throwable => Future(InternalServerError(obj("error" -> e.getMessage)))
    }
  }
}

object CityController extends Controller with CityController with MongoDBConnection {

  val collectionName = configuration.getString("mongo.cities.collection")
                                    .getOrElse("cities")

  override def repository = new MongoDBCityRepository(db(collectionName))
}