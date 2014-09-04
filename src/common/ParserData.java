package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import parser.BlobParserThread;
import parser.ParserThreadPool;

import util.Logger;

public class ParserData {


	private static int initialCount = 0;
	
	private static List<Address> processedAddresses = Collections.synchronizedList(new ArrayList<Address>());
	
	public ParserData() {}
	
	public static void loadAddressList(ArrayList<Address> addresses){
		processedAddresses.clear();
		processedAddresses = Collections.synchronizedList(addresses);
		initialCount = processedAddresses.size();
	}
		
	public static String count(){
		return "INITIAL ADDRESSES: "+initialCount+"\nCURRENT ADDRESSES: "+processedAddresses.size() ;
	} 
	

	public static void addProcessedAddress(Address address){
		if(!processedAddresses.contains(address))
		{
			ParserThreadPool.execute(new BlobParserThread(address));
			processedAddresses.add(address);
		}
		else{
			int index  = processedAddresses.indexOf(address);
			Address oldAddress = processedAddresses.get(index);
			
			address.setCount(oldAddress.getCount()+1);
			processedAddresses.add(address);
		}
	}
	
	public static void resetProcessed(){
		processedAddresses.clear();
	}
	
	public synchronized static List<Address> getProcessedAddresses()
	{
		return processedAddresses;
	}
	
	public synchronized static void printAddresses(){
        Logger.parser(processedAddresses);
	}
	
}
