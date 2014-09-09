/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import common.Address;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import parser.BlobResult;

/**
 *
 * @author mkaszlej
 */
public class BlobResultModel extends AbstractListModel implements ListModel{

    private final BlobResult result;

    public BlobResultModel(BlobResult result) {
        this.result = result;
    }
    
    @Override
    public int getSize() {
        return result.getParserResult().size();
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public Address getElementAt(int index) {
        return result.getParserResult().get(index);
    }
    
    public boolean isEmpty(){
        return result.getParserResult().isEmpty();
    }
    
}
