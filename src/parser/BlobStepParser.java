package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.Url;
import view.ProgressFrame;

public class BlobStepParser extends BlobParser {

	private String city;
	private String cityCode;
	private String street;
	private String buildingNo;
	private String apartamentNo;
		
	public BlobStepParser(Url domainUrl, Url linkUrl, String blob) {
		super( domainUrl, linkUrl, blob);
	}
	
	public void parse() {
		
            System.out.println("@---STEP---\n"+blob);
		
            findCityCode(blob);
            System.out.println("###");
            findCity(blob);
            System.out.println("###");		
            findStreet(blob);
            System.out.println("###");
            findBuildingNo(blob);
            System.out.println("###");
            findApartamentNo(blob);

            System.out.println("/@---STEP ---");
	}
	
	private void findCityCode(String blob){
        Pattern pattern = Pattern.compile("[\\s>]+([0-9]{2}-[0-9]{3})[\\s<]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        int min_distance = Integer.MAX_VALUE;
        
        while (matcher.find()) { 
        	if(middlePoint-matcher.start() < min_distance)
        	{
        		cityCode = matcher.group(1).trim();
        		min_distance = middlePoint-matcher.start();
        	}
        	System.out.println(matcher.group(1).trim());
        }
	}

	private void findCity(String blob){
        Pattern pattern = Pattern.compile("[\\s>]+[0-9]{2}-[0-9]{3}[\\s<]+([^0-9,\\.<>/\\n\\(\\)]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        int min_distance = Integer.MAX_VALUE;
        
        while (matcher.find()) {
        	if(middlePoint-matcher.start() < min_distance)
        	{
        		city = matcher.group(1).trim();
        		min_distance = middlePoint-matcher.start();
        	}
        	System.out.println(matcher.group(1).trim());
        }
    	System.out.println("CITY: "+city);
	}
	
	private void findStreet(String blob){
        /*Pattern pattern = Pattern.compile("ul[\\.\\s]+([^0-9,\\.<>/]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        while (matcher.find()) {
        	System.out.println(matcher.group(1));
        }*/
        Pattern pattern = Pattern.compile("([^0-9,<>/\\n\\(\\):\\-]+)\\s+\\d{1,5}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        int min_distance = Integer.MAX_VALUE;
        
        while (matcher.find()) {
        	if(middlePoint-matcher.start() < min_distance)
        	{
        		street = matcher.group(1).trim();
        		min_distance = middlePoint-matcher.start();
        	}
        	System.out.println(matcher.group(1).trim());
        }
    	System.out.println("STREET: "+street);
	}

	private void findBuildingNo(String blob){
//        Pattern pattern = Pattern.compile("ul[\\.\\s]+[^0-9,\\.\\n\\(\\)]+(\\d*)", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("[^0-9,<>/\\n\\(\\):]+\\s+(\\d{1,5}[\\w]?)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        int min_distance = Integer.MAX_VALUE;
        
        while (matcher.find()) {
        	if(middlePoint-matcher.start() < min_distance)
        	{
        		buildingNo = matcher.group(1).trim();
        		min_distance = middlePoint-matcher.start();
        	}
        	System.out.println(matcher.group(1).trim());
        }    	
        System.out.println("BUILDING "+buildingNo);
	}
	
	private void findApartamentNo(String blob){
//        Pattern pattern = Pattern.compile("ul[\\.\\s]+[^0-9,\\.]+\\d+[\\s\\/-]+(\\d*)", Pattern.CASE_INSENSITIVE);
		Pattern pattern = Pattern.compile("[^0-9,<>/\\n\\(\\):]+\\s+\\d{1,5}[\\w]?+[\\s\\\\/]+(\\d{1,5})[,\\.\\s<]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        int min_distance = Integer.MAX_VALUE;
        
        while (matcher.find()) {
        	if(middlePoint-matcher.start() < min_distance)
        	{
        		apartamentNo = matcher.group(1).trim();
        		min_distance = middlePoint-matcher.start();
        	}
        	System.out.println(matcher.group(1).trim());
        }
        System.out.println("APARTAMENT "+apartamentNo);
	}
	
}
