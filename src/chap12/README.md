# Chapter 12 새로운 날짜와 시간 API
java.time

## 자바 8에서 java.time API를 제공하는 이유
### 1. Date의 문제점
자바 1.0에서는 java.util.Date 클래스 하나로 날짜와 시간 관련 기능을 제공했다. 이 클래스에는 다음과 같은 문제가 있었다.
- Date 클래스는 특정 시점을 밀리초 단위로 표현한다.
- 1900년을 기준으로 하는 오프셋을 사용한다.
- 달 인덱스가 0에서 시작한다.

```java
@Deprecated
public Date(int year, int month, int date) {
    this(year, month, date, 0, 0, 0);
}
```

### 2. Calendar의 문제점
자바 1.1에서는 Date 클래스의 여러 메서드를 deprecated 시키고, java.util.Calendar라는 클래스를 대안으로 제공했다. 안타깝게도 Calendar 역시 여러 문제를 갖고 있었다.  
- 여전히 달 인덱스가 0에서 시작한다.
- 날짜와 시간을 파싱하는 DateFormat의 일부 기능은 Calander를 지원하지 않았다.

### 3. DateFormat의 문제점
DateFormat에도 문제가 있다. 스레드에 안전하지 않기 때문에 두 스레드가 동시에 하나의 formatter로 날짜를 파싱하면 예기치 못한 결과가 일어날 수 있다.

```java
Date date = new Date(114, 2, 18);
System.out.println(date);

DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
String format1 = format.format(date);
System.out.println("format1 = " + format1);
```

DateFormat과 SimpleDateFormat 내부에는 작업에 사용되는 Calendar 인스턴스 필드가 있다. 작업의 중간 결과를 해당 필드에 저장하기 때문에 두 스레드에서 하나의 인스턴스를 사용하면 서로의 결과가 엉망이 될 수 있다.  

> SimpleDateFormat-Synchronization: Date formats are not synchronized. It is recommended to create separate format instances for each thread. If multiple threads access a format concurrently, it must be synchronized externally.

스레드 세이프하게 날짜 formatter를 재사용하는 첫 번째 방법은 날짜 형식을 ThreadLocal에 넣는 것이다. 일부 라이브러리에서 한 스레드 내에서 동일한 형식을 여러 번 사용해야 하는 경우 이 방식을 이용한다.  
```java
private static final ThreadLocal<DateFormat> formatters =
    ThreadLocal.withInitial(() -> new SimpleDateFormat("dd-MMM-yyyy"));
```

두 번째 방법은 SimpleDateFormat 대신 스레드 세이프한 joda-time 혹은 자바 8의 DateTimeFormat을 사용하는 것이다.  
```java
DateTime dt = new DateTime();
DateTimeFormatter fmt = DateTimeFormat.forPattern("MMMM, yyyy");
String str = fmt.print(dt);
```

#### 실행 코드
- 하나의 SimpleDateFormat을 공유하는 멀티 스레드 프로그램 실행해보기
- 출처 : https://stackoverflow.com/questions/4021151/java-dateformat-is-not-threadsafe-what-does-this-leads-to
```java
public static void main(String[] args) throws Exception {

    final DateFormat format = new SimpleDateFormat("yyyyMMdd");

    Callable<Date> task = new Callable<Date>() {
        public Date call() throws Exception {
            return format.parse("20101022");
        }
    };

    //pool with 5 threads
    ExecutorService exec = Executors.newFixedThreadPool(5);
    List<Future<Date>> results = new ArrayList<Future<Date>>();

    //perform 10 date conversions
    for (int i = 0; i < 10; i++) {
        results.add(exec.submit(task));
    }
    exec.shutdown();

    //look at the results
    for (Future<Date> result : results) {
        System.out.println(result.get());
    }
}
```

#### 결과 1
```text
Fri Apr 01 00:00:00 KST 2016
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Thu Oct 22 00:00:00 KST 22
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
```

#### 결과 2
```text
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Thu Oct 22 00:00:00 KST 22
Fri Oct 22 00:00:00 KST 2010
Exception in thread "main" java.util.concurrent.ExecutionException: java.lang.NumberFormatException: For input string: ""
at java.base/java.util.concurrent.FutureTask.report(FutureTask.java:122)
at java.base/java.util.concurrent.FutureTask.get(FutureTask.java:191)
at chap12.DateTimeExamples.main(DateTimeExamples.java:53)
Caused by: java.lang.NumberFormatException: For input string: ""
at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
at java.base/java.lang.Long.parseLong(Long.java:724)
at java.base/java.lang.Long.parseLong(Long.java:839)
at java.base/java.text.DigitList.getLong(DigitList.java:195)
at java.base/java.text.DecimalFormat.parse(DecimalFormat.java:2197)
at java.base/java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:1932)
at java.base/java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1542)
at java.base/java.text.DateFormat.parse(DateFormat.java:394)
at chap12.DateTimeExamples$2.call(DateTimeExamples.java:37)
at chap12.DateTimeExamples$2.call(DateTimeExamples.java:35)
at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:630)
at java.base/java.lang.Thread.run(Thread.java:831)
```

