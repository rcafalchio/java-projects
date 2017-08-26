package testes;

class Top {
	public Top(String s) {
		System.out.println("B");
	}
	
	public class Bottom2 extends Top{
		public Bottom2(String s) {
			super(s);
			System.out.println("D");
		}
	}
}
