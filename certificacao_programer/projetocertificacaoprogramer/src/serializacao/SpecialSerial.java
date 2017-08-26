package serializacao;

import java.io.Serializable;

public class SpecialSerial implements Serializable {

	transient int y = 7;
	static int z = 9;

}
