package com.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class TesteLambda {

	
	public static void main(String[] args) {
//		new TesteLambda().recebeImprime(s -> System.out.println(s), Arrays.asList("D4444","C33","A1","B22"));
		new TesteLambda().recebeImprime(System.out::println, Arrays.asList("D4444","C33","A1","B22","CEEEE33"));
	}
	
	public void recebeImprime(Impressor<String> impressor,List<String> lista){
		//AS TRES LINHAS SAO A MESMA COISA
		System.out.println("Imprime tudo que começa com C:");
		lista.stream().filter(s -> s.startsWith("C")).forEach(System.out::println);
		System.out.println("\nImprime o tamanho de tudo que começa com C:");
		lista.stream().filter(s -> s.startsWith("C")).map(String::length).forEach(System.out::println);
		
		List<String> listaNomeC = lista.stream().filter(s -> s.startsWith("C")).collect(Collectors.toList());
		
		System.out.println("\nOrdena por tamanho de String");
		lista.sort(Comparator.comparing(String::length));
		lista.forEach(s -> impressor.imprimeString(s));
	}
}
