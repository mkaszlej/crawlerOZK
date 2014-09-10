/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import common.Domain;
import common.Link;
import common.ParserData;
import common.SeekerData;
import database.DatabaseHelper;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import parser.ParserThreadPool;
import seeker.PageProcessor;
import seeker.SeekerThreadPool;
import util.Logger;

/**
 *
 * @author mkaszlej
 */
public class ProgressFrame extends javax.swing.JFrame implements PropertyChangeListener {
    
    private Domain domain;
    private Task task;
    
    private final MainFrame parent;
    private DatabaseHelper dbConnection;
    private AddressFrame addressFrame;
    private final ProgressFrame instance = this;
    
    /**
     * Creates new form ProgressFrame
     */
    public ProgressFrame(MainFrame parentFrame, DatabaseHelper db_connection, Domain domain_to_process) {
        this.domain = domain_to_process;
        this.dbConnection = db_connection;
        this.parent = parentFrame;
        this.addressFrame = new AddressFrame(dbConnection, this);
        initComponents();
        myInit();
    }
    
    public void closingTime(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(false);
            }
        });
        if(parent != null) parent.closingTime();
        this.addressFrame = null;
    }
    
    private void myInit(){
        jProgressBar1.setMaximum(100);
        jProgressBar1.setMinimum(0);
    }

    public void updateStatus(){
       
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jTextArea1.setText("Przetwarzanie domeny:\n"+domain.getUrl()+"\n\nWątków (pracujących i oczekujących):\n"+SeekerThreadPool.counter.get() +" - poszukujących\n"+ParserThreadPool.counter.get()+" - parsujących\n\n"+ParserData.getBlobResultSize()+" - znalezionych adresów\n\n"+SeekerData.count());
            }
        });
		
    }
    
    public void start(DatabaseHelper dbConnection, Domain domain){
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        // Instances of javax.swing.SwingWorker are not reusuable, so
        // we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
        
        final JFrame instance = this;
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                instance.setVisible(true);
            }
        });

    }
    
    public synchronized void updateProgress(final String progress)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jTextArea2.append(progress+"\n");
            }
        });
    }

    public synchronized void logError(final String error)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jTextArea2.append(error+"\n");
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
          int progress = (Integer) evt.getNewValue();
          jProgressBar1.setValue(progress);
          updateStatus();
        }
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
    
    // --------- DEFINE TASK ------ //
    
    
  class Task extends SwingWorker<Void, String> {
    
      
    public Task() {
    }

    /*
     * Main task. Executed in background thread.
     */
    @Override
    public Void doInBackground() {
 
        setProgress(0);
        
        Link initialLink = new Link(domain);
        initSeeker();

        setProgress(5);
        publish("Crawler zainicjowany.\n------------\n");
        
        SeekerData.loadLinkList(dbConnection.getLinks(domain));
        //ParserData.loadAddressList(dbConnection.getAddresses(domain));

        setProgress(15);
        publish("Wczytano zawartość bazy danych dla domeny: "+domain.getUrl()+"\n------------\n");
                
        SeekerThreadPool.setMaxDepth(domain.getSearchDepth());
        
        SeekerThreadPool.execute(new PageProcessor(instance, initialLink));

        publish("Rozpoczęto przeszukiwanie.\n------------\n");
        
        while( 	SeekerThreadPool.counter.get() > 0 ){
            try{Thread.sleep(300);}catch(Exception e){}
            updateStatus();
        }

        setProgress(60);
        publish("Zakończono przeszukiwanie. Trwa parsowanie...\n------------\n");
        
        while( 	ParserThreadPool.counter.get() > 0 ){
            try{Thread.sleep(300);}catch(Exception e){}
            updateStatus();
        }
        
        publish("Parsowanie zakończone. Trwa aktualizacja bazy crawlera...\n------------\n");
                
        setProgress(80);
        
        updateDatabase();

        setProgress(100);
        publish("Zakonczono przetwarzanie domeny.\n------------\n" );
        
        return null;
    }



    private void initSeeker()
    {
        SeekerData.resetFinished();
        SeekerData.resetDomains();
        ParserData.resetProcessed();
        SeekerThreadPool.reset();
        ParserThreadPool.reset();
    }
    
    private void updateDatabase(){
        Logger.info("Updating database state...");
        dbConnection.insertLinksFromSet(SeekerData.getFinishedLinks());
        dbConnection.insertDomainsFromSet(SeekerData.getNewDomains());
        Logger.debug(SeekerData.count());
        Logger.info("Database state updated.");
        
    }
    
    /*
     * Executed in event dispatching thread
     */
    @Override
    protected void process(List<String> chunks) {
        super.process(chunks); //To change body of generated methods, choose Tools | Templates.
        for (String string : chunks) {
            jTextArea2.append( string );
        }
    }
    
    /*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
        setCursor(null); // turn off the wait cursor
        jTextArea1.append("Done!\n");
        showAddressFrame();
    }
  }
    
}
