public class Food {

	public void teste() {
		Popcorn popcorn = new Popcorn() {
			public void a() {
				System.out.println("A ANONIMA");
			}
		};

		popcorn.a(); // Não exerga
	}

	public class Popcorn {
		public void a() {
			System.out.println("A NAO ANONIMA");
		}
	}
	
	public static void main(String[] args) {
		new Food().teste();
	}
}
