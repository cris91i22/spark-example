package sql.model

import java.sql.Date

import org.apache.spark.sql.{Encoder, Encoders}

object Convertions  {

  implicit val dateEncoder: Encoder[Date] = Encoders.javaSerialization[Date]

  implicit val ffEncoder: Encoder[FitnessFreakDAO] = Encoders.product[FitnessFreakDAO]
  implicit val rEncoder: Encoder[RoutineDAO] = Encoders.product[RoutineDAO]
  implicit val eEncoder: Encoder[ExerciseDAO] = Encoders.product[ExerciseDAO]

}
