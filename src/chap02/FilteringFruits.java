package chap02;

import chap02.predicate.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringFruits {

	public static void main(String... args) {
		List<Apple> apples = Arrays.asList(
				new Apple(80, Color.GREEN),
				new Apple(155, Color.GREEN),
				new Apple(120, Color.RED));

		// [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
		List<Apple> greenApples = filter(apples, (Apple apple) -> Color.GREEN.equals(apple.getColor()));
		System.out.println(greenApples);


		List<Banana> bananas = Arrays.asList(
				new Banana(35, Color.GREEN),
				new Banana(55, Color.RED));

		// [Banana{color=GREEN, weight=35}]
		List<Banana> greenBanana = filter(bananas, (Banana banana) -> Color.GREEN.equals(banana.getColor()));
		System.out.println(greenBanana);
	}

	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> result = new ArrayList<>();
		for (T e : list) {
			if (p.test(e)) {
				result.add(e);
			}
		}
		return result;
	}
}
