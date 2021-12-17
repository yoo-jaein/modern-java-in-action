package chap08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapOperation {
	public static void main(String[] args) {
		Map<String, Integer> fruitsPriceMap = Map.ofEntries(
				Map.entry("Banana", 300),
				Map.entry("Apple", 100),
				Map.entry("Kiwi", 200)
		);

		fruitsPriceMap.forEach((key, value) -> System.out.println(key + " " + value));
		System.out.println("==============");

		for (Map.Entry<String, Integer> entry : fruitsPriceMap.entrySet()) {
			System.out.println("entry.getKey() = " + entry.getKey());
			System.out.println("entry.getValue() = " + entry.getValue());
		}

		System.out.println("==============");

		fruitsPriceMap.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.forEachOrdered(System.out::println);

		System.out.println("==============");

		fruitsPriceMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.forEachOrdered(System.out::println);

		System.out.println("==============");

		System.out.println("Price of Apple : " + fruitsPriceMap.getOrDefault("Apple", -1));
		System.out.println("Price of Mango : " + fruitsPriceMap.getOrDefault("Mango", -1));

		System.out.println("==============");

		Map<String, Integer> fruitsPriceMap2 = Map.ofEntries(
				Map.entry("Cherry", 250),
				Map.entry("Grape", 400),
				Map.entry("Pear", 50)
		);


	}
}
