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
    
    private Domain domain;

    public FlowControlThread(DatabaseHelper dbConnection, Domain domain_to_process) {
        this.dbConnection = dbConnection;
        this.domain = domain_to_process;
        this.domain.addVisit();
        this.progressFrame = new ProgressFrame(dbConnection,domain);    
    }
    
    public FlowControlThread(DatabaseHelper dbConnection, String domainUrl, int searchDepth) {
        this.dbConnection = dbConnection;
        this.domain = new Domain(domainUrl, null, searchDepth);
        this.domain.addVisit();
        this.progressFrame = new ProgressFrame(dbConnection,domain); 
    }
    
    public void run() {


        progressFrame.start(dbConnection,domain);

    }

}
