package construtor;

public class Construcao {

	Construcao() {
		System.out.println("Construtor simples Constru��o");
	}
	
	Construcao(String nome){
		this();
		System.out.println(" Constru��o "+nome);
	}

}
