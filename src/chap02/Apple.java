package chap02;

public class Apple {

	private int weight = 0;
	private Color color;

	public Apple(int weight, Color color) {
		this.weight = weight;
		this.color = color;
	}

	public int getWeight() {
		return weight;
	}

	public Color getColor() {
		return color;
	}

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("Apple{color='%s', weight=%d}", color, weight);
	}

}