/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import common.Domain;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;

/**
 *
 * @author mkaszlej
 */
public class DomainModel extends AbstractListModel<Domain> implements ListModel<Domain>{

    private final ArrayList<Domain> domainList;

    public DomainModel(ArrayList<Domain> domainList) {
        this.domainList = domainList;
    }
    
    @Override
    public int getSize() {
        return domainList.size();
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public Domain getElementAt(int index) {
        return domainList.get(index);
    }
    
}
