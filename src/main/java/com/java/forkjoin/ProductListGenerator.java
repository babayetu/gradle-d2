package com.java.forkjoin;

import java.util.ArrayList;
import java.util.List;

public class ProductListGenerator {
	public List<Product> generate(int size) {
		List<Product> ret = new ArrayList<Product>();
		for (int i = 0; i < size; i++) {
			Product product = new Product("Product" + i, 10);
			ret.add(product);
		}
		return ret;
	}
}
