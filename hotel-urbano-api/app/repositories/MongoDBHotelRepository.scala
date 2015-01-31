package repositories

import java.util.UUID

import converters.HotelBSONConverter
import models.{Hotel, HotelList}
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONNull, BSONDocument}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Promise, Future}
import scala.util.{Success, Failure, Try}
import scala.language.postfixOps

class MongoDBHotelRepository(collection: BSONCollection) extends HotelRepository {

  implicit val hotelConverter = HotelBSONConverter

  override def find(id: UUID): Future[Option[Hotel]] = {
    val promise = Promise[Option[Hotel]]()

    try {
      val query = BSONDocument("hotelId" -> id.toString)
      val result = collection.find(query)
        .cursor[Hotel](hotelConverter, global)
        .collect[Seq]()

        for( hotels <- result){
          promise success(hotels headOption)
        }
    }
    catch {
      case error: Throwable => promise failure(error)
    }

    promise future
  }

  override def list(): Future[HotelList] = {

    val allSortedByHotelName = BSONDocument(
      "$query" -> BSONDocument(),
    "$orderby" -> BSONDocument("hotelName" -> 1)
  )

    collection.find(allSortedByHotelName)
      .cursor[Hotel](hotelConverter, global)
      .collect[Seq]()
      .map { hotels => new HotelList(hotels) }
  }

  override def insert(hotel: Hotel): Future[UUID] = {
    val promise = Promise[UUID]()

    collection.insert[Hotel](hotel) onComplete {
      case Success(lastError) => promise success(hotel.id)
      case Failure(error) => promise failure(error)
    }

    promise.future
  }
}
