/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZCompany;

import java.awt.event.MouseListener;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.Calendar;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author RazR
 */
public class DisplaySlip extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     * @param SlipID
     */
    Connection con;
    static String SlipID;
    int Year;
    public DisplaySlip(String ID) {
        initComponents();
        SlipID = ID;
        Year = Calendar.getInstance().get(Calendar.YEAR);
    }

    private void FrameUI()
    {
        setSize(600, 400);
        setTitle("Employee Salary Slip");
    }
    
    public void SetText()
    {
        if (SlipID.contains("F")) {
             String EmployeeID=null;
             try {
                String MonthLong;
                int OvertimeHours;
                double OvertimeRate;
                double OvertimePay;
                double Deductions;
                PreparedStatement stat = con.prepareStatement("SELECT * FROM FixedSalaryTable WHERE SalarySlipID = ?");
                stat.setString(1, SlipID);
                ResultSet rs = stat.executeQuery();
                while(rs.next())
                {
                    EmployeeID = rs.getString("EmployeeID").trim();
                    txtSalary.setText(String.valueOf(rs.getDouble("BasicSalary")).trim());
                    MonthLong = rs.getString("SalaryMonth").trim();
                    txtEPFDeduction.setText(String.valueOf(rs.getDouble("EPFDeduction")).trim());
                    OvertimeRate = rs.getDouble("OvertimeRate");
                    OvertimeHours = rs.getInt("OvertimeHours");
                    txtGross.setText(String.valueOf(rs.getDouble("GrossSalary")));
                    txtTax.setText(String.valueOf(rs.getDouble("TaxRate")));
                    txtNet.setText(String.valueOf(rs.getDouble("NetSalary")));
                    String MonthShort = MonthLong.substring(0, 3);
                    OvertimePay = OvertimeHours * OvertimeRate;
                    txtOvertime.setText(String.valueOf(OvertimePay));
                    txtMonth.setText(MonthShort);
                    txtYear.setText(String.valueOf(Year));
                    txtEmpID.setText(EmployeeID);
                    double Tax = Double.valueOf(txtTax.getText());
                    double EPF = Double.valueOf(txtEPFDeduction.getText());
                    Deductions = Tax + EPF;
                    txtDeductions.setText(String.valueOf(Deductions));
                }
            } catch (SQLException s) {

            }
             
             try
             {
                 PreparedStatement stat = con.prepareStatement("SELECT * FROM EmployeeTable WHERE EmployeeID = ?");
                stat.setString(1, EmployeeID);
                ResultSet rs = stat.executeQuery();
                while(rs.next())
                {
                    String FullName = rs.getString("EmployeeFirstName").trim() + " " + rs.getString("EmployeeLastName").trim();
                    txtDep.setText(rs.getString("Department").trim());
                    txtJoin.setText(rs.getString("DateOfJoining").trim());
                    txtDesignation.setText(rs.getString("WorkingPosition").trim());
                    txtEPF.setText(rs.getString("EPFNumber").trim());
                    txtAccount.setText(rs.getString("BankAccountNumber").trim());
                    txtType.setText(rs.getString("SalaryType").trim());
                    txtName.setText(FullName);
                }
             }
             catch(SQLException e)
             {
                 
             }
        }
        
        if (SlipID.contains("H"))
        {
            String EmployeeID=null;
             try {
                String MonthLong;
                int Hours;
                double Rate;
                int OvertimeHours;
                double OvertimeRate;
                double OvertimePay;
                double Deductions;
                PreparedStatement stat = con.prepareStatement("SELECT * FROM HourlySalaryTable WHERE SalarySlipID = ?");
                stat.setString(1, SlipID);
                ResultSet rs = stat.executeQuery();
                while(rs.next())
                {
                    EmployeeID = rs.getString("EmployeeID").trim();
                    Hours = rs.getInt("HoursWorked");
                    Rate = rs.getDouble("HourlyRate");
                    MonthLong = rs.getString("SalaryMonth").trim();
                    txtEPFDeduction.setText(String.valueOf(rs.getDouble("EPFDeduction")));
                    OvertimeRate = rs.getDouble("OvertimeRate");
                    OvertimeHours = rs.getInt("OvertimeHours");
                    txtGross.setText(String.valueOf(rs.getDouble("GrossSalary")));
                    txtTax.setText(String.valueOf(rs.getDouble("TaxRate")));
                    txtNet.setText(String.valueOf(rs.getDouble("NetSalary")));
                    OvertimePay = OvertimeHours * OvertimeRate;
                    txtOvertime.setText(String.valueOf(OvertimePay));
                    String MonthShort = MonthLong.substring(0, 3);
                    txtMonth.setText(MonthShort);
                    txtYear.setText(String.valueOf(Year));
                    double Salary = Hours * Rate;
                    txtSalary.setText(String.valueOf(Salary));
                    txtEmpID.setText(EmployeeID);
                    double Tax = Double.valueOf(txtTax.getText());
                    double EPF = Double.valueOf(txtEPFDeduction.getText());
                    Deductions = Tax + EPF;
                    txtDeductions.setText(String.valueOf(Deductions));
                }
            } catch (SQLException s) {

            }
             
              try
             {
                 PreparedStatement stat = con.prepareStatement("SELECT * FROM EmployeeTable WHERE EmployeeID = ?");
                stat.setString(1, EmployeeID);
                ResultSet rs = stat.executeQuery();
                while(rs.next())
                {
                    String FullName = rs.getString("EmployeeFirstName").trim() + " " + rs.getString("EmployeeLastName").trim();
                    txtDep.setText(rs.getString("Department").trim());
                    txtJoin.setText(rs.getString("DateOfJoining").trim());
                    txtDesignation.setText(rs.getString("WorkingPosition").trim());
                    txtEPF.setText(rs.getString("EPFNumber").trim());
                    txtAccount.setText(rs.getString("BankAccountNumber").trim());
                    txtType.setText(rs.getString("SalaryType").trim());
                    txtName.setText(FullName);
                }
             }
             catch(SQLException e)
             {
                 
             }
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

        jLabel2 = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        txtMonth = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtEPF = new javax.swing.JTextField();
        txtEmpID = new javax.swing.JTextField();
        txtJoin = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtDesignation = new javax.swing.JTextField();
        txtType = new javax.swing.JTextField();
        txtAccount = new javax.swing.JTextField();
        txtTax = new javax.swing.JTextField();
        txtDep = new javax.swing.JTextField();
        txtNet = new javax.swing.JTextField();
        txtSalary = new javax.swing.JTextField();
        txtOvertime = new javax.swing.JTextField();
        txtGross = new javax.swing.JTextField();
        txtDeductions = new javax.swing.JTextField();
        txtEPFDeduction = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Pay Slip for the period");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, 20));

        txtYear.setEditable(false);
        txtYear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtYear.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtYear.setOpaque(false);
        getContentPane().add(txtYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, 30, 40));

        txtMonth.setEditable(false);
        txtMonth.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMonth.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtMonth.setName(""); // NOI18N
        txtMonth.setOpaque(false);
        getContentPane().add(txtMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 30, 40));

        jLabel3.setText("Employee ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        jLabel4.setText("Department");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        jLabel5.setText("Name");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 30, 20));

        jLabel6.setText("Designation");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 60, 20));

        jLabel7.setText("Earnings");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel8.setText("Amount");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, -1, -1));

        jLabel9.setText("Deductions");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, -1, -1));

        jLabel10.setText("Amount");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200, -1, -1));

        jLabel11.setText("Provident Fund");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, -1, 20));

        jLabel12.setText("Basic Pay");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 20));

        jLabel13.setText("Government Tax");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, -1, 20));

        jLabel14.setText("Overtime Pay");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, 20));

        jLabel15.setText("Account Number");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 80, 20));

        jLabel16.setText("EPF Number");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 20));

        jLabel17.setText("Date Of Joining");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, 20));

        jLabel18.setText("Contract Type");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 70, 20));

        jLabel19.setText("Total Earnings");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jLabel20.setText("Total Deductions");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, -1, -1));

        jLabel21.setText("Net Pay");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, -1, -1));

        txtEPF.setEditable(false);
        txtEPF.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        txtEPF.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtEPF.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtEPF.setOpaque(false);
        getContentPane().add(txtEPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 120, 20));

        txtEmpID.setEditable(false);
        txtEmpID.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtEmpID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtEmpID.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtEmpID.setOpaque(false);
        getContentPane().add(txtEmpID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 110, 20));

        txtJoin.setEditable(false);
        txtJoin.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtJoin.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtJoin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtJoin.setOpaque(false);
        getContentPane().add(txtJoin, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 100, 20));

        txtName.setEditable(false);
        txtName.setFont(new java.awt.Font("Arial Narrow", 0, 14)); // NOI18N
        txtName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtName.setOpaque(false);
        getContentPane().add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 150, 20));

        txtDesignation.setEditable(false);
        txtDesignation.setFont(new java.awt.Font("Arial Narrow", 0, 14)); // NOI18N
        txtDesignation.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtDesignation.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDesignation.setOpaque(false);
        getContentPane().add(txtDesignation, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 140, 20));

        txtType.setEditable(false);
        txtType.setFont(new java.awt.Font("Arial Narrow", 0, 12)); // NOI18N
        txtType.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtType.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtType.setOpaque(false);
        getContentPane().add(txtType, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 120, 20));

        txtAccount.setEditable(false);
        txtAccount.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtAccount.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtAccount.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtAccount.setOpaque(false);
        getContentPane().add(txtAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 120, 20));

        txtTax.setEditable(false);
        txtTax.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTax.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtTax.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTax.setOpaque(false);
        getContentPane().add(txtTax, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 90, 20));

        txtDep.setEditable(false);
        txtDep.setFont(new java.awt.Font("Arial Narrow", 0, 13)); // NOI18N
        txtDep.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtDep.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDep.setOpaque(false);
        getContentPane().add(txtDep, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 120, 20));

        txtNet.setEditable(false);
        txtNet.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNet.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtNet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNet.setOpaque(false);
        getContentPane().add(txtNet, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, 90, 20));

        txtSalary.setEditable(false);
        txtSalary.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtSalary.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtSalary.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtSalary.setOpaque(false);
        getContentPane().add(txtSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 90, 20));

        txtOvertime.setEditable(false);
        txtOvertime.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtOvertime.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtOvertime.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtOvertime.setOpaque(false);
        getContentPane().add(txtOvertime, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 90, 20));

        txtGross.setEditable(false);
        txtGross.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtGross.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtGross.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtGross.setOpaque(false);
        getContentPane().add(txtGross, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 90, 20));

        txtDeductions.setEditable(false);
        txtDeductions.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDeductions.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtDeductions.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtDeductions.setOpaque(false);
        getContentPane().add(txtDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, 90, 20));

        txtEPFDeduction.setEditable(false);
        txtEPFDeduction.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        txtEPFDeduction.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtEPFDeduction.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtEPFDeduction.setOpaque(false);
        getContentPane().add(txtEPFDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, 120, 20));

        jLabel1.setFont(new java.awt.Font("Arial Narrow", 0, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SlipDisplayBackground.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        try {
            setTitle("Salary Slip Form");
            DBConnection objDB = new DBConnection();
            con = objDB.getConnection();
        } catch (Exception exDB) {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: " + exDB, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        SetText();
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(DisplaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplaySlip.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplaySlip(SlipID).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtAccount;
    private javax.swing.JTextField txtDeductions;
    private javax.swing.JTextField txtDep;
    private javax.swing.JTextField txtDesignation;
    private javax.swing.JTextField txtEPF;
    private javax.swing.JTextField txtEPFDeduction;
    private javax.swing.JTextField txtEmpID;
    private javax.swing.JTextField txtGross;
    private javax.swing.JTextField txtJoin;
    private javax.swing.JTextField txtMonth;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNet;
    private javax.swing.JTextField txtOvertime;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtTax;
    private javax.swing.JTextField txtType;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
