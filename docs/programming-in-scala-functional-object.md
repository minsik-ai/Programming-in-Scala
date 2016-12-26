# Programming in Scala

## Chapter 6

### 함수형 객체

다음은 분수 인스턴스를 생성하는데 사용되는 Fraction 클라스 이다. 

```Scala
/** 
  * Fraction 객체는 변경 불가능 하므로, 생성시 필요한 정보들을 모두 제공하여야 한다.
  * n과 d를 클래스 파라미터라고 부르고, 이 두 파라미터를 종합해서 주 생성자(primary constructor)를 만든다.
  */
class Fraction (n:Int, d:Int) {
  //전제 조건(precondition)을 체크한다. 분수의 경우, 분모(d)가 0이면 안된다.
  require(d != 0)

  //최대 공약수 g를 구하여 private 변수로 사용한다. 이는 내부 계산에 사용된다.
  private val g = gcd(n.abs, d.abs)

  //인스턴스 내부의 값을 객체 밖에서 사용할 수 있도록, val변수로 다시 정의해준다.
  val numeric = n / g
  val denominator = d / g

  //인자를 하나만 받는 보조 생성자를 정의하여 사용한다.
  def this(n:Int) = this(n, 1)

  //toString 함수를 오버라이드 하여, 분수에 어울리는 결과값이 출력되도록 한다.
  override def toString():String = numeric + "/" + denominator

  //+ 메소드로서, 중위 연산자로 사용하기 위해 정의되었다.(물론 메소드로도 사용 가능)
  def +(that: Fraction): Fraction =
    new Fraction(this.numeric * that.denominator + that.numeric * this.denominator, this.denominator * that.denominator)

  //역시 + 메소드이지만 정수를 인자로 받도록 오버로드 되었다.
  def +(that: Int): Fraction =
    new Fraction(this.numeric + that * this.denominator, this.denominator)

  def -(that: Fraction): Fraction =
    new Fraction(this.numeric * that.denominator - that.numeric * this.denominator, this.denominator * that.denominator)

  def -(that: Int): Fraction =
    new Fraction(this.numeric - that * this.denominator, this.denominator)

  def *(that: Fraction): Fraction =
    new Fraction(this.numeric * that.numeric, this.denominator * that.denominator)

  def *(that: Int): Fraction =
    new Fraction(this.numeric * that, this.denominator)

  def /(that: Fraction): Fraction =
    new Fraction(this.numeric * that.denominator, this.denominator * that.numeric)

  def /(that: Int): Fraction =
    new Fraction(this.numeric, this.denominator * that)

  //최소공배수(GCD)를 얻는데 사용되는 private 메소드이다.
  private def gcd(a:Int, b:Int):Int =
    if(b == 0) a else gcd(b, a % b)
}
```

위의 Fraction 클래스는 Fraction 및 Int 인스턴스와의 사칙연산을 지원한다. (`val r = new Fraction(1, 2); r * r; r * 2`) 그러나 Int에는 Fraction을 인자로 받는 사칙연산이 없으므로, `2 * r`과 같은 경우는 지원되지 않는다. 따라서, 이와 같은 연산을 사용하려면 **암시적 타입 변환(Implicit Type Conversion)**이 필요하다. 이를 위해서는, 계산이 수행되는 scope(예: scala console)에 다음과 같은 변환 메소드를 정의해주면 된다.

```Scala
import scala.language.implicitConversions
implicit def intToFrac(x: Int) = new Fraction(x)
```

위의 코드에서 `import scala.language.implicitConversions` 없이도 암시적 타입 변환을 사용 가능하지만, 해당 코드를 포함함으로서 명시적으로 암시적 타입 변환을 허용해주는 것이 좋다. 암시적 타입 변환은 매우 강력한 기능으로, 사용시 주의하는 것이 좋기 때문이다.

[Next]