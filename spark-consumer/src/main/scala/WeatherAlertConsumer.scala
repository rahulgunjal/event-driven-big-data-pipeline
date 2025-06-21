import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

object WeatherAlertConsumer {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .appName("WeatherAlertConsumer")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    spark.sparkContext.setLogLevel("WARN")

    // JSON schema
    val schema = new StructType()
      .add("data", ArrayType(
        new StructType()
          .add("temp", DoubleType)
          .add("rh", DoubleType)
          .add("wind_spd", DoubleType)
          .add("precip", DoubleType)
          .add("city_name", StringType)
          .add("datetime", StringType)
      ))

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "weather")
      .option("startingOffsets", "latest")
      .load()

    val jsonDF = df.selectExpr("CAST(value AS STRING)").as[String]

    val weatherDF = jsonDF.select(from_json(col("value"), schema).alias("json"))
      .selectExpr(
        "json.data[0].temp as temp",
        "json.data[0].rh as humidity",
        "json.data[0].wind_spd as wind_spd",
        "json.data[0].precip as precip",
        "json.data[0].city_name as city",
        "json.data[0].datetime as datetime"
      )

    // Add alert OR fallback message
    val eventsDF = weatherDF.withColumn("alert",
      when(col("temp") > 40, concat(lit("Heatwave detected in "), col("city")))
        .when(col("temp") < 5, concat(lit("Frost warning in "), col("city")))
        .when(col("wind_spd") > 50, concat(lit("Storm warning in "), col("city")))
        .when(col("precip") > 10, concat(lit("Heavy rain in "), col("city")))
        .otherwise(lit("No alert - weather normal"))
    )

    val finalEvents = eventsDF.select(
      current_timestamp().alias("processed_at"),
      col("datetime"),
      col("city"),
      col("temp"),
      col("humidity"),
      col("wind_spd"),
      col("precip"),
      col("alert")
    )

    val query = finalEvents.writeStream
      .foreachBatch { (batchDF: Dataset[Row], batchId: Long) =>
        println(s"\n=== Batch $batchId ===")
        batchDF.show(false)

        batchDF.write
          .mode("append")
          .option("header", "true")
          .csv("alerts_output")
      }
      .outputMode("append")
      .start()

    query.awaitTermination()
  }
}
