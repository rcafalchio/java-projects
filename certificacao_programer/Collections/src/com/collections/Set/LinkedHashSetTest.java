package com.collections.Set;

import java.util.LinkedHashSet;

public class LinkedHashSetTest {

	/**
	 * Hash Set é ordenado por ordem de inserção
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		LinkedHashSet<String> linked = new LinkedHashSet<String>();
		linked.add("A");
		linked.add("B");
		linked.add("C");
		linked.add("D");
		linked.add("E");
		linked.add("E");
		linked.add("A");
		linked.add("a");
		for (String string : linked) {
			System.out.println(string);
		}

	}

}
