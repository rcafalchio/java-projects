package construtor;

public class Construcao {

	Construcao() {
		System.out.println("Construtor simples Construção");
	}
	
	Construcao(String nome){
		this();
		System.out.println(" Construção "+nome);
	}

}
