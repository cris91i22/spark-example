import org.apache.spark.{SparkConf, SparkContext}

object SimpleApp {

  def main(args: Array[String]): Unit = {
    val logFile = getClass.getResource("README.txt").getPath
    val sConf = new SparkConf(false).
      setMaster("local[2]").
      setAppName("Simple Application").
      setSparkHome("SPARK_HOME").
      // SPARK_HOME is a environment variable set on .bashrc with "/home/cmedina/dev/programs/spark-2.1.0-bin-hadoop2.7"
      setJars(Seq("target/scala-2.11/spark-example_2.11-1.0.jar"))

    val sc = new SparkContext(sConf)
    val logData = sc.textFile(logFile, 2).cache()
    val words = logData.flatMap(line => line.split(" ")).map(w => (w, 1)).cache()
    val t = words.reduceByKey(_ + _).collect()


    val data = 1 to 10000
    val nms = sc.parallelize(data).filter(_ % 2 == 0).collect()

    println(nms.mkString(", "))
    println(t.mkString(", "))

    sc.stop()
  }

}
