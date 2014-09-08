package parser;


import common.Address;
import common.Link;
import parser.BlobParserFactory.ParserType;
import util.Logger;
import util.Url;
import view.ProgressFrame;

public class BlobParserThread implements Runnable {

    	private Url domainUrl;
	private Url linkUrl;
        private String blob;
	
	public BlobParserThread( Url domainUrl, Url linkUrl, String blob) {
            this.domainUrl = domainUrl;
            this.linkUrl = linkUrl;
            this.blob = blob;
	}
	
	public void run()
	{
		if( domainUrl == null || linkUrl == null || blob.isEmpty() ){
			Logger.error("BLOBPARSERTHREAD address or blob not provided");
                        ParserThreadPool.counter.decrementAndGet();
			return;
		}
		
		for (ParserType type : BlobParserFactory.ParserType.values()) {
                    try{
                        BlobParser p = BlobParserFactory.createParser( type, domainUrl, linkUrl, blob);
                        p.parse();
                    }
                    catch(IllegalArgumentException e){
                        Logger.error("Parser type error", e);
                    }
                    
		}
		
		ParserThreadPool.counter.decrementAndGet();
	}
	
	
}
