package sql.model

import java.sql.Date
import java.util

case class FitnessFreakDAO(id: Long,
                          name: String,
                          birthday: Date)

case class RoutineDAO(id: Long,
                      freakId: Long,
                      name: String,
                      startDate: Date)

case class ExerciseDAO(id: Long,
                      routineId: Long,
                      description: String,
                      series: Seq[Int])
