package com.collections.Set;

import java.util.HashSet;

public class HashSetTest {

	/**
	 * Hash Set não é ordenado
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		HashSet<String> hashSet = new HashSet<String>();
		hashSet.add("A");
		hashSet.add("B");
		hashSet.add("C");
		hashSet.add("D");
		hashSet.add("E");
		hashSet.add("E");
		for (String string : hashSet) {
			System.out.println(string);
		}

	}

}
