name := "spark-example"

version := "1.0"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core_2.11
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11"  % "2.1.0"
libraryDependencies += "org.postgresql"   % "postgresql"      % "9.4.1208.jre7"
libraryDependencies += "joda-time"        % "joda-time"       % "2.9.7"
