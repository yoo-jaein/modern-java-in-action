# Chapter 03 람다 표현식

## 람다
람다(lambda)는 메서드로 전달할 수 있는 익명 함수를 단순화한 것으로 다음과 같은 특징을 갖고 있다.

- 일반적인 메서드와 달리 이름이 없다.
- 메서드처럼 특정 클래스에 종속되지 않으므로 함수라 부른다.
- 람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다.
- 코드가 간결하다.

## 람다 표현식
```java
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```

람다 표현식은 ```파라미터```, ```화살표```, ```바디```로 이루어진다.

- ```파라미터``` : 사용할 파라미터의 리스트다.
- ```화살표``` : 람다의 파라미터와 바디를 구분한다.
- ```바디``` : 람다의 반환값에 해당한다.

## 함수형 인터페이스
우리가 2장에서 만든 Predicate<T> 인터페이스가 바로 함수형 인터페이스(Functional interface)다.

```java
public interface Predicate<T> {
	boolean test(T t);
}
```

함수형 인터페이스는 오직 하나의 추상 메서드만(Single Abstract Method) 지정하는 인터페이스다. 자바 API의 함수형 인터페이스에는 Comparator, Runnable 등이 있다. 

```java
// java.util.Comparator
public interface Comparator<T> {
	int compare(T o1, T o2);
}
```

```java
// java.lang.Runnable
public interface Runnable<T> {
	void run();
}
```

### 함수형 인터페이스의 목적
- 람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있다.
- 람다 표현식을 함수형 인터페이스의 인스턴스로 취급할 수 있다.
- 람다 표현식을 함수형 인터페이스를 구현한 크래스의 인스턴스로 취급할 수 있다.

## @FunctionalInterface
함수형 인터페이스임을 나타내는 정보 제공 목적의 어노테이션이다. @FunctionalInterface가 붙어있지 않아도 함수형 인터페이스의 정의를 충족하는 모든 인터페이스는 컴파일러에 의해 함수형 인터페이스로 취급된다. 만약 @FunctionalInterface가 붙어있지만 실제로 함수형 인터페이스가 아닌 경우 컴파일 에러가 발생한다.

```text
Multiple non-overriding abstract methods found in interface Foo
```

> 인터페이스는 디폴트 메서드(default method)를 포함할 수 있다. 디폴트 메서드는 구현부가 있기 때문에 추상 메서드가 아니다. 따라서 인터페이스 내에 여러 개의 디폴트 메서드가 있더라도 추상 메서드가 오직 하나면 함수형 인터페이스에 해당한다. 마찬가지로 스태틱 메서드(static method)가 여러 개 있더라도 추상 메서드가 오직 하나면 함수형 인터페이스에 해당한다.

## 자바 8의 함수형 인터페이스
자바 8부터 java.util.function 패키지로 여러 가지 함수형 인터페이스가 제공된다.

### Predicate<T>
```java
// java.util.function.Predicate<T>
@FunctionalInterface
public interface Predicate<T> {
	boolean test(T t);
}
```

- 제네릭 형식 T의 객체를 인수로 받아서 boolean을 반환한다.
- test라는 추상 메서드를 제공한다.

### Consumer<T>
```java
// java.util.function.Consumer<T>
@FunctionalInterface
public interface Consumer<T> {
	void accept(T t);
}
```

- 제네릭 형식 T의 객체를 인수로 받아서 void를 반환한다.
- accept라는 추상 메서드를 제공한다.

### Function<T, R>
```java
// java.util.function.Function<T, R>
@FunctionalInterface
public interface Function<T, R> {
	R apply(T t);
}
```

- 제네릭 형식 T의 객체를 인수로 받아서 제네릭 형식 R의 객체를 반환한다.
- apply라는 추상 메서드를 제공한다.

### Primitive Function Specializations
자바의 모든 형식은 참조형(reference type)과 기본형(primitive type)으로 나뉜다.  

- 참조형 : Byte, Integer, Boolean, Object, List
  - 제네릭의 내부 구현 때문에 List<T>의 T와 같은 제네릭 파라미터에는 참조형만 사용할 수 있다.
- 기본형 : int, double, byte, char

자바에는 참조형과 기본형을 서로 변환하기 위해 ```박싱```과 ```언박싱``` 기능을 제공하며 개발의 편의성을 위한 ```오토박싱``` 기능도 제공한다. 그러나 이런 변환 과정에는 비용이 소모된다. 박싱한 값은 기본형을 감싸는 래퍼(wrapper)로 힙에 저장되기 때문에 메모리를 더 소비하며 기본형 데이터를 가져올 때도 메모리를 탐색하는 과정이 필요하다.  

따라서 자바 8에서는 오토박싱을 피할 수 있도록 기본형을 위한 함수형 인터페이스를 제공한다. 예를 들어 IntPredicate의 경우 int t를 인수로 받아 boolean을 반환한다.   

### Two-Arity Function Specializations
두 개의 인수로 람다를 정의하려면 BiFunction, ToDoubleBiFunction, ToIntBiFunction, ToLongBiFunction과 같이 이름에 "Bi" 키워드가 포함된 인터페이스를 사용해야 한다.  

표준 API에서 이 인터페이스를 사용하는 일반적인 예 중 하나는 Map.replaceAll 메서드에 있습니다. 이 메서드를 사용하면 Map의 모든 값을 일부 계산된 값으로 바꿀 수 있습니다.  

