import filters.CorsFilter
import play.api.GlobalSettings
import play.api.mvc.WithFilters

import play.api._
import com.mongodb.casbah.Imports._

object Global extends WithFilters(new CorsFilter) with GlobalSettings {

  override def onStart(app: Application) {
    MongoUnit.cleanAndInsert()
  }
}

object MongoUnit {
  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient("scheduler")
  val citiesCollection = db("cities")
  val hotelsCollection = db("hotels")

  def cleanAndInsert(): Unit = {
    cleanDatabase()

    // citiesCollection.insert(MongoDBObject(
    //   "cityId" -> "a4065490-8cda-45e4-b42c-ff8f7f792ef4",
    //   "cityName" -> "Rio de Janeiro, Brasil"
    // ))
    // citiesCollection.insert(MongoDBObject(
    //   "cityId" -> "c7e6baaf-2812-4e3b-8f29-1c489ceadc15",
    //   "cityName" -> "Fortaleza, Brasil"
    // ))
    // citiesCollection.insert(MongoDBObject(
    //   "cityId" -> "bc587e57-fe83-4ad9-98a9-7bbf0b930a5c",
    //   "cityName" -> "Porto Alegre, Brasil"
    // ))
    //
    // hotelsCollection.insert(MongoDBObject(
    //   "hotelId" -> "51075f93-8ccf-4390-bc17-a370bdf7dbf4",
    //   "hotelName" -> "Fortaleza Park Hotel",
    //   "hotelPrice" -> "98.99",
    //   "cityId" -> "c7e6baaf-2812-4e3b-8f29-1c489ceadc15"
    // ))
    // hotelsCollection.insert(MongoDBObject(
    //   "hotelId" -> "a4cbeb4a-40d9-4ab0-ae07-abd49de5ba3a",
    //   "hotelName" -> "Dunas Hotel",
    //   "hotelPrice" -> "98.99",
    //   "cityId" -> "c7e6baaf-2812-4e3b-8f29-1c489ceadc15"
    // ))
    // hotelsCollection.insert(MongoDBObject(
    //   "hotelId" -> "90675a1e-8340-4acc-9dc5-c691bbf61020",
    //   "hotelName" -> "Iracema Hotel",
    //   "hotelPrice" -> "98.99",
    //   "cityId" -> "c7e6baaf-2812-4e3b-8f29-1c489ceadc15"
    // ))
    // hotelsCollection.insert(MongoDBObject(
    //   "hotelId" -> "adc64b87-4a9d-42af-89ea-c94dd5ecd6f2",
    //   "hotelName" -> "Quality Porto Alegre",
    //   "hotelPrice" -> "100.19",
    //   "cityId" -> "bc587e57-fe83-4ad9-98a9-7bbf0b930a5c"
    // ))
    // hotelsCollection.insert(MongoDBObject(
    //   "hotelId" -> "031c94ae-f1a9-49ce-ae6b-88415cc3fea8",
    //   "hotelName" -> "Vila Galé Rio de Janeiro",
    //   "hotelPrice" -> "138.00",
    //   "cityId" -> "a4065490-8cda-45e4-b42c-ff8f7f792ef4"
    // ))
  }

  def cleanDatabase(): Unit = {
    citiesCollection.drop()
    hotelsCollection.drop()
    db.createCollection("cities", DBObject())
    db.createCollection("hotels", DBObject())
  }
}