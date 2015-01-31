package repositories

import java.util.UUID
import models.City
import scala.concurrent.Future

trait CityRepository {
  def find(id: UUID): Future[Option[City]]
}
