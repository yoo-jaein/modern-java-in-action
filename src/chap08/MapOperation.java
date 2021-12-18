package chap08;

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

		Map<String, String> moviesMap = new HashMap<>();
		moviesMap.put("Raphael", "Star Wars");
		moviesMap.put("Olivia", "james bond");

		moviesMap.replaceAll((key, value) -> value.toUpperCase());
		moviesMap.forEach((key, value) -> System.out.println("key = " + key + ", value = " + value));

		System.out.println("==============");

		Map<String, Integer> bookPriceMap1 = Map.ofEntries(
				Map.entry("Book A", 250),
				Map.entry("Book B", 400),
				Map.entry("Book C", 50)
		);

		Map<String, Integer> bookPriceMap2 = Map.ofEntries(
				Map.entry("Book B", 400),
				Map.entry("Book C", 150),
				Map.entry("Book D", 500)
		);

		Map<String, Integer> totalBookPriceMap = new HashMap<>(bookPriceMap1);
		// bookPriceMap1과 bookPriceMap2를 머지한 결과를 totalBookPriceMap에 저장한다.
		// 만약 bookPriceMap1과 bookPriceMap2에 동일한 key가 있을 경우 두 value를 더한 값을 저장한다.
		bookPriceMap2.forEach(
				(key, value) -> totalBookPriceMap.merge(key, value, (book1, book2) -> book1 + book2));
		totalBookPriceMap.forEach((key, value) -> System.out.println("key = " + key + ", value = " + value));

	}
}
