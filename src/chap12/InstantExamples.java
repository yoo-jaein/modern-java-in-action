package chap12;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InstantExamples {
	public static void main(String[] args) {
		// Instant
		System.out.println("============ Instant ============");
		System.out.println("Instant.MIN = " + Instant.MIN); // -1000000000-01-01T00:00:00Z
		System.out.println("Instant.EPOCH = " + Instant.EPOCH); // 1970-01-01T00:00:00Z
		System.out.println("Instant.now() = " + Instant.now());
		System.out.println("Instant.now().getEpochSecond() = " + Instant.now().getEpochSecond());
		System.out.println("Instant.MAX = " + Instant.MAX); // +1000000000-12-31T23:59:59.999999999Z

		Instant instant1 = Instant.ofEpochSecond(16000_00000);
		System.out.println("instant1.toEpochMilli() = " + instant1.toEpochMilli()); // 1600000000000
		Instant instant2 = Instant.ofEpochMilli(16000_00000_000L);
		System.out.println("instant1.isBefore(instant2) = " + instant1.isBefore(instant2)); // false

		System.out.println("instant1 = " + instant1); // 2020-09-13T12:26:40Z
		System.out.println("instant1.plus(1, ChronoUnit.DAYS) = " + instant1.plus(1, ChronoUnit.DAYS)); // 2020-09-14T12:26:40Z
	}
}
