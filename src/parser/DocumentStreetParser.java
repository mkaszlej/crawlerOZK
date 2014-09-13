package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import common.Address;
import common.Link;
import common.ParserData;
import view.ProgressFrame;

public class DocumentStreetParser extends DocumentParser {
	
    public DocumentStreetParser( Link link, Document htmlData) {
        super( link, htmlData);
        this.addresses.clear();
    }

    
    public void parse(){
        
        Pattern pattern = Pattern.compile("([\\s]+([aup]l\\.){1})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        
        // check all occurences
        while (matcher.find()) {
            Address newAddress =new Address( link, Jsoup.parse(parseBig(matcher.start(), matcher.end(), 250)).text() );
            newAddress.setHtmlString(parseBig(matcher.start(), matcher.end(), 1000));
            addresses.add(newAddress);
        }
        
        for (Address address : addresses) {
            ParserData.addProcessedAddress( address);
        }

    }

    public String parseBig(int s, int e,int lenght){
    	
        int start = s - lenght;
        if( start <= 0 ) start = 0;
        int end = e + lenght;
        if( end > data.length() ) end =data.length();

        return data.substring( start, end );

    }
    
}
