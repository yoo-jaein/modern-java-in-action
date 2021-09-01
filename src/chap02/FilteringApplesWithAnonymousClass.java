package chap02;

import chap02.predicate.apple.ApplePredicate;

import java.util.Arrays;
import java.util.List;

import static chap02.FilteringApples.filter;

public class FilteringApplesWithAnonymousClass {

	public static void main(String... args) {
		List<Apple> inventory = Arrays.asList(
				new Apple(80, Color.GREEN),
				new Apple(155, Color.GREEN),
				new Apple(120, Color.RED));

		// [Apple{color=RED, weight=120}]
		List<Apple> redApples2 = filter(inventory, new ApplePredicate() {
			@Override
			public boolean test(Apple a) {
				return a.getColor() == Color.RED;
			}
		});
		System.out.println(redApples2);
	}
}
