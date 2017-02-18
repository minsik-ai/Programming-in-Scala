def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last[T](ys)
}

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init of empty list")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case z :: zs => z :: concat(zs, ys)
}

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => List()
  case y :: ys => reverse(ys) ++ List(y)
}       // O(n^2)

def removeAt[T](n: Int, xs: List[T]): List[T] = xs match {
  case List() => List()
  case y :: ys =>
    if (n == 0) ys
    else y :: removeAt[T](n-1, ys)
}

//def removeAt[T](n:Int, xs: List[T]): List[T] = (xs take n) ::: (xs drop n+1)
removeAt(3, List('a','b','c','d'))

def flatten(xs: List[Any]): List[Any] = xs match {
  case List() => List()
  case _ =>
    xs.head match {
      case list: List[Any] => flatten(list) ++ flatten(xs.tail)
        // if head is a List, flatten it & concat its elements to flattened tail
      case elem => elem :: flatten(xs.tail)
        // if head is not a list, just prepend element to xs.tail.
    }
}

flatten(List(List(1, 1), 2, List(3, List(5, 8))))