package parser;

import common.Address;
import common.Link;
import util.Url;
import view.ProgressFrame;

public class BlobParserFactory {
	
	public enum ParserType {
        //StepParser,
        UniformParser
    }
 
    public static BlobParser createParser(ParserType parserType, Url domainUrl, Url linkUrl, String blob, String htmlBlob) {
        switch (parserType) {
            //case StepParser:
            //    return new BlobStepParser(a);
            case UniformParser:
            	return new BlobUniformParser( domainUrl, linkUrl, blob, htmlBlob);
        }
        throw new IllegalArgumentException("Parser type " + parserType + " is not recognized.");
    }
}
