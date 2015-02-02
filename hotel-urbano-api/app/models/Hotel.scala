package models

import java.net.URL
import java.util.UUID
import org.joda.time.DateTime

import converters.reads._
import converters.ImplicitBSONConverters
import converters.writes._
import play.api.libs.functional.syntax._
import play.api.libs.json._

object Hotel {
  implicit val hotelWriter: Writes[Hotel] = (
    (JsPath \ "hotelId").write[UUID] and
    (JsPath \ "hotelName").write[String] and
    (JsPath \ "hotelPrice").write[String] and
    (JsPath \ "cityId").write[String] and
    (JsPath \ "dates").write[Seq[DateTime]]
  )(unlift(unapply))

  implicit val hotelReader: Reads[Hotel] = (
    (JsPath \ "hotelId").read[UUID] and
    (JsPath \ "hotelName").read[String] and
    (JsPath \ "hotelPrice").read[String] and
    (JsPath \ "cityId").read[String] and
    (JsPath \ "dates").read[Seq[DateTime]]
  )(apply _)
}

case class Hotel(val id: UUID,
                 val name: String,
                 val price: String,
                 val cityId: String,
                 val dates: Seq[DateTime])

