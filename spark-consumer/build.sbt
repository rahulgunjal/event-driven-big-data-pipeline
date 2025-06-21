name := "SparkKafkaConsumer"

version := "0.1"

scalaVersion := "2.12.18"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.5.1",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.5.1"
)

javaOptions += "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED"
