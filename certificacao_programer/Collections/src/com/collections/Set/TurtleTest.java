package com.collections.Set;

import java.util.HashSet;

public class TurtleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Turtle turtle1 = new Turtle(1);
		Turtle turtle2 = new Turtle(2);
		Turtle turtle3 = new Turtle(1);
//		final LinkedHashSet<Turtle> t = new LinkedHashSet<Turtle>();
		final HashSet<Turtle> t = new HashSet<Turtle>();
		t.add(turtle1);
		t.add(turtle2);
		t.add(turtle3);

		System.out.println("t.size() => " + t.size());

		for (Turtle turtle : t) {
			System.out.println(turtle);
			System.out.println("Size " + turtle.size);
		}
	}

}
