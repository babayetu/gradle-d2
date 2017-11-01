package com.battle.ch6;

public class PrimeUtil {
	public static boolean isPrime(int number) {
		int temp = number;
		if (temp<2) {
			return false;
		}
		
		for (int i = 2; Math.sqrt(temp) >= i; i++) {
			if (temp % i == 0) {
				return false;
			}
		}
		
		return true;
	}
}
