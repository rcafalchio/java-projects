package com.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class DefaultMethods {

	public static void main(String[] args) {
	
		
		new DefaultMethods().start();

		
	}

	private void start() {
		List<String> lista = Arrays.asList("Rodrigo","Bruno","Fernado");
		System.out.println("Lista desordenada: "+lista);
		ordenarModoAntigo(lista);
		ordenarModoJava8(lista);
		foreachModoJava8(lista);
		
	}

	private void foreachModoJava8(List<String> lista) {
		System.out.println("Foreach Java 8");
		
//		lista.forEach(new Consumer<String>() {
//
//			@Override
//			public void accept(String t) {
//				System.out.println("Iterando " + t);
//			}
//		});
		
		lista.forEach(s -> System.out.println(s));
	}

	private void ordenarModoJava8(List<String> lista) {
		lista.sort(null);
		System.out.println("Lista ordenada java 8: "+lista);
	}

	private void ordenarModoAntigo(List<String> lista) {
		Collections.sort(lista);
		System.out.println("Lista ordenada modo antigo: "+lista);
	}

}
