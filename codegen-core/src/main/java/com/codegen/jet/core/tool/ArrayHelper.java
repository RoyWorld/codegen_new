package com.codegen.jet.core.tool;

/**
 * find value quickly in an given array
 */
public class ArrayHelper {

	public static String getValue(String[] array, int indexOf) {
		return getValue(array, indexOf,null);
	}
	
	public static String getValue(String[] array, int indexOf, String defaultValue) {
		if(array.length - 1 >= indexOf) {
			return array[indexOf];
		}
		return defaultValue;
	}
	
}
