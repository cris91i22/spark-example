package sql.model

import org.apache.spark.sql.{Encoder, Encoders}

object Convertions  {

  implicit val ffEncoder: Encoder[FitnessFreak] = Encoders.product[FitnessFreak]
  implicit val rEncoder: Encoder[Routine] = Encoders.product[Routine]
  implicit val eEncoder: Encoder[Exercise] = Encoders.product[Exercise]

}
