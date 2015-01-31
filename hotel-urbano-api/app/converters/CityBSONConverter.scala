package converters

import java.net.URL

import reactivemongo.bson._
import models.City
import java.util.UUID

object CityBSONConverter extends BSONDocumentWriter[City] with BSONDocumentReader[City] {

  override def write(city: City): BSONDocument = {
    val baseDoc = BSONDocument(      
      "cityName" -> city.name
    )
    val optionalFields = Seq(
      ("cityId", city.id.map(id => BSONString(id.toString)))
    )
   
    optionalFields.foldLeft(baseDoc) { case (bson, (key, optionalValue)) =>
      optionalValue match {
        case Some(value) => bson ++ BSONDocument(Seq(key -> value))
        case None => bson
      }
    }
  }

  override def read(bson: BSONDocument): City = {
    City(bson.getAs[String]("cityId").map(id => UUID.fromString(id)),
      bson.getAs[String]("cityName").get
    )
  }
}
