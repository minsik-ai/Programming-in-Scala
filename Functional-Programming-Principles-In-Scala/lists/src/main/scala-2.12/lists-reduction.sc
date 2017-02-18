def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  (xs foldRight List[U]())((elem, list) => f(elem) :: list )

def lengthFun[T](xs: List[T]): Int =
  (xs foldRight 0)((elem, count) => count + 1 )

lengthFun[Int](List(1, 2, 3, 4))

mapFun[String, Int](List("a", "vs", "dji"), (x => x.length()))