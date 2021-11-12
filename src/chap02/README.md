# Chapter 02 동작 파라미터화 코드 전달하기

## 변화에 대응한다는 것
개발자가 훌륭한 객체 지향 설계에 대해 공부하고, 깔끔한 아키텍처를 유지하기 위해 노력하고, 코드를 이해하기 쉽게 리팩터링 하는 작업은 모두 '변화에 대응하기 쉬운 코드'를 작성하기 위함이다. 어떤 상황에서 일을 하든 요구사항은 항상 바뀐다. 자바 8에서는 개발자가 변화하는 요구사항에 대응할 수 있도록 여러 장치를 만들었다. 그 중 하나가 동작 파라미터화(behavior parameterization)다.

## 값 파라미터화
값 파라미터화란 메서드에 파라미터를 추가하는 것이다. 값을 파라미터로 만들면 빨간색, 초록색, 파란색 등 색에 상관없이 동일한 메서드를 사용할 수 있다.

```java
public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
	List<Apple> result = new ArrayList<>();
	for (Apple apple: inventory) {
		if (apple.getColor().equals(color)) {
			result.add(apple);
		}
	}
	return result;
}
```

## 동작 파라미터화
동작 파라미터화란 구체적인 코드 실행 방법을 결정하지 않은 코드 블록을 말한다. 전략 패턴(Strategy pattern)을 사용한 클래스, 익명 클래스, 람다 표현식을 통해 동작 파라미터를 수행한다. 파라미터화된 코드 블록은 나중에 다른 메서드의 인수로 전달되어 다양한 기능을 수행할 수 있다.

```java
List<Apple> result = filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
```

## 전략 패턴(Strategy pattern)
[전략 패턴](https://ko.wikipedia.org/wiki/%EC%A0%84%EB%9E%B5_%ED%8C%A8%ED%84%B4) 또는 정책 패턴(policy pattern)은 실행 중에 알고리즘을 선택할 수 있게 하는 행위 소프트웨어 디자인 패턴이다.  

전략 패턴은

- 특정한 계열의 알고리즘들을 정의하고
- 각 알고리즘을 캡슐화하며
- 이 알고리즘들을 해당 계열 안에서 상호 교체가 가능하게 만든다.

전략 패턴을 사용한 동작 파라미터화의 경우, 여러 클래스를 구현해서 인스턴스화하는 과정이 번거롭게 느껴질 수도 있다.  