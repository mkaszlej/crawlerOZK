package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.Logger;

import common.Address;
import common.Link;
import common.ParserData;

public class DocumentPatternParser extends DocumentParser {
	
	public DocumentPatternParser(Link link, Document htmlData) {
	    super(link, htmlData);
	    this.addresses.clear();
	    //this.data = Jsoup.parse(this.data).text();
	}
	
    public void parse(){
    	
        Pattern pattern = Pattern.compile("(.{0,250}[\\s\\>]+[0-9]{2}\\-[0-9]{3}[\\s\\<]+.{0,250})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        
        // check all occurences
        while (matcher.find()) {
          addresses.add(new Address( link, matcher.group().trim() ));
          //System.out.println("FOUND: "+matcher.group().trim());
        }
        
        for (Address address : addresses) {
            ParserData.addProcessedAddress(address);
		}

        Logger.parser("["+addresses.size()+"] PARSED URI: "+htmlData.baseUri());

    }
    
}
