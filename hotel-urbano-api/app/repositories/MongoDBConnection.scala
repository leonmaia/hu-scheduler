package repositories

import play.api.Play
import reactivemongo.api.MongoDriver
import scala.concurrent.ExecutionContext.Implicits.global

trait MongoDBConnection {

  val configuration = Play.current.configuration

  val driver = new MongoDriver
  val connection = driver.connection(configuration.getStringSeq("mongo.collection.hosts")
                                                  .getOrElse(Seq("localhost")))

  val db = connection(configuration.getString("mongo.scheduler.database")
                                   .getOrElse("scheduler"))
}
