package chap12;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeExamples {
	public static void main(String[] args) {
		//LocalDate
		System.out.println("============ LocalDate ============");
		System.out.println("LocalDate.MIN = " + LocalDate.MIN);
		System.out.println("LocalDate.EPOCH = " + LocalDate.EPOCH);
		System.out.println("LocalDate.MAX = " + LocalDate.MAX);

		LocalDate localDate1 = LocalDate.now();
		LocalDate localDate2 = LocalDate.of(2021, 1, 18);
		System.out.println("localDate1.isAfter(localDate2) = " + localDate1.isAfter(localDate2));
		System.out.println("localDate1.isAfter(localDate2) = " + localDate1.isAfter(localDate2.plusYears(100)));
		System.out.println("localDate1.atStartOfDay() = " + localDate1.atStartOfDay());

		//LocalTime
		System.out.println();
		System.out.println("============ LocalTime ============");
		System.out.println("LocalTime.MIN = " + LocalTime.MIN);
		System.out.println("LocalTime.NOON = " + LocalTime.NOON);
		System.out.println("LocalTime.MAX = " + LocalTime.MAX);

		LocalTime localTime1 = LocalTime.now();
		LocalTime localTime2 = LocalTime.of(23, 10, 25);
		System.out.println("localTime1.plusMinutes(localTime2.getMinute()) = " + localTime1.plusMinutes(localTime2.getMinute()));
		System.out.println("localTime1.toSecondOfDay() = " + localTime1.toSecondOfDay());

		//LocalDateTime
		System.out.println();
		System.out.println("========== LocalDateTime ==========");
		System.out.println("LocalDateTime.MIN = " + LocalDateTime.MIN);
		System.out.println("LocalDateTime.MAX = " + LocalDateTime.MAX);

		LocalDateTime localDateTime1 = LocalDateTime.now();
		LocalDateTime localDateTime2 = LocalDateTime.of(2021, 1, 1, 12, 10, 0);
		System.out.println("localDateTime2.isBefore(localDateTime1) = " + localDateTime2.isBefore(localDateTime1));
		System.out.println("localDateTime2.toLocalTime() = " + localDateTime2.toLocalTime());
	}
}
