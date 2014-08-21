package seeker;

import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.Logger;

import common.Link;
import common.LinkSet;

public class PageProcessor implements Runnable {

	private Link link;

	public PageProcessor(Link link) {
		this.link=link;
	}
	
	public Document downloadPage()
	{		
		Document page = null;
		
		if(link.getUrl().isImage()){
			Logger.warn(link.getUrl() + " - Prawdopodobnie obrazek, zgloszono jako nie dzialajacy!");
			SeekerThreadPool.addBrokenUrl(link.getUrl());
			return page;
		}
		
		try{
			Logger.debug("Processing link: " + link.getUrl() +"");
			
			page   = Jsoup.connect( link.getUrl().toString() )
					.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
					.referrer("http://www.google.com")
					.timeout(5000)
					.maxBodySize(1024*50) //50KB 
					.get();
						
		}
		catch(org.jsoup.UnsupportedMimeTypeException e)
		{
			Logger.warn( link.getUrl() +" - Skipping: unsupported MIME");
			SeekerThreadPool.addBrokenUrl(link.getUrl());
		}
		catch(org.jsoup.HttpStatusException e)
		{
			Logger.warn( link.getUrl() +" - HTTP Exception -> " + e.getCause());
			SeekerThreadPool.addBrokenUrl(link.getUrl());
		}
		catch(Exception e)
		{
			SeekerThreadPool.addBrokenUrl(link.getUrl());
			Logger.error(link.getUrl() + " - Error while loading link. Added to brokenLinkList. Error was: ", e);
		}
		return page;
	}
	
	public void run()
	{
		Document page = downloadPage();
		if(page == null){
			SeekerThreadPool.counter.decrementAndGet();
			return;
		}
		
		HashSet<Link> newLinks = getLinks(page);
		
		link.setLinkCount(newLinks.size());
		
		LinkSet.addFinishedLink(link);
				
		SeekerThreadPool.execute(newLinks);
		
		SeekerThreadPool.counter.decrementAndGet();
	}
	
	private HashSet<Link> getLinks(Document document)
	{
		HashSet<Link> newLinks = new HashSet<Link>();
		
		Elements aLinks = document.select("a");
		Iterator<Element> aLink = aLinks.iterator();
		while(aLink.hasNext())
		{
			String url = aLink.next().attr("href").toString();
			Link newLink = new Link( link, url );
			newLinks.add(newLink);			
		}
		
		return newLinks;
	}
	
}
