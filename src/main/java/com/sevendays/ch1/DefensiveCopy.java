package com.sevendays.ch1;

import java.util.Date;

public class DefensiveCopy {
	private final Date start;
	private final Date end;

	public DefensiveCopy(Date start, Date end) {
		start = new Date(start.getTime());
		end = new Date(end.getTime());
		if (start.compareTo(end) > 0) {
			throw new IllegalArgumentException(start + "after" + end);
		}
		this.start = start;
		this.end = end;
	}

	public Date getStart() {
		return new Date(start.getTime());
	}

	public Date getEnd() {
		return new Date(end.getTime());
	}
	
	
}
