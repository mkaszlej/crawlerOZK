package util;

public class Url {
	public String urlString;
	
	//Url znormalizowany z bazy
	public Url(String url) {
		this.urlString = url;
	}

	//Url z normalizacja
	public Url(String url, String domain_url) {
		this.urlString = convertUrl(url, domain_url);
	}
	
	@Override
	public int hashCode() {
		return urlString.hashCode();
	};
	
	@Override
	public boolean equals(Object o){
	    if(o == null)            return false;
	    if(!(o instanceof Url)) return false;

	    Url other = (Url) o;
	    return this.urlString.equals(other.urlString);
	  }
	
	@Override
	public String toString() {
		return urlString;
	}
	
	public boolean inDomain(String domain_url)
	{
		String domain_name = domain_url.replace("http://", "");
		domain_name = domain_name.replace("www.", "");
		return urlString.contains(domain_name); 
	}
	
	public boolean isImage()
	{
		if( urlString.endsWith(".jpg") ) return true;
		if( urlString.endsWith(".png") ) return true;
		if( urlString.endsWith(".jpeg") ) return true;
		if( urlString.endsWith(".JPG") ) return true;
		if( urlString.endsWith(".PNG") ) return true;
		if( urlString.endsWith(".gif") ) return true;
		if( urlString.endsWith(".GIF") ) return true;
		if( urlString.endsWith(".JPEG") ) return true;
		
		return false;
	}
	
	
	public static String convertUrl(String url, String domainUrl)
	{
		if(!url.contains("http://") && !url.startsWith("www."))
		{
			String retString = url;
			
			if(url.startsWith("/")) retString = url.substring(1);
			else if(url.startsWith("//")) retString = url.substring(2);
			else if(url.startsWith("/../")) retString = url.substring(4);
			else if(url.startsWith("./")) retString = url.substring(2);
			else if(url.startsWith("../")) retString = url.substring(3);
			
			return domainUrl+retString ;
		}
		return url;
	}
}
