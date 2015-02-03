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
import repositories.MongoDBHotelRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.Future.sequence
import scala.concurrent.duration._
import scala.language.postfixOps

class MongoDBHotelRepositorySpec extends BaseSpec with BeforeAndAfterEach with ScalaFutures {

  val driver = new MongoDriver
  val connection = driver.connection(Seq("localhost"))
  val db = connection("mydb")

  val repository = new MongoDBHotelRepository(db("testhotels"))

  val hotel1 = Hotel(UUID.fromString("51075f93-8ccf-4390-bc17-a370bdf7dbf4"),
                     "Fortaleza Park Hotel",
                     "98,99", UUID.fromString("c7e6baaf-2812-4e3b-8f29-1c489ceadc152b"))
   val hotel2 = Hotel(UUID.fromString("adc64b87-4a9d-42af-89ea-c94dd5ecd6f2"),
                      "Quality Porto Alegre",
                      "100,19", UUID.fromString("bc587e57-fe83-4ad9-98a9-7bbf0b930a5c"))

  val hotel3 = Hotel(UUID.fromString("031c94ae-f1a9-49ce-ae6b-88415cc3fea8"),
                     "Vila Galé Rio de Janeiro",
                     "138,00", UUID.fromString("a4065490-8cda-45e4-b42c-ff8f7f792ef4"))

  val hotel4 = Hotel(UUID.fromString("bc587e57-fe83-4ad9-98a9-333333333333"),
                     "Xeorghof Berlin",
                     "44,10", UUID.fromString("d4e80d6a-a9cb-46af-b3ba-5ef5eadf789d"))

  override def beforeEach {
    MongoUnit.cleanAndInsert()
  }

  describe("MongoDBHotelRepository") {
    describe("#find"){
      it("finds a hotel by id and returns an option") {
        val hotelId = UUID.fromString("51075f93-8ccf-4390-bc17-a370bdf7dbf4")
        whenReady(repository.find(hotelId), Timeout(1 minute)) { hotel =>
          hotel should be(Some(hotel1))
        }
      }

      it("returns None if it doesn't find any hotel") {
        val hotelId = UUID.fromString("a4065490-8cda-45e4-b42c-ff8f7f792ef3")
        whenReady(repository.find(hotelId), Timeout(1 minute)) { hotel =>
          hotel should be(None)
        }
      }
    }
    describe("#list"){
      it("returns a list of existing hotels in the repository") {
        whenReady(repository.list(), Timeout(1 minute)) { hotelList =>
          hotelList.hotels should have size 3
          hotelList.hotels should contain theSameElementsAs Seq(hotel1, hotel2, hotel3)
        }
      }
      describe("with no hotels in the repository") {
        it("returns an empty list") {
          MongoUnit.cleanDatabase()

          whenReady(repository.list(), Timeout(1 minute)) { hotelList =>
            hotelList.hotels should be(empty)
          }
        }
      }
    }
    describe("#insert") {
      it("insert another hotel in the collection") {
        MongoUnit.cleanDatabase()

        Await.ready(Future.sequence(Seq(
          repository.insert(hotel4),
          repository.insert(hotel1),
          repository.insert(hotel2),
          repository.insert(hotel3)
        )), 1 minute)


        whenReady(repository.list()) { hotelList =>
          hotelList.hotels should have size 4
          hotelList.hotels should contain theSameElementsAs Seq(hotel1, hotel2, hotel3, hotel4)
        }
      }
    }
  }
}
