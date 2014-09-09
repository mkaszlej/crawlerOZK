package parser;


import common.Address;
import common.Link;
import util.Url;
import view.ProgressFrame;


public abstract class BlobParser {
    
    protected Url linkUrl;
    protected Url domainUrl;
    protected String blob;
    
    protected BlobResult parserResults;

    protected String regexSmallSeparator = "[\\s><]+";
    protected String regexBigSeparator = "[\\s\\n,><]+";
    protected String regexStartSeparator = "[\\s\\n,>]+";
    protected String regexEndSeparator = "[\\s\\n,<]+";
    protected String regexCityCode = "([0-9]{2}-[0-9]{3})";
    protected String s = "[\\s\\n,<>/\\\\.(<\\w\\s/\\=\\(\\)>)]+";

    protected int middlePoint;
    protected String city;
    protected String cityCode;
    protected String street;
    protected String buildingNo;
    protected String apartamentNo;
    
    BlobParser(Url domainUrl, Url linkUrl, String blob){
        this.parserResults = new BlobResult( domainUrl, linkUrl, blob );
        this.domainUrl = domainUrl;
        this.linkUrl = linkUrl;
        this.blob = blob;
        this.middlePoint = blob.length()/2;
    }

    public abstract void parse();
    
}
