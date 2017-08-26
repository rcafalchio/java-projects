package com.regras.cast;

public class Test {

	public static void main(String[] args) {
		Dog dog = new Dog();
		((Animal) dog).sniff();
		Animal animal = dog;
		animal.sniff();
	}
}
