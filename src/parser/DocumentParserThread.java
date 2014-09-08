package parser;

import org.jsoup.nodes.Document;

import parser.DocumentParserFactory.ParserType;
import parser.DocumentParser;

import common.Link;
import util.Logger;
import view.ProgressFrame;

public class DocumentParserThread implements Runnable {

	private Document document;
	private Link link;
        private ProgressFrame parent;

	public DocumentParserThread(ProgressFrame parentFrame,Link link, Document document) {
		this.link = link;
		this.document = document;
                this.parent = parentFrame;
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
                        if(parent != null) parent.logError("["+link.getUrl()+"] Błąd typu document parsera -> błąd wewnętrzny");
                    }
                    
		}
		
	}
	
}
