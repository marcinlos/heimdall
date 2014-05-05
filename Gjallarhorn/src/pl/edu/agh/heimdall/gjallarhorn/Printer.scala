package pl.edu.agh.heimdall.gjallarhorn

import pl.edu.agh.heimdall.consumer.EventConsumer
import pl.edu.agh.heimdall.events.Call
import pl.edu.agh.heimdall.events.Throw
import pl.edu.agh.heimdall.events.Return
import pl.edu.agh.heimdall.events.Catch
import pl.edu.agh.heimdall.output.StdoutPrinter
import scala.collection.mutable.Map
import pl.edu.agh.heimdall.consumer.EventSink
import pl.edu.agh.heimdall.events.Event


class Printer extends EventSink {
  
  private val printers = Map[String, EventConsumer]()
  
  def getPrinter(thread: String): EventConsumer =
    printers.get(thread) match {
      case Some(printer) => printer
      case None =>
        val printer = new StdoutPrinter(thread)
        printers(thread) = printer
        printer
    }
    
  override def push(event: Event) {
    val thread = event.thread
    event.dispatch(getPrinter(thread))
  }
  
}