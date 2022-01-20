package chap12;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterExamples {
	public static void main(String[] args) {
		// DateTimeFormatter
		System.out.println("======== DateTimeFormatter ========");
		System.out.println("DateTimeFormatter.ISO_DATE = " + DateTimeFormatter.ISO_DATE); // ParseCaseSensitive(false)(Value(Year,4,10,EXCEEDS_PAD)'-'Value(MonthOfYear,2)'-'Value(DayOfMonth,2))[Offset(+HH:MM:ss,'Z')]
		System.out.println("DateTimeFormatter.ISO_LOCAL_DATE_TIME = " + DateTimeFormatter.ISO_LOCAL_DATE_TIME); // ParseCaseSensitive(false)(Value(Year,4,10,EXCEEDS_PAD)'-'Value(MonthOfYear,2)'-'Value(DayOfMonth,2))'T'(Value(HourOfDay,2)':'Value(MinuteOfHour,2)[':'Value(SecondOfMinute,2)[Fraction(NanoOfSecond,0,9,DecimalPoint)]])
		System.out.println("DateTimeFormatter.BASIC_ISO_DATE = " + DateTimeFormatter.BASIC_ISO_DATE); // ParseCaseSensitive(false)Value(Year,4)Value(MonthOfYear,2)Value(DayOfMonth,2)[ParseStrict(false)Offset(+HHMMss,'Z')ParseStrict(true)]

		String str1 = "2021.01.01 23:59:59";
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		LocalDateTime localDateTime1 = LocalDateTime.parse(str1, formatter1);
		System.out.println("localDateTime1 = " + localDateTime1); // 2021-01-01T23:59:59

		String str2 = "2021년 02월 01일 23시 59분 59초 입니다.";
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초 입니다.");
		LocalDateTime localDateTime2 = LocalDateTime.parse(str2, formatter2);
		System.out.println("localDateTime2 = " + localDateTime2); // 2021-02-01T23:59:59
	}
}
