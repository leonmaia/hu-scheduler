import com.mongodb.casbah.Imports._

object MongoUnit {
  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient("mydb")
  val collection = db("testcities")

  def cleanAndInsert(): Unit = {
    cleanDatabase()

    collection.insert(MongoDBObject(
      "cityId" -> "a4065490-8cda-45e4-b42c-ff8f7f792ef4",
      "cityName" -> "Rio de Janeiro, Rio de Janeiro Estado, Brasil"
    ))
    collection.insert(MongoDBObject(
      "cityId" -> "c7e6baaf-2812-4e3b-8f29-1c489ceadc15",
      "cityName" -> "Fortaleza, Região Nordeste, Brasil"
    ))
    collection.insert(MongoDBObject(
      "cityId" -> "bc587e57-fe83-4ad9-98a9-7bbf0b930a5c",
      "cityName" -> "city3"
    ))
  }

  def cleanDatabase(): Unit = {
    collection.drop()
    db.createCollection("testcities", DBObject())
  }
}
