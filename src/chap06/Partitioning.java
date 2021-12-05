package chap06;

import chap04.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static chap04.Dish.Type;
import static chap04.Dish.menu;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Partitioning {

	public static void main(String... args) {
		System.out.println("Dishes partitioned by vegetarian: " + partitionByVegetarian());
		System.out.println("Vegetarian Dishes by type: " + vegetarianDishesByType());
		System.out.println("Most caloric dishes by vegetarian: " + mostCaloricPartitionedByVegetarian());
	}

	private static Map<Boolean, List<Dish>> partitionByVegetarian() {
		return menu.stream().collect(partitioningBy(Dish::isVegetarian));
	}

	private static Map<Boolean, Map<Type, List<Dish>>> vegetarianDishesByType() {
		return menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
	}

	private static Object mostCaloricPartitionedByVegetarian() {
		return menu.stream().collect(
				partitioningBy(Dish::isVegetarian,
						collectingAndThen(
								maxBy(comparingInt(Dish::getCalories)),
								Optional::get)));
	}

}
