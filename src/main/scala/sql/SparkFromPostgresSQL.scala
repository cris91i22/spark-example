package sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Column, DataFrame, SparkSession}
import sql.model.{ExerciseDAO, FitnessFreakDAO, RoutineDAO}

object SparkFromPostgresSQL extends SparkConfig with LoadData {

  /**
    * sbt package
    *
    * Start database with docker
    * docker run -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=local -d -p 5432:5432 postgres
    * psql -h 127.0.0.1 -p 5432 -U postgres
    *
    * SPARK_HOME is a environment variable set on .bashrc with "/home/cmedina/dev/programs/spark-2.1.0-bin-hadoop2.7"
    *
    * http://spark.apache.org/docs/latest/sql-programming-guide.html
    */
  def main(args: Array[String]): Unit = {
    val sConf = new SparkConf(false).
      setMaster(master).
      setAppName("Simple Application").
      setSparkHome(sparkHome).
      setJars(Seq(jarPath))

    val spark = SparkSession.builder().config(sConf).getOrCreate()

    // Load app
//    run(500000)

    import sql.model.Convertions._

    val freaks = createBasicDF(spark, "fitness_freaks").as[FitnessFreakDAO]
    val routines = createBasicDF(spark, "routines").withColumnRenamed("freak_id", "freakId").withColumnRenamed("start_date", "startDate").as[RoutineDAO]
    val exercises = createBasicDF(spark, "exercises").withColumnRenamed("routine_id", "routineId").as[ExerciseDAO]


//    val filteredCol = time(freaks.filter(_.name == "Loro 1"))
//    val filteredCol2 = time(routines.filter(_.id == 1))
//    val filteredCol3 = time(exercises.filter(_.routineId == 1))

    val k = freaks.filter(_.name == "Loro 1").join(routines, freaks("id") === routines("freakId"))

    k.collect().foreach{r =>
      println(r)
    }

    spark.stop()
  }

  def time[A](a: => A): A = {
    val now = System.nanoTime
    val result = a
    val milis = (System.nanoTime - now) / 1000000
    println("%d ms.".format(milis))
    result
  }

  def createBasicDF(ss: SparkSession, tableName: String): DataFrame = {
    ss.read
      .format("jdbc")
      .option("url", jdbcUrl)
      .option("dbtable", tableName)
      .option("user", user)
      .option("password", password)
      .load()
  }

}


