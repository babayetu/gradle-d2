package com.battle.ch6;

public class StampedLockBitsTest {
    private static final int LG_READERS = 7;

    private static final long RUNIT = 1L;
    private static final long WBIT  = 1L << LG_READERS;
    private static final long RBITS = WBIT - 1L;
    private static final long RFULL = RBITS - 1L;
    private static final long ABITS = RBITS | WBIT;
    private static final long SBITS = ~RBITS; // note overlap with ABITS
    
	public static void main(String[] args) {
		System.out.println("WBIT:" + WBIT );
		System.out.println("RBITS:" + RBITS);
		System.out.println("ABITS:" + ABITS);
		System.out.println("SBITS:" + SBITS);
	}

}
