# Programming in Scala

## Chapter 3

### 배열 초기화

객체를 인스턴스화(생성)할때, 값과 타입을 파라미터로 넘길 수 있다. 이를 인스턴스를 **파라미터화(parameterization)**이라고 한다. 다음 코드는 "12345"라는 값으로 새로운 java.math.BigInteger 인스턴스를 파라미터화 한 것이다.

```Scala
val big = new java.math.BigInteger("12345")
```

다음 코드는 새로운 배열 인스턴스를 타입으로 파라미터화 한 것이다. 중괄호 안에 하나 이상의 타입을 명시함으로서, 인스턴스를 타입으로 파라미터화 할 수 있다.

```Scala
//Parametrization with type String and variable 3
val strings = new Array[String](3)


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
	+ 스칼라는 연산자 오버로드를 제공하지 않는다. 다만 +, -, \*, / 등의 전통적인 연산자 문자들을 메소드 이름으로 사용할 수 있다. 따라서, `1 + 2` 라는 코드는 실제로는 `(1).+(2)`로 처리되는 것이다. <u>스칼라의 모든 연산자는 메소드 호출과 같다.</u>
- 스칼라의 배열 원소는 자바와 달리 괄호를 사용하여 접근하여야 한다. 변수 뒤에 하나 이상의 값을 괄호로 둘러싸서 호출하면 스칼라는 그 변수에 대해 해당 값들을 인수로 가진 apply라는 메소드를 호출하는 것으로 바꾸어서 적용한다. 따라서 `strings(i)`는 `strings.apply(i)`와 같다. 이 원칙은 배열 뿐만 아니라 어떤 종류의 객체던 똫같이 적용된다. <u>즉, 스칼라에서의 배열 원소 접근은 특별한 형태가 아니고 일반적인 규칙에 따른 것이다.</u>
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
//인자들이 모두 String이므로 Array[String]으로 타입 유추되었다.
val strings = Array("Hello ", "This is ", "Scala")
```

이 코드는 실제로 apply라는 이름의 **팩토리 메소드(factory method)**를 호출한다. 이 메소드는 새로운 배열을 만들어서 반환한다. apply 메소드는 임의 개수의 인자를 받을 수 있으며, Array의 **동반 객체(companion object)**에 정의되어 있다(자바에서 클래스의 정적 메소드(static method)를 호출하는 것과 유사하다). 즉, 다음과 같이도 표현할 수 있다.

```Scala
val strings = Array.apply("Hello ", "This is ", "Scala")
```

