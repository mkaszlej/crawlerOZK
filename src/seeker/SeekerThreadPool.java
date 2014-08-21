package seeker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import util.Logger;
import util.Url;

import common.Domain;
import common.Link;
import common.LinkSet;

public class SeekerThreadPool {

	private static final SeekerThreadPool instance = new SeekerThreadPool();
	private static ExecutorService threadPool = Executors.newFixedThreadPool(15);
	
	private static List<Url> processedUrls = Collections.synchronizedList(new ArrayList<Url>());
	private static List<Url> brokenUrls = Collections.synchronizedList(new ArrayList<Url>());
	
	private static int maxDepth = 2;

	public final static AtomicLong counter = new AtomicLong();
	
	SeekerThreadPool(){
	};
	
	
	public static void setMaxDepth(int depth)
	{
		maxDepth = depth;
	}
	
	public static SeekerThreadPool getInstance()
	{
		return instance;
	}
	
	public static void execute(HashSet<Link> linkSet)
	{
		for (Link link : linkSet) {
			if(checkUrl(link))
			{
				execute(new PageProcessor(link));
			}
		}
	}
	
	public static void execute(Runnable thread)
	{
		threadPool.execute(thread);
		counter.incrementAndGet();
	}
	
	public static void shutdown()
	{
		threadPool.shutdown();
		try{
			threadPool.awaitTermination(10, TimeUnit.SECONDS);
		}
		catch(InterruptedException e)
		{
			Logger.error("Unable to close threadpool", e);
		}
	}
	
	public static boolean checkUrl(Link l){
		if(!l.getUrl().inDomain(l.getDomainUrl()))
		{
			Logger.warn(l.getUrl() + " - Poza domena - dodano nowa domene");
			LinkSet.addNewDomain(new Domain(l));
			return false;
		}
		if(l.getLinkDepth() > maxDepth ){
			Logger.info(l.getUrl() + " - Max glebokosc przekroczona");
			return false;
		}
		if(processedUrls.contains(l.getUrl())){
			Logger.info(l.getUrl() + " - Zostal juz przetworzony/Jest przetwarzany");
			return false;
		}
		if(brokenUrls.contains(l.getUrl())){
			Logger.info(l.getUrl() + " - Zgloszono jako nie dzialajacy link");
			return false;
		}
		
		Logger.info(l.getUrl() + " - Accepted");
		addProcessedUrl(l.getUrl());
		
		return true;
	}
	
	public static synchronized void addBrokenUrl(Url url)
	{
		brokenUrls.add(url);
	}
	
	public static synchronized void addProcessedUrl(Url url)
	{
		processedUrls.add(url);
	}
	
	public static void reset(){
		//shutdown();
		brokenUrls.clear();
		processedUrls.clear();
	}
}
