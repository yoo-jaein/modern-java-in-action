# Chapter 09 리팩터링, 테스팅, 디버깅

## 리팩터링 - 코드 가독성 개선
코드 가독성이 좋다는 것은 추상적인 표현. 일반적으로는 '어떤 코드를 다른 사람도 쉽게 이해할 수 있음'을 의미한다. 코드 가독성을 높이려면 코드의 문서화를 잘하고, 표준 코딩 규칙을 준수하는 등의 노력을 기울여야 한다. 다음의 자바 8 기능을 통해 코드의 가독성을 개선할 수 있다.

- 익명 클래스를 람다 표현식으로
- 람다 표현식을 메서드 참조로
- 명령형 데이터 처리를 스트림으로

## 익명 클래스를 람다 표현식으로
```java
Runnable r1 = new Runnable() {
	public void run() {
		System.out.println("hello");
	}
}
```

```java
Runnable r2 = () -> System.out.println("hello");
```

## 람다 표현식을 메서드 참조로
```java
groupingBy(dish -> {
	if (dish.getCalories() <= 400) return CaloricLevel.DIET;
	else if () ...
	else ...
})
```

```java
groupingBy(Dish::getCaloricLevel)
```

```java
inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
```

```java
inventory.sort(comparing(Apple::getWeight));
```

## 명령형 데이터 처리를 스트림으로
```java
List<String> dishNames = new ArrayList<>();
for (Dish dish : menu) {
	if (dish.getCalories() > 300) {
		dishNames.add(dish.getName());
	}
}
```

```java
menu.parallelStream()
	.filter(d -> d.getCalories() > 300)
	.map(Dish::getName)
	.collect(toList());
```

