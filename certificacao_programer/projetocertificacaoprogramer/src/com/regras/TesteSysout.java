package com.regras;

public class TesteSysout {

	public static void main(String[] args) {
		Long x = 42L;
		Long y = 44L;
		// A contatenação ocorre da esquerda para a direita
		System.out.println(7 + 2 + " ");
		System.out.println(x + 5 + foo());
		System.out.println(x + y + foo());

	}

	static String foo() {
		return "foo";
	}

}
