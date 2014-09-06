package parser;


import common.Address;
import common.Link;
import common.ParserData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import util.Logger;
import util.Url;
import view.AddressFrame;

public class BlobUniformParser extends BlobParser {
	
	String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
        String budynek = "(\\d{1,5}[\\w]?)";
        String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
        String kod = regexCityCode;
        String ul = "ul\\.\\s";
        
	public BlobUniformParser(Url domainUrl, Url linkUrl, String blob) {
		super(domainUrl, linkUrl, blob);
	}
	
	public void parse() {
		//String blob = Jsoup.parse(address.getBlob()).text();
		
		System.out.println("@---  UNIFORM ---@\n"+blob);
		
		miejskiUl(blob);
		miejski(blob);

		miejskiOdwrUl(blob);
		miejskiOdwr(blob);

		wies2Ul(blob);
		wies2(blob);

		wies2OdwrUl(blob);
		wies2Odwr(blob);
		
		minimal(blob);
		
                ParserData.addBlobResult(parserResults);
                                
		System.out.println("@--- /UNIFORM ["+parserResults.size()+"] ---@");
	}
	
	private String getNazwa(String blob, int start)
	{
                return  blob.substring(0,start);
	}
	
	private void miejski(String blob) {
	    
            //ul. Cicha 132 m. 16[1]
	    //62-200 Gniezno
            
            String firstLine = cokolwiek+"\\s+"+budynek+mieszkanie;
            String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;

            String standard = firstLine+secondLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            
            Matcher matcher = pattern.matcher(blob);

            // check all occurences
            while (matcher.find()) {
                System.out.println("\n@miejski:");
                
                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(1);
                String nrDomu = matcher.group(2);
                String nrMieszkania = matcher.group(3);
                String kodPocztowy = matcher.group(5);
                String miejscowosc = matcher.group(6);
                                
                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Standardowy",parsedAddress);
                
/*              System.out.println("ULICA: "+ ulica );
                System.out.println("DOM: "+ nrDomu );
                System.out.println("MIESZKANIE: "+ nrMieszkania );
                System.out.println("KOD: "+ kodPocztowy );
                System.out.println("MIEJSCOWOSC: "+ miejscowosc );*/
            }
                
	}

	private void miejskiOdwr(String blob) {
	    //62-200 Gniezno
	    //ul. Cicha 132 m. 16
		
            String firstLine = kod+"\\s+"+cokolwiek;
            String secondLine = s+cokolwiek+"\\s+"+budynek+mieszkanie;

            String standard = firstLine+secondLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
            // check all occurences
            while (matcher.find()) {

                System.out.println("\n@miejskiOdwr:");

                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(3);
                String nrDomu = matcher.group(4);
                String nrMieszkania = matcher.group(5);
                String kodPocztowy = matcher.group(1);
                String miejscowosc = matcher.group(2);
                                
                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Standardowy odwrócony",parsedAddress);
                
/*              System.out.println("ULICA: "+ ulica );
                System.out.println("DOM: "+ nrDomu );
                System.out.println("MIESZKANIE: "+ nrMieszkania );
                System.out.println("KOD: "+ kodPocztowy );
                System.out.println("MIEJSCOWOSC: "+ miejscowosc );*/
                
            }
                
	}

	private void miejskiOdwrUl(String blob) {
	    //62-200 Gniezno
	    //ul. Cicha 132 m. 16
		
            String firstLine = kod+"\\s+"+cokolwiek;
            String secondLine = s+ul+cokolwiek+"\\s+"+budynek+mieszkanie;

            String standard = firstLine+secondLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
            while (matcher.find()) {

                System.out.println("\n@miejskiOdwrUl:");

                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(3);
                String nrDomu = matcher.group(4);
                String nrMieszkania = matcher.group(5);
                String kodPocztowy = matcher.group(1);
                String miejscowosc = matcher.group(2);
                                
                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Standardowy odwrócony z ul.",parsedAddress);
                               
            }
                
	}
	
