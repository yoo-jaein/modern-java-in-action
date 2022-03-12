# Chapter 16 CompletableFuture : 안정적 비동기 프로그래밍

## Future
Future는 비동기 작업의 결과를 나타내는 데 사용되는 인터페이스다. Future 객체는 ExecutorService의 submit() 메서드에서 반환된다. Future 인터페이스는 작업이 완료되었는지 여부를 확인하고 작업이 끝났을 때 결과에 접근할 수 있는 여러 메서드를 제공한다. Future를 사용했을 때의 이점은 다음과 같다. 시간이 오래 걸릴 수 있는 작업을 Future 내부로 설정하고 호출자 스레드가 결과를 기다리는 동안 다른 작업을 수행할 수 있다. 또한, 저수준의 스레드를 사용할 때에 비해 코드를 직관적으로 이해하기 쉽다. 

## Future의 메서드
### get()
```java
V get() throws InterruptedException, ExecutionException;
V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
```
Future의 결과를 가져온다. 만약 결과가 아직 리턴되지 않았다면 기다린다. 중요한 점은 get()은 결과가 반환되기 전까지 애플리케이션의 진행을 블록한다는 것이다. 두 번째 get()에는 timeout 시간을 지정할 수 있는데, 이 시간 동안 결과가 반환되지 않으면 TimeoutException을 던진다.

### cancel()
```java
boolean cancel(boolean mayInterruptIfRunning);
```
현재 Task의 중단을 시도한다. Task가 이미 완료되었다면 cancel 명령은 동작하지 않는다.

### isCancelled(), isDone()
```java
boolean isCancelled();
```

```java
boolean isDone();
```
현재 Callable Task의 상태를 알 수 있다. 일반적으로 get()과 isDone(), cancel()과 isCancelled()이 짝지어 사용된다.

## Future 사용하기
Future를 이용하려면 원하는 작업을 Callable 객체 내부로 감싼 다음에 ExecutorService에 제출해야 한다.

```java
ExecutorService executor = Executors.newCachedThreadPool();
Future<Double> future = executor.submit(new Callable<Double>() { // executor.submit()에 Callable을 전달한다.
	public Double call() {
		return doSomeLongComputation(); // 비동기로 실행하고 싶은 메서드를 Callable로 감싼다.
	}
});

doSomethingElse(); // doSomeLongComputation()을 실행시켜두고 또 다른 작업을 수행한다.

try {
	Double result = future.get(1, TimeUnit.SECONDS); // doSomeLongComputation()이 끝나면 get()으로 결과를 가져온다. 만약 결과가 준비되어 있지 않으면 호출 스레드가 블록되며 1초까지 기다린다.
} catch (ExecutionException ee) { } // 계산 중에 예외가 발생했다.
catch (InterruptedException ie) { } // 스레드에서 대기 중 인터럽트가 발생했다.
catch (TimeoutException te) { } // Future가 완료되기 전에 타임아웃이 발생했다.
```

## Future의 한계와 CompletableFuture
Future은 복잡한 동시성 상황을 구현하기에 충분하지 않다. 예를 들어 A의 계산이 끝나면 그 결과를 B로 전달하고 B의 결과가 나오면 C의 결과와 B의 결과를 조합하는 상황을 Future로 구현하는 것은 쉽지 않다. 자바 8에서 새로 제공하는 CompletableFuture 클래스는 Future 인터페이스를 구현한 것으로 복잡한 비동기 애플리케이션을 만들 수 있는 다양한 기능을 제공한다.

## 동기 API와 비동기 API
엄격하게 동기(Synchronous)와 비동기(Asynchronous)를 구분해보면 호출되는 함수(callee)의 작업 완료 여부를 누가 확인하냐가 다르다. 호출한 함수가 호출된 함수의 작업이 완료된 후 리턴을 기다리거나, 호출된 함수로부터 바로 리턴받더라도 (블록/논블록 상관 없이) 호출한 함수가 호출된 함수의 작업 완료 여부를 확인한다면 동기다. 호출된 함수에게 콜백을 전달해서 자신의 작업이 완료되었을 때 콜백을 실행하게 만들면 비동기다.  

모던 자바 인 액션 16장에서 말하는 전통적인 동기 API는 동기 & 블로킹 API를 뜻하며 비동기 API는 비동기 & 논블로킹 API를 뜻한다. 동기 API에서는 메서드를 호출한 다음에 메서드가 계산을 완료할 때까지 기다렸다가 메서드가 반환되면 호출자는 반환된 값으로 계속 다음 동작을 수행한다. 비동기 API에서는 메서드가 즉시 반환되며 나머지 작업을 호출자 스레드와 동기적으로 실행될 수 있도록 다른 스레드에 할당한다.

