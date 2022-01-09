package chap11;

import java.util.Optional;
import java.util.Properties;

import static java.util.Optional.*;

public class ReadPositiveIntParam {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.setProperty("a", "5");
		props.setProperty("b", "true");
		props.setProperty("c", "-3");

		System.out.println("result = " + readDurationWithOptional(props, "a")); // 5
		System.out.println("result = " + readDurationWithOptional(props, "b")); // 0
		System.out.println("result = " + readDurationWithOptional(props, "c")); // 0
		System.out.println("result = " + readDurationWithOptional(props, "d")); // 0
	}

	public static int readDurationImperative(Properties props, String name) {
		String value = props.getProperty(name);
		if (value != null) {
			try {
				int i = Integer.parseInt(value);
				if (i > 0) {
					return i;
				}
			} catch (NumberFormatException nfe) {
			}
		}
		return 0;
	}

	public static int readDurationWithOptional(Properties props, String name) {
		return ofNullable(props.getProperty(name))
				.flatMap(ReadPositiveIntParam::s2i)
				.filter(i -> i > 0).orElse(0);
	}

	public static Optional<Integer> s2i(String s) {
		try {
			return of(Integer.parseInt(s));
		} catch (NumberFormatException e) {
			return empty();
		}
	}
}
