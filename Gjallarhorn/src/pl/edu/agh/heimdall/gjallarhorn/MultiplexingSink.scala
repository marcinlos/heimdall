package pl.edu.agh.heimdall.gjallarhorn

import scala.collection.mutable.Map
import pl.edu.agh.heimdall.consumer.EventSink
import pl.edu.agh.heimdall.consumer.EventConsumer
import pl.edu.agh.heimdall.events.Event


trait MultiplexingSink[T <: EventConsumer] extends EventSink {
  
  def newSink(thread: String): T
  
  val sinks = Map[String, T]()
  
  def sink(thread: String): T = 
    sinks.get(thread) match {
      case Some(sink) => sink
      case None =>
        val sink = newSink(thread)
        sinks(thread) = sink
        sink
    }

  override def push(event: Event) {
    val thread = event.thread
    event.dispatch(sink(thread))
  }
  
}