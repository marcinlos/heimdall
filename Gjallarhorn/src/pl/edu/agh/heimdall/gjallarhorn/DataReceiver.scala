package pl.edu.agh.heimdall.gjallarhorn

import java.net.ServerSocket
import java.io.ObjectInputStream
import pl.edu.agh.heimdall.consumer.EventConsumer
import pl.edu.agh.heimdall.events.Event
import pl.edu.agh.heimdall.consumer.EventSink
import java.io.IOException
import scala.annotation.tailrec


class DataReceiver(val port: Int, val sink: EventSink) extends Runnable {

  val server = new ServerSocket(port)
  val socket = server.accept()
  val input = new ObjectInputStream(socket.getInputStream())

  override def run() {
    @tailrec
    def readLoop() {
      if (tryProcessEvent())
        readLoop()
    }
    readLoop()
  }

  def tryProcessEvent(): Boolean = {
    try {
      val event = input.readObject().asInstanceOf[Event]
      sink.push(event)
      true
    } catch {
      case e: IOException => false
    }
  }

}