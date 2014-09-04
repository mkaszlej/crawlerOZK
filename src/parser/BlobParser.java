package parser;


import common.Address;


public abstract class BlobParser {
	protected Address address;
	
	protected String regexSmallSeparator = "[\\s><]+";
	protected String regexBigSeparator = "[\\s\\n,><]+";
	protected String regexStartSeparator = "[\\s\\n,>]+";
	protected String regexEndSeparator = "[\\s\\n,<]+";
	protected String regexCityCode = "([0-9]{2}-[0-9]{3})";
	protected String s = "[\\s\\n,<>/\\\\.(<\\w\\s/\\=\\(\\)>)]+";
	protected String cokolwiek = "([^0-9,<>/\\n\\(\\):])";
	protected String budynek = "(\\d{1,5}[\\w]?)";
	protected String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
	protected String kod = regexCityCode;
	protected String ul = "ul\\.\\s";
	
	protected int middlePoint;
	protected String city;
	protected String cityCode;
	protected String street;
	protected String buildingNo;
	protected String apartamentNo;
	
	
	BlobParser(Address address){
		this.address = address;
		this.middlePoint = address.getBlob().length()/2;
	}
	
    public abstract void parse();
    
}
