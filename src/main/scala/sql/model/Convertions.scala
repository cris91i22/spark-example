package sql.model

import org.apache.spark.sql.{Encoder, Encoders}

object Convertions  {

  implicit val encoder: Encoder[SparkModel] = Encoders.product[SparkModel]

}
