package models

import java.net.URL
import java.util.UUID

import converters.reads._
import converters.writes._
import play.api.libs.functional.syntax._
import play.api.libs.json._

object City {
  implicit val cityWriter: Writes[City] = (
    (JsPath \ "cityId").write[UUID] and
    (JsPath \ "cityName").write[String]
  )(unlift(unapply))

  implicit val cityReader: Reads[City] = (
    (JsPath \ "cityId").read[UUID] and
    (JsPath \ "cityName").read[String]
  )(apply _)
}

case class City(val id: UUID,
                val name: String)