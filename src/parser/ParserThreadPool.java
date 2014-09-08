package parser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.jsoup.nodes.Document;

import common.Link;
import view.ProgressFrame;

public class ParserThreadPool {

	private static final ParserThreadPool instance = new ParserThreadPool();
	private static ExecutorService threadPool = Executors.newFixedThreadPool(1);
	
	public final static AtomicLong counter = new AtomicLong();
	
	ParserThreadPool(){};
	
	public static ParserThreadPool getInstance()
	{
		return instance;
	}
	
	public static void execute(ProgressFrame parentFrame, Link link, Document document)
	{
		execute(new DocumentParserThread(parentFrame, link, document));
	}
	
	public static void execute(Runnable thread)
	{
		threadPool.execute(thread);
		counter.incrementAndGet();
	}
	
	public static void shutdown()
	{
		threadPool.shutdown();
	}
	
	public static void reset(){
	}
}
