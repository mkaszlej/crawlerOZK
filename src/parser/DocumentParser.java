package parser;


import java.util.ArrayList;

import org.jsoup.nodes.Document;

import common.Address;
import common.Link;
import view.ProgressFrame;


public abstract class DocumentParser {
	protected String data;
	protected Document htmlData;
	protected ArrayList<Address> addresses;
	protected Link link;
	
	DocumentParser( Link link, Document htmlData){
            this.link = link;
            this.addresses = new ArrayList<Address>();
	    this.htmlData = htmlData;
	    this.data = htmlData.toString();
	}
	
    public abstract void parse();
    
}
