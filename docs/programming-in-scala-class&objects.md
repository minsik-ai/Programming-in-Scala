# Programming in Scala

## Chapter 4

### 클래스 및 멤버

**클래스(Class)**는 다음과 같이 생성할 수 있다.

```Scala
class ChecksumAccumulator {
    var sum = 0
    def add(b: Byte): Unit = {
        sum += b
    }
    def checksum(): Int = {
        return ~(sum & 0xFF) + 1
    }
}
```

클래스의 정의 안에 있는 필드(sum) 및 메소드(add, checksum)를 **멤버(Member)**라고 한다. 

클래스는 다음과 같이 여러번 인스턴스화 할 수 있다.

```Scala
val acc = new ChecksumAccumulator
val bcc = new ChecksumAccumulator
```

클래스 안의 필드는 다른 말로 **인스턴스 변수(instance variable)**이라고도 한다. 모든 인스턴스가 각자의 변수 집합을 가지기 때문이다. 객체의 인스턴스 변수들이 한데 모여 메모리상의 해당 객체 이미지를 만들어낸다.

객체의 강건성(robustness, 오류에 잘 대체할 수 있는 능력)을 유지할 수 있는 중요한 방법 중 하나는 객체의 상태(인스턴스 변수의 값 전체)를 해당 인스턴스가 살아 있는 동안 항상 바르게 유지하는 것이다. 이를 위해선, 필드를 **비공개(private)**으로 만들어 외부에서 직접 접근할 수 없게 만드는 것이 필요하다. 비공개 필드는 인스턴스의 상태를 변경하는 코드를 클래스 내부로 한정하는 효과를 가진다. private 접근 수식자(access modifier)의 추가로 필드를 비공개로 만들 수 있다.

```Scala
class ChecksumAccumulator {
    private var sum = 0
    def add(b: Byte): Unit = {
        sum += b
    }
    def checksum(): Int = {
        return ~(sum & 0xFF) + 1
    }
}
```

스칼라의 기본 접근 수준은 전체 공개(public)으로, 자바와 달리 어떤 접근 수식자도 지정하지 않은 변수는 공개 멤버이다.

클래스의 멤버 메소드에 대해 살펴보자. 메소드 파라미터를 메소드 안에서 사용할 수 있다. 모든 메소드 파라미터는 val로서, 변경할 수 없다. 즉, 다음과 같은 메소드는, b가 변경 불가능한 변수이므로 컴파일되지 않는다.

```Scala
def add(b: Byte): Unit = {
    b = 1
    sum += b
}
```

또한 checkSum 메소드의 마지막 줄에 있는 return을 없앨 수 있다. 스칼라는 명시적인 return이 없어도 메소드의 맨 나중에 계산한 값을 반환한다. 

*NOTE) 스칼라에서 권장되는 스타일은, return을 명시적으로 사용하지 않는 것으로서, 메소드를 한 값을 반환하는 표현식으로 생각하는 것이다. 이런 철학으로 코딩하면, 메소드를 아주 작게 유지하고 커다란 메소드를 여러개의 작은 메소드로 나누게 된다.* 

메소드가 오직 하나의 표현식만 계산하는 경우 중괄호를 없앨 수 있다. 이런 방식들로 간결하게 만든 ChecksumAccumulator 클래스의 모습은 다음과 같다.

```Scala
class ChecksumAccumulator {
    private var sum = 0
    def add(b: Byte): Unit = sum += b
    def checksum(): Int = ~(sum & 0xFF) + 1
}
```

add 메소드와 같이 타입이 Unit인 경우, 이 메소드는 부수 효과를 위해 사용한다는 뜻이다. 이런 메소드는 결과 타입 및 등호를 없애고 메소드 본체를 중괄호로 감싸서, 마치 **프로시져(procedure)** 같은 형태로 바꿀 수 있다. 프로시져는 오직 부수 효과를 얻기 위해 사용하는 메소드를 뜻한다.

