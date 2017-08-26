package strings;

public class AQuestion {

	public void method(Object o) {
		System.out.println("Object Verion");
	}

//	public void method(String s) {
//		System.out.println("String Version");
//	}
//	
//	public void method(Integer i) {
//		System.out.println("Integer Version");
//	}


	public static void main(String args[]) {
		AQuestion question = new AQuestion();
		question.method(null);
	}
}
