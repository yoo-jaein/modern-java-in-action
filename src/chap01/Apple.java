package chap01;

public class Apple {

	private int weight = 0;
	private String color = "";

	public Apple(int weight, String color) {
		this.weight = weight;
		this.color = color;
	}

	public int getWeight() {
		return weight;
	}

	public String getColor() {
		return color;
	}

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("Apple{color='%s', weight=%d}", color, weight);
	}

}