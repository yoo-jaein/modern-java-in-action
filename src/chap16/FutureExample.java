package chap16;

import java.util.concurrent.*;

public class FutureExample {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		future();
	}

	public static void future() {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Double> future = executor.submit(new Callable<Double>() { // executor.submit()에 Callable을 전달한다.
			public Double call() {
				return doSomeLongComputation(); // 비동기로 실행하고 싶은 메서드를 Callable로 감싼다.
			}
		});

		doSomethingElse(); // doSomeLongComputation()을 실행시켜두고 또 다른 작업을 수행한다.

		try {
			// doSomeLongComputation()이 끝나면 get()으로 결과를 가져온다. 만약 결과가 준비되어 있지 않으면 호출 스레드가 블록되며 10초까지 기다린다.
			Double result = future.get(10, TimeUnit.SECONDS);
			System.out.println("result = " + result);
		} catch (ExecutionException ee) {
			System.out.println("Execution Exception"); // 계산 중에 예외가 발생했다.
		} catch (InterruptedException ie) {
			System.out.println("Interrupted Exception"); // 스레드에서 대기 중 인터럽트가 발생했다.
		} catch (TimeoutException te) {
			System.out.println("Timeout Exception"); // Future가 완료되기 전에 타임아웃이 발생했다.
		}
		executor.shutdown();
	}

	public static Double doSomeLongComputation() {
		System.out.println("==Run doSomeLongComputation==");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("==Finish doSomeLongComputation==");
		return 10.0;
	}

	public static void doSomethingElse() {
		System.out.println("==Run doSomethingElse==");
		System.out.println("==Finish doSomethingElse==");
	}
}
