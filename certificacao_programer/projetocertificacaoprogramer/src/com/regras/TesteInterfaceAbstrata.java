package com.regras;

import com.regras.interfaces.AbstractInterface;

public class TesteInterfaceAbstrata implements AbstractInterface {
   
	@Override
	public void metodo(String s) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
//		((TesteInterfaceAbstrata) new TesteExtends()).metodo("");
//		((TesteExtends) new TesteInterfaceAbstrata()).metodo("");
		
		new TesteExtends();
		
	}


}
