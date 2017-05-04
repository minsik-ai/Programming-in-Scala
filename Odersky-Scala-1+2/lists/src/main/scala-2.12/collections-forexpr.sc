def scalarProduct0(xs: List[Double], ys: List[Double]): Double =
  (xs zip ys).map { case (x, y) => x * y }.sum

def scalarProduct1(xs: List[Double], ys: List[Double]): Double = {
  (for ((x, y) <- xs zip ys) yield (x * y)).sum
}

scalarProduct0(List[Double](1, 2, 3), List[Double](1, 2, 3))
scalarProduct1(List[Double](1, 2, 3), List[Double](1, 2, 3))

// N-Queens problem

//columns
def nqueens(n: Int): Set[List[Int]] = {
  //rows
  def placeQueens(k: Int): Set[List[Int]] = {
    if (k == 0) Set(List())
    else
      for {
        queens <- placeQueens(k - 1)
        col <- 0 until n
        if isSafe(col, queens)
      } yield col :: queens
  }
  placeQueens(n)
}
def isSafe(col: Int, queens: List[Int]): Boolean = {
  val row = queens.length
  val qWithRow = (row -1 to 0 by -1) zip queens
  qWithRow.forall{
    case (r, c) => col != c && math.abs(col - c) != row - r
  }
}

nqueens(4)