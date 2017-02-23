package sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import sql.model.FitnessFreak

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
        run(500000)

    val freaks = createBasicDF(spark, "fitness-freaks")
    val exercises = createBasicDF(spark, "exercises")
    val routines = createBasicDF(spark, "routines")


    import sql.model.Convertions._

    val filteredCol = time(freaks.as[FitnessFreak].filter(_.name == "coco 0"))

    filteredCol.foreach{ r =>
      println(r)
    }

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


