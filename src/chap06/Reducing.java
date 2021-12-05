package chap06;

import chap04.Dish;

import java.util.stream.Collectors;

import static chap04.Dish.menu;

public class Reducing {

	public static void main(String... args) {
		System.out.println("Total calories in menu: " + calculateTotalCalories());
		System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodReference());
		System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
	}

	private static int calculateTotalCalories() {
		return menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (Integer i, Integer j) -> i + j));
	}

	private static int calculateTotalCaloriesWithMethodReference() {
		return menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
	}

	private static int calculateTotalCaloriesUsingSum() {
		return menu.stream().mapToInt(Dish::getCalories).sum();
	}

}
