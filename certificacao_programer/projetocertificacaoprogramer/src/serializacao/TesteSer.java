package serializacao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TesteSer {

	public static void main(String[] args) {
		SpecialSerial s = new SpecialSerial();
		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream("myfile"));
			os.writeObject(s);
			os.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		s.z += 20;
		
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
