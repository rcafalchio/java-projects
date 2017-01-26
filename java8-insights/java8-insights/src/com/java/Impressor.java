package com.java;

@FunctionalInterface
public interface Impressor<T> {

	void imprimeString(T t);
	
}
