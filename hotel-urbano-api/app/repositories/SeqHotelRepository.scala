package repositories

import models._
import java.util.UUID
import scala.concurrent.{Promise, ExecutionContext, Future}
import ExecutionContext.Implicits.global

class SeqHotelRepository(var hotels: Seq[Hotel]) extends HotelRepository {

  override def find(id: UUID): Future[Option[Hotel]] = Future {
    hotels.find(_.id == id)
  }

  override def list(): Future[HotelList] = Future(new HotelList(hotels))

  override def insert(hotel: Hotel): Future[UUID] = Future(UUID.randomUUID())
}
