package chap08;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class ListOperation {
	public static void main(String[] args) {
		List<String> fruitsList = Arrays.asList("apple", "banana", "kiwi");
		fruitsList.stream()
				.map(fruit -> Character.toUpperCase(fruit.charAt(0)) + fruit.substring(1))
				.collect(Collectors.toList())
				.forEach(System.out::println);

		fruitsList.forEach(System.out::println);
		System.out.println("==============");

		for (ListIterator<String> iterator = fruitsList.listIterator(); iterator.hasNext();) {
			String fruit = iterator.next();
			iterator.set(Character.toUpperCase(fruit.charAt(0)) + fruit.substring(1));
		}

		fruitsList.forEach(System.out::println);
		System.out.println("==============");

		List<String> fruitsList2 = Arrays.asList("grape", "mango", "cherry");
		fruitsList2.replaceAll(fruit -> Character.toUpperCase(fruit.charAt(0)) + fruit.substring(1));

		fruitsList2.forEach(System.out::println);
	}
}
