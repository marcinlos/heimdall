package pl.edu.agh.heimdall.gjallarhorn

import scala.swing._
import scala.actors.threadpool.Executors


object Main extends SimpleSwingApplication {
  
  val sink = new Printer
  val receiver = new DataReceiver(2000, sink)
  val exec = Executors.newSingleThreadExecutor()
  
  def top = new MainFrame {
    title = "Gjallarhorn"
    size = new Dimension(700, 400)
    centerOnScreen()

    contents = new TabbedPane() {
      
    }
  }
  exec.execute(receiver)
  

}