package math;

public class TestMath {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(2 ^ 4);
		
		float nr = 5.50f;

		System.out.println("Absoluto: " + Math.abs(nr) +

		"\nInteiro mais alto: " + Math.ceil(nr) +

		"\nInteiro mais baixo: " + Math.floor(nr) +

		"\nDouble mais próximo: " + Math.rint(nr) +

		"\nArredondamento: " + Math.round(nr));
	}

}
