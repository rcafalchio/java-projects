package threads;

public class Test1 extends Thread {

	private String sTname = "";

	Test1(String s) {
		sTname = s;

	}

	public void run() {
		for (int i = 0; i < 2; i++) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}

			yield();
			System.out.println(sTname);
		}

	}

}