*NOTE) 부수효과의 정의 : 외부의 상태를 변경하거나 I/O를 수행하는 것.*

ChecksumAccumulator 클래스의 최종 버전은 다음과 같다.

```Scala
//ChecksumAccumulator.scala 파일
class ChecksumAccumulator {
    private var sum = 0
    def add(b: Byte) { sum += b }
    def checksum(): Int = ~(sum & 0xFF) + 1
}
```

마지막으로 헷갈릴 수 있는 점은, 스칼라에서 함수를 프로시져 스타일로 표현하면 결과값이 어떻던 자동으로 Unit으로 바꾼다는 점이다. 다음 코드로 설명을 대신하겠다.

```Scala
def f(): Unit = "Lost String"
// 실행시, f()안의 String은 사라진다.

def g() {"String lost again"}
// 실행시, g()안의 String은 사라진다.(프로시져 스타일)

def h() = {"This String is returned!"}
// 실행시, h()안의 String이 반환된다.
```

###세미콜론 추론

스칼라 프로그램에서는 보통 문장 끝의 세미콜론(;)을 생략할 수 있다. 그러나, 한 줄에 여러 문장을 넣으려면 다음과 같이 꼭 중간에 세미콜론이 필요하다.

```Scala
val string = "hey"; println(string)
```

여러 줄에 걸쳐 한 문장을 입력하려면 보통의 경우 단순히 입력하면 된다.

```Scala
if(x < 2)
  println("too small")
else
  println("okay")
```

가끔 자동으로 스칼라가 문장을 나누는 경우도 있는데, 다음과 같은 경우는 주의하여야 한다.

```Scala
x
+ y         // x와 + y의 두 문장으로 파싱됨

(x
+ y)        // 괄호로 인해, x + y로 파싱됨

x +
y           // 연산자가 줄의 끝에 있으므로, x + y로 파싱됨
```

위와 같은 이유로 인해, 중위연산자(두 변수 사이의 중간에 들어가는 연산자)를 사용할때는 줄의 시작보다 줄의 끝에 연산자를 배치하는 것이 일반적인 스칼라 코딩 스타일이다.

*NOTE) 세미콜론 추론 규칙*
    *1. 어떤 줄이 어떤 명령을 끝낼 수 있는 단어로 끝나지 않음("."나 중위연산자 등이 줄의 맨 끝에 있음)*
    *2. 다음줄의 맨 앞이 문장을 시작할 수 없는 단어로 시작함*
    *3. 줄이 괄호(())나 대괄호([]) 사이에서 끝남.*

###싱글톤 객체

스칼라는 자바 클래스의 정적(static)멤버들 대신 **싱글톤 객체(Singleton Object)**라는 것을 제공한다. 싱글톤 객체의 정의는 클래스 정의와 비슷하지만, class 대신 object라는 키워드로 시작한다. 다음은 한 싱글톤 객체의 정의이다.

```Scala
//ChecksumAccumulator.scala 파일
import scala.collection.mutable.Map
object ChecksumAccumulator {
  private val cache = Map[String, Int]()
  def calculate(s:String): Int =
    if(cache.contains(s))
      cache(s)
    else {
      val acc = new ChecksumAccumulator
      for (c <- s)
        acc.add(c.toByte)
      val cs = acc.checksum()
      cache += (s->cs)
      cs
    }
}
```

이 싱글톤 객체의 이름은 클래스를 살펴볼때 정의한 클래스와 같다. 이런 경우, 이 객체를 클래스의 **동반 객체(Companion Object)**라고 한다. 클래스와 그의 동반 객체는 반드시 같은 소스 파일 안에 정의해야 한다. 이때, 해당 클래스는 싱글톤 객체의 **동반 클래스(Companion Class)**라고 한다. 클래스와 동반 객체는 상대방의 비공개 멤버에 접근 가능하다.

