# Chapter 06 스트림으로 데이터 수집

## Collectors
- java.util.stream.Collectors 클래스의 정적 팩토리 메서드들로 다음의 기능을 수행할 수 있다.
- 스트림 요소를 하나의 값으로 리듀스하기
- 스트림 요소를 하나의 값으로 요약하기
- 스트림 요소를 그룹으로 묶기
- 스트림 요소를 분할하기

## 컬렉터 1: 리듀싱과 요약하기
### 1. toList()
```java
List<Dish> dishes = menuStream.collect(Collectors.toList());
```
- 스트림의 모든 항목을 리스트로 수집한다.

### 2. toSet()
```java
Set<Dish> dishes = menuStream.collect(Collectors.toSet());
```
- 스트림의 모든 항목을 중복이 없는 집합으로 수집한다.

### 3. toCollection()
```java
Collection<Dish> dishes = menuStream.collect(Collectors.toCollection(), ArrayList::new);
```
- 스트림의 모든 항목을 발행자가 제공하는 컬렉션으로 수집한다.

### 4. counting()
```java
long howManyDishes = menuStream.collect(Collectors.counting());
```
- 스트림의 항목 수를 계산한다.

### 5. summingInt()
```java
int totalCalories = menuStream.collect(Collectors.summingInt(Dish::getCalories));
```
- 스트림 항목에서 정수 프로퍼티값을 더한다.
- summingLong, summingDouble

### 6. averagingInt()
```java
double avgCalories = menuStream.collect(Collectors.aceragingInt(Dish::getCalories));
```
- 스트림 항목에서의 정수 프로퍼티값의 평균값을 계산한다.
- averagingInt, averagingLong, averagingDouble

### 7. summarizingInt()
```java
IntSummaryStatistics menuStatistics = menuStream.collect(Collectors.summarizingInt(Dish::getCalories));
```
- 스트림 항목의 최댓값, 최솟값, 합계, 평균 등 정수 정보 통계를 수집한다.
- IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}
- summarizingLong, summarizingDouble

### 8. joining()
```java
String shortMenu = menuStream.map(Dish::getName).collect(Collectors.joining(", "));
```
- 스트림의 각 항목에 toString 메서드를 호출한 결과 문자열을 ", "으로 연결한다.
- 내부적으로 StringBuilder를 이용해서 문자열을 하나로 만든다.

### 9. maxBy()
```java
Optional<Dish> fattest = menuStream.collect(Collectors.maxBy(comparingInt(Dish::getCalories)));
```
- 주어진 비교자를 이용해서 스트림의 최댓값 요소를 Optional로 감싼 값을 반환한다.
- 스트림에 요소가 없을 때는 Optional.empty()를 반환한다.

### 10. minBy()
```java
Optional<Dish> lightest = menuStream.collect(Collectors.minBy(comparingInt(Dish::getCalories)));
```
- 주어진 비교자를 이용해서 스트림의 최솟값 요소를 Optional로 감싼 값을 반환한다.
- 스트림에 요소가 없을 때는 Optional.empty()를 반환한다.

### 11. reducing()
```java
int totalCalories = menuStream.collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
```
- 누적자를 초깃값으로 설정한다. 그리고 BinaryOperator로 스트림의 각 요소를 반복적으로 누적자와 합치면서 스트림을 하나의 값으로 리듀싱한다.

## 컬렉터 2: 그룹화하기
### 1. collectionAndThen()
```java
int howManyDishes = menuStream.collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
```
- 다른 컬렉터를 감싸고 그 결과에 변환 함수를 적용한다.

### 2. groupingBy()
```java
Map<Dish.Type, List<Dish>> dishesByType = menuStream.collect(Collectors.groupingBy(Dish::getType));
```
- 하나의 프로퍼티값을 기준으로 스트림 항목을 그룹화한다.
- 기준 프로퍼티값을 결과 맵의 키로 사용한다.

## 컬렉터 3: 분할하기
### 1. partitioningBy()
```java
Map<Boolean, List<Dish>> vegetarianDishes = menuStream.collect(Collectors.partitioningBy(Dish::isVegetarian));
```
- 프레디케이트를 스트림의 각 항목에 적용한 결과로 항목을 분할한다.
- 분할이란 그룹화의 특수한 종류다. 분할 함수를 사용하면 참, 거짓 두 가지 요소의 스트림 리스트를 모두 유지할 수 있다.
