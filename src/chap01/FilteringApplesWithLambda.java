package chap01;

import java.util.Arrays;
import java.util.List;

import static chap01.FilteringApples.filterApples;

public class FilteringApplesWithLambda {

	public static void main(String... args) {
		List<Apple> inventory = Arrays.asList(
				new Apple(80, "green"),
				new Apple(155, "green"),
				new Apple(120, "red")
		);

		// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
		List<Apple> greenApples2 = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
		System.out.println(greenApples2);

		// [Apple{color='green', weight=155}]
		List<Apple> heavyApples2 = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
		System.out.println(heavyApples2);

		// []
		List<Apple> weirdApples = filterApples(inventory,
				(Apple a) -> a.getWeight() < 80 || "brown".equals(a.getColor()));
		System.out.println(weirdApples);
	}
}
