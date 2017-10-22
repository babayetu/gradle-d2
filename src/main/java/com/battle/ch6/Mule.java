package com.battle.ch6;

public class Mule implements Horse, Donkey {

	@Override
	public void piss() {
		System.out.println("mule piss");

	}

	@Override
	public void eat() {
		System.out.println("mule eat");

	}
	
	/**
	 * 多重继承，必须显式指明需要调用的父函数，在有冲突的时候
	 */
	@Override
	public void run() {
		Donkey.super.run();
	}
	
	public static void main(String[] args) {
		Mule mule = new Mule();
		mule.eat();
		mule.piss();
		mule.run();
	}

}
