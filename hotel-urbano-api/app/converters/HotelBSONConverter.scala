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
    val baseDoc = BSONDocument(
      "hotelId" -> hotel.id.toString,
      "hotelName" -> hotel.name,
      "hotelPrice" -> hotel.price,
      "cityId" -> hotel.cityId.toString,
      "dates" -> hotel.dates
    )

    val optionalFields = Seq(
      ("dates", hotel.dates.map(BSONArray(_)))
      )
    optionalFields.foldLeft(baseDoc) { case (bson, (key, optionalValue)) =>
      optionalValue match {
        case Some(value) => bson ++ BSONDocument(Seq(key -> value))
        case None => bson
      }
  }
}

  override def read(bson: BSONDocument): Hotel = {
    Hotel(UUID.fromString(bson.getAs[String]("hotelId").get),
           bson.getAs[String]("hotelName").get,
           bson.getAs[String]("hotelPrice").get,
           UUID.fromString(bson.getAs[String]("cityId").get),
           bson.getAs[Seq[String]]("dates")
         )
  }
}
