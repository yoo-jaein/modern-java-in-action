package chap01;

import java.util.Arrays;
import java.util.List;

import static chap01.FilteringApples.*;

public class FilteringApplesWithMethodReference {

	public static void main(String... args) {
		List<Apple> inventory = Arrays.asList(
				new Apple(80, "green"),
				new Apple(155, "green"),
				new Apple(120, "red")
		);

		// [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
		List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple);
		System.out.println(greenApples);

		// [Apple{color='green', weight=155}]
		List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);
		System.out.println(heavyApples);
	}
}
