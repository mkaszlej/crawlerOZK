package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import parser.BlobParserThread;
import parser.BlobResult;
import parser.ParserThreadPool;
import util.Logger;
import util.Url;
import view.ProgressFrame;

public class ParserData {


	private static int initialCount = 0;
	
	private static List<Address> processedAddresses = Collections.synchronizedList(new ArrayList<Address>());
        private static List<Address> newAddresses = Collections.synchronizedList(new ArrayList<Address>());
	
        private static List<BlobResult> parsingResults = Collections.synchronizedList(new ArrayList<BlobResult>());
        
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
                //TODO przekazywanie jako URL
                ParserThreadPool.execute(new BlobParserThread(new Url(address.getDomain()),address.getLink(), address.getBlob()));
                processedAddresses.add(address);
            }
            else{
                int index  = processedAddresses.indexOf(address);
                Address oldAddress = processedAddresses.get(index);

                address.setCount(oldAddress.getCount()+1);
                processedAddresses.add(address);
            }
	}
	
        public static void addNewAddress(Address a)
        {
            Logger.info("new Address: "+a.toString() );
            newAddresses.add(a);
        }
        
	public static void resetProcessed(){
            processedAddresses.clear();
            parsingResults.clear();
	}
	
	public synchronized static List<Address> getProcessedAddresses()
	{
            return processedAddresses;
	}

        public static List<Address> getNewAddresses() {
            return newAddresses;
        }
	
	public synchronized static void printAddresses(){
            Logger.parser(processedAddresses);
	}
	
        public static void addBlobResult(BlobResult result){
            parsingResults.add(result);
        }
        
        public static int getBlobResultSize()
        {
            return parsingResults.size();
        }

        public static List<BlobResult> getParsingResults() {
            return parsingResults;
        }
        
        public static void removeBlobResult(BlobResult result)
        {
            parsingResults.remove(result);
        }
        
}
