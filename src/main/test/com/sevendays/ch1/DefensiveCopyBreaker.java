package com.sevendays.ch1;

import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;

public class DefensiveCopyBreaker {

	@Test
	public void testOne() {
		Date start = new Date();
		Date end = new Date();
		DefensiveCopy dc = new DefensiveCopy(start,end);
		start.setYear(118);
		System.out.println(dc.getStart());
		System.out.println(dc.getEnd());
		assertFalse(dc.getStart().compareTo(dc.getEnd()) > 0);
		
	}
	
	@Test
	public void testTwo() {
		Date start = new Date();
		Date end = new Date();
		DefensiveCopy dc = new DefensiveCopy(start,end);
		dc.getStart().setYear(118);
		System.out.println(dc.getStart());
		System.out.println(dc.getEnd());
		assertFalse(dc.getStart().compareTo(dc.getEnd()) > 0);
	}
}
