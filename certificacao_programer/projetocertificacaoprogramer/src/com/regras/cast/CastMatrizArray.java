package com.regras.cast;

public class CastMatrizArray {

	{
		System.out.println("A   ");
	}
	
	static {
		
		System.out.println("C   ");
	}
	
	public CastMatrizArray() {
		System.out.println("B   ");
	}

	public static void main(String[] args) {
		int[][] a = { { 1, 2 }, { 3, 4 } };
		int[] b = (int[]) a[1];
		int[][] a2 = (int[][]) a;		
	}

}
