package sql

import java.sql.{Connection, Date, DriverManager}
import java.util.Properties
import sql.model.{ExerciseDAO, FitnessFreakDAO, RoutineDAO}

trait LoadData {

  this: SparkConfig =>

  def run(qtyOfRows: Int): Unit = {
    var connection: Connection = null

    try {
      // make the connection
      Class.forName(driver)
      val p = new Properties()

      connection = DriverManager.getConnection(jdbcUrl, user, password)

      createSchema(connection, "fitness_freaks", "CREATE TABLE fitness_freaks(id bigserial primary key, name varchar(90) NOT NULL, birthday date NOT NULL)")
      createSchema(connection, "\"routines\"", "CREATE TABLE \"routines\"(id bigserial primary key, freak_id bigserial references fitness_freaks, name varchar(40) NOT NULL, start_date date NOT NULL)")
      createSchema(connection, "exercises", "CREATE TABLE exercises(id bigserial primary key, routine_id bigserial references routines, description varchar(256) NOT NULL, series integer[])")

      // create the statement, and run the select query
      val statement = connection.createStatement()

      (1 to 100000).foreach{ i =>
        val n = FitnessFreakDAO(i, s"Loro $i", new Date(21321321))
        statement.execute(s"INSERT INTO fitness_freaks (name, birthday) VALUES ('${n.name}', current_date)")
        (1 to 3).foreach{ j =>
          val n2 = RoutineDAO(j, i, s"Routine for usr $i", new Date(21321321))
          statement.execute(s"INSERT INTO routines (freak_id, name, start_date) VALUES (${n2.freakId}, '${n2.name}', current_date)")
          (1 to 4).foreach{z =>
            val n3 = ExerciseDAO(z, j, s"Exercise $z to routine $j", List(10,10,8,8))
            statement.execute(s"INSERT INTO exercises (routine_id, description, series) VALUES (${n3.routineId}, '${n3.description}', ARRAY[${n3.series.mkString(",")}])")
          }
        }
      }
      statement.executeBatch()
    } catch {
      case e: Exception => e.printStackTrace()
    }

    connection.close()
  }

  private def createSchema(connection: Connection, tbName: String, query: String) = {
    if(!connection.getMetaData.getTables(null,null,tbName, null).next()){
      val statement = connection.createStatement()
      statement.execute(query)
    }
  }

}
