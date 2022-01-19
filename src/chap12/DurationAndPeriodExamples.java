package chap12;

import java.time.*;

public class DurationAndPeriodExamples {
	public static void main(String[] args) {
		// Duration
		System.out.println("============ Duration ============");
		LocalTime localTime1 = LocalTime.of(13, 0, 0);
		LocalTime localTime2 = LocalTime.of(14, 0, 0);
		Duration duration1 = Duration.between(localTime1, localTime2);
		System.out.println("duration1.getSeconds() = " + duration1.getSeconds()); // 3600

		Duration duration2 = Duration.ofDays(1);
		System.out.println("duration1.plus(duration2).toHours() = " + duration1.plus(duration2).toHours()); // 25

		Instant instant1 = Instant.ofEpochSecond(1500000000);
		Instant instant2 = Instant.ofEpochSecond(1600000000);
		Duration duration3 = Duration.between(instant1, instant2);
		System.out.println("duration3.getSeconds() = " + duration3.getSeconds()); // 100000000
		System.out.println("duration3.toHours() = " + duration3.toHours()); // 27777

		// Period
		System.out.println("============= Period =============");
		LocalDate localDate1 = LocalDate.of(2022, 1, 1);
		LocalDate localDate2 = LocalDate.of(2022, 1, 7);
		Period period = Period.between(localDate1, localDate2);
		System.out.println("period.getDays() = " + period.getDays()); // 6
		System.out.println("period.getUnits() = " + period.getUnits()); // [Years, Months, Days]
	}
}
