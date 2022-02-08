package chap15;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureCombine {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		int x = 10;

		CompletableFuture<Integer> a = new CompletableFuture<>();
		CompletableFuture<Integer> b = new CompletableFuture<>();
		CompletableFuture<Integer> c = a.thenCombine(b, (y, z) -> y + z);
		executorService.submit(() -> a.complete(f(x)));
		executorService.submit(() -> b.complete(g(x)));

		System.out.println(c.get());
		executorService.shutdown();
	}

	public static int f(int x) {
		return x * 2;
	}

	public static int g(int x) {
		return x + 1;
	}
}
