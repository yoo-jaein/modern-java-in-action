package chap12;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZoneExamples {
	public static void main(String[] args) {
		// ZoneId
		System.out.println("============ ZoneId ============");
		System.out.println("ZoneId.systemDefault() = " + ZoneId.systemDefault()); // Asia/Seoul
		System.out.println("ZoneId.of(\"Africa/Cairo\") = " + ZoneId.of("Africa/Cairo")); // Africa/Cairo

		// ZonedDateTime
		System.out.println("======== ZonedDateTime ========");
		System.out.println("ZonedDateTime.now() = " + ZonedDateTime.now()); // 2022-01-20T22:46:05.696827+09:00[Asia/Seoul]
		System.out.println("ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(\"Africa/Cairo\")) = "
				+ ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Africa/Cairo"))); // 2022-01-20T22:46:05.697656+02:00[Africa/Cairo]
	}
}
