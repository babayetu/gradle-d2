package com.java.forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ProductTask extends RecursiveAction {
	private List<Product> productList;
	private int first;
	private int last;
	private double increment;
	private static Logger logger = Logger.getLogger(ProductTask.class);

	public ProductTask(List<Product> productList, int first, int last, double increment) {
		this.productList = productList;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}

	@Override
	protected void compute() {
		if (last - first < 10) {
			updatePrices();
		} else {
			int middle = (last + first) / 2;
			logger.info("Task: Pending tasks:" + getQueuedTaskCount());
			ProductTask t1 = new ProductTask(productList, first, middle + 1, increment);
			ProductTask t2 = new ProductTask(productList, middle + 1, last, increment);
			invokeAll(t1, t2);
		}

	}

	private void updatePrices() {
		for (int i = first; i < last; i++) {
			Product product = productList.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}

	public static void main(String[] args) {
		ProductListGenerator generator = new ProductListGenerator();
		List<Product> products = generator.generate(1000);
		ProductTask productTask = new ProductTask(products, 0, products.size(), 0.20);
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(productTask);

		do {
			logger.info("Main: Thread Count: " + pool.getActiveThreadCount());
			logger.info("Main: Thread Steal: " + pool.getStealCount());
			logger.info("Main: Parallelism: " + pool.getParallelism());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!productTask.isDone());

		pool.shutdown();

		if (productTask.isCompletedNormally()) {
			logger.debug("Main: The process has completed normally");
		}

		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			if (product.getPrice() != 12) {
				logger.debug("Product: " + product.getName() + " Price:" +product.getPrice());
			}
		}
		
		logger.info("Main: end of product program");
	}

}
