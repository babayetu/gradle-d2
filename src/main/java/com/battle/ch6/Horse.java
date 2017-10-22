package com.battle.ch6;

/**
 * 演示Java 8 之后的多重继承
 * @author jingjing03.liu
 *
 */
public interface Horse {
	void eat();
	default void run() {
		System.out.println("horse can run");
	}
}
