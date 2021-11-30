# Chapter 05 스트림 활용

## 외부 반복을 내부 반복으로
```java
List<Dish> vegetarianDishes = new ArrayList<>();
for (Dish d : menu) {
    if (d.isVegetarian()) {
        vegetarianDishes.add(d);
    }
}
```
- 데이터 컬렉션 반복을 명시적으로 관리하는 외부 반복 코드

```java
import static java.util.stream.Collectors.toList;
List<Dish> vegetarianDishes = menu.stream()
        .filter(Dish::isVegetarian)
        .collect(toList());
```
- 데이터 컬렉션 반복을 스트림 API 내부적으로 처리하는 내부 반복 코드

## 활용 1: 필터링
### 1. filter()
```java
List<Dish> vegetarianMenu = menu.stream()
        .filter(Dish::isVegetarian)
        .collect(toList());
```
- filter()는 프레디케이트(불리언을 반환하는 함수)를 인수로 받아서 프레디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.
- menu 스트림에서 isVegetarian()의 리턴값이 true인 요소들만 남겨져 vegetarianMenu를 구성하게 된다.

### 2. distinct()
```java
numbers.stream()
        .filter(i -> i % 2 == 0)
        .distinct()
        .forEach(System.out::println);
```
- distinct()는 스트림 요소의 중복을 제거한다.

## 활용 2: 슬라이싱
### 1. takeWhile()
```java
specialMenu.stream()
        .takeWhile(dish -> dish.getCalories() < 320)
        .collect(toList());
```
- takeWhile()은 이미 정렬되어 있는 대량의 데이터 컬렉션에 유용하게 사용된다.
- 320칼로리 이상인 요리가 나왔을 때 반복 작업을 중단한다.
- 320칼로리 미만인 요소들이 반환된다.

### 2. dropWhile()
```java
specialMenu.stream()
        .dropWhile(dish -> dish.getCalories() < 320)
        .collect(toList());
```
- dropWhile()은 takeWhile()의 정반대 작업을 수행한다.
- 320칼로리 이상인 요리가 나왔을 때 지금까지 발견된 요소를 버리고 남은 모든 요소를 반환한다.
- 320칼로리 이상인 요소들이 반환된다.

### 3. limit()
```java
specialMenu.stream()
        .filter(dish -> dish.getCalories() > 300)
        .limit(3)
        .collect(toList());
```
- limit()은 주어진 값 이하의 크기를 갖는 스트림을 반환한다.
- 320칼로리 이상인 요리 3개를 반환한다.

### 4. skip()
```java
specialMenu.stream()
        .filter(dish -> dish.getCalories() > 300)
        .skip(2)
        .collect(toList());
```
- skip()은 주어진 값만큼 요소를 제외한 스트림을 반환한다.
- 만약 크기가 1인 스트림에 skip(2)를 호출하면 빈 스트림이 반환된다.
- 300칼로리 이상의 처음 두 요리를 건너뛴 다음에 300칼로리가 넘는 나머지 요리를 반환한다.

## 활용 3: 매핑
### 1. map()
```java
words.stream()
        .map(String::length)
        .collect(toList());
```
- map()은 기존 요소를 새로운 요소로 변환(매핑)한다.
- 문자열을 요소로 받아서 문자열의 길이를 반환한다.

### 2. flatMap()
```java
words.stream()
        .map(word -> word.split(""))
        .flatMap(Arrays::stream)
        .distinct()
        .collect(toList());
```
- flatMap()은 여러 개의 스트림을 하나의 스트림으로 평면화한다. 

## 활용 4: 검색
### 1. anyMatch()
```java
if (menu.stream().anyMatch(Dish::isVegetarian)) {
    System.out.println("채식 요리가 하나 이상 존재합니다.");
}
```
- anyMatch()는 프레디케이트가 적어도 하나의 요소와 일치하는지 확인한다.
- 불리언을 반환하는 최종 연산이다.
- 쇼트서킷 기법을 활용한다.

### 2. allMatch()
```java
if (menu.stream().allMatch(Dish::isVegetarian)) {
    System.out.println("모든 요리가 채식 요리입니다.");
}
```
- allMatch()는 프레디케이트가 모든 요소와 일치하는지 확인한다. 
- 불리언을 반환하는 최종 연산이다.
- 쇼트서킷 기법을 활용한다.

### 3. noneMatch()
```java
if (menu.stream().noneMatch(Dish::isVegetarian)) {
    System.out.println("모든 요리가 채식 요리가 아닙니다.");
}
```
- noneMatch()는 allMatch()와 반대 연산을 수행한다.
- 주어진 프레디케이트와 일치하는 요소가 없는지 확인한다.
- 쇼트서킷 기법을 활용한다.

### 4. findAny()
```java
menu.stream()
        .filter(Dish::isVegetarian)
        .findAny();
```
- findAny()는 스트림에서 임의의 요소를 반환한다.
- 쇼트서킷 기법을 활용한다.
- 요소의 반환 순서가 상관없다면 병렬 스트림에서는 제약이 적은 findAny()를 사용한다.

### 5. findFirst()
```java
menu.stream()
        .filter(Dish::isVegetarian)
        .findFirst();
```
- findFirst()는 첫 번째 요소를 반환한다.

## 활용 5: 리듀싱
### 1. reduce()
```java
numbers.stream()
         .reduce(0, (a, b) -> a + b);
```
- reduce()는 모든 스트림 요소를 처리해서 값으로 도출한다.
- 마치 종이를 작은 조각이 될 때까지 반복해서 접는 것과 비슷하다는 의미로 폴드라고도 부른다.
- 메뉴의 모든 칼로리의 합계를 구하거나, 메뉴에서 칼로리가 가장 높은 요리 등 복잡한 질의를 표현할 수 있다.

### 2. IntStream & mapToInt()
```java
int calories = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
```
- 메뉴의 칼로리 합계를 계산한다.
- 이 코드에는 박싱 비용이 숨어있다. 합계를 계산하기 전에 Integer를 기본형 int로 언박싱해야 한다.
- 이 문제를 해결하기 위해 스트림 API는 기본형 특화 스트림(IntStream, DoubleStream, LongStream)을 제공한다.

```java
IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
```
- 스트림을 기본형 특화 스트림으로 변환할 때 mapToInt(), mapToDouble(), mapToLong()을 사용한다.
- mapToInt()는 스트림을 IntStream 숫자 스트림으로 변환한다.

### 3. boxed()
```java
Stream<Integer> stream = intStream.boxed();
```
- boxed()는 숫자 스트림을 일반 스트림으로 변환한다.
