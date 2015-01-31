import java.util.UUID

import controllers.CityController
import models._
import play.api.libs.json._
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.SeqCityRepository

class CityControllerSpec extends SchedulerSpec with Results {

  class FakeCityController(cities: City*) extends Controller with CityController {
    override def repository = new SeqCityRepository(cities)
  }

  describe("CityController") {
    val cities = Seq(City(UUID.randomUUID(),"Fortaleza, Região Nordeste, Brasil"),
                     City(UUID.randomUUID(),"Rio de Janeiro, Brasil"),
                     City(UUID.randomUUID(),"Porto Alegre, Brasil"))

    val cityController = new FakeCityController(cities: _*)

    describe("#index") {
      it("returns OK and a list of cities") {
        val result = cityController.index()(FakeRequest())

        status(result) shouldBe(OK)
        contentAsJson(result) shouldBe(JsObject(Seq(
          "numberOfcities" -> JsNumber(3),
          "cities" -> JsArray(Seq(
            toJson(cities(0)),
            toJson(cities(1)),
            toJson(cities(2))
          ))
      )))
      }
      describe("with empty repository") {
        val cityController = new FakeCityController()

        it("returns OK with an empty list of cities") {
          val result = cityController.index()(FakeRequest())
          status(result) shouldBe(OK)
          contentAsJson(result) shouldBe(JsObject(Seq(
            "numberOfcities" -> JsNumber(0),
            "cities" -> JsArray(Nil)
          )))
        }
      }
    }
    describe("#create") {
          it("returns CREATED after successful creation") {
            val cityId = UUID.randomUUID()
            val request = FakeRequest().withBody(JsObject(Seq(
              "cityId" -> JsString(cityId.toString),
              "cityName" -> JsString("city1")
            )))
            val result = cityController.create()(request)
            status(result) shouldBe(CREATED)
            contentAsJson(result) should be(JsObject(Seq(
              "cityId" -> JsString(cityId.toString)
            )))
          }
        }
    describe("#get") {

      it("returns BAD_REQUEST for invalid id") {
        val result = cityController.get("someId")(FakeRequest())

        status(result) shouldBe(BAD_REQUEST)
        contentAsJson(result) shouldBe(JsObject(Seq(
          "message" -> JsString("Invalid UUID string: someId")
        )))
      }

      it("finds a city by id and returns OK with it") {
        val result = cityController.get(cities(1).id.toString)(FakeRequest())

        status(result) shouldBe(OK)
        contentAsJson(result) shouldBe(toJson(cities(1)))
      }

      describe("with empty repository") {
        val cityController = new FakeCityController()

        it("finds no city and returns NOT_FOUND with a message") {
          val cityId = UUID.randomUUID
          val result = cityController.get(cityId.toString)(FakeRequest())

          status(result) shouldBe(NOT_FOUND)
          contentAsJson(result) shouldBe(JsObject(Seq(
            "message" -> JsString(s"There is no city with ${cityId} identifier")
          )))
        }
      }
    }
  }
}

