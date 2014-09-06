/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

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
    
    private String domainUrl;
    
    public FlowControlThread(DatabaseHelper dbConnection, String domainUrl) {
        this.dbConnection = dbConnection;
        this.domainUrl = domainUrl;
        this.progressFrame = new ProgressFrame(domainUrl);    
        this.addressFrame = new AddressFrame(dbConnection);
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressFrame.setVisible(true);
            }
        });
        
    }
    
    public void run() {
        
        //Wait until seeking is over
        while( 	SeekerThreadPool.counter.get() > 0 )
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    progressFrame.update();
                }
            });
        }
        
        //Hide progress
        progressFrame.setVisible(false);

        //Show address frame
        addressFrame.prepare();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                addressFrame.setVisible(true);
            }
        });

    }
}
