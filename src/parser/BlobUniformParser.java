package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import common.Address;

public class BlobUniformParser extends BlobParser {


	
	
	
	public BlobUniformParser(Address address) {
		super(address);
	}
	
	public void parse() {
		//String blob = Jsoup.parse(address.getBlob()).text();
		String blob = address.getBlob();
		
		System.out.println("@---  UNIFORM ---@\n"+blob);
		
		miejskiUl(blob);
		miejski(blob);

		miejskiOdwrUl(blob);
		miejskiOdwr(blob);

		wies1OdwrUl(blob);
		wies1Odwr(blob);

		wies2Ul(blob);
		wies2(blob);

		wies2OdwrUl(blob);
		wies2Odwr(blob);
		
		minimal(blob);
	
		
		System.out.println("@--- /UNIFORM ---@");
	}
	
	private void getNazwa(String blob, int start)
	{
		String nazwa = blob.substring(0,start);
		System.out.println("NAZWA: "+ nazwa);
	}
	
	private void miejski(String blob) {
	    //ul. Cicha 132 m. 16[1]
	    //62-200 Gniezno
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		
		String firstLine = cokolwiek+"\\s+"+budynek+mieszkanie;
		String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		
		String standard = firstLine+secondLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {
    		System.out.println("\n@miejski:");
    		
    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(1) );
            System.out.println("DOM: "+ matcher.group(2) );
            System.out.println("MIESZKANIE: "+ matcher.group(3) );
            System.out.println("KOD: "+ matcher.group(5) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(6) );
        }
                
	}

	private void miejskiOdwr(String blob) {
	    //62-200 Gniezno
	    //ul. Cicha 132 m. 16
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		
		String firstLine = kod+"\\s+"+cokolwiek;
		String secondLine = s+cokolwiek+"\\s+"+budynek+mieszkanie;
		
		String standard = firstLine+secondLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {

    		System.out.println("\n@miejskiOdwr:");
    		
    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
    		System.out.println("ULICA: "+ matcher.group(3) );
            System.out.println("DOM: "+ matcher.group(4) );
            System.out.println("MIESZKANIE: "+ matcher.group(5) );
            System.out.println("KOD: "+ matcher.group(1) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(2) );
        }
                
	}

	private void miejskiOdwrUl(String blob) {
	    //62-200 Gniezno
	    //ul. Cicha 132 m. 16
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		String ul = "ul\\.\\s";
		
		String firstLine = kod+"\\s+"+cokolwiek;
		String secondLine = s+ul+cokolwiek+"\\s+"+budynek+mieszkanie;
		
		String standard = firstLine+secondLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {

    		System.out.println("\n@miejskiOdwr:");
    		
    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
    		System.out.println("ULICA: "+ matcher.group(3) );
            System.out.println("DOM: "+ matcher.group(4) );
            System.out.println("MIESZKANIE: "+ matcher.group(5) );
            System.out.println("KOD: "+ matcher.group(1) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(2) );
        }
                
	}
	
	private void miejskiUl(String blob) {
	    //Janina Nowak
		//ul. Sochacz 5 m. 16
	    //62-200 Gniezno
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String ul = "ul\\.\\s";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		
		String firstLine = ul+cokolwiek+"\\s+"+budynek+mieszkanie;
		String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		
		String standard = firstLine+secondLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {
    		System.out.println("\n@miejskiUl:");

    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(1) );
            System.out.println("DOM: "+ matcher.group(2) );
            System.out.println("MIESZKANIE: "+ matcher.group(3) );
            System.out.println("KOD: "+ matcher.group(5) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(6) );
        }
                
	}	
	
	private void wies1Odwr(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
		//Sochacz 5 m. 16
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		
		String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		String thirdLine = s+cokolwiek+"\\s+"+budynek+mieszkanie;
		
		String standard = secondLine+thirdLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {
    		System.out.println("\n@wies1odwr:");

    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(3) );
            System.out.println("DOM: "+ matcher.group(4) );
            System.out.println("MIESZKANIE: "+ matcher.group(5) );
            System.out.println("KOD: "+ matcher.group(1) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(2) );
        }
                
	}

	private void wies1OdwrUl(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
		//ul. Sochacz 5 m. 16
		
		String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		String thirdLine = s+ul+cokolwiek+"\\s+"+budynek+mieszkanie;
		
		String standard = secondLine+thirdLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {
    		System.out.println("\n@wies1odwrUl:");

    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(3) );
            System.out.println("DOM: "+ matcher.group(4) );
            System.out.println("MIESZKANIE: "+ matcher.group(5) );
            System.out.println("KOD: "+ matcher.group(1) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(2) );
        }
                
	}
	
	private void wies2(String blob) {
	    //Janina Nowak
		//Sochaczewska
	    //62-200 Gniezno
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		
		String secondLine = s+cokolwiek;
		String thirdLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		
		String standard = secondLine+thirdLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {
    		System.out.println("\n@wies2:");
    		
    		getNazwa(blob, matcher.start());
    		
            System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(1) );
            System.out.println("KOD: "+ matcher.group(2) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(3) );
        }
                
	}
	
	private void wies2Ul(String blob) {
	    //Janina Nowak
		//ul. Sochaczewska
	    //62-200 Gniezno
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String ul = "ul\\.\\s";
		String kod = regexCityCode;
		
		String secondLine = s+ul+cokolwiek;
		String thirdLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		
		String standard = secondLine+thirdLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {
    		System.out.println("\n@wies2Ul:");
    		
    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(1) );
            System.out.println("KOD: "+ matcher.group(2) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(3) );
        }
                
	}
	
	private void wies2Odwr(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
		//Sochaczewska
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		
		String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		String thirdLine = s+cokolwiek;
		
		String standard = secondLine+thirdLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        while (matcher.find()) {
    		System.out.println("\n@wies2Odwr:");
            
    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(3) );
            System.out.println("KOD: "+ matcher.group(1) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(2) );
        }
                
	}

	private void wies2OdwrUl(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
		//ul. Sochaczewska
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String ul = "ul\\.\\s";
		String kod = regexCityCode;
		
		String secondLine =kod+"\\s+"+cokolwiek;
		String thirdLine = s+ul+cokolwiek;
		
		String standard = secondLine+thirdLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        while (matcher.find()) {
    		System.out.println("\n@wies2OdwrUl:");
    		
    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("ULICA: "+ matcher.group(3) );
            System.out.println("KOD: "+ matcher.group(1) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(2) );
        }
                
	}

	private void minimal(String blob) {
	    //Janina Nowak
	    //62-200 Gniezno
		String cokolwiek = "([^0-9,<>/\\n\\(\\):]+)";
		String budynek = "(\\d{1,5}[\\w]?)";
		String mieszkanie = "([\\s\\\\/m\\.]+(\\d{0,5}))?";
		String kod = regexCityCode;
		
		String secondLine = "[\\s\\n,>]+"+kod+"\\s+"+cokolwiek;
		
		String standard = secondLine;
		Pattern pattern = Pattern.compile(standard, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(blob);
        
        // check all occurences
        while (matcher.find()) {
    		System.out.println("\n@minimal:");
    		
    		getNazwa(blob, matcher.start());
    		
    		System.out.println( matcher.group() );
            System.out.println("KOD: "+ matcher.group(1) );
            System.out.println("MIEJSCOWOSC: "+ matcher.group(2) );
        }
                
	}
	
}

//TODO: Komornica ul. Pogodna 30, 05-135 Wieliszew