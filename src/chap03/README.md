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
두 개의 인수로 람다를 정의하려면 BiFunction, ToDoubleBiFunction, ToIntBiFunction, ToLongBiFunction과 같이 이름에 "Bi" 키워드가 포함된 인터페이스를 사용해야 한다. 표준 API에서 이 인터페이스를 사용하는 예시는 Map.replaceAll()이다.  

```java
Map<String, Integer> salaries = new HashMap<>();
salaries.put("John", 40000);
salaries.put("Freddy", 30000);
salaries.put("Samuel", 50000);

salaries.replaceAll((name, oldValue) -> name.equals("Freddy") ? oldValue : oldValue + 10000);
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

### BiPredicate<T, U>
```java
// java.util.function.BiPredicate<T, U>
@FunctionalInterface
public interface BiPredicate<T, U> { 
	boolean test(T t, U u);
}
```

- 제네릭 형식 T의 객체 1개, 제네릭 형식 U의 객체 1개를 인수로 받아서 boolean을 반환한다.

### BiConsumer<T, U>
```java
// java.util.function.BiPConsumer<T, U>
@FunctionalInterface
public interface BiConsumer<T, U> {
	void accept(T t, U u);
}
```

- 제네릭 형식 T의 객체 1개, 제네릭 형식 U의 객체 1개를 인수로 받아서 void를 반환한다.

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
### 1. 람다 표현식을 익명 클래스와 동일하게 취급하지 말기
[geeksforgeeks.org/difference-between-anonymous-inner-class-and-lambda-expression/](geeksforgeeks.org/difference-between-anonymous-inner-class-and-lambda-expression/)  
익명 클래스를 람다 표현식으로 대체한 이전 사례에도 불구하고 두 개념에는 차이가 있다. 람다 표현식 내에서 this 키워드를 사용하면 현재 클래스를 나타내는 반면, 익명 클래스의 경우 this 클래스는 해당 익명 클래스를 나타낸다.

### 2. 파라미터 타입 생략하기
컴파일러는 형식 추론을 통해 람다 파라미터의 형식을 확인할 수 있다. 따라서 파라미터에 유형을 추가하는 것은 선택 사항이며 생략할 수 있다.

```java
(String a, String b) -> a.toLowerCase() + b.toLowerCase();
```

```java
(a, b) -> a.toLowerCase() + b.toLowerCase();
```

### 3. 단일 파라미터 주위에 괄호를 사용하지 말기
람다 파라미터가 하나만 있는 경우 괄호를 제외하면 코드를 조금 더 짧게 만들 수 있다.

```java
a -> a.toLowerCase();
```

```java
(a) -> a.toLowerCase();
```

### 4. "Effectively Final" 변수를 사용하기
람다 표현식 내에서 final이 아닌 변수에 액세스하면 컴파일 에러가 발생한다. 그렇다고 해서 모든 대상 변수를 final로 선언해야 하는 것은 아니다.

"Effectively final" 개념에 따르면 컴파일러는 한 번만 할당되는 변수를 final로 취급한다. 따라서 Effectively final 변수를 사용해서 람다의 실행을 스레드 세이프하게 만들어야 한다.

## 참고
https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html  
https://www.baeldung.com/java-8-functional-interfaces  
https://www.baeldung.com/java-8-lambda-expressions-tips  