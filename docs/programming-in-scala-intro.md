# Programming in Scala

## Chapter 1

### 왜 스칼라인가?

- 호환성
- 간결성
- 고수준 추상화
- 고급 정적 타이핑

## Chapter 2

### 스칼라 설치

[스칼라 2.12.1 바이너리 다운로드](http://downloads.lightbend.com/scala/2.12.1/scala-2.12.1.msi)

### 스칼라 스크립팅

#### 스칼라 인터프리터

스칼라 인터프리터는 scala라는 커멘드로 쉘에서 실행 가능하다. 파이썬 인터프리터와 비슷한 기능을 가지고 있다.

#### 스칼라 스크립트

스칼라 스크립트를 실행시 args라는 스칼라 배열에 인자들을 받을 수 있다.

예를 들어, hello.scala라는 스칼라 스크립트의 코드는 다음과 같다.

```Scala
println("Hello " + args(0) + "!")
```

또한 이를 실행하기 위해 쉘에서 입력한 내용과 아웃풋은 다음과 같다.

```Console
$ scala hello.scala World
Hello World!
```

### 스칼라 문법

#### 변수 정의

- val : 자바의 final 변수. 재할당 불가능.
- var : 재할당 가능한 변수.

스칼라의 **타입 추론** 기능을 활용하여 자바의 String 타입의 변수를 다음과 같은 방법들로 정의 가능하다.

```Scala
val msg = "Hello"
val msg2: java.lang.String = "Hello!"
val msg3: String = "Hello!!"
```

#### 함수 정의

변수 x, y와 함수 결과 타입이 Int로 지정된 함수 max는 다음과 같이 구현한다.

```Scala
def max(x: Int, y: Int): Int = {
	if (x > y) x
	else y
}
```
- 스칼라는 파라미터 타입을 추론하지 않으므로, 파라미터의 타입 지정이 필수적으로 필요함.
- 함수 결과 타입은 일반적인 경우 생략 가능하나, 재귀적(recursive) 함수라면 꼭 표기해주어야 한다.

함수 결과를 돌려주지 않는 함수는 다음과 같이 구현한다.

```Scala
def greet() = println("Hello, World!")
```

해당 함수는 Unit이란 함수 결과 타입을 가진다.(자바의 void 타입)

#### 주석

자바의 주석과 같이 //(한줄 주석)과 /* */(여러줄 주석)으로 표기한다.

#### while 루프

자바와 같다. 다음은 인자들을 각각 한번씩 출력하는 스칼라 스크립트이다.

```Scala
var i = 0
while (i < args.length) {
	print(args(i))
	i += 1
}
println()
```

스칼라에서는 자바의 i++, ++i 같은 문법을 사용해 변수를 1만큼 증가시킬 수 없다. i += 1이란 표현을 사용하여야 한다.

*Note) 해당 프로그램은 C, 자바와 같은 명령형(imperative) 스타일의 프로그램으로써, 스칼라에서 권장하는 스타일이 아니다.*

#### foreach 루프

다음은 스칼라에서 권장하는 **함수형(functional) 스타일**로 인자들을 각각 한번씩 출력하는 스칼라 스크립트를 더 간결하게 작성한 것이다.

```Scala
args.foreach(arg => println(arg))
```

일단 더 간결하다는 장점이 있다. arg => println(arg)같은 표현을 **함수 리터럴(function literal)** 이라고 한다.

앞의 예에서는 arg의 타입이 String 임을 스칼라 인터프리터가 추론했지만, 파라미터의 타입을 명시적으로 표현해주는 것이 더욱 정상적인 함수 리터럴 문법이다.

```Scala
args.foreach((arg:String) => println(arg))
```

훨씬 더 간결하게 쓰고 싶다면, 스칼라가 제공하는 축약형을 사용한다.

```Scala
args.foreach(println)
```

명령형 언어에서 사용했던 for 루프를 쓰고 싶다면, 다음과 같은 유사한 함수형 표현을 쓸 수 있다. 이를 **for 표현식(expression)** 이라 부른다.

```Scala
// for arg in args
for (arg <- args)
	println(arg)
```

이러한 표현에서, arg는 val 변수의 이름으로써 변경불가능한 변수임을 유의해야 한다.