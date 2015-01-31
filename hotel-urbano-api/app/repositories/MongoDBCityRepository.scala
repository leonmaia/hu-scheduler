package repositories

import java.util.UUID

import converters.CityBSONConverter
import models.City
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.{BSONNull, BSONDocument}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Promise, Future}
import scala.util.{Success, Failure, Try}
import scala.language.postfixOps

class MongoDBCityRepository(collection: BSONCollection) extends CityRepository {

  implicit val cityConverter = CityBSONConverter

  override def find(id: UUID): Future[Option[City]] = {
    val promise = Promise[Option[City]]()

    try {
      val query = BSONDocument("cityId" -> id.toString)
      val result = collection.find(query)
        .cursor[City](cityConverter, global)
        .collect[Seq]()

      for( cities <- result){
        promise success(cities headOption)
      }
    }
    catch {
      case error: Throwable => promise failure(error)
    }

    promise future
  }
}
