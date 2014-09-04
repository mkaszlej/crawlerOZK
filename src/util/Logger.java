package util;

import java.util.List;

import common.Address;

public class Logger {
	
	private static boolean showInfo = false;
	private static boolean showWarn = false;
	private static boolean showDebug = false;
	private static boolean showParser = false;
	
	public static void debug(String s)
	{
		if(showDebug)
			System.out.println("["+System.currentTimeMillis()+"][DEBUG] "+s);
	}
	
	public static void info(String s)
	{
		if(showInfo)
			System.out.println("["+System.currentTimeMillis()+"][INFO] "+s);
	}
	
	public static void parser(String s)
	{
		if(showParser)
		System.out.println("["+System.currentTimeMillis()+"][PARSER] "+s);
	}
	
	public static void parser(List<Address> s)
	{
		if(showParser)
		{
			System.out.println("["+System.currentTimeMillis()+"][PARSER]:");
			for (Address address : s) {		
				System.out.println(address);
			}
		}
	}
	
	public static void warn(String s)
	{		
		if(showWarn)
			System.out.println("["+System.currentTimeMillis()+"][WARN] "+s);
	}
	
	public static void error(String s, Exception e)
	{
		System.out.println("["+System.currentTimeMillis()+"][ERROR] "+s);
		System.err.println("["+System.currentTimeMillis()+"][ERROR] "+s);
        e.printStackTrace();
	}
	
	public static void error(String s)
	{
		System.out.println("["+System.currentTimeMillis()+"][ERROR] "+s);
		System.err.println("["+System.currentTimeMillis()+"][ERROR] "+s);
	}
}
