package chap02;

import chap02.predicate.apple.AppleColorPredicate;
import chap02.predicate.apple.AppleRedAndHeavyPredicate;
import chap02.predicate.apple.AppleWeightPredicate;

import java.util.Arrays;
import java.util.List;

import static chap02.FilteringApples.filter;

public class FilteringApplesWithPredicate {

	public static void main(String... args) {
		List<Apple> inventory = Arrays.asList(
				new Apple(80, Color.GREEN),
				new Apple(155, Color.GREEN),
				new Apple(120, Color.RED));

		// [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
		List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());
		System.out.println(greenApples2);

		// [Apple{color=GREEN, weight=155}]
		List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
		System.out.println(heavyApples);

		// []
		List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
		System.out.println(redAndHeavyApples);
	}
}
