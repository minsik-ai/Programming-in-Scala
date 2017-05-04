object rationalsTest {
  val x = new Rational(1,3)
  val y = new Rational(5,7)
  val z = new Rational(3,2)

  x - y - z
  y + y
  x max y

  new Rational(2)

  val strange = new Rational(1, 0)
  strange + strange

  class Rational (x :Int, y :Int) {
    require(y != 0, "denominator must not be zero")

    def this(x: Int) = this(x, 1)

    private def gcd(a: Int, b: Int): Int = if(b==0) a else gcd(b, a % b)

    def numer = x
    def denom = y

    //Overflow can happen here!
    def <(that: Rational) = this.numer * that.denom < that.numer * this.denom

    def max(that: Rational) = if (this < that) that else this

    //Overflow can happen here!
    def + (that :Rational) = new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

    def unary_- :Rational = new Rational(-numer, denom)

    def - (that :Rational) = this + -that

    override def toString: String = {
      val g = gcd(numer, denom)
      numer/g + "/" + denom/g
    }
  }
}