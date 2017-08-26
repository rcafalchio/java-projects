package serializacao;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class TestDesSer {

	public static void main(String[] args) {
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(
					"myfile"));
			SpecialSerial s2 = (SpecialSerial) is.readObject();
			is.close();
			System.out.println(s2.y + " " + s2.z);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
