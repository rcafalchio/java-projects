package com.regras;

import java.util.Scanner;

public class TesteOperadores {

	public static void main(String[] args) {
		
		String s1 = "abc";
		String s2 = s1;
		s1 += "d";
		System.out.println(s1 + " " + s2 + " " + (s1==s2));
		
		Scanner scanner = new Scanner("1 2 34 5 a");
		for (int i = 0; i < 10; i++) {
			System.out.println(scanner.nextInt());
		}
		
	}
}
