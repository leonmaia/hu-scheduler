package converters

import java.net.URL

import reactivemongo.bson._
import models.Hotel
import java.util.UUID

object HotelBSONConverter extends BSONDocumentWriter[Hotel] with BSONDocumentReader[Hotel] {

  override def write(hotel: Hotel): BSONDocument = {
    BSONDocument(
      "hotelId" -> hotel.id.toString,
      "hotelName" -> hotel.name,
      "hotelPrice" -> hotel.price,
      "cityId" -> hotel.cityId      
    )
  }

  override def read(bson: BSONDocument): Hotel = {
    Hotel(UUID.fromString(bson.getAs[String]("hotelId").get),
           bson.getAs[String]("hotelName").get,
           bson.getAs[String]("hotelPrice").get,
           bson.getAs[String]("cityId").get
    )
  }
}
