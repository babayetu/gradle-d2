package com.battle.ch4;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.apache.log4j.Logger;

public class LockFreeVector<E> {
	private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
	private AtomicReference<Descriptor<E>> descriptor;
	private final static int N_BUCKET = 30;
	private final static int FIRST_BUCKET_SIZE = 8;
	private final static int ZERO_NUM_FIRST = 28;
	private static Logger logger = Logger.getLogger(LockFreeVector.class);
	private static final boolean debug = true;
	
	public LockFreeVector() {
		this.buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
		buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
		descriptor = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,null));
	}
	
	public E get(int idx) {
		int pos = idx + FIRST_BUCKET_SIZE;
		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
		int bucketIdx = ZERO_NUM_FIRST - zeroNumPos;
		int index = (0x80000000 >>> zeroNumPos)^pos;
		return buckets.get(bucketIdx).get(index);
	}
	
	public void pushBack(E e) {
		Descriptor<E> desc;
		Descriptor<E> newd;
		
		do {
			desc = descriptor.get();
			desc.completeWrite();
			
			int pos = desc.size + FIRST_BUCKET_SIZE;
			int zeroNumPos = Integer.numberOfLeadingZeros(pos);
			int bucketIdx = ZERO_NUM_FIRST - zeroNumPos;
			if (buckets.get(bucketIdx) == null) {
				int newLen = 2 * buckets.get(bucketIdx - 1).length();
				if (debug) {
					logger.info("new Length is:" + newLen);
				}
				
				buckets.compareAndSet(bucketIdx, null, new AtomicReferenceArray<E>(newLen));
			}
			
			int idx = (0x80000000 >>> zeroNumPos) ^ pos;
			newd = new Descriptor<E>(desc.size + 1, new WriteDescriptor<E>(buckets.get(bucketIdx),idx,null,e));
			
		} while (!descriptor.compareAndSet(desc, newd));
		descriptor.get().completeWrite();
	}

	//操作cas写入新元素
	static class Descriptor<E> {
		public int size;
		volatile WriteDescriptor<E> writeop;
		public Descriptor(int size, WriteDescriptor<E> writeop) {
			this.size = size;
			this.writeop = writeop;
		}
		
		public void completeWrite() {
			WriteDescriptor<E> tmpOp = writeop;
			if (tmpOp!= null) {
				tmpOp.doIt();
				writeop = null;
			}
		}
	}
	
	static class WriteDescriptor<E> {
		public E oldValue;
		public E newValue;
		public AtomicReferenceArray<E> addr;
		public int addrIdx;
		
		public WriteDescriptor(AtomicReferenceArray<E> array, int idx, E oldValue, E newValue) {
			this.oldValue = oldValue;
			this.newValue = newValue;
			this.addr = array;
			this.addrIdx = idx;
		}

		public void doIt() {
			addr.compareAndSet(addrIdx, oldValue, newValue);
			
		}
	}
	
	public static void main(String[] args) {
		LockFreeVector<Integer> vector = new LockFreeVector<Integer>();
		for (int i = 0; i < 200; i++) {
			vector.pushBack(Integer.valueOf(i));
		}
		
		logger.info(vector.get(0));
		logger.info(vector.get(50));
		logger.info(vector.get(150));
	}
}
