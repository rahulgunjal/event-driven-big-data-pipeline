import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SimpleConsumer {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .appName("SparkKafkaConsumer")
      .master("local[*]")  // Use all cores locally
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    // Read stream from Kafka
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "logs")
      .option("startingOffsets", "earliest")
      .load()

    // Cast binary key & value to string
    val logsDF = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")

    val logsWithTimestamp = logsDF.withColumn("timestamp", current_timestamp())

    val query = logsWithTimestamp.writeStream
      .outputMode("append")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
