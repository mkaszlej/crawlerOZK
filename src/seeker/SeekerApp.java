package seeker;

import java.util.ArrayList;

import util.Logger;

import common.Domain;
import common.Link;
import common.LinkSet;

import database.DatabaseHelper;


public class SeekerApp {

	private DatabaseHelper dbConnection;
	private ArrayList<Domain> domains;
	
	public SeekerApp(DatabaseHelper connection) {
        domains = new ArrayList<Domain>();
		dbConnection = connection;
	}
	
	private void addTestDomains()
	{
		//domains.add(new Domain("http://www.wieliszew.pl/", System.currentTimeMillis(), 1, 1));
		//domains.add(new Domain("http://www.rozan.eur.pl/", System.currentTimeMillis(), 1, 1));
		domains.add(new Domain("http://www.mkaszlej.pl/", System.currentTimeMillis(), 15 , 1));
	}
	
	private void getDomains()
	{
		domains = dbConnection.getDomains();
		addTestDomains();
	}

	public void updateDatabase(){
		Logger.info("Updating database state...");
		dbConnection.insertLinksFromSet(LinkSet.getFinishedLinks());
		dbConnection.insertDomainsFromSet(LinkSet.getNewDomains());
		Logger.debug(LinkSet.count());
		Logger.info("Database state updated.");
	}
	
	private void resetSeeker()
	{
		LinkSet.resetFinished();
		LinkSet.resetDomains();
		SeekerThreadPool.reset();
	}
	
	private void processSingleDomain(Domain domain){
		Link initialLink = new Link(domain);
		
		resetSeeker();
	
		LinkSet.loadLinkSet(dbConnection.getLinks(domain));
		
		SeekerThreadPool.setMaxDepth(domain.getSearchDepth());
		SeekerThreadPool.execute(new PageProcessor(initialLink));
		
		while( 	SeekerThreadPool.counter.get() != 0 ){
			try{Thread.sleep(300);}catch(Exception e){}
			Logger.info( "OBECNIE: "+SeekerThreadPool.counter.get() +" WATKOW" );
		}
		
		Logger.info( "Zakonczono przetwarzanie domeny" );
		
		updateDatabase();
	}
	
	public void init()
	{
		getDomains();
		Logger.info("SeekerApp initialized");
	}
	
	public void start()
	{
		for (Domain domain : domains) {
			if(domain.isAccepted())
				processSingleDomain(domain);
		}
		
		shutdown();
	}
	
	public void shutdown()
	{
		SeekerThreadPool.shutdown();
	}
}
