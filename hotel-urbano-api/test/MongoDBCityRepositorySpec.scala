import java.net.URL
import java.util.UUID

import converters.reads._
import models._
import org.joda.time.DateTime
import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.exceptions.TestFailedException
import reactivemongo.api.MongoDriver
import repositories.MongoDBCityRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.Future.sequence
import scala.concurrent.duration._
import scala.language.postfixOps

class MongoDBCityRepositorySpec extends BaseSpec with BeforeAndAfterEach with ScalaFutures {

  val driver = new MongoDriver
  val connection = driver.connection(Seq("localhost"))
  val db = connection("mydb")

  val repository = new MongoDBCityRepository(db("testcities"))

  val city1 = City(Some(UUID.fromString("a4065490-8cda-45e4-b42c-ff8f7f792ef4")), "Rio de Janeiro, Rio de Janeiro Estado, Brasil")

  override def beforeEach {
    MongoUnit.cleanAndInsert()
  }

  describe("MongoDBCityRepository") {
    describe("#find"){
      it("finds a city by id and returns an option") {
        val cityId = UUID.fromString("a4065490-8cda-45e4-b42c-ff8f7f792ef4")
        whenReady(repository.find(cityId), Timeout(1 minute)) { city =>
          city should be(Some(city1))
        }
      }

      it("returns None if it doesn't find any city") {
        val cityId = UUID.fromString("a4065490-8cda-45e4-b42c-ff8f7f792ef3")
        whenReady(repository.find(cityId), Timeout(1 minute)) { city =>
          city should be(None)
        }
      }
    }
  }
}
