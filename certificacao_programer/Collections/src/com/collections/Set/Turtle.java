package com.collections.Set;

public class Turtle {

	int size;

	public Turtle(int size) {
		this.size = size;
	}

	@Override
	public boolean equals(Object obj) {
		return this.size == ((Turtle) obj).size;
	}

	public int hashCode() {
		return size / 2;
	}

}
