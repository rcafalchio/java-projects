package com.collections.list;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArrayListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<String> listaString = new ArrayList<String>();
		listaString.add("A");
		listaString.add("B");
		listaString.add("C");
		listaString.add("D");
		listaString.add("E");
		listaString.remove(0);
		listaString.add(0, "Z");
		for (String string : listaString) {
			System.out.println(string);
		}
	}

}
