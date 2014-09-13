/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import common.Address;
import common.Domain;
import database.DatabaseHelper;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;

/**
 *
 * @author mkaszlej
 */
public class ExporterFrame extends javax.swing.JFrame implements PropertyChangeListener {

    private database.DatabaseHelper dbConnection;
    private Domain domain;
    private Task task;
    private final MainFrame parent;
    private String path = null;
    
    /**
     * Creates new form ExporterFrame
     */
    public ExporterFrame(MainFrame parentFrame, database.DatabaseHelper dbConnection, Domain domain_to_export) {
        this.parent = parentFrame;
        this.domain = domain_to_export;
        this.dbConnection = dbConnection;
        initComponents();
        myInit();
    }
    
    private void myInit(){
        jButton1.addActionListener(new SaveL());
    }

    public void closingTime(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(false);
            }
        });
        if(parent != null) parent.closingTime();
    }
    
    public void update(final String status){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                jTextArea1.append(status);
            }
        });
		
    }
    
    private void start(DatabaseHelper dbConnection, Domain domain){
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        task = new ExporterFrame.Task();
        task.execute();
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
          int progress = (Integer) evt.getNewValue();
          jProgressBar1.setValue(progress);
        }
    } 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Plik do zapisu:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Zmień");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("Zapisz");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Zakończ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if( path != null ) 
            start(dbConnection, domain);
        else
            jTextArea1.append("\nProszę podać ścieżkę do zapisu");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        closingTime();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    class SaveL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Save" dialog:
            int rVal = c.showSaveDialog(ExporterFrame.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                path = c.getSelectedFile().getPath();

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        jTextField1.setText(path);
                    }
                });

            }
        }
    }
    

    class Task extends SwingWorker<Void, String> {
    

        public Task() {
        }

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            
            setProgress(0);
            publish("Rozpoczynam generowanie pliku .csv\n------\n");

            ArrayList<Address> addresses = dbConnection.getAddresses(domain);
            setProgress(5);
            publish("Wczytano "+addresses.size()+" z bazy dla domeny:"+domain.getUrl()+"\n------\n");
            
            PrintWriter writer;
            try {
                
                writer = new PrintWriter(path, "UTF-8");
                writer.println("GMINA|TERYT|INDEX|KATEGORIA|NAZWA|ULICA|NR_BUDYNKU|NR_MIESZKANIA|KOD POCZTOWY|MIASTO|TELEFON|EMAIL|ILOŚĆ_MIEJSC|ZATRUDNIENIE|FLAGA|GODZINY PRACY PN|GODZINY PRACY WT|GODZINY PRACY SR|GODZINY PRACY CZW|GODZINY PRACY PT|GODZINY PRACY SO|GODZINY PRACY ND|GODZINY PRACY PN_WAKACJE|GODZINY PRACY WT_WAKACJE|GODZINY PRACY SR_WAKACJE|GODZINY PRACY CZW_WAKACJE|GODZINY PRACY PT_WAKACJE|GODZINY PRACY SO_WAKACJE|GODZINY PRACY ND_WAKACJE|GODZINY PRACY SWIETO|FORMA_DZIALANOSCI|PODJAZD|WNETRZE|NIESLYSZACY|NIEWIDOMI|RODZIC_Z_DZIECKIEM|WINDY|TOALETY|INNE|KOMENTARZ|KOMENTARZ_KORESPONDENTA");
                int i=0, max = addresses.size();
                for (Address address : addresses) {
                    String out = address.getCity()+"|"+"BD"+"|"+address.getIndex()+"|"+address.getCategory()+"|"+address.getName()+"|"+address.getStreet()+"|"+address.getBuildingNo()+"|"+address.getApartamentNo()+"|"+address.getCityCode()+"|"+address.getCity()+"|"+address.getPhone()+"|"+address.getEmail()+"|"+"BD"+"|"+"BD"+"|"+address.getFlag()+"|"+address.getPn()+"|"+address.getWt()+"|"+address.getSr()+"|"+address.getCzw()+"|"+address.getPt()+"|"+address.getSo()+"|"+address.getNd()+"|"+address.getPn_wakacje()+"|"+address.getWt_wakacje()+"|"+address.getSr_wakacje()+"|"+address.getCzw_wakajce()+"|"+address.getPt_wakacje()+"|"+address.getSo_wakacje()+"|"+address.getNd_wakacje()+"|"+address.getSw()+"|"+address.getFormaDzialanosci()+"|"+address.getPodjazd()+"|"+address.getWnetrze()+"|"+address.getNieslyszacy()+"|"+address.getNiewidomi()+"|"+address.getRodzic_z_dzieckiem()+"|"+address.getWindy()+"|"+address.getToalety()+"|"+address.getInne()+"|"+address.getKomentarz()+"|"+address.getKomentarz_korespondenta();
                    writer.println(out);
                    publish("Eksport domeny: "+domain+" zakończono.");           
                    setProgress(Math.abs(i/max*100));
                }
                writer.close();
                
                setProgress(100);
                publish("\n------\nEksport zakończony. Zapisano do pliku:\n"+path);

            } catch (FileNotFoundException ex) {
                setProgress(100);
                publish("\n------\nBład - nie można stworzyć pliku:\n"+path);
            } catch (UnsupportedEncodingException ex) {
                setProgress(100);
                publish("\n------\nBład - błąd kodowania.\n");
            }

            return null;
        }

        /*
        * Executed in event dispatching thread
        */
       @Override
       protected void process(List<String> chunks) {
           super.process(chunks); //To change body of generated methods, choose Tools | Templates.
           for (String string : chunks) {
               jTextArea1.append( string+"\n" );
           }
       }

        @Override
        protected void done() {
            setCursor(null); // turn off the wait cursor
            super.done(); //To change body of generated methods, choose Tools | Templates.
        }
               
    }
     

}
