package models

import models.Hotel._
import play.api.libs.json.{Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

object HotelList {

  implicit val hotelListWriter: Writes[HotelList] = (
    (JsPath \ "numberOfhotels").write[Int] and
    (JsPath \ "hotels").write[Seq[Hotel]]
  )(unlift(unapply))

  def unapply(hotelList: HotelList): Option[(Int, Seq[Hotel])] =
    Some(hotelList.hotels.size, hotelList.hotels)
}

class HotelList(val hotels: Seq[Hotel])
