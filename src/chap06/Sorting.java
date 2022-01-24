package chap06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorting {

	public static void main(String[] args) {
		List<Student> list = new ArrayList<>();
		list.add(new Student(1, "kim", 1));
		list.add(new Student(2, "jun", 2));
		list.add(new Student(3, "seo", 3));
		list.add(new Student(4, "seo", 2));
		list.add(new Student(5, "han", 3));
		list.add(new Student(6, "min", 1));
		list.add(new Student(7, "yoo", 1));
		list.add(new Student(8, "lee", 2));
		list.add(new Student(9, "min", 3));
		list.add(new Student(10, "lee", 2));

		System.out.println("========== 학년순 정렬 ==========");
		list.stream() // Student가 Comparable 인터페이스를 구현하지 않았으므로 직접 Comparator를 정의해준다
				.sorted(Comparator.comparing(Student::getGrade)) // grade를 비교하는 Comparator
				.collect(Collectors.toList())
				.forEach(System.out::println);

		System.out.println("====== 학년순 -> 이름순 정렬 ======");
		list.stream()
				.sorted(Comparator.comparing(Student::getGrade)
						.thenComparing(Student::getStudentName))
				.collect(Collectors.toList())
				.forEach(System.out::println);

		System.out.println("==== 학년순 -> 이름역순 -> id순 ====");
		list.stream()
				.sorted(Comparator.comparing(Student::getGrade)
						.thenComparing(Student::getStudentName, Comparator.reverseOrder())
						.thenComparing(Student::getStudentId))
				.collect(Collectors.toList())
				.forEach(System.out::println);

		System.out.println("= 학년순 -> 이름순 -> id순 -> 뒤집기 =");
		list.stream()
				.sorted(Comparator.comparing(Student::getGrade)
						.thenComparing(Student::getStudentName)
						.thenComparing(Student::getStudentId).reversed())
				.collect(Collectors.toList())
				.forEach(System.out::println);

		// Comparator를 외부에서 생성하고 주입시켜줄 수도 있다.
		Comparator<Student> compareByGrade = Comparator.comparing(Student::getGrade); //student -> student.getGrade()
		Comparator<Student> compareByStudentName = Comparator.comparing(Student::getStudentName); //student -> student.getStudentName()
		Comparator<Student> compare = compareByGrade.thenComparing(compareByStudentName);
		List<Student> students = list.stream().sorted(compare).collect(Collectors.toList());
	}

	public static class Student {
		int studentId;
		String studentName;
		int grade;

		@Override
		public String toString() {
			return "Student{" +
					"studentId=" + studentId +
					", studentName='" + studentName + '\'' +
					", grade=" + grade +
					'}';
		}

		public Student(int studentId, String studentName, int grade) {
			this.studentId = studentId;
			this.studentName = studentName;
			this.grade = grade;
		}

		public int getStudentId() {
			return studentId;
		}

		public String getStudentName() {
			return studentName;
		}

		public int getGrade() {
			return grade;
		}
	}
}
