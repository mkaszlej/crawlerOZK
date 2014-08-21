package util;

public class Logger {
	
	private static boolean showInfo = true;
	
	public static void debug(String s)
	{
		System.out.println("["+System.currentTimeMillis()+"][DEBUG] "+s);
	}
	
	public static void info(String s)
	{
		if(showInfo)
		System.out.println("["+System.currentTimeMillis()+"][INFO] "+s);
	}
	
	public static void warn(String s)
	{		
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
