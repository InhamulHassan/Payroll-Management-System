/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XYZCompany;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {

    Connection con;

    public Login() {
        initComponents();
        txtUsername.requestFocus();
        txtUsername.addActionListener(new EnterAction());
        txtPassword.addActionListener(new EnterAction());
        txtUsername.setToolTipText("Enter the username in this field");
        txtPassword.setToolTipText("Enter the password in this field");
    }

    public class EnterAction implements ActionListener {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            System.out.println("Enter Pressed");
            btnLoginMouseClicked(null);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnLogin = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setOpaque(false);

        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/LoginButton.png"))); // NOI18N
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });

        txtUsername.setFont(new java.awt.Font("Century", 0, 18)); // NOI18N
        txtUsername.setToolTipText("");
        txtUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsername.setName(""); // NOI18N
        txtUsername.setOpaque(false);
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });

        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtPassword.setOpaque(false);
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(btnLogin)
                .addGap(0, 320, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogin)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 170, 760, 370);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/backgroundwithbutton.png"))); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(0, 0, 800, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            setTitle("XYZ Salary Management System Login Page");
            setLayout(null);
            setVisible(true);
            DBConnection objDB = new DBConnection();
            con = objDB.getConnection();

        } catch (Exception exDB) {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: " + exDB, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_formWindowOpened

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked

        String User = txtUsername.getText();
        char[] PassChar = txtPassword.getPassword();
        String Pass = new String(PassChar);

        if (User.isEmpty() && Pass.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Username and Password field is empty", "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else if (User.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Username field is empty", "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else if (Pass.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Password field is empty", "Empty Field", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                PreparedStatement stat = con.prepareStatement("select * from Usertable where Username=?");
                stat.setString(1, User);
                ResultSet rs = stat.executeQuery();
                if (rs.next()) {
                    String PasswordDB = rs.getString(2).trim();
                    String UserTypeDB = rs.getString(3).trim();

                    if ((Pass.equals(PasswordDB))) {
                        switch (UserTypeDB) {
                            case "User": {
                                JOptionPane.showMessageDialog(rootPane, "Login Successful\nWelcome to the XYZ Salary Management System, " + UserTypeDB, UserTypeDB + " Login Success", JOptionPane.PLAIN_MESSAGE);
                                HRManagerPage Obj1 = new HRManagerPage();
                                Obj1.setVisible(true);
                                this.dispose();
                                txtUsername.setText(null);
                                txtPassword.setText(null);
                                break;
                            }
                            case "Administrator": {

                                JOptionPane.showMessageDialog(rootPane, "Login Successful\nWelcome to the XYZ Salary Management System, " + UserTypeDB, UserTypeDB + " Login Success", JOptionPane.PLAIN_MESSAGE);
                                SystemAdminPage Obj = new SystemAdminPage();
                                Obj.setVisible(true);
                                this.dispose();
                                txtUsername.setText(null);
                                txtPassword.setText(null);
                                break;
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Login Failed, Incorrect Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                        txtPassword.setText(null);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Login Failed, Incorrect Username", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    txtUsername.setText(null);
                    txtPassword.setText(null);
                }
            } catch (Exception ex1) {
                JOptionPane.showMessageDialog(rootPane, "Exception error occured during login process\nError: " + ex1, "User Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnLoginMouseClicked

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        Color color = new Color(245, 246, 247);
        txtUsername.setBackground(color);
        txtUsername.setOpaque(true);
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        Color color = new Color(245, 246, 247);
        txtPassword.setBackground(color);
        txtPassword.setOpaque(true);
    }//GEN-LAST:event_txtPasswordKeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnLogin;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
