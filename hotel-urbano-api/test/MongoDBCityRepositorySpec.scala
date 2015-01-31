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

  val city1 = City(UUID.fromString("a4065490-8cda-45e4-b42c-ff8f7f792ef4"), "Rio de Janeiro, Rio de Janeiro Estado, Brasil")
  val city2 = City(UUID.fromString("c7e6baaf-2812-4e3b-8f29-1c489ceadc15"), "Fortaleza, Região Nordeste, Brasil")
  val city3 = City(UUID.fromString("bc587e57-fe83-4ad9-98a9-7bbf0b930a5c"), "city3")
  val city4 = City(UUID.fromString("bc587e57-fe83-4ad9-98a9-333333333333"), "city4")


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
    describe("#list"){
      it("returns a list of existing cities in the repository") {
        whenReady(repository.list(), Timeout(1 minute)) { cityList =>
          cityList.cities should have size 3
          cityList.cities should contain theSameElementsAs Seq(city1, city2, city3)
        }
      }
      describe("with no cities in the repository") {
        it("returns an empty list") {
          MongoUnit.cleanDatabase()

          whenReady(repository.list(), Timeout(1 minute)) { cityList =>
            cityList.cities should be(empty)
          }
        }
      }
    }
    describe("#insert") {
      it("insert another city in the collection") {
        MongoUnit.cleanDatabase()

        Await.ready(Future.sequence(Seq(
          repository.insert(city4),
          repository.insert(city1),
          repository.insert(city2),
          repository.insert(city3)
        )), 1 minute)


        whenReady(repository.list()) { cityList =>
          cityList.cities should have size 4
          cityList.cities should contain theSameElementsAs Seq(city1, city2, city3, city4)
        }
      }
    }
  }
}
