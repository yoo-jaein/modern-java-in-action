package chap02.predicate.apple;

import chap02.Apple;
import chap02.Color;

public class AppleRedAndHeavyPredicate implements ApplePredicate {

	@Override
	public boolean test(Apple apple) {
		return apple.getColor() == Color.RED && apple.getWeight() > 150;
	}

}