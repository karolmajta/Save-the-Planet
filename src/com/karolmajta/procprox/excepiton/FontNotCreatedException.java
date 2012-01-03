package com.karolmajta.procprox.excepiton;

import com.karolmajta.stp.exception.STPException;

public class FontNotCreatedException extends STPException {
	private String name;
	private int size;
	
	public FontNotCreatedException(String name, int size) {
		this.name = name;
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Font named '" + name + "', size: " + size + "was not created";
	}
}
