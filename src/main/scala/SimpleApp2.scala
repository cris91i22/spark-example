import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SimpleApp2 {

  /**
    * Start database with docker
    * docker run --name some-postgres -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=local -d -p 5432:5432 postgres
    * psql -h 127.0.0.1 -p 5432 -U postgres
    *
    * CREATE TABLE spark(id int,name varchar(255));
    * INSERT INTO spark VALUES (1, "coco");
    *
    * SPARK_HOME is a environment variable set on .bashrc with "/home/cmedina/dev/programs/spark-2.1.0-bin-hadoop2.7"
    *
    *
    * http://spark.apache.org/docs/latest/sql-programming-guide.html
    */
  def main(args: Array[String]): Unit = {

    val sConf = new SparkConf(false).
      setMaster("local[2]").
      setAppName("Simple Application").
      setSparkHome("SPARK_HOME").
      setJars(Seq("target/scala-2.11/spark-example_2.11-1.0.jar"))

    val spark = SparkSession
      .builder()
      .config(sConf)
      .getOrCreate()

    // For implicit conversions like converting RDDs to DataFrames

    val jdbcDF = spark.read
      .format("jdbc")
      .option("url", "jdbc:postgresql://127.0.0.1:5432/local")
      .option("dbtable", "spark")
      .option("user", "postgres")
      .option("password", "1234")
      .load()

    val n = jdbcDF.filter("name = 'coco'")
    n.foreach{ r =>
      println(r)
    }

    jdbcDF.foreach{r =>
      println(r)
    }
  }

}
