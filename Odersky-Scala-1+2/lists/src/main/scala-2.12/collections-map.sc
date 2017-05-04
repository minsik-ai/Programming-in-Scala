
class Poly(terms0: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = terms0
  val terms2 = terms0 withDefaultValue 0.0

  def +(other: Poly) = new Poly(terms ++ (other.terms map (adjust0(_))))
  def +@(other: Poly) = new Poly(terms2 ++ (other.terms2 map adjust1))
  def +#(other: Poly) = new Poly((other.terms2 foldLeft terms2)(addTerm))

  def adjust0(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    terms get exp match {
      case Some(coeff1) => exp -> (coeff + coeff1)
      case None => exp -> coeff
    }
  }
  def adjust1(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    exp -> (coeff + terms2(exp))
  }

  def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
    val (exp, coeff) = term
    terms + (exp -> (terms(exp) + coeff))
  }

  override def toString =
    (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString " + "
}

val p1 = new Poly(Map(1 -> 2.0, 3 -> 2.0, 5 -> 7.0))
val p2 = new Poly(Map(1 -> 4.1, 2 -> 1.2, 3 -> 7.1))

p1 +@ p2
p1 +# p2