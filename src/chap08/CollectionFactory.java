package chap08;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionFactory {
	public static void main(String[] args) {
		List<String> fruitsList = List.of("Apple", "Banana", "Kiwi");
		// fruitsList.add("Mango"); 새 요소를 추가하거나 요소를 삭제할 수 없기 때문에 UnsupportedOperationException 발생!

		Set<String> fruitsSet = Set.of("Apple", "Banana", "Kiwi");
		// fruitsSet.add("Apple"); 집합은 오직 고유한 요소만 가질 수 있기 때문에 IllegalArgumentException 발생!

		Map<String, Integer> fruitsPriceMap = Map.of("Apple", 100, "Banana", 200, "Kiwi", 300);
		Map<String, Integer> fruitsPriceMapFromEntry = Map.ofEntries(
				Map.entry("Apple", 100),
				Map.entry("Banana", 200),
				Map.entry("Kiwi", 300)
		);
	}
}
