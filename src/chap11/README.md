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
- Optional 변수는 null이 아니어아 하며 항상 Optional 인스턴스를 가리켜야 한다.

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

## Optional 사용 사례
1. 리턴 타입이 컬렉션이나 배열인 경우, 반환할 요소가 없을 때 빈 인스턴스가 빈 Optional 및 null보다 좋다.
   ```java
   Optional<List<Item>> itemsOptional;
   ```
   Optional을 사용하면 클라이언트 코드를 복잡하게 만들 뿐이므로 피한다. 빈 컬렉션을 반환하고 싶은 경우 Collections.emptyList(), emptySet(), emptyMap() 등을 적극 활용한다.


2. 생성자와 메서드 파라미터에 Optional을 사용한다면 오버로딩을 고려해본다.
   ```java
   public Student(String name, int grade, Optional<Car> car)
   ...
   Student student1 = new Student("kim", 1, Optional.empty());
   Car car = new Car(...);
   Student student2 = new Student("lee", 3, Optional.ofNullable(car));
   ```
   ```java
   public Student(String name, int grade, Car car)
   public Student(String name, int grade)
   ...
   Student student1 = new Student("kim", 1);
   Car car = new Car(...);
   Student student2 = new Student("lee", 3, car);
   ```
   선택적인 파라미터를 허용하는 경우, 메서드 오버로딩을 사용하여 설계하는 것을 고려해본다. 별도의 생성자로 분리하는 것이 클라이언트 코드를 훨씬 더 단순하고 읽기 쉽게 만들 수 있다. 만약 너무 많은 생성자가 만들어진다면 빌더 클래스를 사용하는 것도 하나의 방법이다. 빌더를 사용하면 객체의 가능한 모든 조합을 만들 수 있다. Lombok을 사용하면 빌더 작업도 매우 단순하다.


3. POJO 필드와 getter에 Optional을 사용하는 경우
   ```java
   public class Person {
       public Optional<Car> car;
       public Optional<Car> getCar() {
           return car;
       }
   }
   ```
   Optional 사용 사례에서 가장 논쟁의 여지가 있다. 일반적으로 다음의 두 가지 이유로 POJO 필드와 getter에 Optional을 사용하지 않는다. 첫째, 자바 언어 아키텍트인 Brian Goetz는 스택 오버플로우에서 " [getter에 대한 반환 값으로 Optional을 일상적으로 사용하는 것은 확실히 남용되는 것이라고 생각합니다.](https://stackoverflow.com/questions/26327957/should-java-8-getters-return-optional-type/26328555#26328555) " 라고 밝힌 바 있다. 둘째, Optional은 Serializable 인터페이스를 의도적으로 구현하지 않았다. 

   > 참고! Optional의 세 가지 오버헤드  
       1. Optional은 space overhead를 유발한다. Optional은 추가 메모리를 소비하는 별도의 객체다. -> 메모리 오버헤드는 요즘 시대에 사실 미미함  
       2. Optional은 time overhead를 유발한다. 데이터는 추가 간접 참조를 통해 액세스해야 하며 이에 대한 메서드 호출은 null보다 더 비싸다.  
       3. Optional은 coding overhead를 유발한다. null을 사용하는 기존 인터페이스와의 비호환성, 직렬화 불가능 문제 등을 처리해야 한다.

## 참고
https://docs.oracle.com/javase/7/docs/api/java/lang/NullPointerException.html  
https://homes.cs.washington.edu/~mernst/advice/nothing-is-better-than-optional.html  
https://homoefficio.github.io/2019/10/03/Java-Optional-%EB%B0%94%EB%A5%B4%EA%B2%8C-%EC%93%B0%EA%B8%B0/  
http://dolszewski.com/java/java-8-optional-use-cases/  
https://medium.com/@yassinhajaj/optionals-are-bad-practices-still-bad-practices-if-everyone-practices-them-6ec5a66c30aa  