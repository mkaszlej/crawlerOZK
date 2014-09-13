package parser;

import common.Address;
import common.Link;
import common.ParserData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import util.Logger;

public class DocumentCityCodeParser extends DocumentParser {
	
	public DocumentCityCodeParser( Link link, Document htmlData) {
	    super( link, htmlData);
	    this.addresses.clear();
	}
	
    public void parse(){
    	
        Pattern pattern = Pattern.compile("(.{0,250}[\\s\\>]+[0-9]{2}\\-[0-9]{3}[\\s\\<]+.{0,250})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        
        // check all occurences
        while (matcher.find()) {
            Address address = new Address( link, Jsoup.parse(matcher.group()).text() );
            address.setHtmlString(parseBig());
            addresses.add(address);
        }
        
        for (Address address : addresses) {
            ParserData.addProcessedAddress( address);
        }

        Logger.parser("["+addresses.size()+"] PARSED URI: "+htmlData.baseUri());

    }
    
    
    public String parseBig(){
    	
        Pattern pattern = Pattern.compile("(.{0,1000}[\\s\\>]+[0-9]{2}\\-[0-9]{3}[\\s\\<]+.{0,1000})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        
        // check all occurences
        if (matcher.find()) {
             return matcher.group();
        }
        return null;
    }
    
}
