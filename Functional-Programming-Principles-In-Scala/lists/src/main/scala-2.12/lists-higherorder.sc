def squareList0(xs: List[Int]): List[Int] =
  xs match {
    case Nil => Nil
    case y :: ys => y * y :: squareList0(ys)
  }

def squareList1(xs: List[Int]): List[Int] =
  xs map (x => x * x)

// Tail-Recursive
def squareList2(xs: List[Int]): List[Int] = {
  def innerSqList(acc: List[Int], xs1: List[Int]): List[Int] = {
    xs1 match {
      case Nil => acc
      case y :: ys => innerSqList(acc ++ List(y * y), ys)
    }
  }

  xs match {
    case Nil => Nil
    case y :: ys => innerSqList(List(y * y), ys)
  }
}

val trylist = List(1, 4, 2, 7, 5, 3, 0, -1)

squareList0(trylist)
squareList1(trylist)
squareList2(trylist)

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 => {
    val (f, fnot) = xs.span(e => e == x)
    f :: pack(fnot)
  }
}

def encode[T](xs: List[T]): List[(T, Int)] =
  pack(xs).map(e => (e.head, e.length))

val l = List("a", "a", "a", "b", "c", "c", "a")

pack(l)
encode(l)