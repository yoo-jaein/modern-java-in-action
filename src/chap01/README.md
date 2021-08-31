# Chapter 01 자바 8, 9, 10, 11 : 무슨 일이 일어나고 있는가?

## 일급 시민
스칼라와 그루비와 같은 언어에서 메서드를 일급 시민으로 취급한다. 그렇게 했을 때 프로그래머가 활용할 수 있는 도구가 다양하지면서 프로그래밍이 수월해진다는 것이 확인되었다. 자바 8의 설계자들은 이 아이디어를 가져와서 메서드를 값으로 취급할 수 있는 기능들을 추가했다.

### 1. 메서드 참조 (method reference)
```java
filterApples(inventory, Apple::isGreenApple);
```
자바 8부터 메서드는 일급값으로 취급된다. 기존에 객체 참조(object reference, new로 객체 참조를 생성함)를 이용해서 객체를 주고받았던 것처럼 자바 8에서는 Class::method를 이용해서 메서드 참조를 만들어 전달할 수 있게 되었다.

### 2. 익명 함수 람다 (lambda)
```java
filterApples(inventory, (Apple a) -> a.getWeight() > 80 ||
        RED.equals(a.getColor()));
```
익명 함수 람다도 동일하게 일급값으로 취급할 수 있다. 람다 문법을 이용하면 직접 메서드를 정의하고 구현할 때보다 코드를 더욱 간결하게 작성할 수 있다. 다만, 람다가 몇 줄 이상으로 길어진다면 익명 람다보다는 코드의 역할을 드러내는 이름을 가진 메서드를 정의하고 메서드 참조를 활용하는 것이 바람직하다. 람다 문법으로 구현된 프로그램을 함수형 프로그래밍이라고도 한다.

## 스트림
아래는 리스트에서 고가의 거래만 필터링한 다음에 통화로 결과를 그룹화하는 코드다.
```java
Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();
for (Transaction transaction : transactions) {
	if (transaction.getPrice() > 1000) {
		Currency currency = transaction.getCurrency();
		List<Transaction> transactionsForCurrency = transactionsByCurrencies.get(currency);
		if (transactionsFOrCurrency == null) {
			transactionsForCurrency = new ArrayList<>();
			transactionsByCurrencies.put(currency, transactionsForCurrency);
        }
		transactionsForCurrency.add(transaction);
	}
}
```

중첩된 제어 흐름 문장이 많아서 코드를 한 번에 이해하기가 어렵다. 스트림 API를 이용하면 다음처럼 문제를 해결할 수 있다.
```java
import static java.util.stream.Collectors.groupingBy;
Map<Currency, List<Transaction>> transactionsByCurrencies = transactions.stream()
        .filter((Transaction t) -> t.getPrice() > 1000)
        .collect(groupingBy(Transaction::getCurrency));
```

for-each 루프를 이용해서 각 요소를 반복하면서 작업을 직접 처리하는 방식을 외부 반복(external iteration)이라 한다. 반대로 스트림 API처럼 라이브러리 내부에서 모든 데이터가 처리되는 반복 방식을 내부 반복(internal iteration)이라 한다. 

## 디폴트 메서드
인터페이스에 새로운 메서드를 추가한다면 그 인터페이스를 구현하는 모든 클래스는 새로 추가된 메서드를 구현해야 한다. 디폴드 메서드를 이용하면 기존의 코드를 건드리지 않고도 원래의 인터페이스 설계를 자유롭게 확장할 수 있다. default 키워드를 사용한다.
