package pl.edu.agh.heimdall.gjallarhorn

import scala.swing.BorderPanel
import scala.swing.ListView

import pl.edu.agh.heimdall.consumer.EventConsumer
import pl.edu.agh.heimdall.consumer.EventSink
import pl.edu.agh.heimdall.events.Call
import pl.edu.agh.heimdall.events.Catch
import pl.edu.agh.heimdall.events.Event
import pl.edu.agh.heimdall.events.Return
import pl.edu.agh.heimdall.events.Throw

class ThreadSequencePanel extends BorderPanel with EventConsumer with EventSink {

  private val list = new ListView[Event]

  add(list, BorderPanel.Position.Center)
  
  def called(info: Call) {
    
  }
  
  def returned(info: Return) {
    
  }
  
  def throwed(info: Throw) {
    
  }
  
  def catched(info: Catch) {
    
  }
  
  def push(e: Event) {
    e.dispatch(this)
  }
}