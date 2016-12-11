# Programming in Scala

## Chapter 3

### 배열 초기화

객체를 인스턴스화(생성)할때, 값과 타입을 파라미터로 넘길 수 있다. 이를 인스턴스를 **파라미터화(parameterization)**한다고 한다. 다음 코드는 "12345"라는 값으로 새로운 java.math.BigInteger 인스턴스를 파라미터화 한 것이다.

```Scala
val big = new java.math.BigInteger("12345")
```

다음 코드는 새로운 배열 인스턴스를 타입으로 파라미터화 한 것이다. 중괄호 안에 하나 이상의 타입을 명시함으로서, 인스턴스를 타입으로 파라미터화 할 수 있다.

```Scala
val strings = new Array[String](3)
//Parametrization with type String and variable 3

strings(0) = "Hi. "
strings(1) = "My Very First "
strings(2) = "Scala Experience."

for(i <- 0 to 2)
	print(strings(i))
```

스칼라에서는 자바와 달리 배열 인덱스를 괄호(())에 넣어서 배열 원소에 접근한다.

방금 다룬 코드는 다음과 같은 주안점들을 가지고 있다.

- 어떤 변수를 val로 지정하면 그 변수를 재할당 할 수 없으나, 그 변수가 나타내는 객체는 기본적으로 변경 가능하다. 즉, 위의 코드에서 strings 변수는 다시 할당할 수 없으므로 항상 Array[String] 타입의 배열을 가리킨다. 하지만 해당 Array[String]의 원소는 언제나 변경할 수 있다, 즉 **변경 가능(mutable)** 하다.
- 메소드가 파라미터를 하나만 요구하는 경우, 그 메소드를 점(.)과 괄호(()) 없이 호출할 수 있다. 위 코드의 `0 to 2`는 실제로는 `(0).to(2)`라는 메소드 호출로 바뀐다(to는 실제로는 Int라는 클래스의 메소드이다). 이 문법은 호출 대상 객체를 명시적으로 지정할 때에만 사용할 수 있다.
	+ 스칼라는 연산자 오버로드를 제공하지 않는다. 다만 +, -, \*, / 등의 전통적인 연산자 문자들을 메소드 이름으로 사용할 수 있다. 따라서, `1 + 2` 라는 코드는 실제로는 `(1).+(2)`로 처리되는 것이다. *스칼라의 모든 연산자는 메소드 호출과 같다.
- 스칼라의 배열 원소는 자바와 달리 괄호를 사용하여 접근하여야 한다. 변수 뒤에 하나 이상의 값을 괄호로 둘러싸서 호출하면 스칼라는 그 변수에 대해 해당 값들을 인수로 가진 apply라는 메소드를 호출하는 것으로 바꾸어서 적용한다. 따라서 `strings(i)`는 `strings.apply(i)`와 같다. 이 원칙은 배열 뿐만 아니라 어떤 종류의 객체던 똫같이 적용된다. 즉, 스칼라에서의 배열 원소 접근은 특별한 형태가 아니고 일반적인 규칙에 따른 것이다.
	+ 마찬가지로, 괄호로 둘러싼 인자들이 있는 표현식에 할당을 하면 괄호 안과 등호 오른쪽의 값을 모두 인자로 넣어 update 메소드를 호출한다. `strings(i) = "Hello"`는 `strings.update(i, "Hello")`와 같다.

즉 위에서 다룬 코드는 다음과 같이도 표현할 수 있다.

```Scala
val strings: Array[String] = new Array[String](3)

strings.update(0, "Hi. ")
strings.update(1, "My Very First ")
strings.update(2, "Scala Experience.")

for(i <- (0).to(2))
	print(strings.apply(i))
```

*NOTE) 스칼라는 배열부터 수식까지 모든 것을 메소드가 있는 객체로 다루며, 이를 통해 개념을 단순화한다. __스칼라에서는 자바와 달리 특별한 경우를 기억할 필요가 없다.__*

스칼라에서는 배열을 초기화하기 위한 더 간편한 방법이 있다.

```Scala
val strings = Array("Hello ", "This is ", "Scala")
//인자들이 모두 String이므로 Array[String]으로 타입 유추되었다.
```

