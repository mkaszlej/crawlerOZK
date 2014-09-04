package parser;

import org.jsoup.nodes.Document;

import common.Link;

public class DocumentParserFactory {
	
	public enum ParserType {
        PatternParser
    }
 
    public static DocumentParser createParser(ParserType parserType, Link link, Document data) {
        switch (parserType) {
            case PatternParser:
                return new DocumentPatternParser(link, data);
        }
        throw new IllegalArgumentException("Parser type " + parserType + " is not recognized.");
    }
}
