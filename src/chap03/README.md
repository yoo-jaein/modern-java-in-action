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

함수형 인터페이스는 오직 하나의 추상 메서드만(Simple Abstract Method) 지정하는 인터페이스다. 자바 API의 함수형 인터페이스에는 Comparator, Runnable 등이 있다. 

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

함수형 인터페이스의 목적
- 람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있다.
- 람다 표현식을 함수형 인터페이스의 인스턴스로 취급할 수 있다.
- 람다 표현식을 함수형 인터페이스를 구현한 크래스의 인스턴스로 취급할 수 있다.

## 함수 디스크립터
함수형 인터페이스의 추상 메서드 시그니처는 람다 표현식의 시그니처를 가리킨다. 람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터(Function descriptor)라고 부른다.

## @FunctionalInterface
함수형 인터페이스임을 나타내는 정보 제공 목적의 어노테이션이다. @FunctionalInterface가 붙어있지 않아도 함수형 인터페이스의 정의를 충족하는 모든 인터페이스는 컴파일러에 의해 함수형 인터페이스로 취급된다. 만약 @FunctionalInterface가 붙어있지만 실제로 함수형 인터페이스가 아닌 경우 컴파일 에러가 발생한다.

```text
Multiple nonoverriding abstract methods found in interface Foo
```

> 인터페이스는 디폴트 메서드(default method)를 포함할 수 있다. 디폴트 메서드는 구현부가 있기 때문에 추상 메서드가 아니다. 따라서 인터페이스 내에 여러 개의 디폴트 메서드가 있더라도 추상 메서드가 오직 하나면 함수형 인터페이스에 해당한다. 마찬가지로 스태틱 메서드(static method)가 여러 개 있더라도 추상 메서드가 오직 하나명 함수형 인터페이스에 해당한다.

## 자바 8의 함수형 인터페이스
java.util.function 패키지로 여러 가지 함수형 인터페이스가 제공된다.

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

### Two-Arity Function Specializations

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

## 메서드 참조
메서드 참조(method reference)

## 참고
https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html  
https://www.baeldung.com/java-8-functional-interfaces  
