package construtor;

public class Casa extends Construcao {
	
	Casa() {
		System.out.println("Construtor simples Casa");
	}
	
	Casa(String nome){
		this();
		System.out.println("Construtor com parâmetro "+nome);
	}
	
	public static void main(String[] args) {
		new Casa("x ");
	}
}

