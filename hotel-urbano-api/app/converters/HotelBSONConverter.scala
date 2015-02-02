package converters

import java.net.URL
import org.joda.time.DateTime
import org.joda.time.format._

import reactivemongo.bson._
import models.Hotel
import java.util.UUID

object HotelBSONConverter extends BSONDocumentWriter[Hotel] with BSONDocumentReader[Hotel] {

  val dateFormatter = DateTimeFormat.forPattern("dd/MM/yyyy")

  override def write(hotel: Hotel): BSONDocument = {
    BSONDocument(
      "hotelId" -> hotel.id.toString,
      "hotelName" -> hotel.name,
      "hotelPrice" -> hotel.price,
      "cityId" -> hotel.cityId,
      "dates" -> hotel.dates
    )
  }

  override def read(bson: BSONDocument): Hotel = {
    Hotel(UUID.fromString(bson.getAs[String]("hotelId").get),
           bson.getAs[String]("hotelName").get,
           bson.getAs[String]("hotelPrice").get,
           bson.getAs[String]("cityId").get,
           bson.getAs[Seq[String]]("dates").get
         )
  }
}
