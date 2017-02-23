package sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import sql.model.SparkModel

object SparkFromPostgresSQL extends SparkConfig with LoadData {

  /**
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

    val spark = SparkSession
      .builder()
      .config(sConf)
      .getOrCreate()

    val jdbcDF = spark.read
      .format("jdbc")
      .option("url", jdbcUrl)
      .option("dbtable", dbTable)
      .option("user", user)
      .option("password", password)
      .load()

//    run(500000)

    val n = jdbcDF.filter("name = 'coco 0'")

    import sql.model.Convertions._

    n.as[SparkModel].foreach{ r =>
      println(r)
    }

//    jdbcDF.foreach{r =>
//      println(r)
//    }
  }

}


