/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZCompany;

/**
 *
 * @author RazR
 */
public class HRManagerPage extends javax.swing.JFrame {

    /**
     * Creates new form HRManagerPage
     */
    public HRManagerPage() {
        initComponents();

        
    }
    public void UIDesign()
    {
        btnAddDetails.setBorderPainted(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        btnAddDetails = new javax.swing.JButton();
        btnEditEmployeeDetails = new javax.swing.JButton();
        btnEmployeeInformation = new javax.swing.JButton();
        btnPayrollInformation = new javax.swing.JButton();
        btnSalarySlip = new javax.swing.JButton();
        btnSystemLogout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        desktopPane.setBackground(new java.awt.Color(204, 204, 204));
        desktopPane.setOpaque(false);

        btnAddDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/AddEmployeeDetails.png"))); // NOI18N
        btnAddDetails.setToolTipText("This icon would redirect you to the Adding Employee Details form");
        btnAddDetails.setPreferredSize(new java.awt.Dimension(150, 150));
        btnAddDetails.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/AddEmployeeDetailsPress.png"))); // NOI18N
        btnAddDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDetailsActionPerformed(evt);
            }
        });

        btnEditEmployeeDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/EditEmployeeDetails.png"))); // NOI18N
        btnEditEmployeeDetails.setToolTipText("This icon would redirect you to the Editing Employee Details form");
        btnEditEmployeeDetails.setPreferredSize(new java.awt.Dimension(150, 150));
        btnEditEmployeeDetails.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/EditEmployeeDetailsPress.png"))); // NOI18N
        btnEditEmployeeDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditEmployeeDetailsActionPerformed(evt);
            }
        });

        btnEmployeeInformation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/EmployeeInformation.png"))); // NOI18N
        btnEmployeeInformation.setToolTipText("This icon would redirect you to the Employee Information Page");
        btnEmployeeInformation.setPreferredSize(new java.awt.Dimension(150, 150));
        btnEmployeeInformation.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/EmployeeInformationPress.png"))); // NOI18N
        btnEmployeeInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeInformationActionPerformed(evt);
            }
        });

        btnPayrollInformation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SalaryInformation.png"))); // NOI18N
        btnPayrollInformation.setToolTipText("This icon would redirect you to the Payroll Information Page");
        btnPayrollInformation.setPreferredSize(new java.awt.Dimension(150, 150));
        btnPayrollInformation.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SalaryInformationPress.png"))); // NOI18N
        btnPayrollInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayrollInformationActionPerformed(evt);
            }
        });

        btnSalarySlip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SalarySlip.png"))); // NOI18N
        btnSalarySlip.setToolTipText("This icon would redirect you to the Salary Slip Page");
        btnSalarySlip.setPreferredSize(new java.awt.Dimension(150, 150));
        btnSalarySlip.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SalarySlipPress.png"))); // NOI18N
        btnSalarySlip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalarySlipActionPerformed(evt);
            }
        });

        btnSystemLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SystemLogout.png"))); // NOI18N
        btnSystemLogout.setToolTipText("This icon would log you out of the Human Resources User Page");
        btnSystemLogout.setPreferredSize(new java.awt.Dimension(150, 150));
        btnSystemLogout.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SystemLogoutPress.png"))); // NOI18N
        btnSystemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSystemLogoutActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Sans", 1, 24)); // NOI18N
        jLabel2.setText("Human Resourses User Page");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmployeeInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditEmployeeDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPayrollInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSalarySlip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSystemLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, desktopPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(70, 70, 70)
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addComponent(btnSalarySlip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(btnSystemLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addComponent(btnAddDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(btnEmployeeInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addComponent(btnEditEmployeeDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(93, 93, 93)
                        .addComponent(btnPayrollInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        desktopPane.setLayer(btnAddDetails, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnEditEmployeeDetails, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnEmployeeInformation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnPayrollInformation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnSalarySlip, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(btnSystemLogout, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(desktopPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 760, 560));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/Systembackground.jpg"))); // NOI18N
        jLabel1.setFocusable(false);
        jLabel1.setRequestFocusEnabled(false);
        jLabel1.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDetailsActionPerformed
        AddEmployeeDetails AED=new AddEmployeeDetails();
        desktopPane.add(AED);
        AED.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnAddDetailsActionPerformed

    private void btnSystemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSystemLogoutActionPerformed
        Login Obj=new Login();    
        this.setVisible(false);                            
        Obj.setVisible(true);
        btnSystemLogout.setVisible(false);
    }//GEN-LAST:event_btnSystemLogoutActionPerformed

    private void btnEditEmployeeDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditEmployeeDetailsActionPerformed
        EditEmployeeDetails EID=new EditEmployeeDetails();
        desktopPane.add(EID);
        EID.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnEditEmployeeDetailsActionPerformed

    private void btnSalarySlipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalarySlipActionPerformed
        SalarySlip SS=new SalarySlip();
        desktopPane.add(SS);
        SS.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnSalarySlipActionPerformed

    private void btnEmployeeInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeInformationActionPerformed
        EmployeeInformation EI=new EmployeeInformation();
        desktopPane.add(EI);
        EI.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnEmployeeInformationActionPerformed

    private void btnPayrollInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayrollInformationActionPerformed
        PayrollInformation PI=new PayrollInformation();
        desktopPane.add(PI);
        PI.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnPayrollInformationActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HRManagerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HRManagerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HRManagerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HRManagerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HRManagerPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDetails;
    private javax.swing.JButton btnEditEmployeeDetails;
    private javax.swing.JButton btnEmployeeInformation;
    private javax.swing.JButton btnPayrollInformation;
    private javax.swing.JButton btnSalarySlip;
    private javax.swing.JButton btnSystemLogout;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
