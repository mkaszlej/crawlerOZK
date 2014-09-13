package common;

import util.Url;

public class Address {

        private String name=null, phone=null, email=null, index=null, cityCode=null, city=null, street=null, domain=null, blob=null, buildingNo=null, apartamentNo=null, flag=null, category=null;
	private String htmlString = null;
       
        private long timestamp;
	private int addressId,  count;
	private Url link;
	
        String formaDzialanosci=null, pn=null, wt=null,sr=null,czw=null,pt=null,so=null,nd=null,sw=null,pn_wakacje=null,wt_wakacje=null,sr_wakacje=null,czw_wakajce=null,pt_wakacje=null,so_wakacje=null,nd_wakacje=null;
        String podjazd=null,wnetrze=null,nieslyszacy=null,niewidomi=null,rodzic_z_dzieckiem=null,windy=null,toalety=null,inne=null;
        String komentarz=null,komentarz_korespondenta=null, strona_www = null;
        
	@Override
	public boolean equals(Object o){
	    if(o == null)            return false;
	    if(!(o instanceof Address)) return false;

	    Address other = (Address) o;
	    
            //BLOB IS CURRENTLY ALREADY PARSED
	    if(blobEquals(other,5)) return true;
	    	
	    if(this.city == null) return false;
	    if(!this.city.equals(other.city)) return false;
	    if(!this.cityCode.equals(other.cityCode)) return false;
	    if(!this.street.equals(other.street)) return false;
	    if(!(this.buildingNo.equals(other.buildingNo))) return false;
	    if(!(this.apartamentNo.equals(other.apartamentNo))) return false;
	    return true;
	  }
	
	public boolean blobEquals(Address a, int margin)
	{
		if( this.blob.length() < 2*margin ) return false;
		
		String myBlob = this.blob.substring(margin, this.blob.length()-margin);
		return a.getBlob().contains(myBlob);
	}
	
	// Address z bazy danych
        public Address( int addressId, String name, String phone, String email, String index, String category, String cityCode, String city, String street, String domain, String link, String blob, long timestamp, String buildingNo, String apartamentNo, int count, String flag) {
            this.addressId = addressId;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.index = index;
            this.category = category;
            this.cityCode = cityCode;
            this.city = city;
            this.street = street;
            this.domain = domain;
            this.link = new Url(link);
            this.blob = blob;
            this.timestamp = timestamp;
            this.buildingNo = buildingNo;
            this.apartamentNo = apartamentNo;
            this.count = count;
            this.flag = flag;
        }
    
    //Address na podstawie linku i blob
    public Address( Link link, String blob )
    {
    	this.link = link.getUrl();
    	this.blob = blob;
    	this.domain = link.getDomainUrl();
    	this.timestamp = System.currentTimeMillis();
    	this.count = 1;
    }
    
    //Address podczas parsowania
    public Address( String name, String kodPocztowy, String miejscowosc, String ulica, String nrDomu, String nrMieszkania, Url domainUrl, Url linkUrl, String blob )
    {
        this.name = name;
        this.cityCode = kodPocztowy;
        this.city = miejscowosc;
        this.street = ulica;
        
        this.buildingNo = nrDomu;
        this.apartamentNo = nrMieszkania;
        
        this.domain = domainUrl.toString();
        this.link = linkUrl;
        this.blob = blob;
        this.count = 1;
        this.timestamp = System.currentTimeMillis();             
    }

    public void setStrona_www(String strona_www) {
        this.strona_www = strona_www;
    }

    public String getStrona_www() {
        return strona_www;
    }
    
    public void setCzw(String czw) {
        this.czw = czw;
    }

    public void setCzw_wakajce(String czw_wakajce) {
        this.czw_wakajce = czw_wakajce;
    }

    public void setFormaDzialanosci(String formaDzialanosci) {
        this.formaDzialanosci = formaDzialanosci;
    }

    public void setInne(String inne) {
        this.inne = inne;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }

