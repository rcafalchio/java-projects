package com.collections.queue;

import java.util.PriorityQueue;

public class PriorityQueueTest {

	public static void main(String[] args) {
		final PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(
				7);

		int ia[] = { 4, 5, 6, 1, 2, 3 };
		for (int i : ia) {
			priorityQueue.offer(i);
			priorityQueue.add(i);
		}

		for (int i : ia) {
			System.out.println(priorityQueue.poll());
		}

	}
}
