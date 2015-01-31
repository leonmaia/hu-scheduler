package converters

import java.net.URL

import reactivemongo.bson._
import models.Bundle
import java.util.UUID

object BundleBSONConverter extends BSONDocumentWriter[Bundle] with BSONDocumentReader[Bundle] {

  override def write(bundle: Bundle): BSONDocument = {
    val baseDoc = BSONDocument(
      "bundleId" -> bundle.id.toString,
      "bundleName" -> bundle.name,
      "bundlePrice" -> bundle.price
    )
    val optionalFields = Seq(
      ("cityId", bundle.cityId.map(BSONString(_)))
    )
    optionalFields.foldLeft(baseDoc) { case (bson, (key, optionalValue)) =>
      optionalValue match {
        case Some(value) => bson ++ BSONDocument(Seq(key -> value))
        case None => bson
      }
    }
  }

  override def read(bson: BSONDocument): Bundle = {
    Bundle(UUID.fromString(bson.getAs[String]("bundleId").get),
           bson.getAs[String]("bundleName").get,
           bson.getAs[String]("bundlePrice").get,
           bson.getAs[String]("cityId")
    )
  }

}
