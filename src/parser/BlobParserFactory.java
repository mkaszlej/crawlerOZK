package parser;

import common.Address;

public class BlobParserFactory {
	
	public enum ParserType {
        //StepParser,
        UniformParser
    }
 
    public static BlobParser createParser(ParserType parserType, Address a) {
        switch (parserType) {
            //case StepParser:
            //    return new BlobStepParser(a);
            case UniformParser:
            	return new BlobUniformParser(a);
        }
        throw new IllegalArgumentException("Parser type " + parserType + " is not recognized.");
    }
}
