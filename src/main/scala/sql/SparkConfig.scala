package sql

trait SparkConfig {

  lazy val master = "local[2]"
  lazy val sparkHome = "SPARK_HOME"

  lazy val jarPath = "/home/cmedina/dev/workspaces/spark-example/target/scala-2.11/spark-example_2.11-1.0.jar"

  lazy val driver = "org.postgresql.Driver"
  lazy val jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/local"
  lazy val user = "postgres"
  lazy val password = "1234"


}
