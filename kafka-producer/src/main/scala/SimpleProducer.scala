import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object SimpleProducer {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    for (i <- 1 to 10) {
      val record = new ProducerRecord[String, String]("logs", s"key-$i", s"This is log message $i")
      producer.send(record)
      println(s"Sent: key-$i => This is log message $i")
      Thread.sleep(10)
    }

    producer.close()
  }
}
