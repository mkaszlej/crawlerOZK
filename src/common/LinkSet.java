package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinkSet {

	private static int initialCount = 0;
	
	private static List<Link> initialLinks = Collections.synchronizedList(new ArrayList<Link>());
	private static List<Link> finishedLinks = Collections.synchronizedList(new ArrayList<Link>());
	
	private static List<Domain> newDomains = Collections.synchronizedList(new ArrayList<Domain>());
	
	LinkSet(){}
	
	public static String count(){
		return "INITIAL LINKS: "+initialCount+"\nCURRENT SEARCH LINKS: "+finishedLinks.size()+"\nCURRENT SEARCH DOMAINS: "+ newDomains.size();
	} 
	
	public static void loadLinkSet(ArrayList<Link> links){
		initialLinks.clear();
		finishedLinks.clear();
		initialLinks = Collections.synchronizedList(links);
		initialCount = initialLinks.size();
	}
	
	public static void addFinishedLink(Link link){
		if(!initialLinks.contains(link))
			finishedLinks.add(link);
		else{
			int index  = initialLinks.indexOf(link);
			Link oldLink = initialLinks.get(index);
			
			link.setVisits(oldLink.getVisits()+1);
			finishedLinks.add(link);
		}
	}
	
	public static void resetFinished(){
		finishedLinks.clear();
	}
	
	public synchronized static List<Link> getFinishedLinks()
	{
		return finishedLinks;
	}
	
	// --- New domains section ---
	
	public static void addNewDomain(Domain domain)
	{
		if(!newDomains.contains(domain))
			newDomains.add(domain);
	}
	
	public synchronized static List<Domain> getNewDomains()
	{
		return newDomains;
	}
	
	public static void resetDomains(){
		newDomains.clear();
	}
	
}
