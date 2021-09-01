package chap02;

public class Banana {
	private int weight = 0;
	private Color color;

	public Banana(int weight, Color color) {
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
		return String.format("Banana{color='%s', weight=%d}", color, weight);
	}

}
