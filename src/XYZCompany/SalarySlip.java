 /*
 * To change this license header, choose License Headers in Project Properties.   
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZCompany;

import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author RazR
 */
public class SalarySlip extends javax.swing.JInternalFrame {

    Connection con;
    String SelectedEmpID;
    
    public SalarySlip() {
        initComponents();
        FrameUI();
    }

    private void FrameUI()
    {
        setSize(760, 550);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setTitle("Salary Slip");
        setClosable(true);
        BasicInternalFrameUI basicInternalFrameUI = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI());
        for (MouseListener listener : basicInternalFrameUI.getNorthPane().getMouseListeners()) 
        {
            basicInternalFrameUI.getNorthPane().removeMouseListener(listener);
        }
    }
    
    public void FixedSalary()
    {
        SelectedEmpID = cmbEmployeeID.getSelectedItem().toString();
        double BasicSalary=0;
        double OvertimeRateFixed=0;
        double TaxRateFixed=0;
        double EPFFixed=0;
        double EPFDeductionFixed=0;
        double ETFFixed=0;
        try
        {
            PreparedStatement st1 = con.prepareStatement("SELECT * FROM FixedSalaryTable where EmployeeID=?");
            st1.setString(1, SelectedEmpID);
            ResultSet res1 = st1.executeQuery();
            while (res1.next()) {
                BasicSalary = res1.getDouble("BasicSalary");
                OvertimeRateFixed = res1.getDouble("OvertimeRate");
                TaxRateFixed = res1.getDouble("TaxRate");
                if(OvertimeRateFixed==0.00)
                {
                    OvertimeFixedPanel.setVisible(false);
                    OvertimeDefaultPanel.setVisible(true);
                }
                else
                {
                    OvertimeFixedPanel.setVisible(true);
                    OvertimeDefaultPanel.setVisible(false);
                }
                txtBasicSalary.setText(String.valueOf(BasicSalary));
                txtOvertimeRateFixed.setText(String.valueOf(OvertimeRateFixed));
                txtTaxDeductionFixed.setText(String.valueOf(TaxRateFixed));
                EPFFixed = (int) (BasicSalary * (12.00 / 100.00));
                txtEPFFixed.setText(String.valueOf(EPFFixed));
                EPFDeductionFixed = BasicSalary * (8.00 / 100.00);
                txtEPFDeductionFixed.setText(String.valueOf(EPFDeductionFixed));
                ETFFixed = BasicSalary * (3.00 / 100.00);
                txtETFFixed.setText(String.valueOf(ETFFixed));
        
            }
        }
        catch(Exception exDB)
        {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during Fixed Salary Generation\nError: "+exDB);
        }
    }
    
    public void HourlySalary()
    {
        SelectedEmpID = cmbEmployeeID.getSelectedItem().toString();
        double HourlyRate=0;
        double OvertimeRateHourly=0;
        double TaxRateHourly=0;
        double EPFHourly=0;
        double EPFDeductionHourly=0;
        double ETFHourly=0;
        try
        {
            PreparedStatement st2 = con.prepareStatement("SELECT * FROM HourlySalaryTable where EmployeeID=?");
                    st2.setString(1, SelectedEmpID);
                    ResultSet res2 = st2.executeQuery();
                    while (res2.next()) {
                        HourlyRate = res2.getDouble("HourlyRate");
                        OvertimeRateHourly = res2.getDouble("OvertimeRate");
                        TaxRateHourly = res2.getDouble("TaxRate");
                        
                        if(OvertimeRateHourly == 0.00)
                        {
                            OvertimeHourlyPanel.setVisible(false);
                            OvertimeDefaultHourly.setVisible(true);
                        }
                        else
                        {
                            OvertimeHourlyPanel.setVisible(true);
                            OvertimeDefaultHourly.setVisible(false);
                        }
                        txtHourlyRate.setText(String.valueOf(HourlyRate));
                        txtOvertimeRateHourly.setText(String.valueOf(OvertimeRateHourly));
                        txtTaxDeductionHourly.setText(String.valueOf(TaxRateHourly));
                        EPFHourly = HourlyRate * (12.00 / 100.00);
                        txtEPFHourly.setText(String.valueOf(EPFHourly));
                        EPFDeductionHourly = HourlyRate * (8.00 / 100.00);
                        txtEPFDeductionHourly.setText(String.valueOf(EPFDeductionHourly));
                        ETFHourly = HourlyRate * (3.00 / 100.00);
                        txtETFHourly.setText(String.valueOf(ETFHourly));
                    }
        }
        catch(Exception exDB)
        {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during Hourly Salary Generation\nError: "+exDB);
        }
    }
    
    
    public void SalaryInfoGeneration(String SalaryType)
    {
        String SalarySlipID;
        
        try {
            switch (SalaryType) {
                
                case "Fixed":
                    PreparedStatement stat1 = con.prepareStatement("SELECT SalarySlipID FROM FixedSalaryTable WHERE EmployeeID=?");
                    stat1.setString(1, cmbEmployeeID.getSelectedItem().toString());
                    ResultSet rs1 = stat1.executeQuery();
                    while (rs1.next()) {
                        SalarySlipID = rs1.getString("SalarySlipID").trim();
                        txtSalaryID.setText(SalarySlipID);
                        FixedSalaryPanel.setVisible(true);
                        HourlySalaryPanel.setVisible(false);
                        DefaultPanel.setVisible(false);
                        FixedSalary();
                    }
                    break;
                    
                    
                case "Hourly":
                    PreparedStatement stat2 = con.prepareStatement("SELECT SalarySlipID FROM HourlySalaryTable WHERE EmployeeID=?");
                    stat2.setString(1, cmbEmployeeID.getSelectedItem().toString());
                    ResultSet rs2 = stat2.executeQuery();
                    while (rs2.next()) {
                        SalarySlipID = rs2.getString("SalarySlipID").trim();
                        txtSalaryID.setText(SalarySlipID);
                        HourlySalaryPanel.setVisible(true);
                        FixedSalaryPanel.setVisible(false);
                        DefaultPanel.setVisible(false);
                        HourlySalary();
                    }
                    break;
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(rootPane, "An error occured during Salary Information Generation\n"+ex);
        }
        catch (NullPointerException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void EmployeeNameDatabase()
    {
        try
        {
            Statement st = con.createStatement();
            ResultSet rs=st.executeQuery("SELECT EmployeeID FROM EmployeeTable");

 
            while (rs.next()) 
            {  
                cmbEmployeeID.addItem(rs.getString("EmployeeID").trim());
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(rootPane,"Exception error occured during Employee ID Generation\nError: "+e,"Database Connection Error", JOptionPane.WARNING_MESSAGE);
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

        jSpinner1 = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        cmbEmployeeID = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSalaryID = new javax.swing.JTextField();
        parentPanel = new javax.swing.JPanel();
        DefaultPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        HourlySalaryPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        parentHourlyOvertime = new javax.swing.JPanel();
        OvertimeDefaultHourly = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        OvertimeHourlyPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtOvertimeRateHourly = new javax.swing.JTextField();
        txtOvertimeHoursHourly = new javax.swing.JTextField();
        HourlyDeductionsPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTaxDeductionHourly = new javax.swing.JTextField();
        txtETFHourly = new javax.swing.JTextField();
        txtEPFDeductionHourly = new javax.swing.JTextField();
        txtEPFHourly = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbMonthHourly = new javax.swing.JComboBox();
        txtHoursWorked = new javax.swing.JTextField();
        txtHourlyRate = new javax.swing.JTextField();
        txtGrossSalaryHourly = new javax.swing.JTextField();
        txtNetSalaryHourly = new javax.swing.JTextField();
        FixedSalaryPanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        parenOvertimetPanel = new javax.swing.JPanel();
        OvertimeFixedPanel = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtOvertimeRateFixed = new javax.swing.JTextField();
        txtOvertimeHoursFixed = new javax.swing.JTextField();
        OvertimeDefaultPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        FixedDeductionsPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtTaxDeductionFixed = new javax.swing.JTextField();
        txtETFFixed = new javax.swing.JTextField();
        txtEPFDeductionFixed = new javax.swing.JTextField();
        txtEPFFixed = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cmbMonthFixed = new javax.swing.JComboBox();
        txtBasicSalary = new javax.swing.JTextField();
        txtGrossSalaryFixed = new javax.swing.JTextField();
        txtNetSalaryFixed = new javax.swing.JTextField();
        btnGenerateSlip = new javax.swing.JButton();

        setClosable(true);
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SalarySlipIcon.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 550));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Salary Slip Information"));

        cmbEmployeeID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cmbEmployeeID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select employee ID>" }));
        cmbEmployeeID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeIDActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Employee ID");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Salary ID");

        txtSalaryID.setEditable(false);
        txtSalaryID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSalaryID, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtSalaryID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        parentPanel.setLayout(new java.awt.CardLayout());

        DefaultPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Salary Information"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Please select an Employee ID to display the relevant salary information field");

        javax.swing.GroupLayout DefaultPanelLayout = new javax.swing.GroupLayout(DefaultPanel);
        DefaultPanel.setLayout(DefaultPanelLayout);
        DefaultPanelLayout.setHorizontalGroup(
            DefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DefaultPanelLayout.createSequentialGroup()
                .addContainerGap(156, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap(156, Short.MAX_VALUE))
        );
        DefaultPanelLayout.setVerticalGroup(
            DefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DefaultPanelLayout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        parentPanel.add(DefaultPanel, "card2");

        HourlySalaryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Hourly Salary Information"));

        jLabel4.setText("Hours Worked");

        jLabel5.setText("Hourly Rate");

        jLabel6.setText("Salary Month");

        parentHourlyOvertime.setLayout(new java.awt.CardLayout());

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel26.setText("This employee is not eligible for Overtime Pay");

        javax.swing.GroupLayout OvertimeDefaultHourlyLayout = new javax.swing.GroupLayout(OvertimeDefaultHourly);
        OvertimeDefaultHourly.setLayout(OvertimeDefaultHourlyLayout);
        OvertimeDefaultHourlyLayout.setHorizontalGroup(
            OvertimeDefaultHourlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeDefaultHourlyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        OvertimeDefaultHourlyLayout.setVerticalGroup(
            OvertimeDefaultHourlyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeDefaultHourlyLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel26)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        parentHourlyOvertime.add(OvertimeDefaultHourly, "card3");

        OvertimeHourlyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Overtime Information"));

        jLabel10.setText("Overtime Rate");

        jLabel11.setText("Overtime Hours");

        txtOvertimeRateHourly.setEditable(false);
        txtOvertimeRateHourly.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtOvertimeHoursHourly.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtOvertimeHoursHourly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOvertimeHoursHourlyActionPerformed(evt);
            }
        });
        txtOvertimeHoursHourly.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOvertimeHoursHourlyKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout OvertimeHourlyPanelLayout = new javax.swing.GroupLayout(OvertimeHourlyPanel);
        OvertimeHourlyPanel.setLayout(OvertimeHourlyPanelLayout);
        OvertimeHourlyPanelLayout.setHorizontalGroup(
            OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeHourlyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OvertimeHourlyPanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtOvertimeRateHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OvertimeHourlyPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                        .addComponent(txtOvertimeHoursHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        OvertimeHourlyPanelLayout.setVerticalGroup(
            OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeHourlyPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtOvertimeRateHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtOvertimeHoursHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        parentHourlyOvertime.add(OvertimeHourlyPanel, "card2");

        HourlyDeductionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Deductions"));

        jLabel7.setText("EPF Deduction");

        jLabel8.setText("EPF");

        jLabel9.setText("ETF");

        jLabel12.setText("Tax Deduction");

        txtTaxDeductionHourly.setEditable(false);
        txtTaxDeductionHourly.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtETFHourly.setEditable(false);
        txtETFHourly.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtEPFDeductionHourly.setEditable(false);
        txtEPFDeductionHourly.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtEPFHourly.setEditable(false);
        txtEPFHourly.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        javax.swing.GroupLayout HourlyDeductionsPanelLayout = new javax.swing.GroupLayout(HourlyDeductionsPanel);
        HourlyDeductionsPanel.setLayout(HourlyDeductionsPanelLayout);
        HourlyDeductionsPanelLayout.setHorizontalGroup(
            HourlyDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HourlyDeductionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HourlyDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HourlyDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                        .addComponent(txtTaxDeductionHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HourlyDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtEPFHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HourlyDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtETFHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HourlyDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtEPFDeductionHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        HourlyDeductionsPanelLayout.setVerticalGroup(
            HourlyDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HourlyDeductionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HourlyDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEPFDeductionHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HourlyDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEPFHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HourlyDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtETFHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HourlyDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTaxDeductionHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Gross Salary");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Net Salary");

        cmbMonthHourly.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        txtHoursWorked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoursWorkedActionPerformed(evt);
            }
        });
        txtHoursWorked.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoursWorkedKeyTyped(evt);
            }
        });

        txtHourlyRate.setEditable(false);

        txtGrossSalaryHourly.setEditable(false);

        txtNetSalaryHourly.setEditable(false);

        javax.swing.GroupLayout HourlySalaryPanelLayout = new javax.swing.GroupLayout(HourlySalaryPanel);
        HourlySalaryPanel.setLayout(HourlySalaryPanelLayout);
        HourlySalaryPanelLayout.setHorizontalGroup(
            HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HourlySalaryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, HourlySalaryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbMonthHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, HourlySalaryPanelLayout.createSequentialGroup()
                            .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addGap(18, 18, 18)
                            .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtHoursWorked, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(txtGrossSalaryHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNetSalaryHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(HourlyDeductionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HourlySalaryPanelLayout.createSequentialGroup()
                    .addContainerGap(336, Short.MAX_VALUE)
                    .addComponent(parentHourlyOvertime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        HourlySalaryPanelLayout.setVerticalGroup(
            HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbMonthHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtHoursWorked, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)))
                    .addComponent(HourlyDeductionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGrossSalaryHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNetSalaryHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
            .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HourlySalaryPanelLayout.createSequentialGroup()
                    .addContainerGap(182, Short.MAX_VALUE)
                    .addComponent(parentHourlyOvertime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        parentPanel.add(HourlySalaryPanel, "card2");

        FixedSalaryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Fixed Salary Information"));

        jLabel16.setText("Salary Month");

        jLabel17.setText("Basic Salary");

        parenOvertimetPanel.setLayout(new java.awt.CardLayout());

        OvertimeFixedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Overtime Information"));

        jLabel22.setText("Overtime Rate");

        jLabel23.setText("Overtime Hours");

        txtOvertimeRateFixed.setEditable(false);
        txtOvertimeRateFixed.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtOvertimeHoursFixed.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtOvertimeHoursFixed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOvertimeHoursFixedActionPerformed(evt);
            }
        });
        txtOvertimeHoursFixed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOvertimeHoursFixedKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout OvertimeFixedPanelLayout = new javax.swing.GroupLayout(OvertimeFixedPanel);
        OvertimeFixedPanel.setLayout(OvertimeFixedPanelLayout);
        OvertimeFixedPanelLayout.setHorizontalGroup(
            OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeFixedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OvertimeFixedPanelLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtOvertimeRateFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(OvertimeFixedPanelLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                        .addComponent(txtOvertimeHoursFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        OvertimeFixedPanelLayout.setVerticalGroup(
            OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeFixedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtOvertimeRateFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtOvertimeHoursFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        parenOvertimetPanel.add(OvertimeFixedPanel, "card2");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel15.setText("This employee is not eligible for Overtime Pay");

        javax.swing.GroupLayout OvertimeDefaultPanelLayout = new javax.swing.GroupLayout(OvertimeDefaultPanel);
        OvertimeDefaultPanel.setLayout(OvertimeDefaultPanelLayout);
        OvertimeDefaultPanelLayout.setHorizontalGroup(
            OvertimeDefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeDefaultPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel15)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        OvertimeDefaultPanelLayout.setVerticalGroup(
            OvertimeDefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeDefaultPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel15)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        parenOvertimetPanel.add(OvertimeDefaultPanel, "card3");

        FixedDeductionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Deductions"));

        jLabel18.setText("EPF Deduction");

        jLabel19.setText("EPF");

        jLabel20.setText("ETF");

        jLabel21.setText("Tax Deduction");

        txtTaxDeductionFixed.setEditable(false);
        txtTaxDeductionFixed.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtETFFixed.setEditable(false);
        txtETFFixed.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtEPFDeductionFixed.setEditable(false);
        txtEPFDeductionFixed.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtEPFFixed.setEditable(false);
        txtEPFFixed.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        javax.swing.GroupLayout FixedDeductionsPanelLayout = new javax.swing.GroupLayout(FixedDeductionsPanel);
        FixedDeductionsPanel.setLayout(FixedDeductionsPanelLayout);
        FixedDeductionsPanelLayout.setHorizontalGroup(
            FixedDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FixedDeductionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FixedDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FixedDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                        .addComponent(txtTaxDeductionFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FixedDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtEPFFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FixedDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtETFFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(FixedDeductionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtEPFDeductionFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        FixedDeductionsPanelLayout.setVerticalGroup(
            FixedDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FixedDeductionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FixedDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtEPFDeductionFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FixedDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtEPFFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FixedDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtETFFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FixedDeductionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtTaxDeductionFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Net Salary");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Gross Salary");

        cmbMonthFixed.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        txtBasicSalary.setEditable(false);

        txtGrossSalaryFixed.setEditable(false);

        txtNetSalaryFixed.setEditable(false);

        javax.swing.GroupLayout FixedSalaryPanelLayout = new javax.swing.GroupLayout(FixedSalaryPanel);
        FixedSalaryPanel.setLayout(FixedSalaryPanelLayout);
        FixedSalaryPanelLayout.setHorizontalGroup(
            FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FixedSalaryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FixedSalaryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbMonthFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FixedSalaryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(18, 18, 18)
                            .addComponent(txtBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(txtGrossSalaryFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNetSalaryFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(FixedDeductionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FixedSalaryPanelLayout.createSequentialGroup()
                    .addContainerGap(334, Short.MAX_VALUE)
                    .addComponent(parenOvertimetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        FixedSalaryPanelLayout.setVerticalGroup(
            FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(cmbMonthFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24)))
                    .addComponent(FixedDeductionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGrossSalaryFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNetSalaryFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(86, Short.MAX_VALUE))
            .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FixedSalaryPanelLayout.createSequentialGroup()
                    .addGap(181, 181, 181)
                    .addComponent(parenOvertimetPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        parentPanel.add(FixedSalaryPanel, "card2");

        btnGenerateSlip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/GenerateSalaryIcon.png"))); // NOI18N
        btnGenerateSlip.setText("Generate Salary Slip");
        btnGenerateSlip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateSlipActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGenerateSlip, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerateSlip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        try
        {
            setTitle("Salary Slip Form");
            DBConnection objDB=new DBConnection();
            con = objDB.getConnection();
        }
        catch(Exception exDB)
        {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: "+exDB, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        
        EmployeeNameDatabase();
    }//GEN-LAST:event_formInternalFrameOpened

    private void cmbEmployeeIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeIDActionPerformed
        txtBasicSalary.setText(null);
        txtGrossSalaryFixed.setText(null);
        txtGrossSalaryHourly.setText(null);
        txtHoursWorked.setText(null);
        txtNetSalaryFixed.setText(null);
        txtNetSalaryHourly.setText(null);
        txtOvertimeHoursFixed.setText(null);
        txtOvertimeHoursHourly.setText(null);

        SelectedEmpID = cmbEmployeeID.getSelectedItem().toString();
        String SalaryType;
        if(SelectedEmpID.startsWith("<"))
        {
            DefaultPanel.setVisible(true);
        }
        else
        {
            try
            {
                PreparedStatement stat = con.prepareStatement("SELECT SalaryType FROM EmployeeTable WHERE EmployeeID=?");
                stat.setString(1, SelectedEmpID);
                ResultSet rs = stat.executeQuery();
                while(rs.next())
                {
                    SalaryType = rs.getString("SalaryType").trim();
                    SalaryInfoGeneration(SalaryType);
                }
            }
            catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(rootPane, "An error occured during Salary Information Generation\n"+ex);
            }
            
        }
    }//GEN-LAST:event_cmbEmployeeIDActionPerformed

    private void txtHoursWorkedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoursWorkedActionPerformed
        String Hours = txtHoursWorked.getText();
        double HourlyRate = Double.parseDouble(txtHourlyRate.getText());
        double EPFDeduction = Double.parseDouble(txtEPFDeductionHourly.getText());
        double TotalSalary=0;
        double EPF = Double.parseDouble(txtEPFHourly.getText());
        double ETF = Double.parseDouble(txtETFHourly.getText());
        double TaxDeduction = Double.parseDouble(txtTaxDeductionHourly.getText());
        double OvertimeHourlyRate = Double.parseDouble(txtOvertimeRateHourly.getText());
        double NetSalaryHourly=0;
        double GrossSalaryHourly=0;
        if(txtHoursWorked.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "The Hours Worked field is empty","Empty Field",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            int HoursWorked = Integer.valueOf(Hours);
            TotalSalary = HourlyRate * HoursWorked;
            if(OvertimeHourlyRate == 0.00)
            {
                GrossSalaryHourly = TotalSalary;
            }
            else
            {
                if(txtOvertimeHoursHourly.getText().isEmpty())
                {
                    GrossSalaryHourly = TotalSalary;
                }
                else
                {
                    double OvertimeHoursHourly = Double.parseDouble(txtOvertimeHoursHourly.getText());
                    double OvertimeSalary = OvertimeHourlyRate * OvertimeHoursHourly;
                    GrossSalaryHourly = TotalSalary + OvertimeSalary;
                }
            }

            NetSalaryHourly = GrossSalaryHourly - (TaxDeduction + EPFDeduction);
            txtGrossSalaryHourly.setText(String.valueOf(GrossSalaryHourly));
            txtNetSalaryHourly.setText(String.valueOf(NetSalaryHourly));
        }
        
    }//GEN-LAST:event_txtHoursWorkedActionPerformed

    private void txtOvertimeHoursHourlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOvertimeHoursHourlyActionPerformed
        String Hours = txtHoursWorked.getText();
        double HourlyRate = Double.parseDouble(txtHourlyRate.getText());
        double EPFDeduction = Double.parseDouble(txtEPFDeductionHourly.getText());
        double TotalSalary=0;
        double EPF = Double.parseDouble(txtEPFHourly.getText());
        double ETF = Double.parseDouble(txtETFHourly.getText());
        double TaxDeduction = Double.parseDouble(txtTaxDeductionHourly.getText());
        double OvertimeHourlyRate = Double.parseDouble(txtOvertimeRateHourly.getText());
        String OvertimeHours = txtOvertimeHoursHourly.getText();
        double NetSalaryHourly=0;
        double GrossSalaryHourly=0;
        
        if(OvertimeHourlyPanel.isVisible())
        {
            if(txtOvertimeHoursHourly.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(rootPane, "The Overtime Hours field is empty","Empty Field",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int OvertimeHoursHourly = Integer.valueOf(OvertimeHours);
                int HoursWorked = Integer.valueOf(Hours);
                TotalSalary = HourlyRate * HoursWorked;
                if (OvertimeHourlyRate == 0.00) {
                    GrossSalaryHourly = TotalSalary;
                } else {
                    double OvertimeSalary = OvertimeHourlyRate * OvertimeHoursHourly;
                    if (txtHoursWorked.getText().isEmpty()) {
                        GrossSalaryHourly = OvertimeSalary;
                    } else {
                        GrossSalaryHourly = TotalSalary + OvertimeSalary;
                    }
                }
            }
        }
        else
        {
            GrossSalaryHourly = TotalSalary;
            txtOvertimeRateHourly.setText("0.00");
        }
        NetSalaryHourly = GrossSalaryHourly - (TaxDeduction + EPFDeduction);
        txtGrossSalaryHourly.setText(String.valueOf(GrossSalaryHourly));
        txtNetSalaryHourly.setText(String.valueOf(NetSalaryHourly));
    }//GEN-LAST:event_txtOvertimeHoursHourlyActionPerformed

    private void txtOvertimeHoursFixedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOvertimeHoursFixedActionPerformed
        double BasicSalary = Double.parseDouble(txtBasicSalary.getText());
        double EPFDeduction = Double.parseDouble(txtEPFDeductionFixed.getText());
        double EPF = Double.parseDouble(txtEPFFixed.getText());
        double ETF = Double.parseDouble(txtETFFixed.getText());
        double TaxDeduction = Double.parseDouble(txtTaxDeductionFixed.getText());
        double OvertimeRateFixed = Double.parseDouble(txtOvertimeRateFixed.getText());
        String OvertimeHours = txtOvertimeHoursFixed.getText();
        double NetSalaryFixed = 0;
        double GrossSalaryFixed = 0;
        
        if(OvertimeFixedPanel.isVisible())
        {
            if(txtOvertimeHoursFixed.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(rootPane, "The Overtime Hours field is empty","Empty Field",JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                int OvertimeHoursFixed = Integer.valueOf(OvertimeHours);
                if (OvertimeRateFixed == 0.00) {
                    GrossSalaryFixed = BasicSalary;
                } else {
                    double OvertimeSalary = OvertimeHoursFixed * OvertimeRateFixed;
                    GrossSalaryFixed = BasicSalary + OvertimeSalary;
                }
            }
        }
        else
        {
            txtOvertimeRateFixed.setText("0.00");
            GrossSalaryFixed = BasicSalary;
        }
        
        NetSalaryFixed = GrossSalaryFixed - (TaxDeduction + EPFDeduction);
        txtNetSalaryFixed.setText(String.valueOf(NetSalaryFixed));
        txtGrossSalaryFixed.setText(String.valueOf(GrossSalaryFixed));
    }//GEN-LAST:event_txtOvertimeHoursFixedActionPerformed

    private void btnGenerateSlipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateSlipActionPerformed
        boolean Success = false;
        if(FixedSalaryPanel.isVisible())
        {
            if(OvertimeFixedPanel.isVisible())
            {
                if(txtOvertimeHoursFixed.getText().isEmpty())
                {
                    Success = false;
                    JOptionPane.showMessageDialog(rootPane, "The Overtime Hours field is empty","Empty Field",JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    Success = true;
                }
            }
            else
            {
                Success = true;
                txtOvertimeHoursFixed.setText("0.00");
            }
        }
        else if (HourlySalaryPanel.isVisible())
        {
            if(OvertimeHourlyPanel.isVisible())
            {
                if(txtHoursWorked.getText().isEmpty())
                {
                    Success = false;
                    JOptionPane.showMessageDialog(rootPane, "The Hours Worked field is empty","Empty Field",JOptionPane.ERROR_MESSAGE);
                    System.err.println("Hours Worked Field Empty");
                }
                else if(txtOvertimeHoursHourly.getText().isEmpty())
                {
                    Success = false;
                    JOptionPane.showMessageDialog(rootPane, "The Overtime Hours field is empty","Empty Field",JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    Success = true;
                }
            }
            else
            {
                Success = true;
                txtOvertimeHoursHourly.setText("0.00");
            }
        }
        if(Success == true)
        {
            try
            {
                PreparedStatement stat = con.prepareStatement("SELECT SalaryType FROM EmployeeTable WHERE EmployeeID=?");
                stat.setString(1, cmbEmployeeID.getSelectedItem().toString());
                ResultSet rs = stat.executeQuery();
                while(rs.next())
                {
                    String Type = rs.getString("SalaryType").trim();

                    if(Type.equals("Fixed"))
                    {
                        if(txtGrossSalaryFixed.getText().isEmpty() || txtNetSalaryFixed.getText().isEmpty())
                        {
                            txtOvertimeHoursFixedActionPerformed(evt);
                        }
                        else
                        {
                            try
                            {
                                PreparedStatement st1 = con.prepareStatement("UPDATE FixedSalaryTable SET BasicSalary=?, SalaryMonth=?, EPF=?, EPFDeduction=?, ETF=?, OvertimeRate=?, OvertimeHours=?, GrossSalary=?, TaxRate=?, NetSalary=? WHERE SalarySlipID=?");
                                st1.setDouble(1, Double.parseDouble(txtBasicSalary.getText()));
                                st1.setString(2, cmbMonthFixed.getSelectedItem().toString());
                                st1.setDouble(3, Double.parseDouble(txtEPFFixed.getText()));
                                st1.setDouble(4, Double.parseDouble(txtEPFDeductionFixed.getText()));
                                st1.setDouble(5, Double.parseDouble(txtETFFixed.getText()));
                                st1.setDouble(6, Double.parseDouble(txtOvertimeRateFixed.getText()));
                                st1.setDouble(7, Double.parseDouble(txtOvertimeHoursFixed.getText()));
                                st1.setDouble(8, Double.parseDouble(txtGrossSalaryFixed.getText()));
                                st1.setDouble(9, Double.parseDouble(txtTaxDeductionFixed.getText()));
                                st1.setDouble(10, Double.parseDouble(txtNetSalaryFixed.getText()));
                                st1.setString(11, txtSalaryID.getText().toString());

                                int FixRow = 0;
                                FixRow = st1.executeUpdate();
                                if(FixRow >0)
                                {
                                    String ID = txtSalaryID.getText().toString();
                                    JOptionPane.showMessageDialog(rootPane, "The Fixed Salary Slip has been generated", "Salary Slip Generation Succesfull", JOptionPane.INFORMATION_MESSAGE);
                                    DisplaySlip obj = new DisplaySlip(ID);
                                    obj.setVisible(true);
                                }
                            }
                            catch(SQLException sqlE)
                            {
                                JOptionPane.showMessageDialog(rootPane,"A Database exception error occured during execution\nError: "+sqlE,"Fixed Salary Database Exception Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    else if(Type.equals("Hourly"))
                    {
                        if(txtGrossSalaryHourly.getText().isEmpty()  || txtNetSalaryHourly.getText().isEmpty())
                        {
                            txtOvertimeHoursHourlyActionPerformed(evt);
                            txtHoursWorkedActionPerformed(evt);
                        }
                        else
                        {
                            try
                            {
                                PreparedStatement st2=con.prepareStatement("UPDATE HourlySalaryTable SET HoursWorked=?, HourlyRate=?, SalaryMonth=?, EPF=?, EPFDeduction=?, ETF=?, OvertimeRate=?, OvertimeHours=?, GrossSalary=?, TaxRate=?, NetSalary=? WHERE SalarySlipID=?");
                                st2.setDouble(1, Double.parseDouble(txtHoursWorked.getText()));
                                st2.setDouble(2, Double.parseDouble(txtHourlyRate.getText()));
                                st2.setString(3, cmbMonthHourly.getSelectedItem().toString());
                                st2.setDouble(4, Double.parseDouble(txtEPFHourly.getText()));
                                st2.setDouble(5, Double.parseDouble(txtEPFDeductionHourly.getText()));
                                st2.setDouble(6, Double.parseDouble(txtETFHourly.getText()));
                                st2.setDouble(7, Double.parseDouble(txtOvertimeRateHourly.getText()));
                                st2.setDouble(8, Double.parseDouble(txtOvertimeHoursHourly.getText()));
                                st2.setDouble(9, Double.parseDouble(txtGrossSalaryHourly.getText()));
                                st2.setDouble(10, Double.parseDouble(txtTaxDeductionHourly.getText()));
                                st2.setDouble(11, Double.parseDouble(txtNetSalaryHourly.getText()));
                                st2.setString(12, txtSalaryID.getText().toString());

                                int HourRow=0;
                                HourRow=st2.executeUpdate();
                                if(HourRow > 0)
                                {
                                    String ID = txtSalaryID.getText().toString();
                                    JOptionPane.showMessageDialog(rootPane, "The Hourly Salary Slip has been generated", "Salary Slip Generation Succesfull", JOptionPane.INFORMATION_MESSAGE);
                                    DisplaySlip obj = new DisplaySlip(ID);
                                    obj.setVisible(true);
                                }
                            }
                            catch(SQLException E)
                            {
                                JOptionPane.showMessageDialog(rootPane,"A Database exception error occured during execution\nError: "+E,"Hourly Salary Database Exception Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
            catch(SQLException e)
            {
                JOptionPane.showMessageDialog(rootPane, "An error occured during Salary Slip Generation\n"+e);
            }
        }
    }//GEN-LAST:event_btnGenerateSlipActionPerformed

    private void txtOvertimeHoursFixedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOvertimeHoursFixedKeyTyped
        char TestChar = evt.getKeyChar();
        if (!(Character.isDigit(TestChar)))
        evt.consume();
    }//GEN-LAST:event_txtOvertimeHoursFixedKeyTyped

    private void txtHoursWorkedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHoursWorkedKeyTyped
        char TestChar = evt.getKeyChar();
        if (!(Character.isDigit(TestChar)))
        evt.consume();
    }//GEN-LAST:event_txtHoursWorkedKeyTyped

    private void txtOvertimeHoursHourlyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOvertimeHoursHourlyKeyTyped
        char TestChar = evt.getKeyChar();
        if (!(Character.isDigit(TestChar)))
        evt.consume();
    }//GEN-LAST:event_txtOvertimeHoursHourlyKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DefaultPanel;
    private javax.swing.JPanel FixedDeductionsPanel;
    private javax.swing.JPanel FixedSalaryPanel;
    private javax.swing.JPanel HourlyDeductionsPanel;
    private javax.swing.JPanel HourlySalaryPanel;
    private javax.swing.JPanel OvertimeDefaultHourly;
    private javax.swing.JPanel OvertimeDefaultPanel;
    private javax.swing.JPanel OvertimeFixedPanel;
    private javax.swing.JPanel OvertimeHourlyPanel;
    private javax.swing.JButton btnGenerateSlip;
    private javax.swing.JComboBox cmbEmployeeID;
    private javax.swing.JComboBox cmbMonthFixed;
    private javax.swing.JComboBox cmbMonthHourly;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JPanel parenOvertimetPanel;
    private javax.swing.JPanel parentHourlyOvertime;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JTextField txtBasicSalary;
    private javax.swing.JTextField txtEPFDeductionFixed;
    private javax.swing.JTextField txtEPFDeductionHourly;
    private javax.swing.JTextField txtEPFFixed;
    private javax.swing.JTextField txtEPFHourly;
    private javax.swing.JTextField txtETFFixed;
    private javax.swing.JTextField txtETFHourly;
    private javax.swing.JTextField txtGrossSalaryFixed;
    private javax.swing.JTextField txtGrossSalaryHourly;
    private javax.swing.JTextField txtHourlyRate;
    private javax.swing.JTextField txtHoursWorked;
    private javax.swing.JTextField txtNetSalaryFixed;
    private javax.swing.JTextField txtNetSalaryHourly;
    private javax.swing.JTextField txtOvertimeHoursFixed;
    private javax.swing.JTextField txtOvertimeHoursHourly;
    private javax.swing.JTextField txtOvertimeRateFixed;
    private javax.swing.JTextField txtOvertimeRateHourly;
    private javax.swing.JTextField txtSalaryID;
    private javax.swing.JTextField txtTaxDeductionFixed;
    private javax.swing.JTextField txtTaxDeductionHourly;
    // End of variables declaration//GEN-END:variables
}
