package parser;

import org.jsoup.nodes.Document;

import parser.DocumentParserFactory.ParserType;

import common.Link;
import util.Logger;

public class DocumentParserThread implements Runnable {

	private Document document;
	private Link link;

	public DocumentParserThread(Link link, Document document) {
		this.link = link;
		this.document = document;
	}
	
	public void run()
	{
		if(document == null){
			ParserThreadPool.counter.decrementAndGet();
			return;
		}

		invokeAllParsers();
		
		ParserThreadPool.counter.decrementAndGet();
	}
	
	public void invokeAllParsers(){

		for (ParserType type : DocumentParserFactory.ParserType.values()) {

                    try{
                        DocumentParser p = DocumentParserFactory.createParser(type, link, document);
			p.parse();
                    }
                    catch(IllegalArgumentException e){
                        Logger.error("Parser type error", e);
                    }
                    
		}
		
	}
	
}
