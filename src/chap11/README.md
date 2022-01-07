# Chapter 11 null 대신 Optional 클래스
java.util.Optional<T>

## NullPointerException
다음과 같은 상황에서 NullPointerException이 발생한다.

- null 객체의 인스턴스 메서드를 호출할 때
- null 객체의 필드에 액세스하거나 수정할 때
- (배열처럼) null의 길이를 취할 때
- (배열처럼) null 슬롯에 액세스하거나 수정할 때
- (예외처럼) null을 throw할 때

## NullPointerException 피하기
```java
public String getCarInsuranceName(Person person) {
	if (person != null) {
		Car car = person.getCar();
		if (car != null) {
			Insurance insurance = car.getInsurance();
			if (insurance != null) {
				return insurance.getName();
			}
		}
	}
	return "Unknown";
}
```
1. 변수를 참조할 때마다 null을 확인한다. -> 모든 변수를 검사하기 때문에 인덴트 레벨이 증가한다. 가독성이 떨어진다.

```java
public String getCarInsuranceName(Person person) {
	if (person == null) {
		return "Unknown";
	}
	Car car = person.getCar();
	if (car == null) {
		return "Unknown";
	}
	Insurance insurance = car.getInsurance();
	if (insurance == null) {
		return "Unknown";
	}
	return insurance.getName();
}
```
2. 중첩 if 블록을 없앴다. -> 출구가 너무 많아서 유지보수가 어렵다.

## 다른 프로그래밍 언어에서 null 참조를 해결하는 방법
- 그루비: 안전 내비게이션 연산자(?.)
  ```groovy
  def carInsuranceName = person?.car?.insurance?.name
  ```
- 하스켈: Maybe
- 스칼라: Option[T]

## Optional
하스켈과 스칼라는 '값이 있거나 없음을 표현하는 형식'으로 null 참조를 해결한다. 자바 8은 이들의 영향을 받아 Optional<T>라는 새로운 클래스를 제공한다.

- 값이 있으면 Optional 클래스는 값을 감싼다.
- 값이 없으면 Optional.empty()로 Optional 인스턴스를 반환한다.

## Optional 클래스의 메서드
- empty() : 빈 Optional 인스턴스를 반환한다.
- filter() : 값이 존재하거나 프레디케이트와 값이 일치하면 그 값을 포함하는 Optional을 반환한다. 값이 없거나 프레디케이트와 일치하지 않으면 빈 Optional을 반환한다.
- flatMap() : 값이 존재하면 인수로 제공된 함수를 적용한 결과의 Optional을 반환한다. 값이 없으면 빈 Optional을 반환한다.
- get() : 값이 존재하면 Optional 내부의 값을 반환하고, 값이 없으면 NoSuchElementException이 발생한다.
- ifPresent() : 값이 존재하면 지정된 Consumer를 실행하고, 값이 없으면 아무 일도 일어나지 않는다.
- ifPresentOrElse() : 값이 존재하면 지정된 Consumer를 실행하고, 값이 없으면 아무 일도 일어나지 않는다.
- isPresent() : 값이 존재하면 true를 반환하고, 값이 없으면 false를 반환한다.
- map() : 값이 존재하면 제공된 매핑 함수를 적용한다.
- of() : 값이 존재하면 값을 감싸는 Optional을 반환한다. 값이 null이면 NullPointerException이 발생한다.
- ofNullable() : 값이 존재하면 값을 감싸는 Optional을 반환한다. 값이 null이면 빈 Optional을 반환한다.
- or() : 값이 존재하면 같은 Optional을 반환한다. 값이 없으면 Supplier에서 만든 Optional을 반환한다.
- orElse() : 값이 존재하면 값을 반환한다. 값이 없으면 기본 값을 반환한다.
- orElseGet() : 값이 존재하면 값을 반환한다. 값이 없으면 Supplier에서 제공하는 값을 반환한다.
- orElseThrow() : 값이 존재하면 값을 반환한다. 값이 없으면 Supplier에서 생성한 예외를 발생한다.
- stream() : 값이 존재하면 존재하는 값만 포함하는 스트림을 반환한다. 값이 없으면 빈 스트림을 반환한다.

## Optional 잘 사용하기
- https://homoefficio.github.io/2019/10/03/Java-Optional-%EB%B0%94%EB%A5%B4%EA%B2%8C-%EC%93%B0%EA%B8%B0/  

## 참고
https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html  