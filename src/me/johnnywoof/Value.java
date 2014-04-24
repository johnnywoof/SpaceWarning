package me.johnnywoof;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Value {

	public static long space = Long.MAX_VALUE;
	public static boolean warn = true;
	public static boolean shutdown = false;
	public static final List<String> silents = new ArrayList<String>();
	
	public static long getSpaceLeftInMB(){
		
		return (new File("/").getFreeSpace() / 1048576);
		
	}
	
}
