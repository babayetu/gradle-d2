package com.battle.ch6;

import java.util.ArrayList;
import java.util.List;

public class ParallelMapToStreamDemo {

	public static void main(String[] args) {
		List<Student> arr = new ArrayList<Student>();
		for (int i = 0; i < 10; i++) {
			arr.add(new Student(i,"name"+i));
		}
		
		double averageScore = arr.parallelStream().parallel().mapToInt((x) -> x.getScore()).average().getAsDouble();
		System.out.println("平均分是"+averageScore);
	}

}
