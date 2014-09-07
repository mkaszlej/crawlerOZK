/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import common.Domain;
import database.DatabaseHelper;
import java.util.logging.Level;
import java.util.logging.Logger;
import seeker.SeekerThreadPool;

/**
 *
 * @author mkaszlej
 */
public class FlowControlThread implements Runnable{

    private DatabaseHelper dbConnection;
    
    private final ProgressFrame progressFrame;
    
    private final AddressFrame addressFrame;
    
    private Domain domain;

    public FlowControlThread(DatabaseHelper dbConnection, Domain domain_to_process) {
        this.dbConnection = dbConnection;
        this.domain = domain_to_process;
        this.domain.addVisit();
        this.progressFrame = new ProgressFrame(domain);    
        this.addressFrame = new AddressFrame(dbConnection);
    }
    
    public FlowControlThread(DatabaseHelper dbConnection, String domainUrl, int searchDepth) {
        this.dbConnection = dbConnection;
        this.domain = new Domain(domainUrl, null, searchDepth);
        this.domain.addVisit();
        this.progressFrame = new ProgressFrame(domain);    
        this.addressFrame = new AddressFrame(dbConnection);
    }
    
    public void run() {

        //Show progress frame
        showProgressFrame();
        
        //Show address frame
        //showAddressFrame();

    }
    
    private void showAddressFrame(){
        
        addressFrame.prepare();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                addressFrame.setVisible(true);
            }
        });
        
    }
    
    private void showProgressFrame(){
        
        /* Create and display the form 
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressFrame.setVisible(true);
            }
        });*/
        
        progressFrame.start(dbConnection,domain);
        
        /*Wait until seeking is over
        while( 	SeekerThreadPool.counter.get() > 0 )
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    progressFrame.update();
                }
            });
        }
        
        //Hide progress
        progressFrame.setVisible(false); */
    }
}
