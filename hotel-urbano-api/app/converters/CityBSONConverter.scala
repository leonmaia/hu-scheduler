package converters

import java.net.URL

import reactivemongo.bson._
import models.City
import java.util.UUID

object CityBSONConverter extends BSONDocumentWriter[City] with BSONDocumentReader[City] {

  override def write(city: City): BSONDocument = {
    BSONDocument(
      "cityId" -> city.id.toString,
      "cityName" -> city.name
  )
  }

  override def read(bson: BSONDocument): City = {
    City(UUID.fromString(bson.getAs[String]("cityId").get),
      bson.getAs[String]("cityName").get
      )
  }
}
