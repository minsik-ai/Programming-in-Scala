trait Generator[+T] {
  self =>

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  val random = new java.util.Random

  def generate: Int = random.nextInt
}

val booleans = integers.map(_ > 0)

trait Tree

case class Inner(left: Tree, right: Tree) extends Tree

case class Leaf(x: Int) extends Tree

// Tree Generator

object treeGens {
  val trees: Generator[Tree] = {
      for {
        isLeaves <- booleans
        x <- if (isLeaves) leaves else inners
      } yield x
  }

  val leaves: Generator[Leaf] =
    for {
      x <- integers
    } yield Leaf(x)

  val inners: Generator[Inner] =
    for {
      l <- trees
      r <- trees
    } yield Inner(l, r)

}

treeGens.trees.generate