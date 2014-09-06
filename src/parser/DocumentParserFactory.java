package parser;

import org.jsoup.nodes.Document;

import common.Link;

public class DocumentParserFactory {
	
	public enum ParserType {
        CityCodeParser,
        StreetParser
    }
 
    public static DocumentParser createParser(ParserType parserType, Link link, Document data) {
        switch (parserType) {
            case CityCodeParser:
                return new DocumentCityCodeParser(link, data);
            case StreetParser:
                return new DocumentStreetParser(link, data);
        }
        throw new IllegalArgumentException("Parser type " + parserType + " is not recognized.");
    }
}
