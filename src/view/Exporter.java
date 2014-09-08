/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import common.Domain;
import javax.swing.SwingWorker;

/**
 *
 * @author mkaszlej
 */
public class Exporter extends javax.swing.JFrame{

    private database.DatabaseHelper dbConnection;
    private Domain domain;

    public Exporter(database.DatabaseHelper dbConnection, Domain domain_to_export) {
        this.domain = domain_to_export;
        this.dbConnection = dbConnection;
    }
    
    class Task extends SwingWorker<Void, String>{

        public Task() {
        }
    
        
        @Override
        protected Void doInBackground() throws Exception {
            return null;
        }
    }
    
}
