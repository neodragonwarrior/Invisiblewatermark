

package watermarking;

import javax.swing.JOptionPane;

public class select extends javax.swing.JFrame {
    
    /** Creates new form select */
    public select() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        embedbutton = new javax.swing.JRadioButton();
        retrievebutton = new javax.swing.JRadioButton();
        selectgo = new javax.swing.JButton();
        selectexit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EMBED/RETRIEVE");
        getContentPane().setLayout(null);

        buttonGroup1.add(embedbutton);
        embedbutton.setFont(new java.awt.Font("Times New Roman", 1, 24));
        embedbutton.setText("EMBED");
        getContentPane().add(embedbutton);
        embedbutton.setBounds(110, 70, 180, 37);

        buttonGroup1.add(retrievebutton);
        retrievebutton.setFont(new java.awt.Font("Times New Roman", 1, 24));
        retrievebutton.setText("RETRIEVE");
        getContentPane().add(retrievebutton);
        retrievebutton.setBounds(110, 130, 170, 37);

        selectgo.setText("GO");
        selectgo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectgoActionPerformed(evt);
            }
        });
        getContentPane().add(selectgo);
        selectgo.setBounds(203, 240, 80, 23);

        selectexit.setText("EXIT");
        selectexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectexitActionPerformed(evt);
            }
        });
        getContentPane().add(selectexit);
        selectexit.setBounds(295, 240, 80, 23);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-408)/2, (screenSize.height-333)/2, 408, 333);
    }// </editor-fold>//GEN-END:initComponents

    private void selectgoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectgoActionPerformed
       if(buttonGroup1.getSelection()==null) {
            JOptionPane.showMessageDialog(this,"please select any option");
        }
        
        
        if(embedbutton.isSelected()==true) {
            new base().setVisible(true);
            setVisible(false);
            
        } else if(retrievebutton.isSelected()==true){
            new retrieve().setVisible(true);
            setVisible(false);
        } // TODO add your handling code here:
    }//GEN-LAST:event_selectgoActionPerformed

    private void selectexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectexitActionPerformed
    dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_selectexitActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new select().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton embedbutton;
    private javax.swing.JRadioButton retrievebutton;
    private javax.swing.JButton selectexit;
    private javax.swing.JButton selectgo;
    // End of variables declaration//GEN-END:variables
    
}