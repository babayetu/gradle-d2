package com.battle.ch6;

public interface Donkey {
	void piss();
	default void run() {
		System.out.println("donkey can run");
	}
}
