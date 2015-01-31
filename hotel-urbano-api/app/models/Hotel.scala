package models

import java.net.URL
import java.util.UUID

import converters.reads._
import converters.writes._
import play.api.libs.functional.syntax._
import play.api.libs.json._

object Hotel {
  implicit val hotelWriter: Writes[Hotel] = (
    (JsPath \ "hotelId").write[UUID] and
    (JsPath \ "hotelName").write[String] and
    (JsPath \ "hotelPrice").write[String] and
    (JsPath \ "cityId").write[String]
  )(unlift(unapply))

  implicit val hotelReader: Reads[Hotel] = (
    (JsPath \ "hotelId").read[UUID] and
    (JsPath \ "hotelName").read[String] and
    (JsPath \ "hotelPrice").read[String] and
    (JsPath \ "cityId").read[String]
  )(apply _)
}

case class Hotel(val id: UUID,
                  val name: String,
                  val price: String,
                  val cityId: String)
