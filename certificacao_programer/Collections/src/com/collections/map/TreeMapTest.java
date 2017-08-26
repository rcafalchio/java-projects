package com.collections.map;

import java.util.TreeMap;

import com.collections.common.ObjetoDoidoVO;

public class TreeMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();

//		treeMap.put("B", 2);
//		treeMap.put("C", 3);
//		treeMap.put("D", 4);
//		treeMap.put("A", 1);
//		System.out.println(treeMap.pollFirstEntry());
//		System.out.println(treeMap.pollLastEntry());

		TreeMap<ObjetoDoidoVO, Integer> treeMapObjetoDoido = new TreeMap<ObjetoDoidoVO, Integer>();
		treeMapObjetoDoido.put(new ObjetoDoidoVO(), 1);
		treeMapObjetoDoido.put(new ObjetoDoidoVO(), 2);
		treeMapObjetoDoido.put(new ObjetoDoidoVO(), 3);
		System.out.println(treeMapObjetoDoido.pollFirstEntry());
		System.out.println(treeMapObjetoDoido.pollLastEntry());
	}
}
