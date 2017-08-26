package date;

import java.util.Date;

public class TestReferencia {
	public static void main(String args[]) {
		MeuVO meuVO = new MeuVO();
		meuVO.setNome("Ricardo");
		Date d1 = new Date(99, 11, 31);
		Date d2 = new Date(99, 11, 31);
		method(d1, d2, meuVO);
		System.out.println("d1 is " + d1 + "\nd2 is " + d2);
		System.out.println("Não Muda? " + meuVO.getNome());
	}

	public static void method(Date d1, Date d2, MeuVO meuVO) {
		meuVO = new MeuVO();
		d2.setYear(100);
		d1 = d2;
	}

}
