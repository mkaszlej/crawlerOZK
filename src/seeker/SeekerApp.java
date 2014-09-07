package seeker;

import common.Domain;
import common.Link;
import common.ParserData;
import common.SeekerData;
import database.DatabaseHelper;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import parser.BlobResult;
import parser.ParserThreadPool;
import util.Logger;
import util.Url;
import view.AddressFrame;


public class SeekerApp implements Runnable {

    private DatabaseHelper dbConnection;
    private ArrayList<Domain> domains;

    public SeekerApp(DatabaseHelper connection, Domain domain) {
        this.dbConnection = connection;
        domains = new ArrayList<Domain>();
        domains.add(domain);
        domain.addVisit();
        dbConnection.insertDomain(domain);
    }

    public SeekerApp(DatabaseHelper connection, String url, int depth) {
        this.dbConnection = connection;
        Domain domain = new Domain(url, null, depth);
        domain.addVisit();
        dbConnection.insertDomain(domain);
        domains = new ArrayList<Domain>();
        domains.add(domain);
    }

    private void getDomains()
    {
        domains = dbConnection.getDomains();
    }

    public void updateDatabase(){
        
        Logger.info("Updating database state...");
        dbConnection.insertLinksFromSet(SeekerData.getFinishedLinks());
        dbConnection.insertDomainsFromSet(SeekerData.getNewDomains());
        Logger.debug(SeekerData.count());
        Logger.info("Database state updated.");
        
    }

    private void resetSeeker()
    {
        SeekerData.resetFinished();
        SeekerData.resetDomains();
        ParserData.resetProcessed();
        SeekerThreadPool.reset();
        ParserThreadPool.reset();
    }

    private void processSingleDomain(Domain domain){
        
        Link initialLink = new Link(domain);

        resetSeeker();

        SeekerData.loadLinkList(dbConnection.getLinks(domain));
        ParserData.loadAddressList(dbConnection.getAddresses(domain));

        SeekerThreadPool.setMaxDepth(domain.getSearchDepth());
        SeekerThreadPool.execute(new PageProcessor(initialLink));

        while( 	SeekerThreadPool.counter.get() > 1 ){
                try{Thread.sleep(300);}catch(Exception e){}
                Logger.info( "SEEKER["+ (SeekerThreadPool.counter.get()-1) +"] - PARSER["+ParserThreadPool.counter.get()+"]" );
        }

        while( 	ParserThreadPool.counter.get() != 0 ){
                try{Thread.sleep(300);}catch(Exception e){}
                Logger.info( "[PARSER] OBECNIE: "+ParserThreadPool.counter.get() +" WATKOW. \nADRESÓW ["+ParserData.getBlobResultSize()+"]" );
        }

        updateDatabase();

        Logger.info( "Zakonczono przetwarzanie domeny" );

    }
    @Override
    public void run() {

        for (Domain domain : domains) {
            processSingleDomain(domain);
        }

        SeekerThreadPool.counter.decrementAndGet();
    }
    
}
