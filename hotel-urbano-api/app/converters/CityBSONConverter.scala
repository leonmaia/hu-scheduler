package converters

import java.net.URL

import reactivemongo.bson._
import models.City
import java.util.UUID

object CityBSONConverter extends BSONDocumentWriter[City] with BSONDocumentReader[City] {

}
