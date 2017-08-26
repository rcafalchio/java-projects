package threads;

public class Test {

	public static void main(String argv[]) {
		Test1 pm1 = new Test1("One");
		pm1.start();
		Test1 pm2 = new Test1("Two");
		pm2.start();

	}

}
