# Programming in Scala

## Chapter 5

### 기본 타입

아래 표에서 스칼라의 여러 기본 타입과 그 인스턴스가 취할 수 있는 값의 범위를 확인할 수 있다. Byte, Short, Int, Long, Char같은 타입들을 **정수적인 타입(Integral Type)**이라고 한다. 이런 정수적인 타입들에, Float과 Double을 덧붙여서 **수 타입(Numeric Type)**이라 한다.

|   타입     |    범위                                  |
|:-----------|:-----------------------------------------|
|Byte       |8비트의 부호 있는 정수(-2^7 ~ 2^7 -1)       |
|Short      |16비트의 부호 있는 정수(-2^15 ~ 2^15 - 1)   |
|Int        |32비트의 부호 있는 정수(-2^31 ~ 2^31 - 1)   |
|Long       |64비트의 부호 있는 정수(-2^63 ~ 2^63 - 1)   |
|Char       |16비트의 부호 없는 유니코드 문자(0 ~ 2^16-1) |
|String     |Char의 시퀀스                              |
|Float      |32비트 IEEE 754 single-precision float    |
|Double     |64비트 IEEE 754 double-precision float    |

부호 있는 정수의 경우, 2의 보수 표현을 사용하여 표기한다. Java.lang 패키지의 String 타입을 제외하면, 모두 scala 패키지의 멤버이며, 자동으로 임포트된다. 이들(Byte, Short, Int, Long, Char, Float, Double)을 **값 타입(Value Type)**이라고도 부르며, 스칼라 컴파일러가 바이트 코드를 생성할때 대응하는 역할의 자바 원시 타입으로 자유롭게 변환할 수 있다.

### 리터럴

**리터럴(Literal)**은 상수 값을 코드에 직접 적는 방법을 의미한다. 위의 표의 모든 기본 타입들은 리터럴로 적을 수 있다. 여기선, 스칼라가 자바와 다른 부분만 살펴보겠다.

####문자열 리터럴

문자열 리터럴은 큰따옴표(")로 둘러쌓인 문자들로 이루어진다. 이 문자들의 문법은 문자 리터럴과 동일하다(이스케이프 시퀀스의 적용 등). 이스케이프 시퀀스가 많거나 여러 줄에 걸친 문자열의 경우, 이런 문법을 사용해 문자열을 표기하면 읽기 어렵다. 따라서, 스칼라에는 **Raw 문자열**을 위한 특별한 문법이 추가되어 있다. 이러한 Raw 문자열은 큰 따옴표 3개를 연속으로 사용해(""") 시작하며, 이 내부의 문자들이 그대로 문자열이 된다.

```Scala
println("""Hello this is
        Scala""")
```

위의 프로그램을 실행시 다음과 같은 결과물이 나온다.

```Console
Hello this is
        Scala
```

이는 두번째 줄 앞의 공백들도 문자열에 들어갔기 때문이다. 이를 없애기 위해서는, 파이프 문자(|)를 각 줄의 시작 부분에 넣은 뒤 stripMargin 메소드를 그 문자열에 대해 호출해야 한다.

```Scala
println("""|Hello this is
           |Scala""".stripMargin)
```

위의 프로그램을 실행시 다음과 같은 결과물이 나온다.

```Console
Hello this is
Scala
```

#### 심볼 리터럴

**심볼(Symbol)**은 **인턴(Intern)**된 스트링이다. 같은 심볼 리터럴을 두번 사용하면, 두 표현식 모두 완전히 동일한 Symbol 객체를 참조한다. 심볼은 다음과 같이 정의한다.

```Scala
val s = 'newSymbol
s.name                  //newSymbol이란 String
```

s는 newSymbol이란 String 이름을 가진 심볼이다. 심볼 리터럴은, 동적으로 타입이 정해지는 언어였다면 단순 식별자를 사용할 만한 경우에 사용된다(데이터베이스의 key 등).

### 연산자 표현 기법

스칼라에서, 모든 메소드는 연산자로 표기 가능하다. 메소드를 연산자를 표기할 시에, **중위 연산자(infix operator)**로 표현 하는 경우 다음과 같이 표현할 수 있다. 

```Scala
1 + 3                       //1.+(3)
string indexOf 'o'          //string.indexOf('o')
string indexOf ('o', 3)     //string.indexOf('o', 3)
```

전위(prefix)나 후위(postfix) 연산자는 **단항(unary)**연산자로서, 피연산자가 하나뿐이다. 전위 연산자로 쓰일 수 있는 식별자는 +, -, !, ~ 네가지 뿐이다. 각각 unary_+, unary_-, unary_!, unary_~라는 메소드 명을 가진다.

```Scala
+2.0                        //(2.0).unary_+
-2.0                        //(2.0).unary_-
```

후위 연산자의 경우, 인자를 가지고 있는 연산자를 (.)과 괄호 없이 호출하는 경우이다. 다음과 같다.

```Scala
s toLowercase               //s.toLowerCase
```

### 객체 동일성

스칼라에서는 자바와 달리 '=='(equals)메소드가 왼쪽 인자와 오른쪽 인자의 내용 만을 비교한다.

//TODO

### 연산자 우선순위와 결합 법칙

계산의 순서는 **할당 연산자(assignment operator)**을 제외하면 연산자 첫문자의 우선순위를 따른다. 또한 첫문자의 우선순위가 같은 연산자들의 경우 맨 끝 문자가 ':'인 연산자라면 오른쪽에서 왼쪽으로, 아니라면 왼쪽에서 오른쪽으로 계산한다.

//TODO

### Rich Wrapper

스칼라의 기본 타입들은 Rich Wrapper로 **암시적 변환(Implicit Conversion)**되어, max, min, abs, round, isInfinity, to 등의 메소드들을 추가로 사용 가능하다.

    Byte -> scala.runtime.RichByte
    Short -> scala.runtime.RichShort
    Int -> scala.runtime.RichInt

    ...
    String -> scala.collection.immutable.StringOps

[Next](programming-in-scala-functional-object.md)