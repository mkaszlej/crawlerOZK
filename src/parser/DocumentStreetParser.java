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
        
        Pattern pattern = Pattern.compile("(.{10,250}[\\s]+([au]l\\.){1}.{10,250})", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(data);
        
        // check all occurences
        while (matcher.find()) {
          addresses.add(new Address( link, Jsoup.parse(matcher.group()).text() ));
        }
        
        for (Address address : addresses) {
            ParserData.addProcessedAddress( address);
        }

    }

    
}
