package models

import models.City._
import play.api.libs.json.{Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

object CityList {

  implicit val hotelListWriter: Writes[CityList] = (
    (JsPath \ "numberOfcities").write[Int] and
    (JsPath \ "cities").write[Seq[City]]
  )(unlift(unapply))

  def unapply(cityList: CityList): Option[(Int, Seq[City])] =
    Some(cityList.cities.size, cityList.cities)
}

class CityList(val cities: Seq[City])
