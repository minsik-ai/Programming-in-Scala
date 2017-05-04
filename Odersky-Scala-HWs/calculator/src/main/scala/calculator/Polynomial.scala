package calculator

import scala.math.sqrt

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal[Double](b()*b() - 4*a()*c())
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    def expr: Set[Double] =
      if (delta() < 0)
        Set()
      else if(delta() == 0)
        Set(-b()/(2*a()))
      else
        Set((-b() + sqrt(delta()))/(2*a()), (-b() - sqrt(delta()))/(2*a()))

    Signal[Set[Double]](expr)
  }
}
