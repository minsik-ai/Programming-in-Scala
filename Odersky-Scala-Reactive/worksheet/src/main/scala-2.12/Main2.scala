import rx.lang.scala.Observable

import scala.concurrent.duration._

/**
  * Created by Minsik on 2017-05-10.
  */
object Main2 extends App {

  override def main(args: Array[String]): Unit = {

    val xs: Observable[Int] = Observable.from(List(3, 2, 1))

    val yss: Observable[Observable[Int]] =
      xs.map(x => Observable.interval(x seconds).map(_ => x).take(2))

    val zs: Observable[Int] = yss.flatten

    val zs2: Observable[Int] = yss.concat

    val zs3: Observable[Int] = xs.flatMap(x => Observable.interval(x seconds).map(_ => x).take(2))

    val s = zs2.subscribe(println(_))

    readLine()
  }

}
