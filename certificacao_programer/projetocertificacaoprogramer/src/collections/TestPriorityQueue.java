package collections;

import java.util.PriorityQueue;

public class TestPriorityQueue {

	public static void main(String[] args) {

		PriorityQueue<String> queue = new PriorityQueue<String>();
		queue.size();
		queue.add("1");
		queue.add("2");
		queue.add("3");
		queue.add("3");
		// Mantém os elementos
		System.out.println(queue.peek());
		System.out.println(queue.peek());
		// Remove os elementos
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}

}
