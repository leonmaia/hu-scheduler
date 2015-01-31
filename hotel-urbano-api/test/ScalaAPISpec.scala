import play.api.libs.ws.WS
import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatestplus.play._

class SchedulerAPISpec extends PlaySpec with OneServerPerSuite {

  implicit override lazy val app: FakeApplication =
    FakeApplication(
      additionalConfiguration = Map(
        "mongo.connection.uriList" -> Seq("localhost"),
        "mongo.scheduler.database" -> "mydb",
        "mongo.cities.collection" -> "cities"
      )
    )

  "Scheduler API" should {
    "return a list of cities" in {
      val myPublicAddress =  s"localhost:$port"
      val testPaymentGatewayURL = s"http://$myPublicAddress/cities"
      val response = await(WS url(testPaymentGatewayURL) get())

      response.status mustBe(OK)
      response.body mustBe("""{"numberOfcities":0,"cities":[]}""")
    }
  }
}
