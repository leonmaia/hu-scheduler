import com.mongodb.casbah.Imports._

object MongoUnit {
  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient("mydb")
  val citiesCollection = db("testcities")
  val hotelsCollection = db("testhotels")

  def cleanAndInsert(): Unit = {
    cleanDatabase()

    citiesCollection.insert(MongoDBObject(
      "cityId" -> "a4065490-8cda-45e4-b42c-ff8f7f792ef4",
      "cityName" -> "Rio de Janeiro, Rio de Janeiro Estado, Regiao Sudeste, Brasil"
    ))
    citiesCollection.insert(MongoDBObject(
      "cityId" -> "c7e6baaf-2812-4e3b-8f29-1c489ceadc15",
      "cityName" -> "Fortaleza, Ceara, Regiao Nordeste, Brasil"
    ))
    citiesCollection.insert(MongoDBObject(
      "cityId" -> "bc587e57-fe83-4ad9-98a9-7bbf0b930a5c",
      "cityName" -> "Porto Alegre, Rio Grande do Sul, Regiao Sul, Brasil"
    ))

    hotelsCollection.insert(MongoDBObject(
      "hotelId" -> "51075f93-8ccf-4390-bc17-a370bdf7dbf4",
      "hotelName" -> "Fortaleza Park Hotel",
      "hotelPrice" -> "98,99",
      "cityId" -> "c7e6baaf-2812-4e3b-8f29-1c489ceadc152b"
    ))
    hotelsCollection.insert(MongoDBObject(
      "hotelId" -> "adc64b87-4a9d-42af-89ea-c94dd5ecd6f2",
      "hotelName" -> "Quality Porto Alegre",
      "hotelPrice" -> "100,19",
      "cityId" -> "bc587e57-fe83-4ad9-98a9-7bbf0b930a5c"
    ))
    hotelsCollection.insert(MongoDBObject(
      "hotelId" -> "031c94ae-f1a9-49ce-ae6b-88415cc3fea8",
      "hotelName" -> "Vila Galé Rio de Janeiro",
      "hotelPrice" -> "138,00",
      "cityId" -> "a4065490-8cda-45e4-b42c-ff8f7f792ef4"
    ))
  }

  def cleanDatabase(): Unit = {
    citiesCollection.drop()
    hotelsCollection.drop()
    db.createCollection("testcities", DBObject())
    db.createCollection("testhotels", DBObject())
  }
}
