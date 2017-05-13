import rx.lang.scala.Observable

import scala.concurrent.duration._

/**
  * Created by Minsik on 2017-05-10.
  */
object Main1 extends App {

  override def main(args: Array[String]): Unit = {

    val ticks: Observable[Long] = Observable.interval(Duration(1, SECONDS))

    val evens: Observable[Long] = ticks.filter(_ % 2 == 0)

    val buffers = evens.slidingBuffer(count = 3, skip = 1)

    val sub = buffers.subscribe(println(_))

    readLine()

    sub.unsubscribe()
  }
}