싱글톤 객체를 자바의 정적 메소드를 보관하는 곳으로 생각해도 좋다. 싱글톤 객체 정의는 타입을 정의하지 않는다. 자바와 다른 점은, 싱글톤 객체는 **1급(first class)**으로써 슈퍼클래스를 확장(extend)하거나 트레이트를 믹스인(mix-in)할 수 있다는 점이다. 각 싱글톤 객체는 확장한 슈퍼클래스나 믹스인 트레이트의 인스턴스로써 해당 타입의 인스턴스가 하는 역할들을 할 수 있다.

클래스와 싱글톤 객체의 차이 중 하나는 클래스와 달리 싱글톤 객체는 파라미터를 받을 수 없다는 것이다. 컴파일러는 각 싱글톤 객체를 **합성한 클래스(Synthetic Class)**의 인스턴스로 구현하고, 이를 정적 변수가 참조한다. 어떤 싱글톤 객체의 초기화는 어떤 코드가 그 객체에 처음 접근할때 일어난다. 즉 ChecksumAccumulator의 경우, 처음 접근시에 ChecksumAccumulator$라는 인스턴스가 생성되고 이를 ChecksumAccumulator라는 정적 변수가 참조하게 된다.

동반 클래스가 없는 싱글톤 객체를 **독립 객체(Standalone Object)** 혹은 **독립 싱글톤 객체(Standalone Singleton Object)**라고 한다. 이는 필요한 유틸 메소드들을 모아놓거나 어플리케이션의 진입점을 만들 때 사용 가능하다.

###스칼라 애플리케이션

스칼라 프로그램의 실행을 위해서는, Array[String]을 유일한 인자로 받고 Unit을 반환하는 main이란 메소드가 있는 독립 싱글톤 객체 이름이 필요하다. 타입에 맞는 main 메소드만 있으면 어떤 독립 객체도 어플리케이션의 시작점 역할을 할 수 있다.

```Scala
//Summer.scala 파일. 
//스칼라에서는 파일의 이름을 꼭 클래스, 객체의 이름과 맞출 필요는 없다.

import ChecksumAccumulator.calculate        // Java의 static import와 같다

object Summer {
  def main(args: Array[String]) {           // 애플리케이션 시작점 역할의 메소드
    for (arg <- args)
      println(arg +": "+calculate(arg))
  }
}
```

스칼라는 항상 java.lang과 scala 패키지의 멤버를 암시적으로 임포트한다. scala 패키지의 Predef라는 싱글톤 객체에 유용한 메소드(println, assert 등)들이 많은수 분포한다.

해당 애플리케이션은 스칼라 컴파일러 **scalac**를 통하여 컴파일 할 수 있다.

```Console
scalac ChecksumAccumulator.scala Summer.scala
```

scalac의 컴파일은 초기화 작업으로 인해 꽤 오랜 시간이 걸린다. **fsc(fast scala compiler)**을 사용하면 최초 실행할때에만 초기화 작업을 기다리면 되며, 다음 부터는 데몬에서 코드가 빠르게 컴파일된다. fsc -shutdown 커맨드로 데몬을 중지할 수 있다.

```Console
fsc ChecksumAccumulator.scala Summer.scala
```

컴파일된 애플리케이션은 main 메소드가 들어 있는 독립 싱글톤 객체의 이름을 사용하여 살행할 수 있다.

```Console
scala Summer is here
```

is, here 두 문자열에 대한 체크섬이 출력된다.

### App 트레이트

Summer 싱글톤 오브젝트는 App 트레이트를 상속함으로써 main 메소드 없이 간단하게 구현가능하다. 싱글톤 객체의 중괄호 사이에 있는 코드는 싱글톤 객체의 **주 생성자(primary constructor)** 안에 들어가며, 오브젝트를 초기화할때 실행된다.

```Scala
object Summer extends App {
  for (arg <- args)
    println(arg + ": " + calculate(arg))
}
```

[Next](programming-in-scala-basic-types&ops.md)