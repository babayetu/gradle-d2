package com.battle.ch6;

/**
 * FunctionalInterface和Overrider类似
 * 是IDE检查标志
 * 函数式接口只能有一个接口函数，但是不包括Object实现的函数，比如equals
 * @author jingjing03.liu
 *
 */
@FunctionalInterface
public interface IntHandler {
	void process();
	boolean equals(Object obj);
}
