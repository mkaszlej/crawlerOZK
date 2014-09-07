package database;

import common.Address;
import common.Domain;
import common.Link;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.Logger;
 
public class DatabaseHelper {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:crawler.db";
 
    public static final String CREATE_DOMAINS_TABLE = "CREATE TABLE IF NOT EXISTS domains (domain_id INTEGER PRIMARY KEY AUTOINCREMENT, domain_url varchar(2000) NOT NULL UNIQUE, parent_url varchar(2000), search_depth INTEGER, date_visited INTEGER, visits INTEGER )";
    public static final String CREATE_LINKS_TABLE = "CREATE TABLE IF NOT EXISTS links (link_id INTEGER PRIMARY KEY AUTOINCREMENT, domain_url varchar(2000), link_url varchar(2000) NOT NULL UNIQUE, link_depth INTEGER, date_visited INTEGER, visits INTEGER, hit_count INTEGER, link_count INTEGER , flags VARCHAR(50)) ";
    public static final String CREATE_ADDRESS_TABLE = "CREATE TABLE IF NOT EXISTS address (address_id INTEGER PRIMARY KEY AUTOINCREMENT, cityCode varchar(6), city varchar(2000), street varchar(2000), buildingNo varchar(20), apartamentNo varchar(20), blob varchar(5000), count INTEGER, timestamp INTEGER, domain_url varchar(2000), link_url varchar(2000) ) ";
    
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
            stat.execute(CREATE_ADDRESS_TABLE);
        } catch (SQLException e) {
            Logger.error("Blad przy inicjalizacji bazy:", e);
        }
        Logger.info("DB Engine initialized");
    }
    
    //--- DOMAIN ---
    
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
        return insertDomain(domain.getUrl().toString(), domain.getParent_url().toString(), domain.getSearchDepth(), domain.getVisits());
    }
    
    
    public boolean insertDomain(String domain_url, String parent_url, int search_depth, int visits) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert or replace into domains values (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, domain_url);
            prepStmt.setString(2, parent_url);
            prepStmt.setInt(3, search_depth);
            prepStmt.setLong(4, System.currentTimeMillis() );
            prepStmt.setInt(5, visits );
            prepStmt.execute();
        } catch (SQLException e) {
            Logger.error("Error on domain insert", e);
            return false;
        }
        Logger.info("Domain "+domain_url+" inserted");
        return true;
    }

    public ArrayList<Domain> getDomains() {
        ArrayList<Domain> domains = new ArrayList<Domain>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM domains");
            
            long timestamp;
            int search_depth, visits;
            String domain_url,parent_url;
            
            while(result.next()) {
                domain_url = result.getString("domain_url");
                parent_url = result.getString("parent_url");
                timestamp = result.getLong("date_visited");
                search_depth = result.getInt("search_depth");
                visits = result.getInt("visits");
                Domain newDomain = new Domain(domain_url, parent_url, timestamp, search_depth, visits); 
                domains.add(newDomain);
            }
        } catch (SQLException e) {
        	Logger.error("Error fetching domains: ",e);
        	return null;
        }
        return domains;
    }
    
    //--- ADDRESS ---
    public ArrayList<Address> insertAddressesFromSet(List<Address> addressSet)
    {
    	ArrayList<Address> notAdded = new ArrayList<Address>();
    	
    	if(addressSet.isEmpty()){
    		Logger.info("No new address");
    		return notAdded;
    	}
    	
    	for (Address address : addressSet) {
			if(!insertAddress(address))
			{
				Logger.warn("Skipping address: "+address.toString());
				notAdded.add(address);	
			}
		}
    	
    	return notAdded;
    }
    

    public boolean insertAddress(Address a) {
        return insertAddress(a.getCityCode(), a.getCity(), a.getStreet(), a.getDomain(), a.getLink().toString() , a.getBlob(), a.getTimestamp(), a.getBuildingNo(), a.getApartamentNo(), a.getCount());
    }    
    public boolean insertAddress( String cityCode, String city, String street, String domain, String link, String blob, long timestamp, String buildingNo, String apartamentNo, int count) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert or replace into address values (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, cityCode);
            prepStmt.setString(2, city);
            prepStmt.setString(3, street);
            prepStmt.setString(4, domain);
            prepStmt.setString(5, link);
            prepStmt.setString(6, blob);
            prepStmt.setLong(7, timestamp );
            prepStmt.setString(8, buildingNo );
            prepStmt.setString(9, apartamentNo );
            prepStmt.setInt(10, count );
            prepStmt.execute();
        } catch (SQLException e) {
            Logger.error("Error on address insert", e);
            return false;
        }
        Logger.info("Address: "+cityCode+" "+city+" ul."+street+" "+buildingNo+"/"+apartamentNo+" inserted");
        return true;
    }


    public ArrayList<Address> getAddresses(Domain domain) {
    	return getAddresses(domain.getUrl().toString());
    }
    
    public ArrayList<Address> getAddresses(String domain_url) {
        ArrayList<Address> addresses = new ArrayList<Address>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM address WHERE domain_url LIKE '"+domain_url+"'");
            
        	String cityCode, city, street, domain, blob, link, buildingNo, apartamentNo;
        	long timestamp;
        	int addressId, count;
            
            while(result.next()) {
                addressId = result.getInt("addressId");
            	cityCode = result.getString( "cityCode" );
                city = result.getString( "city" );
                street = result.getString( "street" );
                domain = result.getString( "domain" );
                link = result.getString( "link" );
                blob = result.getString( "blob" );
                timestamp = result.getLong( "timestamp" );
                buildingNo = result.getString( "buildingNo" );
                apartamentNo = result.getString( "apartamentNo" );
                count = result.getInt( "count" );
                addresses.add(new Address(addressId,cityCode,city,street,domain,link,blob,timestamp,buildingNo,apartamentNo,count));
            }
        } catch (SQLException e) {
        	Logger.error("Error fetching addresses: ",e);
        	return null;
        }
        return addresses;
    }

    
    //--- LINK ---
    
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