#### 결과 3
```text
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 20100
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Thu Oct 22 00:00:00 KST 201
Thu Oct 22 00:00:00 KST 201
Fri Oct 22 00:00:00 KST 2010
```

#### 결과 4
```text
Fri Oct 22 00:00:00 KST 2010
Fri Oct 22 00:00:00 KST 2010
Exception in thread "main" java.util.concurrent.ExecutionException: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 19
	at java.base/java.util.concurrent.FutureTask.report(FutureTask.java:122)
	at java.base/java.util.concurrent.FutureTask.get(FutureTask.java:191)
	at chap12.SimpleDateFormatIsNotThreadSafe.main(SimpleDateFormatIsNotThreadSafe.java:39)
Caused by: java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 19
	at java.base/java.text.DigitList.fitsIntoLong(DigitList.java:230)
	at java.base/java.text.DecimalFormat.parse(DecimalFormat.java:2195)
	at java.base/java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:1932)
	at java.base/java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1542)
	at java.base/java.text.DateFormat.parse(DateFormat.java:394)
	at chap12.SimpleDateFormatIsNotThreadSafe$1.call(SimpleDateFormatIsNotThreadSafe.java:23)
	at chap12.SimpleDateFormatIsNotThreadSafe$1.call(SimpleDateFormatIsNotThreadSafe.java:21)
```

## Temporal
- java.time.Temporal
- Temporal 인터페이스는 날짜와 시간 API의 모든 클래스가 구현하는 인터페이스다.
- 특정 시간을 모델링하는 객체의 값을 어떻게 읽고 조작할지 정의한다.

## LocalDate
- java.time.LocalDate
- LocalDate 인스턴스는 날짜를 표현하는 불변 객체다.
- 시간대 정보를 포함하지 않는다.
- 팩토리 메서드 now()는 시스템 시계의 정보를 이용해서 현재 날짜 정보를 얻는다.
- 날짜 문자열에서 LocalDate의 인스턴스를 만들 수 있다.

## LocalTime
- java.time.LocalTime
- LocalTime 인스턴스는 시간을 표현하는 불변 객체다.
- 시간 문자열에서 LocalTime의 인스턴스를 만들 수 있다.

## LocalDateTime
- java.time.LocalDateTime
- LocalDateTime은 LocalDate와 LocalTime을 쌍으로 갖는 복합 클래스다. 날짜와 시간을 모두 표현하는 불변 객체다.

## Instant
- java.time.Instant
- Instant 인스턴스는 유닉스 에포크 시간([Unix epoch time](https://github.com/yoo-jaein/TIL/blob/main/Java/Unix_epoch_time.md), 1970년 1월 1일 0시 0분 0초 UTC)을 기준으로 특정 지점까지의 시간을 초로 표현하는 불변 객체다.
- 팩토리 메서드 ofEpochSecond()에 초를 넘겨줘서 Instant 인스턴스를 만들 수 있다.
- 나노초(10억분의 1초)의 정밀도를 제공한다.

## Duration
- java.time.Duration
- Duration 인스턴스는 두 시간 객체 사이의 지속시간을 표현하는 불변 객체다.
- 초와 나노초로 시간 단위를 표현한다.
- 두 개의 LocalTime, 두 개의 LocalDateTime, 두 개의 Instant로 Duration 인스턴스를 만들 수 있다.

## Period
- java.time.Period
- Period 인스턴스는 두 시간 객체 사이의 지속시간을 표현하는 불변 객체다.
- 년, 월, 일로 시간 단위를 표현한다.

## TemporalUnit

## ChronoUnit

## TemporalAdjusters
- TemporalAdjusters : 여러 TemporalAdjuster를 반환하는 정적 팩토리 메서드를 포함하는 클래스
- TemporalAdjuster : 인터페이스

## DateTimeFormatter
- java.time.format.DateTimeFormatter
- BASIC_ISO_DATE
- ISO_LOCAL_DATE

## ZoneId
- java.time.ZoneId
- ZoneRules
- ZoneOffset
- ZonedDateTime
- OffsetDateTime

## 참고
