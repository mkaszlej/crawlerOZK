package common;

import util.Url;

public class Domain {

	private long timestamp;
	private int  search_depth;
	private Url domain_url;
	private int accepted;
   
	@Override
	public boolean equals(Object o){
	    if(o == null)            return false;
	    if(!(o instanceof Domain)) return false;

	    Domain other = (Domain) o;
	    if(!this.domain_url.equals(other.domain_url)) return false;
	    return true;
	  }
	
    public Domain(String domain_url,  long timestamp, int search_depth, int accepted) {
    	this.domain_url = new Url(domain_url);
    	this.search_depth = search_depth;
    	this.timestamp = timestamp;
    	this.accepted = accepted;
    }
	
    public Domain(Link l)
    {
    	this.domain_url = l.getUrl();
    	this.search_depth = 1;
    	this.timestamp = l.getTimestamp();
    	this.accepted = 0;
    }
    
    public Url getUrl()
    {
    	return this.domain_url;
    }
    
    public void setUrl(String newUrl)
    {
    	this.domain_url = new Url(newUrl);
    	this.timestamp = System.currentTimeMillis();
    }
    
    public int getSearchDepth()
    {
    	return this.search_depth;
    }
    
    public void setSearchDepth(int newSearchDepth)
    {
    	this.search_depth = newSearchDepth;
    	this.timestamp = System.currentTimeMillis();
    }
    
    public int getAccepted()
    {
    	return this.accepted;
    }
    
    public long getTimestamp()
    {
    	return this.timestamp;
    }
    
    
    public boolean isAccepted()
    {
    	return this.accepted > 0;
    }
}
