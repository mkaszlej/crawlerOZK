package parser;

import parser.BlobParserFactory.ParserType;
import util.Logger;

import common.Address;

public class BlobParserThread implements Runnable {

	private Address address;
	
	public BlobParserThread(Address address) {
		this.address = address;
	}
	
	public void run()
	{
		if(address == null || address.getBlob().isEmpty() ){
			Logger.error("BLOBPARSERTHREAD address or blob not provided");
			ParserThreadPool.counter.decrementAndGet();
			return;
		}
		
		for (ParserType type : BlobParserFactory.ParserType.values()) {
			BlobParser p = BlobParserFactory.createParser(type, address);
			p.parse();
		}
		
		ParserThreadPool.counter.decrementAndGet();
	}
	
	
}
