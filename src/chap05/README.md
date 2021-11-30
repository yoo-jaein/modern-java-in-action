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

