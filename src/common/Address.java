package common;

import util.Url;

public class Address {

        private String name=null, cityCode=null, city=null, street=null, domain=null, blob=null, buildingNo=null, apartamentNo=null;
	private long timestamp;
	private int addressId,  count;
	private Url link;
	

	@Override
	public boolean equals(Object o){
	    if(o == null)            return false;
	    if(!(o instanceof Address)) return false;

	    Address other = (Address) o;
	    
	    if(blobEquals(other,50)) return true;
	    	
	    if(this.city == null) return false;
	    if(!this.city.equals(other.city)) return false;
	    if(!this.cityCode.equals(other.cityCode)) return false;
	    if(!this.street.equals(other.street)) return false;
	    if(!(this.buildingNo.equals(other.buildingNo))) return false;
	    if(!(this.apartamentNo.equals(other.apartamentNo))) return false;
	    return true;
	  }
	
	public boolean blobEquals(Address a, int margin)
	{
		if( this.blob.length() < 2*margin ) return false;
		
		String myBlob = this.blob.substring(margin, this.blob.length()-margin);
		return a.getBlob().contains(myBlob);
	}
	
	// Address z bazy danych
    public Address( int addressId, String cityCode, String city, String street, String domain, String link, String blob, long timestamp, String buildingNo, String apartamentNo, int count) {
    	this.addressId = addressId;
    	this.cityCode = cityCode;
    	this.city = city;
    	this.street = street;
    	this.domain = domain;
    	this.link = new Url(link);
    	this.blob = blob;
    	this.timestamp = timestamp;
    	this.buildingNo = buildingNo;
    	this.apartamentNo = apartamentNo;
    	this.count = count;
    }
    
    //Address na podstawie linku i blob
    public Address( Link link, String blob )
    {
    	this.link = link.getUrl();
    	this.blob = blob;
    	this.domain = link.getDomainUrl();
    	this.timestamp = System.currentTimeMillis();
    	this.count = 1;
    }
    
    //Address podczas parsowania
    public Address( String name, String kodPocztowy, String miejscowosc, String ulica, String nrDomu, String nrMieszkania, Url domainUrl, Url linkUrl, String blob )
    {
        this.name = name;
        this.cityCode = kodPocztowy;
        this.city = miejscowosc;
        this.street = ulica;
        
        this.buildingNo = nrDomu;
        this.apartamentNo = nrMieszkania;
        
        this.domain = domainUrl.toString();
        this.link = linkUrl;
        this.blob = blob;
        this.count = 1;
        this.timestamp = System.currentTimeMillis();             
    }
	   
    public String getCityCode() {
		return cityCode;
	}
    
    public String getCity() {
		return city;
	}
    
    public String getApartamentNo() {
		return apartamentNo;
	}
    
    public String getBlob() {
		return blob;
	}
    
    public String getBuildingNo() {
		return buildingNo;
	}
    
    public int getCount() {
		return count;
	}
    
    public String getDomain() {
		return domain;
	}
    
    public Url getLink() {
		return link;
	}
    
    public String getStreet() {
		return street;
	}
    
    public long getTimestamp() {
		return timestamp;
	}
    
    public void setCount(int count) {
		this.count = count;
		this.timestamp = System.currentTimeMillis();
	}

    public String getName() {
        return name;
    }
    
    public String toString(){
    	return name+" ul. "+street+" "+buildingNo+"/"+apartamentNo+", "+cityCode+" "+city;
    }
}