이 코드는 실제로 apply라는 이름의 **팩토리 메소드(factory method)**를 호출한다. 이 메소드는 새로운 배열을 만들어서 반환한다. apply 메소드는 임의 개수의 인자를 받을 수 있으며, Array의 **동반 객체(companion object)**에 정의되어 있다(자바에서 클래스의 정적 메소드(static method)를 호출하는 것과 유사하다). 즉, 다음과 같이도 표현할 수 있다.

```Scala
val strings = Array.apply("Hello ", "This is ", "Scala")
```

### 리스트

함수형 프로그래밍의 가장 큰 착안점 중 하나는, 메소드에 **부수 효과(side effect)**가 없어야 한다는 것이다. 메소드의 유일한 동작은 계산을 하여 값을 반환하는 것 뿐이어야 한다. 이는 메소드를 더 신뢰하고 사용할 수 있다는 이점을 주고, 정적 타입의 언어에서는 메소드에 들어가는 인자들과 반환값을 타입 검사기가 검사하기 때문에 논리적 오류가 타입 오류의 형태로 드러날 확률이 높다는 이점을 준다. 이런 함수형 프로그래밍의 철학을 적용하기 위해서, **변경 불가능한(immutable)** 객체가 권장된다.

스칼라의 **리스트(List)**는 같은 타입의 객체로 이뤄진 변경 불가능한 시퀀스로서, 함수형 프로그래밍을 위해 설계된 클래스이다. 스칼라의 리스트 `scala.List`는 변경 불가능하다는 점에서 자바의 `java.util.List`와 다르다. 리스트는 다음과 같이 정의한다.

```Scala
val simpleList = List(1, 2, 3)
//Array의 경우와 같이 apply() 팩토리 메소드가 적용된 경우이다.
```

List는 변경 불가능하기 때문에 자바의 String과 비슷하게 동작한다. 즉, 리스트의 내용을 변경하는 것 같아 보이는 메소드를 호출하면 새 값을 갖는 리스트를 새로 만들어서 반환한다. 다음의 예는 두 리스트를 이어붙이는 ::: 메소드를 사용한 것이다.

```Scala
val simpleList1 = List(1, 2, 3)
val simpleList2 = List(4, 5, 6)
val resultList = simpleList1 ::: simpleList2    //List(1, 2, 3, 4, 5, 6)

println(simpleList1 + " and " + simpleList2 + " are not mutated.")
println("Therefore, " + resultList + " is a new list.")
```

다음의 코드는 새 원소를 기존 리스트의 앞에 추가하여 반환하는 ::메소드의 예시이다. `::`메소드는 **콘즈(cons)**라고 부르며, 리스트에서 가장 자주 사용하는 연산자들 중의 하나이다.

```Scala
val simpleList = List(1,2,3)
val resultList = 0 :: simpleList

println(resultList)     //List(0, 1, 2, 3)
```

*Note) `0 :: simpleList`에서 ::는 오른쪽에 있는 피연산자 List인 simpleList의 메소드이다. 이는 행렬에서 살펴보았던 \*나 to같은 메소드와 달리, :로 끝나는 메소드의 경우 연산자 표기법으로 사용할 시 오른쪽의 피연산자에 대해 호출을 한다는 규칙에 의해서이다.*

빈 리스트(Nil)와 콘즈 연산자를 사용하여 다음과 같은 방법으로도 새로운 리스트를 초기화할 수 있다.

```Scala
val resultList = 1 :: 2 :: 3 :: Nil
println(resultList)     //List(1, 2, 3)
```

### 튜플

스칼라에서 **튜플(tuple)**은 각기 다른 타입의 원소를 넣을 수 있는, 변경 불가능한 컨테이너 객체이다. 튜플은 다음과 같이 인스턴스화 할 수 있다. 또한 `._N`이란 문법을 통해 N번째 원소에 접근할 수 있다. 스칼라 튜플의 인덱스는 1부터 시작한다.

```Scala
val pair = (99, "Luftballons")
println(pair._1)    //99
println(pair._2)    //Luftballons
```

튜플의 실제 타입은 내부에 들어 있는 원소의 개수와 각각의 타입에 따라 바귄다. 따라서 `(99, "Luftballons")`의 타입은 `Tuple2[Int, String]`이고, `('a', 'b', 12, "alpha")`의 타입은 `Tuple4[Char, Char, Int, String]`이다. 개념적으로 원하는 길이의 튜플을 마음대로 만들 수 있지만, 현재 스칼라 라이브러리는 `Tuple22`까지만 지원한다.

