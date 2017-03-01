package sql.model

import org.joda.time.LocalDate

case class FitnessFreak(id: Long,
                        name: String,
                        birthday: LocalDate,
                        routines: List[Routine])

case class Routine(id: Long,
                   freakId: Long,
                   name: String,
                   startDate: LocalDate,
                   exercises: List[Exercise])

case class Exercise(id: Long,
                    routineId: Long,
                    description: String,
                    series: List[Int])