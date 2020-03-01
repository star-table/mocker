package org.nico.mocker.utils;

import java.util.Random;

public class RandomUtils {

	private static Random random = new Random();
	
	public static int random(int bound) {
		return random.nextInt(bound);
	}
}
