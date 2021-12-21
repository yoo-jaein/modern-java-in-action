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

## 객체지향 디자인 패턴에서 람다 활용하기
### Strategy : 전략
전략 패턴은 한 유형의 알고리즘을 보유한 상태에서 런타임에 적절한 알고리즘을 선택하는 기법이다. 특정한 계열의 알고리즘들을 정의하고 각 알고리즘을 캡슐화하며 이 알고리즘들을 해당 계열 안에서 상호 교체가 가능하게 만든다.

### Template method : 템플릿 메서드
템플릿 메서드 패턴은 부모 클래스에 기본적인 기능 구현을 하고 필요한 처리를 자식 클래스에게 위임하는 기법이다. 어떤 알고리즘을 사용하고 싶은데 그대로는 안 되고 조금 고쳐야 하는 상황에 적합하다.

### Observer : 옵저버
옵저버 패턴은 객체의 상태 변화를 관찰하는 관찰자들, 즉 옵저버들의 목록을 객체에 등록하여 상태 변화가 있을 때마다 메서드 등을 통해 객체가 직접 목록의 각 옵저버에게 통지하도록 하는 디자인 패턴이다. 주로 분산 이벤트 핸들링 시스템을 구현하는 데 사용된다. 발행/구독 모델이라고도 한다.  

옵저버 또는 리스너라 불리는 하나 이상의 객체를 관찰 대상이 되는 객체(subject)에 등록시킨다. 그리고 각각의 옵저버들은 관찰 대상인 객체가 발생시키는 이벤트를 받아서 처리한다.

### Chain of responsibility : 의무 체인
한 객체 A가 어떤 작업을 처리한 다음에 다른 객체 B로 결과를 전달하고, B도 해야 할 작업을 처리한 다음에 또 다른 객체 C로 전달하는 패턴이 의무 체인 패턴이다. 다음에 처리할 객체의 정보를 필드로 포함하는 방식으로 의무 체인 패턴을 구성한다. 작업을 처리하고 있는 객체가 자신의 작업을 끝냈으면 다음 작업 처리 객체로 결과를 전달한다. 

### factory : 팩토리
생성과 사용을 분리하기 위해 객체 생성에 특화된 객체를 팩토리라고 부른다. 팩토리 패턴을 사용하면 생성자와 그 로직을 외부로 노출하지 않음으로써 클라이언트가 단순하게 객체를 생성할 수 있다.