	private void miejskiUl(String blob) {
	    //Janina Nowak
            //ul. Sochacz 5 m. 16
	    //62-200 Gniezno

            String firstLine = ul+cokolwiek+"\\s+"+budynek+mieszkanie;
            String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		
            String standard = firstLine+secondLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
            // check all occurences
            while (matcher.find()) {
                System.out.println("\n@miejskiUl:");

                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(1);
                String nrDomu = matcher.group(2);
                String nrMieszkania = matcher.group(3);
                String kodPocztowy = matcher.group(5);
                String miejscowosc = matcher.group(6);
                                
                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Standardowy z ul.",parsedAddress);
                
            }
                
	}	
	
	private void wies2(String blob) {
	    //Janina Nowak
            //Sochaczewska
	    //62-200 Gniezno
		
            String secondLine = s+cokolwiek;
            String thirdLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		
            String standard = secondLine+thirdLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
            // check all occurences
            while (matcher.find()) {
                System.out.println("\n@wies2:");

                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(1);
                String nrDomu = null;
                String nrMieszkania = null;
                String kodPocztowy = matcher.group(2);
                String miejscowosc = matcher.group(3);

                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Bez nr domu/mieszkania",parsedAddress);


            }

	}
	
	private void wies2Ul(String blob) {
	    //Janina Nowak
            //ul. Sochaczewska
	    //62-200 Gniezno

            String secondLine = s+ul+cokolwiek;
            String thirdLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;

            String standard = secondLine+thirdLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
            // check all occurences
            while (matcher.find()) {
                System.out.println("\n@wies2Ul:");

                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(1);
                String nrDomu = null;
                String nrMieszkania = null;
                String kodPocztowy = matcher.group(2);
                String miejscowosc = matcher.group(3);

                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Bez nr domu/mieszkania z ul.",parsedAddress);
            }
                
	}
	
	private void wies2Odwr(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
            //Sochaczewska
		
            String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
            String thirdLine = s+cokolwiek;

            String standard = secondLine+thirdLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
            while (matcher.find()) {
                System.out.println("\n@wies2Odwr:");
     
                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(3);
                String nrDomu = null;
                String nrMieszkania = null;
                String kodPocztowy = matcher.group(1);
                String miejscowosc = matcher.group(2);

                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Bez nr domu/mieszkania odwrócony",parsedAddress);
                
            }
                
	}

	private void wies2OdwrUl(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
            //ul. Sochaczewska
		
            String secondLine =kod+"\\s+"+cokolwiek;
            String thirdLine = s+ul+cokolwiek;

            String standard = secondLine+thirdLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
            while (matcher.find()) {
                System.out.println("\n@wies2OdwrUl:");

                String nazwa = getNazwa(blob, matcher.start());
                String ulica = matcher.group(3);
                String nrDomu = null;
                String nrMieszkania = null;
                String kodPocztowy = matcher.group(1);
                String miejscowosc = matcher.group(2);

                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Bez nr domu/mieszkania odwrócony z ul.",parsedAddress);
                
            }
                
	}

	private void minimal(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
		
            String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;

            String standard = secondLine;
            Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(blob);
        
            // check all occurences
            while (matcher.find()) {
                System.out.println("\n@minimal:");

                String nazwa = getNazwa(blob, matcher.start());
                String ulica = null;
                String nrDomu = null;
                String nrMieszkania = null;
                String kodPocztowy = matcher.group(1);
                String miejscowosc = matcher.group(2);

                Address parsedAddress;
                parsedAddress = new Address(nazwa, kodPocztowy, miejscowosc, ulica, nrDomu, nrMieszkania, domainUrl, linkUrl, blob );
                Logger.info(parsedAddress.toString());
                parserResults.add("Kod i miasto",parsedAddress);
            }
                
	}
	
}

//TODO: Komornica ul. Pogodna 30, 05-135 Wieliszew