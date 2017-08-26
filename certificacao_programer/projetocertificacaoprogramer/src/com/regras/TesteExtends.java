package com.regras;

public class TesteExtends extends TesteInterfaceAbstrata{

	private TesteExtends() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void metodo(String s) {
		// TODO Auto-generated method stub
		
	}
	
	public void metodo2(){
		
	}
	
	public static void main(String[] args) {
//		((TesteInterfaceAbstrata) new TesteExtends()).metodo("");
//		((TesteExtends) new TesteInterfaceAbstrata()).metodo("");
		
		new TesteExtends();
		
	}

}
