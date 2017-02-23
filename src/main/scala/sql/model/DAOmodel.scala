package sql.model

import java.sql.Date

case class FitnessFreakDAO(id: Long,
                          name: String,
                          birthday: Date)

case class RoutineDAO(id: Long,
                     freakId: Long,
                     name: String,
                     date: Date)

case class ExerciseDAO(id: Long,
                      routineId: Long,
                      description: String,
                      series: List[Int])