### 집합과 맵

스칼라의 목적은 프로그래머들이 함수형 스타일과 명령형 스타일의 장점을 모두 취할 수 있게 돕는 것이다. 이를 위해, 스칼라 컬렉션 라이브러리는 변경 가능한 컬랙션과 변경 불가능한 컬렉션을 구분하여 제공한다. 예를 들어 배열은 항상 변경 가능하지만, 리스트는 항상 변경 불가능하다. 집합과 맵의 경우, 변경 가능한 것과 변경 불가능 한 것을 다른 패키지(`scala.collection.immutable`, `scala.collection.mutable`)의 두가지 **트레이트(trait)** 및 클래스들을 통해 모두 제공한다.

*NOTE) 트레이트는 자바의 인터페이스와 비슷한 개념이다. 자바는 인터페이스를 구현(implement)하지만, 스칼라에서는 트레이트를 확장(extend)하거나 혼합(mix in)하는 차이점이 있다.*

예를 들어 스칼라의 집합은 `scala.collection`패키지 내부에서 다음과 같은 계층 구조를 가지고 있다.

    .immutable.Set 트레이트와 .mutable.Set 트레이트는 .Set 트레이트를 확장한다.
    .immutable.HashSet 클래스와 .mutable.HashSet는 각각 .immutable.Set와 .mutable.Set 트레이트를 확장한다.

변경 불가능한 집합의 인스턴스화 및 사용은 다음과 같다.

```Scala
var carMakers = Set("Hyundai", "Toyota")
//+=을 통한 재할당을 위해서는 carMakers가 변경 가능한 변수(var)여야 한다.

carMakers += "GM"
//carMakers = carMakers + "GM"과 같다.
//carMakers 집합에 "GM" 원소를 추가하여 리턴한 새로운 집합을 다시 carMakers에 할당함.

println(carMakers.contains("KIA"))
```

변경 가능한 집합을 사용하려면, 변경 불가능한 집합과 달리 `scala.collection.mutable.Set` 트레이트를 **임포트(import)**해주어야 한다.

```Scala
import scala.collection.mutable.Set

val carMakers = Set("Hyundai", "Toyota")
//재할당이 아닌 += 메소드를 통한 변경이므로, 변경 불가능한 변수(val)로 설정한다.

carMakers += "GM"
//mutable Set에는 +=이란 메소드가 있어서, 실제로는 carMakers.+=("GM")과 같다.

println(carMakers)
```

다음은 원하는 종류의 집합(HashSet)을 지정해서 사용하는 경우의 예시이다.

```Scala
import scala.collection.immutable.HashSet

val hashSet = HashSet("Seoul", "Busan")
println(hashSet + "Daejeon")
```

**맵(Map)**또한 집합과 마찬가지로 변경 가능한 것과 변경 불가능한 것이 있다. 변경 가능한 맵의 인스턴스화 및 사용은 다음과 같다.

```Scala
import scala.collection.mutable.Map

val instructionMap = Map[Int, String]()
//Map을 원소들과 함께 초기화 하지 않았으므로 컴파일러가 타입을 유추할 수가 없다.
//따라서 Map의 변수들의 타입을 명시적으로 지정해주어야 한다.

instructionMap += (1 -> "Turn on the Computer")
//instructionMap.+=(1.->("Turn on the Computer"))와 같다.

instructionMap += (2 -> "Run shell")
instructionMap += (3 -> "Type scala")

println(instructionMap(2))
```

위의 코드에서의 `1 -> "Turn on the Computer"`는 앞에서 살펴보았듯이 `1.->("Turn on the Computer")`와 같다. 스칼라에서 -> 메소드를 어떤 객체에 대해 호출하면, 해당 객체를 키로하고 인자로 받은다른 객체를 값으로 하는 원소가 2개인 튜플을 만들어 반환한다. 스칼라에서 -> 메소드를 모든 객체에 적용 가능하게 만들 수 있는 메커니즘을 **암시적 변환(implicit conversion)**이라고 한다(차후에 살펴볼 것이다.)

변경 불가능한 맵은 다음과 같이 인스턴스화하고 사용할 수 있다.

```Scala
val immutableMap = Map(
  1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV"
)
println(immutableMap(1))
```