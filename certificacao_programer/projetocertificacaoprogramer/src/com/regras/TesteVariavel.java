package com.regras;

public class TesteVariavel {

	int x = 3;
	
	int go(){
		int x = 4;
		x= x +1;
		return x;
	}
	
	public static void main(String[] args) {
		ClasseA classeA = new ClasseA();
		ClasseB classeB = new ClasseB();
		ClasseComVariavelStatica a = classeA.teste();
		System.out.println("a "+ClasseComVariavelStatica.a);
		ClasseComVariavelStatica b = classeB.teste();
		System.out.println("b "+ClasseComVariavelStatica.a);
		// tem que mudar pra 15
		System.out.println("a "+ClasseComVariavelStatica.a);
		
	}
	
}
