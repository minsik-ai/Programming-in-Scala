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