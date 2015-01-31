package models

import java.net.URL
import java.util.UUID

import converters.reads._
import converters.writes._
import play.api.libs.functional.syntax._
import play.api.libs.json._

object Bundle {
  implicit val bundleWriter: Writes[Bundle] = (
    (JsPath \ "bundleId").write[UUID] and
    (JsPath \ "bundleName").write[String] and
    (JsPath \ "bundlePrice").write[String] and
    (JsPath \ "cityId").writeNullable[String]
  )(unlift(unapply))

  implicit val bundleReader: Reads[Bundle] = (
    (JsPath \ "bundleId").read[UUID] and
    (JsPath \ "bundleName").read[String] and
    (JsPath \ "bundlePrice").read[String] and
    (JsPath \ "cityId").readNullable[String]
  )(apply _)
}

case class Bundle(val id: UUID,
                  val name: String,
                  val price: String,
                  val cityId: Option[String]=None)
