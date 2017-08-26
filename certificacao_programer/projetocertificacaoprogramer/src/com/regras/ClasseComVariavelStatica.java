package com.regras;

public class ClasseComVariavelStatica {

	static int a = 0;
	int y = 2;
	
	static void metodoStatico(){
		int x = 0;
		ClasseComVariavelStatica classeComVariavelStatica = new ClasseComVariavelStatica();
		classeComVariavelStatica.metodoNaoStatico();
		metodoNaoStatico();
		y++;
		a++;
		
	}
	
	void metodoNaoStatico(){
		int x = 0;
		ClasseComVariavelStatica.metodoStatico();
	}
	
}
