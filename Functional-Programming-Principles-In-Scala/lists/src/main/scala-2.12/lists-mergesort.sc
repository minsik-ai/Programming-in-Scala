import math.Ordering

def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
  val n = xs.length / 2
  if(n == 0) xs
  else {

    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
        case (xs, Nil) => xs
        case (Nil, ys) => ys
        case (x :: xs1, y :: ys1) =>
          if (ord.lt(x, y)) x :: merge(xs1, ys)
          else y :: merge(xs, ys1)
      }

    val (fst, snd) = xs.splitAt(n)
    merge(msort(fst), msort(snd))
  }
}

msort(List(1, 2,3, 6.1, 3, 2, 6,2.1, 10.9, 5, 7))