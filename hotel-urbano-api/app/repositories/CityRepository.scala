package repositories

import java.util.UUID
import models.{City, CityList}
import scala.concurrent.Future

trait CityRepository {
  def find(id: UUID): Future[Option[City]]

  def list(name: Option[String] = None): Future[CityList]

  def insert(city: City): Future[UUID]
}
