package chap06;

import chap04.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static chap04.Dish.Type;
import static chap04.Dish.menu;
import static java.util.stream.Collectors.*;

public class Grouping {

	public static void main(String... args) {
		System.out.println("Dishes grouped by type: " + groupDishesByType());
		System.out.println("Dish names grouped by type: " + groupDishNamesByType());
		System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
		System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
		System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
		System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
		System.out.println("Count dishes in groups: " + countDishesInGroups());
		System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
		System.out.println("Sum calories by type: " + sumCaloriesByType());
		System.out.println("Caloric levels by type: " + caloricLevelsByType());
	}

	private static Map<Type, List<Dish>> groupDishesByType() {
		return menu.stream().collect(groupingBy(Dish::getType));
	}

	private static Map<Type, List<String>> groupDishNamesByType() {
		return menu.stream().collect(
				groupingBy(Dish::getType,
						mapping(Dish::getName, toList())));
	}

	private static Map<Type, Set<String>> groupDishTagsByType() {
		return menu.stream().collect(
				groupingBy(Dish::getType,
						flatMapping(dish -> Dish.dishTags.get(dish.getName()).stream(), toSet())));
	}

	private static Map<Type, List<Dish>> groupCaloricDishesByType() {
		return menu.stream().collect(
				groupingBy(Dish::getType,
						filtering(dish -> dish.getCalories() > 500, toList())));
	}

	private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
		return menu.stream().collect(
				groupingBy(dish -> {
					if (dish.getCalories() <= 400) {
						return CaloricLevel.DIET;
					} else if (dish.getCalories() <= 700) {
						return CaloricLevel.NORMAL;
					} else {
						return CaloricLevel.FAT;
					}
				})
		);
	}

	private static Map<Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
		return menu.stream().collect(
				groupingBy(Dish::getType,
						groupingBy((Dish dish) -> {
							if (dish.getCalories() <= 400) {
								return CaloricLevel.DIET;
							} else if (dish.getCalories() <= 700) {
								return CaloricLevel.NORMAL;
							} else {
								return CaloricLevel.FAT;
							}
						})
				)
		);
	}

	private static Map<Type, Long> countDishesInGroups() {
		return menu.stream().collect(groupingBy(Dish::getType, counting()));
	}

	private static Map<Type, Optional<Dish>> mostCaloricDishesByType() {
		return menu.stream().collect(
				groupingBy(Dish::getType,
						reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
	}

	private static Map<Type, Integer> sumCaloriesByType() {
		return menu.stream().collect(groupingBy(Dish::getType,
				summingInt(Dish::getCalories)));
	}

	private static Map<Type, Set<CaloricLevel>> caloricLevelsByType() {
		return menu.stream().collect(
				groupingBy(Dish::getType, mapping(
						dish -> {
							if (dish.getCalories() <= 400) {
								return CaloricLevel.DIET;
							} else if (dish.getCalories() <= 700) {
								return CaloricLevel.NORMAL;
							} else {
								return CaloricLevel.FAT;
							}
						},
						toSet()
				))
		);
	}

}
