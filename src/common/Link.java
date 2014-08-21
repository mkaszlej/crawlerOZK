package common;

import util.Url;

public class Link {

	private long timestamp;
	private int link_id,  visits, link_depth, hit_count, link_count;
	private String flags;
	private Url link_url;
	private String domain_url;
		
	@Override
	public boolean equals(Object o){
	    if(o == null)            return false;
	    if(!(o instanceof Link)) return false;

	    Link other = (Link) o;
	    if(!this.link_url.equals(other.link_url)) return false;
	    if(!this.domain_url.equals(other.domain_url)) return false;
	    return true;
	  }
	
	// Link z bazy danych
    public Link(int link_id, String domain_url, String link_url, int link_depth, long timestamp, int visits, int hit_count, int link_count, String flags ) {
    	this.link_id = link_id;
    	this.domain_url = domain_url;
    	this.link_url = new Url(link_url);
    	this.link_depth = link_depth;
    	this.timestamp = timestamp;
    	this.visits = visits;
    	this.hit_count = hit_count;
    	this.link_count = link_count;
    	this.flags = flags;
    }
	   
    // Link na bazie rodzica
    public Link( Link parent_link, String link_url )
    {
    	this.link_id = -1;
    	this.domain_url = parent_link.domain_url;
    	this.link_url = new Url(link_url, domain_url);
    	this.link_depth = parent_link.link_depth+1;
    	this.timestamp = System.currentTimeMillis();
    	this.visits = 0;
    	this.hit_count = 0;
    	this.link_count = 0;
    	this.flags = "";
    	
    	if( !this.link_url.inDomain(domain_url) ) 
    	{	this.flags = "E" ;	}
    	
    }
    
    // Link na bazie domeny
    public Link( Domain domain )
    {
    	this.link_id = -1;
    	this.link_url = domain.getUrl();
    	this.domain_url = domain.getUrl().toString();
    	this.link_depth = 0;
    	this.timestamp = System.currentTimeMillis();
    	this.visits = 0;
    	this.hit_count = 0;
    	this.link_count = 0;
    	this.flags = "D";
    }
    
    public void addFlag(String flag)
    {
    	this.flags = this.flags + flag;
    	this.timestamp = System.currentTimeMillis();
    }
    
	public String getFlags()
	{
		return this.flags;
	}
    
    public int getHitCount()
    {
    	return this.hit_count;
    }
	    
    public Url getUrl()
    {
    	return this.link_url;
    }
	    
    public void setUrl(String new_url)
    {
    	this.link_url = new Url(new_url);
    	this.timestamp = System.currentTimeMillis();
    }
	    
    public int getLinkDepth()
    {
    	return this.link_depth;
    }
    
    public String getDomainUrl()
    {
    	return this.domain_url;
    }
    
    public int getVisits()
    {
    	return this.visits;
    }
    
    public void setVisits(int newVisits){
    	this.visits = newVisits;
    	this.timestamp = System.currentTimeMillis();
    }
    
    public int getLinkCount()
    {
    	return this.link_count;
    }
    
    public void setLinkCount(int newCount)
    {
    	this.link_count = newCount;
    	this.timestamp = System.currentTimeMillis();
    }
	    
    public long getTimestamp(){
    	return this.timestamp;
    }
    
    
    public String toString()
    {
    	return ">"+this.link_depth+"|domainUrl: "+domain_url+"|linkID: "+link_id+"|URL: "+link_url+" |LinkCount: "+link_count+"|HitCount: "+hit_count+"|FLAGS: "+this.flags+"|VISITS: "+visits+"|TIMESTAMP: "+this.timestamp;
    }
		
}
