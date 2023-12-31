
import javax.swing.DefaultListModel;
import java.sql.*;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author oguzy
 */
public class frmMain extends javax.swing.JFrame {

    /**
     * Creates new form frmMain
     */
    
    //String url = "jdbc://mysql:3306/tr-en";
    
    DefaultListModel listModel;
    BaseDatabaseManager dbManager;
    Connection conn;
    
    public frmMain() {
        initComponents();
        listModel = new DefaultListModel();
        
        lstTranslates.setModel(listModel);
        
        dbManager = new MySQLDatabaseManager();
        conn = dbManager.connect("root", "12345");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tren = new javax.swing.ButtonGroup();
        txtLetter = new javax.swing.JTextField();
        lblGiveMeLetter = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTranslates = new javax.swing.JList<>();
        rdEnglish = new javax.swing.JRadioButton();
        rdTurkish = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtLetter.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtLetter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLetterActionPerformed(evt);
            }
        });

        lblGiveMeLetter.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGiveMeLetter.setText("Give me letter:");

        lstTranslates.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lstTranslates.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstTranslates);

        tren.add(rdEnglish);
        rdEnglish.setSelected(true);
        rdEnglish.setText("English");

        tren.add(rdTurkish);
        rdTurkish.setText("Turkish");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblGiveMeLetter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLetter, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdEnglish, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdTurkish, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLetter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGiveMeLetter)
                    .addComponent(rdEnglish)
                    .addComponent(rdTurkish))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLetterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLetterActionPerformed
        // TODO add your handling code here:        
        String textFromInput = txtLetter.getText();
        
        if(textFromInput.length() >= 3){
            new Thread(new Runnable() {
                public void run() {
                //new frmMain().setVisible(true);
                
                // listModel.add(0, textFromInput);
                String[][] translate;
                if(rdEnglish.isSelected())
                    translate = dbManager.getTurkishWordFromEnglish(conn, textFromInput);
                else
                    translate = dbManager.getEnglishWordFromTurkish(conn, textFromInput);
                                
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        listModel.clear();
                        
                        if(translate == null)
                            return;
                        
                        for (int i = 0; i < translate[1].length; i++) {
                            listModel.add(i, translate[0][i] + " : " + translate[1][i]);
                        }
                        System.out.println("Bitti");
                    }
                });
                
                }
            }).start();          
            
        }
    }//GEN-LAST:event_txtLetterActionPerformed

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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGiveMeLetter;
    private javax.swing.JList<String> lstTranslates;
    private javax.swing.JRadioButton rdEnglish;
    private javax.swing.JRadioButton rdTurkish;
    private javax.swing.ButtonGroup tren;
    private javax.swing.JTextField txtLetter;
    // End of variables declaration//GEN-END:variables
}
