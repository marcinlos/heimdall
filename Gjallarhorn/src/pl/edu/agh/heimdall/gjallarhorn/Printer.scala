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


class Printer extends MultiplexingSink[StdoutPrinter] {

  def newSink(thread: String): StdoutPrinter = new StdoutPrinter(thread)
  
}