package repositories

import models._
import java.util.UUID
import scala.concurrent.{Promise, ExecutionContext, Future}
import ExecutionContext.Implicits.global

class SeqCityRepository(var cities: Seq[City]) extends CityRepository {

  override def find(id: UUID): Future[Option[City]] = Future {
    cities.find(_.id == id)
  }

  override def list(): Future[CityList] = Future(new CityList(cities))

  override def insert(city: City): Future[UUID] = Future(UUID.randomUUID())
}
