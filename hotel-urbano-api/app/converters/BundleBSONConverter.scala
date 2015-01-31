package converters

import java.net.URL

import reactivemongo.bson._
import models.Bundle
import java.util.UUID

object BundleBSONConverter extends BSONDocumentWriter[Bundle] with BSONDocumentReader[Bundle] {
}
