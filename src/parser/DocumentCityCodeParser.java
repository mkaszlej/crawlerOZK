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
    	
        Pattern pattern = Pattern.compile("([\\s\\>]+[0-9]{2}\\-[0-9]{3}[\\s\\<]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        
        // check all occurences
        while (matcher.find()) {
            Address address = new Address( link, Jsoup.parse(parseBig(matcher.start(),matcher.end(),250)).text() );
            address.setHtmlString(parseBig(matcher.start(),matcher.end(),1000));
            addresses.add(address);
        }
        
        for (Address address : addresses) {
            ParserData.addProcessedAddress( address);
        }

        Logger.parser("["+addresses.size()+"] PARSED URI: "+htmlData.baseUri());

    }
    
    public String parseBig(int s, int e,int lenght){
    	
        int start = s - lenght;
        if( start <= 0 ) start = 0;
        int end = e + lenght;
        if( end > data.length() ) end =data.length();

        return data.substring( start, end );

    }
    
}
