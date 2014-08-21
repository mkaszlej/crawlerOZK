package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.Logger;

import common.Domain;
import common.Link;
 
public class DatabaseHelper {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:crawler.db";
 
    public static final String CREATE_DOMAINS_TABLE = "CREATE TABLE IF NOT EXISTS domains (domain_url varchar(2000) PRIMARY KEY NOT NULL UNIQUE, search_depth INTEGER, date_visited INTEGER, accepted INTEGER )";
    public static final String CREATE_LINKS_TABLE = "CREATE TABLE IF NOT EXISTS links (link_id INTEGER PRIMARY KEY AUTOINCREMENT, domain_url varchar(2000), link_url varchar(2000) NOT NULL UNIQUE, link_depth INTEGER, date_visited INTEGER, visits INTEGER, hit_count INTEGER, link_count INTEGER , flags VARCHAR(50)) ";
    
    private Connection conn;
    private Statement stat;
 
    public DatabaseHelper() {
        try {
            Class.forName(DatabaseHelper.DRIVER);
        } catch (ClassNotFoundException e) {
            Logger.error("Brak sterownika JDBC", e);
        }
 
        this.init();
    }
 
    private void init()
    {
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        	stat.execute(CREATE_DOMAINS_TABLE);
            stat.execute(CREATE_LINKS_TABLE);
        } catch (SQLException e) {
            Logger.error("Blad przy inicjalizacji bazy:", e);
        }
        Logger.info("DB Engine initialized");
    }
    
    public ArrayList<Domain> insertDomainsFromSet(List<Domain> domainSet)
    {
    	ArrayList<Domain> notAdded = new ArrayList<Domain>();
    	
    	if(domainSet.isEmpty()){
    		Logger.info("No new domains");
    		return notAdded;
    	}
    	
    	for (Domain domain : domainSet) {
			if(!insertDomain(domain))
			{
				Logger.warn("Skipping domain: "+domain.getUrl());
				notAdded.add(domain);	
			}
		}
    	
    	return notAdded;
    }
    

    public boolean insertDomain(Domain domain) {
        return insertDomain(domain.getUrl().toString(), domain.getSearchDepth(), domain.getAccepted());
    }
    
    
    public boolean insertDomain(String domain_url, int search_depth, int accepted) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert or replace into domains values (?, ?, ?, ?);");
            prepStmt.setString(1, domain_url);
            prepStmt.setInt(2, search_depth);
            prepStmt.setLong(3, System.currentTimeMillis() );
            prepStmt.setLong(4, accepted );
            prepStmt.execute();
        } catch (SQLException e) {
            Logger.error("Error on domain insert", e);
            return false;
        }
        Logger.info("Domain "+domain_url+" inserted");
        return true;
    }
    
    public ArrayList<Link> insertLinksFromSet(List<Link> linkSet)
    {
    	ArrayList<Link> notAdded = new ArrayList<Link>();
    	
    	if(linkSet.isEmpty()){
    		Logger.warn("Empty result set");
    		return notAdded;
    	}
    	
    	for (Link link : linkSet) {
			if(!insertLink(link))
			{
				Logger.warn("Skipping link: "+link.getUrl());
				notAdded.add(link);	
			}
		}
    	
    	return notAdded;
    }

    private boolean insertLink(String domain_url, String link_url, int link_depth, int visits, int hit_count, int link_count, String flags, long timestamp) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert or replace into links values (NULL, ?, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, domain_url);
            prepStmt.setString(2, link_url);
            prepStmt.setInt(3, link_depth);
            prepStmt.setLong(4, timestamp );
            prepStmt.setInt(5, visits);
            prepStmt.setInt(6, hit_count);
            prepStmt.setInt(7, link_count);
            prepStmt.setString(8, flags);
            prepStmt.execute();
        } catch (SQLException e) {
        	Logger.error("Error on link insert",e);
            return false;
        }
        Logger.info("Inserted link values. domain_url: "+domain_url+" link_url: "+link_url+" link_depth: "+link_depth+" visits: "+visits+" hits: "+hit_count+" links: "+link_count+" flags: "+flags+" timestamp: "+timestamp);
        return true;
    }
    
    private boolean insertLink(Link link) {
    	
    	String domain_url = link.getDomainUrl();
    	String link_url = link.getUrl().toString();
    	int link_depth = link.getLinkDepth();
    	int visits = link.getVisits();
    	int hit_count = link.getHitCount();
    	int link_count = link.getLinkCount();
    	String flags = link.getFlags();
    	long timestamp = link.getTimestamp();
    	
    	return insertLink(domain_url, link_url, link_depth, visits, hit_count, link_count, flags, timestamp);
	        
    }
    
    public ArrayList<Domain> getDomains() {
        ArrayList<Domain> domains = new ArrayList<Domain>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM domains");
            
            long timestamp;
            int search_depth, accepted;
            String domain_url;
            
            while(result.next()) {
                domain_url = result.getString("domain_url");
                timestamp = result.getLong("date_visited");
                search_depth = result.getInt("search_depth");
                accepted = result.getInt("accepted");
                domains.add(new Domain(domain_url, timestamp, search_depth, accepted));
            }
        } catch (SQLException e) {
        	Logger.error("Error fetching domains: ",e);
        	return null;
        }
        return domains;
    }

    public ArrayList<Link> getLinks(Domain domain) {
        return getLinks(domain.getUrl().toString());
    }
    
    public ArrayList<Link> getLinks(String domain_url) {
        ArrayList<Link> links = new ArrayList<Link>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM links WHERE domain_url LIKE '"+domain_url+"'");
            
            long timestamp;
            int link_id, visits, link_depth, hit_count, link_count;
            String link_url, flags;
            
            while(result.next()) {
                link_id = result.getInt("link_id");
                link_url = result.getString("link_url");
                timestamp = result.getLong("date_visited");
                link_depth = result.getInt("link_depth");
                visits = result.getInt("visits");
                hit_count = result.getInt("hit_count");
                link_count = result.getInt("link_count");
                flags = result.getString("flags");
                links.add(new Link(link_id, domain_url, link_url, link_depth, timestamp, visits, hit_count, link_count, flags));
            }
        } catch (SQLException e) {
        	Logger.error("Error fetching links for domain: "+domain_url+": ",e);
        	return null;
        }
        return links;
    }
    
}
