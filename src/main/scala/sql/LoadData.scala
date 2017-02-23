package sql

import java.sql.{Connection, Date, DriverManager}

import sql.model.{ExerciseDAO, FitnessFreakDAO, RoutineDAO}

trait LoadData {

  this: SparkConfig =>

  def run(qtyOfRows: Int): Unit = {
    var connection: Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(jdbcUrl, user, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()

      if (!connection.getMetaData.getTables(null, null, "fitness_freaks", null).next()){
        statement.execute("CREATE TABLE fitness_freaks(id bigserial primary key, name varchar(20) NOT NULL, birthday date NOT NULL)")
      }
      if (!connection.getMetaData.getTables(null, null, "routines", null).next()){
        statement.execute("CREATE TABLE routines(id bigserial primary key, freak_id bigserial references fitness_freaks, name varchar(20) NOT NULL, date date NOT NULL)")
      }
      if (!connection.getMetaData.getTables(null, null, "exercises", null).next()){
        statement.execute("CREATE TABLE exercises(id bigserial primary key, routine_id bigserial references routines, description varchar(256) NOT NULL, series integer[])")
      }

      (1 to 5).foreach{ i =>
        val n = FitnessFreakDAO(i, s"Loro $i", new Date(21321321))
        statement.addBatch(s"INSERT INTO fitness_freaks (id, name, birthday) VALUES (${n.id}, '${n.name}', '${n.birthday}')")
//        (1 to 2).foreach{ j =>
//          val n2 = RoutineDAO(j, i, s"Routine for usr $i", new Date(2017, 1, 1))
//          statement.addBatch(s"INSERT INTO routines (id, freak_id, name, date) VALUES (${n2.id}, ${n2.freakId}, '${n2.name}', ${n2.date})")
//          (1 to 2).foreach{z =>
//            val n3 = ExerciseDAO(z, j, s"Exercise $z to routine $j", List(10,10,8,8))
//            statement.addBatch(s"INSERT INTO exercises (id, routine_id, description, series) VALUES (${n3.id}, ${n3.routineId}, '${n3.description}', ${n3.series.mkString(",")})")
//          }
//        }
      }
      statement.executeBatch()
    } catch {
      case e: Exception => e.printStackTrace()
    }

    connection.close()
  }

}
