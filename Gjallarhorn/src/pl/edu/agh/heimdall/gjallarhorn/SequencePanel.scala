package pl.edu.agh.heimdall.gjallarhorn

import scala.collection.mutable.Map
import scala.swing.TabbedPane
import pl.edu.agh.heimdall.consumer.EventSink
import pl.edu.agh.heimdall.events.Event
import pl.edu.agh.heimdall.consumer.EventConsumer


class SequencePanel extends TabbedPane with MultiplexingSink[ThreadSequencePanel] {

  override def newSink(thread: String): ThreadSequencePanel =
    new ThreadSequencePanel
  
}