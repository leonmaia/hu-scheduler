package repositories

import java.util.UUID
import models.{Hotel, HotelList}
import scala.concurrent.Future

trait HotelRepository {
  def find(id: UUID): Future[Option[Hotel]]
  
  def list(): Future[HotelList]
  
  def insert(hotel: Hotel): Future[UUID]
}
