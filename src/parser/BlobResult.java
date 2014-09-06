/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;

import common.Address;
import common.Link;
import java.util.ArrayList;
import java.util.HashMap;
import util.Url;

/**
 *
 * @author mkaszlej
 */
public class BlobResult {
 
    private final Url domainUrl;
    private final Url linkUrl;
    
    private final String blob;
    
    private ArrayList<Address> parserResult;

    /**
     *
     * @param domainUrl
     * @param linkUrl
     * @param blob
     */
    public BlobResult(Url domainUrl, Url linkUrl, String blob) {
        this.domainUrl = domainUrl;
        this.linkUrl = linkUrl;
        this.blob = blob;
        parserResult = new ArrayList<Address>();
    }
    
    public void add(String key, Address address){
        parserResult.add(address);
    }

    public String getBlob() {
        return blob;
    }

    public Url getDomainUrl() {
        return domainUrl;
    }

    public Url getLinkUrl() {
        return linkUrl;
    }

    public ArrayList<Address> getParserResult() {
        return parserResult;
    }

    @Override
    public String toString() {
        return "["+linkUrl.toString()+"]"+"PARSED FROM:\n"+blob+"\nRESULTS:\n"+parserResult.toString();
    }
    
    public int size()
    {
        return parserResult.size();
    }
    
}
