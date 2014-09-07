package common;

import util.Logger;
import util.Url;

public class Domain {

    private long timestamp;
    private int visited;
    private int search_depth;
    
    private Url parent_url;
    private Url domain_url;

    @Override
    public boolean equals(Object o){
        if(o == null)            return false;
        if(!(o instanceof Domain)) return false;

        Domain other = (Domain) o;
        if(!this.domain_url.equals(other.domain_url)) return false;
        return true;
      }
	
    public Domain(String domain_url, String parent_url, long timestamp, int search_depth, int visits) {
    	this.domain_url = new Url(domain_url);
        this.parent_url = new Url(parent_url);
    	this.search_depth = search_depth;
    	this.timestamp = timestamp;
    	this.visited = visits;
    }
	
    public Domain(Link l)
    {
    	this.domain_url = l.getUrl();
        this.parent_url = new Url(l.getDomainUrl());
    	this.search_depth = 1;
    	this.timestamp = l.getTimestamp();
    	this.visited = 0;
    }

    public Domain(String domain_url, String parent_url, int search_depth) {
    	this.domain_url = new Url(domain_url);
        this.parent_url = new Url(parent_url);
    	this.search_depth = search_depth;
    	this.timestamp = System.currentTimeMillis();
    	this.visited = 0;
    }
    
    public void update(Domain d)
    {
        if( this.parent_url == null ) this.parent_url = d.getParent_url();
        if( d.getSearchDepth() > this.search_depth ) this.search_depth = d.getSearchDepth();
        if( d.getVisits() > this.visited ) this.visited = d.getVisits();
        this.timestamp = System.currentTimeMillis();
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

    public Url getParent_url() {
        return parent_url;
    }

    public void setParent_url(Url parent_url) {
        this.parent_url = parent_url;
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
    
    public int getVisits()
    {
    	return this.visited;
    }
    
    public long getTimestamp()
    {
    	return this.timestamp;
    }
    
    
    public boolean isProcessed()
    {
    	return this.visited > 0;
    }

    public void addVisit() {
        this.visited++;
    }
    
    @Override
    public String toString() {
        return domain_url.toString();
    }
    
    public String getMeta(){
        return "PRZETWORZONE: "+Boolean.toString(visited > 0)+"\n"+
               "DATA OSTATNIEJ ZMIANY: "+timestamp+"\n";           
    }
}
