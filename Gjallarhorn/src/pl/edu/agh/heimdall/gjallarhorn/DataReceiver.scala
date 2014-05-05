package pl.edu.agh.heimdall.gjallarhorn

import java.net.ServerSocket
import java.io.ObjectInputStream
import pl.edu.agh.heimdall.consumer.EventConsumer
import pl.edu.agh.heimdall.events.Event
import pl.edu.agh.heimdall.consumer.EventSink

class DataReceiver(val port: Int, val sink: EventSink) extends Runnable {
  
  val server = new ServerSocket(port)
  val socket = server.accept()
  val input = new ObjectInputStream(socket.getInputStream())
  
  override def run() {
    while (true) {
      val data = input.readObject()
      data match {
        case e: Event => sink.push(e)
      }
    }
  }

}