```java
Map<String, Integer> salaries = new HashMap<>();
salaries.put("John", 40000);
salaries.put("Freddy", 30000);
salaries.put("Samuel", 50000);

salaries.replaceAll((name, oldValue) -> 
  name.equals("Freddy") ? oldValue : oldValue + 10000);
```

### Supplier<T>
```java
// java.util.function.Supplier<T>
@FunctionalInterface
public interface Supplier<T> {
	T get();
}
```

- 제네릭 형식 T의 객체를 반환한다.
- get이라는 추상 메서드를 제공한다.

### UnaryOperator<T>
```java
// java.util.function.UnaryOperator<T>
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {
	//...
}
```

- 제네릭 형식 T의 객체를 인수로 받아서 동일한 타입 T의 객체를 반환한다.
- Function<T, R>을 확장한 인터페이스다.

### BinaryOperator<T>
```java
// java.util.function.BinaryOperator<T>
@FunctionalInterface
public interface BinaryOperator<T> extends BiFunction<T,T,T> {
	//...
}
```

- 제네릭 형식 T의 객체 2개를 인수로 받아서 동일한 타입 T의 객체를 반환한다.
- BiFunction<T, U, R>을 확장한 인터페이스다.

### BiPredicate<L, R>

### BiConsumer<T, U>

### BiFunction<T, U, R>
```java
// java.util.function.BiFunction<T, U, R>
@FunctionalInterface
public interface BiFunction<T, U, R> { 
	R apply(T t, U u);
}
```

- 제네릭 형식 T의 객체 1개, 제네릭 형식 U의 객체 1개를 인수로 받아서 제네릭 형식 R의 객체를 반환한다.

## 메서드 참조
메서드 참조(method reference, 메서드 레퍼런스)는 '특정 메서드만 호출하는 람다'의 축약형이다. 메서드명 앞에 구분자(::)를 붙이는 방식을 사용하며, 실제로 메서드를 호출하는 것은 아니기 때문에 괄호는 필요 없다. 

```java
filter(words, this::isValidName)
```

```java
Apple::new
```

```java
Letter::checkSpelling
```

## 람다 표현식 사용 팁
### 1. 람다 표현식을 이너 클래스로 취급하지 말기

본질적으로 내부 클래스를 람다 식으로 대체한 이전 예에도 불구하고 두 개념은 중요한 면에서 다릅니다.

내부 클래스를 사용하면 새 범위가 생성됩니다. 같은 이름의 새 지역 변수를 인스턴스화하여 둘러싸는 범위에서 지역 변수를 숨길 수 있습니다. 내부 클래스 내에서 this 키워드를 인스턴스에 대한 참조로 사용할 수도 있습니다.

그러나 람다 표현식은 범위를 둘러싸고 작동합니다. 우리는 람다 바디 내부의 둘러싸는 범위에서 변수를 숨길 수 없습니다. 이 경우 키워드 this는 둘러싸는 인스턴스에 대한 참조입니다.

### 2. Lambda 본문에서 코드 블록 피하기
이상적인 상황에서 람다는 한 줄의 코드로 작성되어야 합니다. 이 접근 방식에서 람다는 어떤 데이터로 어떤 작업을 실행해야 하는지 선언하는 자체 설명 구조입니다(매개변수가 있는 람다의 경우).

코드 블록이 크면 람다의 기능이 즉시 명확하지 않습니다.

### 3. 매개변수 유형 지정 피하기
A compiler, in most cases, is able to resolve the type of lambda parameters with the help of type inference. Consequently, adding a type to the parameters is optional and can be omitted.

We can do this:

(a, b) -> a.toLowerCase() + b.toLowerCase();
Instead of this:

(String a, String b) -> a.toLowerCase() + b.toLowerCase();

### 3. 단일 매개변수 주위에 괄호를 사용하지 말기
Lambda syntax only requires parentheses around more than one parameter, or when there is no parameter at all. That's why it's safe to make our code a little bit shorter, and to exclude parentheses when there is only one parameter.

So we can do this:

a -> a.toLowerCase();
Instead of this:

(a) -> a.toLowerCase();

### 4. "Effectively Final" 변수를 사용하기
Accessing a non-final variable inside lambda expressions will cause a compile-time error, but that doesn’t mean that we should mark every target variable as final.

According to the “effectively final” concept, a compiler treats every variable as final as long as it is assigned only once.

It's safe to use such variables inside lambdas because the compiler will control their state and trigger a compile-time error immediately after any attempt to change them.

This approach should simplify the process of making lambda execution thread-safe.


### 5. 돌연변이로부터 개체 변수 보호하기
One of the main purposes of lambdas is use in parallel computing, which means that they're really helpful when it comes to thread-safety.

The “effectively final” paradigm helps a lot here, but not in every case. Lambdas can't change a value of an object from enclosing scope. But in the case of mutable object variables, a state could be changed inside lambda expressions.

Consider the following code:

int[] total = new int[1];
Runnable r = () -> total[0]++;
r.run();
This code is legal, as total variable remains “effectively final,” but will the object it references have the same state after execution of the lambda? No!

Keep this example as a reminder to avoid code that can cause unexpected mutations.

## 참고
https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html  
https://www.baeldung.com/java-8-functional-interfaces  
https://www.baeldung.com/java-8-lambda-expressions-tips  