    public void setKomentarz_korespondenta(String komentarz_korespondenta) {
        this.komentarz_korespondenta = komentarz_korespondenta;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public void setNd_wakacje(String nd_wakacje) {
        this.nd_wakacje = nd_wakacje;
    }

    public void setNieslyszacy(String nieslyszacy) {
        this.nieslyszacy = nieslyszacy;
    }

    public void setNiewidomi(String niewidomi) {
        this.niewidomi = niewidomi;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public void setPn_wakacje(String pn_wakacje) {
        this.pn_wakacje = pn_wakacje;
    }

    public void setPodjazd(String podjazd) {
        this.podjazd = podjazd;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public void setPt_wakacje(String pt_wakacje) {
        this.pt_wakacje = pt_wakacje;
    }

    public void setRodzic_z_dzieckiem(String rodzic_z_dzieckiem) {
        this.rodzic_z_dzieckiem = rodzic_z_dzieckiem;
    }

    public void setSo_wakacje(String sb_wakacje) {
        this.so_wakacje = sb_wakacje;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public void setSr_wakacje(String sr_wakacje) {
        this.sr_wakacje = sr_wakacje;
    }

    public void setSw(String sw) {
        this.sw = sw;
    }

    public void setToalety(String toalety) {
        this.toalety = toalety;
    }

    public void setWindy(String windy) {
        this.windy = windy;
    }

    public void setWnetrze(String wnetrze) {
        this.wnetrze = wnetrze;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public void setWt_wakacje(String wt_wakacje) {
        this.wt_wakacje = wt_wakacje;
    }
    
    public void setHtmlString(String htmlString) {
        this.htmlString = htmlString;
    }

    public String getCzw() {
        return czw;
    }

    public String getCzw_wakajce() {
        return czw_wakajce;
    }

    public String getFormaDzialanosci() {
        return formaDzialanosci;
    }

    public String getInne() {
        return inne;
    }

    public String getKomentarz() {
        return komentarz;
    }

    public String getKomentarz_korespondenta() {
        return komentarz_korespondenta;
    }

    public String getNd() {
        return nd;
    }

    public String getNd_wakacje() {
        return nd_wakacje;
    }

    public String getNieslyszacy() {
        return nieslyszacy;
    }

    public String getNiewidomi() {
        return niewidomi;
    }

    public String getPn() {
        return pn;
    }

    public String getPn_wakacje() {
        return pn_wakacje;
    }

    public String getPodjazd() {
        return podjazd;
    }

    public String getPt() {
        return pt;
    }

    public String getPt_wakacje() {
        return pt_wakacje;
    }

    public String getRodzic_z_dzieckiem() {
        return rodzic_z_dzieckiem;
    }

    public String getSo() {
        return so;
    }

    public String getSo_wakacje() {
        return so_wakacje;
    }

    public String getSr() {
        return sr;
    }

    public String getSr_wakacje() {
        return sr_wakacje;
    }

    public String getSw() {
        return sw;
    }

    public String getToalety() {
        return toalety;
    }

    public String getWindy() {
        return windy;
    }

    public String getWnetrze() {
        return wnetrze;
    }

    public String getWt() {
        return wt;
    }

    public String getWt_wakacje() {
        return wt_wakacje;
    }
    
    public String getHtmlString() {
        return htmlString;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getEmail() {
        return email;
    }

    public String getFlag() {
        return flag;
    }

    public String getIndex() {
        return index;
    }

    public String getCategory() {
        return category;
    }

    public String getPhone() {
        return phone;
    }
    
    public String getCityCode() {
		return cityCode;
	}
    
    public String getCity() {
		return city;
	}
    
    public String getApartamentNo() {
		return apartamentNo;
	}
    
    public String getBlob() {
		return blob;
	}
    
    public String getBuildingNo() {
		return buildingNo;
	}
    
    public int getCount() {
		return count;
	}
    
    public String getDomain() {
		return domain;
	}
    
    public Url getLink() {
		return link;
	}
    
    public String getStreet() {
		return street;
	}
    
    public long getTimestamp() {
		return timestamp;
	}
    
    public void setCount(int count) {
		this.count = count;
		this.timestamp = System.currentTimeMillis();
	}

    public String getName() {
        return name;
    }
    
    public String toString(){
    	return name+" ul. "+street+" "+buildingNo+"/"+apartamentNo+", "+cityCode+" "+city;
    }
}
