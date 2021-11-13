package chap03;

import chap02.Apple;
import chap02.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class Sorting {

	public static void main(String... args) {
		List<Apple> inventory = new ArrayList<>();
		inventory.addAll(Arrays.asList(
				new Apple(80, Color.GREEN),
				new Apple(155, Color.GREEN),
				new Apple(120, Color.RED)
		));

		// 1단계 : 코드 전달
		inventory.sort(new AppleComparator());
		System.out.println(inventory);
		// [Apple{color=GREEN, weight=80}, Apple{color=RED, weight=120}, Apple{color=GREEN, weight=155}]

		inventory.set(1, new Apple(30, Color.GREEN));

		// 2단계 : 익명 클래스 사용
		inventory.sort(new Comparator<Apple>() {
			@Override
			public int compare(Apple a1, Apple a2) {
				return a1.getWeight() - a2.getWeight();
			}
		});
		System.out.println(inventory);
		// [Apple{color=GREEN, weight=30}, Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]

		inventory.set(1, new Apple(20, Color.RED));

		// 3단계 : 람다 표현식 사용
		inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
		System.out.println(inventory);
		// [Apple{color=RED, weight=20}, Apple{color=GREEN, weight=30}, Apple{color=GREEN, weight=155}]

		inventory.set(1, new Apple(10, Color.RED));

		// 4단계 : 메서드 참조 사용
		inventory.sort(comparing(Apple::getWeight)); //import static java.util.Comparator.comparing;
		System.out.println(inventory);
		// [Apple{color=RED, weight=10}, Apple{color=RED, weight=20}, Apple{color=GREEN, weight=155}]
	}

	static class AppleComparator implements Comparator<Apple> { // 1단계
		@Override
		public int compare(Apple a1, Apple a2) {
			return a1.getWeight() - a2.getWeight();
		}
	}
}

