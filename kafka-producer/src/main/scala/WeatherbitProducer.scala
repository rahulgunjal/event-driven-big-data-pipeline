import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, ProducerConfig}
import java.util.Properties
import scala.io.Source
import java.net.URL
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object WeatherbitProducer {
  def main(args: Array[String]): Unit = {

    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topic = "weather"

    val apiKey = "a4d79bbcea674e5e92da1e6d4b8c74b3" 
    val city = "Aurangabad"
    val country = "IN"
    val url = s"https://api.weatherbit.io/v2.0/current?city=$city&country=$country&key=$apiKey"

    println(s"Starting Weatherbit Producer for $city, $country")

    try {
      val json = Source.fromURL(new URL(url)).mkString
      println(s"Fetched weather data: $json")

      val record = new ProducerRecord[String, String](topic, null, json)
      producer.send(record)
      println(s"Sent to Kafka topic '$topic'")

    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      producer.close()
      println("Producer closed.")
    }
  }
}
