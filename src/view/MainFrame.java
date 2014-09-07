/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import common.Domain;
import database.DatabaseHelper;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import parser.ParserThreadPool;
import seeker.SeekerApp;
import seeker.SeekerThreadPool;

/**
 *
 * @author mkaszlej
 */
public class MainFrame extends javax.swing.JFrame {

    private static DatabaseHelper dbConnection;
    private static ArrayList<Domain> domainList;
    private Domain selectedDomain = null;
    private boolean manual = true;
    private static MainFrame instance;
    /**
     * Creates new form MainFrame
     */
    public MainFrame(DatabaseHelper dbConnection) {
        this.dbConnection = dbConnection;
        domainList = dbConnection.getDomains();
        
        initComponents();
        myInitComponents();
    }
    
    public void resetFields()
    {
        
    }
    
    private void myInitComponents(){
        
        generateTree();
        
        jTree1.addTreeSelectionListener(new TreeSelectionListener() { 
            @Override 
            public void valueChanged(TreeSelectionEvent e) 
            { 
                manual = false;
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
                if(selectedNode.getUserObject() instanceof String)
                {
                    resetFields();
                    selectedDomain = null;
                    return;
                }
                Domain domain = (Domain)selectedNode.getUserObject();
                jTextField1.setText(domain.getUrl().toString());
                jTextField2.setText(domain.getSearchDepth()+"");
                jLabel4.setText(domain.getMeta());
                selectedDomain = domain;
            }

        });
        
        jTextField1.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
             }

            @Override
            public void keyPressed(KeyEvent e) {
                manual = true;
                jLabel4.setText("Wprowadzono nową domenę.");
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void generateTree(){
        
        HashMap<String, DefaultMutableTreeNode> hierarchyProcessed = new HashMap<String, DefaultMutableTreeNode>();
        HashMap<String, DefaultMutableTreeNode> hierarchyNotProcessed = new HashMap<String, DefaultMutableTreeNode>();
                
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Domeny");
        DefaultMutableTreeNode przetworzone = new DefaultMutableTreeNode("Przetworzone");
        DefaultMutableTreeNode nowe = new DefaultMutableTreeNode("Nowe");
        DefaultMutableTreeNode recznieNowe = new DefaultMutableTreeNode("Wprowadzone ręcznie");
        DefaultMutableTreeNode reczniePrzetworzone = new DefaultMutableTreeNode("Wprowadzone ręcznie");
        root.add(przetworzone);
        root.add(nowe);
        przetworzone.add(reczniePrzetworzone);
        nowe.add(recznieNowe);
               
        
        for (Domain domain : domainList) {
            String parent = domain.getParent_url().toString();
            
            if(domain.isProcessed()){
                if(parent == null){
                    //TOP level domain
                    DefaultMutableTreeNode domainNode = new DefaultMutableTreeNode(domain);
                    reczniePrzetworzone.add(domainNode);
                }
                else{
                    if(hierarchyProcessed.get(parent) == null) {
                        DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode("Z domeny - "+parent);
                        przetworzone.add(parentNode);
                        hierarchyProcessed.put(parent, parentNode);
                    }
                    hierarchyProcessed.get(parent).add(new DefaultMutableTreeNode(domain));  
                }
            }
            else{
                if(parent == null){
                    //TOP level domain
                    DefaultMutableTreeNode domainNode = new DefaultMutableTreeNode(domain);
                    recznieNowe.add(domainNode);
                }
                else{
                    if(hierarchyNotProcessed.get(parent) == null) {
                        DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode("Z domeny - "+parent);
                        nowe.add(parentNode);
                        hierarchyNotProcessed.put(parent, parentNode);
                    }
                    hierarchyNotProcessed.get(parent).add(new DefaultMutableTreeNode(domain));   
                }                
            }

        }
        
        jTree1 = new javax.swing.JTree(root);
        jTree1.setRootVisible(false);
        jScrollPane2.setViewportView(jTree1);
        
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
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("URL:");

        jTextField1.setText("http://www.rozan.eur.pl/");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("POZIOM:");

        jTextField2.setText("1");

        jLabel3.setText("INFORMACJE:");

        jButton1.setText("Przetworz");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
            .addComponent(jTextField2)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        SeekerApp seeker;
        if(manual) //Wprowadzono ręcznie
        {
            instance.setVisible(false);
            new Thread(new FlowControlThread(dbConnection, jTextField1.getText(), Integer.parseInt(jTextField2.getText() ))).start();
        }
        else if(selectedDomain != null){
            instance.setVisible(false);
            selectedDomain.setSearchDepth(Integer.parseInt(jTextField2.getText()));
            new Thread(new FlowControlThread(dbConnection, selectedDomain)).start();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        final DatabaseHelper dbConnection = new DatabaseHelper();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
               
                instance = new MainFrame(dbConnection);
                instance.setVisible(true);
                
            }
        });
    }

    private javax.swing.JTree jTree1;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
