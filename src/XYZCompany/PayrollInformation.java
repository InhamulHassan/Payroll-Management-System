/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZCompany;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.*;

/**
 *
 * @author RazR
 */
public class PayrollInformation extends javax.swing.JInternalFrame {

    Connection con;
    String [] FixedColumnNames ={"SalarySlipID","EmployeeID","BasicSalary","SalaryMonth","EPF","EPFDeduction","ETF","OvertimeRate","OvertimeHours","GrossSalary","TaxDeduction","NetSalary"};
    String [] HourlyColumnNames = {"SalarySlipID","EmployeeID","HoursWorked","HourlyRate","SalaryMonth","EPF","EPFDeduction","ETF","OvertimeRate","OvertimeHours","GrossSalary","TaxDeduction","NetSalary"};
    
    public PayrollInformation() {
        initComponents();
        FrameUI();
        SalaryInformationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        SalaryInformationTable.setFillsViewportHeight(true);
    }

    private void FrameUI()
    {
        setSize(760, 550);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setTitle("Payroll Information");
        setClosable(true);
        BasicInternalFrameUI basicInternalFrameUI = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI());
        for (MouseListener listener : basicInternalFrameUI.getNorthPane().getMouseListeners()) 
        {
            basicInternalFrameUI.getNorthPane().removeMouseListener(listener);
        }

    }
    
    public void TableResize() {
        JTable table = SalaryInformationTable;
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

        //  We've exceeded the maximum width, no need to check other rows
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
    }
    
    public void EmployeeNameDatabase()
    {
        try
        {
            Statement st = con.createStatement();
            ResultSet rs=st.executeQuery("select * from EmployeeTable");

 
            while (rs.next()) 
            {  
                cmbEmployeeID.addItem(rs.getString("EmployeeID").trim());
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+e,"Database Connection Error", JOptionPane.WARNING_MESSAGE);
        }
        
        try
        {
            Statement st1 = con.createStatement();
            ResultSet rs1=st1.executeQuery("select * from FixedSalaryTable");

 
            while (rs1.next()) 
            {  
                cmbSalarySlipID.addItem(rs1.getString("SalarySlipID").trim());
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+e,"Database Connection Error", JOptionPane.WARNING_MESSAGE);
        }
        
        try
        {
            Statement st2 = con.createStatement();
            ResultSet rs2=st2.executeQuery("select * from HourlySalaryTable");

 
            while (rs2.next()) 
            {  
                cmbSalarySlipID.addItem(rs2.getString("SalarySlipID").trim());
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+e,"Database Connection Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    
    public void PopulatingFixedSalaryTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(FixedColumnNames);
        
        if(cmbSalarySlipID.getSelectedIndex()>0)
        {
            try 
            {
                PreparedStatement stat1 = con.prepareStatement("SELECT * FROM FixedSalaryTable WHERE SalarySlipID=?");
                stat1.setString(1, cmbSalarySlipID.getSelectedItem().toString());
                ResultSet rs1 = stat1.executeQuery();
                int i = 0;
                if(rs1.next())
                {
                    String SlipID = rs1.getString("SalarySlipID").trim();
                    String EmployeeID = rs1.getString("EmployeeID").trim();
                    double BasicSalary = rs1.getDouble("BasicSalary");
                    String SalaryMonth = rs1.getString("SalaryMonth").trim();
                    double EPF = rs1.getDouble("EPF");
                    double EPFDeduction = rs1.getDouble("EPFDeduction");
                    double ETF = rs1.getDouble("ETF");
                    double OvertimeRate = rs1.getDouble("OvertimeRate");
                    int OvertimeHours = rs1.getInt("OvertimeHours");
                    double GrossSalary = rs1.getDouble("GrossSalary");
                    double TaxDeduction = rs1.getDouble("TaxRate");
                    double NetSalary = rs1.getDouble("NetSalary");

                    model.addRow(new Object[]{SlipID, EmployeeID, BasicSalary, SalaryMonth, EPF, EPFDeduction, ETF, OvertimeRate, OvertimeHours, GrossSalary, TaxDeduction, NetSalary});
                    i++;
                    if(i>0)
                    {
                        System.out.println("Found "+SlipID);
                    }
                }
                SalaryInformationTable.setModel(model);
            }
            catch (SQLException e) 
            {
                JOptionPane.showMessageDialog(rootPane, "An error occured during Fixed Salary Information Generation\n"+e);
            }  
        }
    }
    
    public void PopulatingHourlySalaryTable()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(HourlyColumnNames);
        
        if(cmbSalarySlipID.getSelectedIndex()>0)
        {
            try {
                PreparedStatement stat = con.prepareStatement("SELECT * FROM HourlySalaryTable WHERE SalarySlipID=?");
                stat.setString(1, cmbSalarySlipID.getSelectedItem().toString());
                ResultSet rs1 = stat.executeQuery();
                int c = 0;
                if (rs1.next()) 
                {
                    String SlipID = rs1.getString("SalarySlipID").trim();
                    String EmployeeID = rs1.getString("EmployeeID").trim();
                    int HoursWorked = rs1.getInt("HoursWorked");
                    double HourlyRate = rs1.getDouble("HourlyRate");
                    String SalaryMonth = rs1.getString("SalaryMonth").trim();
                    double EPF = rs1.getDouble("EPF");
                    double EPFDeduction = rs1.getDouble("EPFDeduction");
                    double ETF = rs1.getDouble("ETF");
                    double OvertimeRate = rs1.getDouble("OvertimeRate");
                    int OvertimeHours = rs1.getInt("OvertimeHours");
                    double GrossSalary = rs1.getDouble("GrossSalary");
                    double TaxDeduction = rs1.getDouble("TaxRate");
                    double NetSalary = rs1.getDouble("NetSalary");

                    model.addRow(new Object[]{SlipID, EmployeeID, HoursWorked, HourlyRate, SalaryMonth, EPF, EPFDeduction, ETF, OvertimeRate, OvertimeHours, GrossSalary, TaxDeduction, NetSalary});
                    c++;
                    if (c > 0) 
                    {
                        System.out.println("Found "+SlipID);
                    }
                }
                SalaryInformationTable.setModel(model);
            } 
            catch (SQLException e) 
            {
                JOptionPane.showMessageDialog(rootPane, "An error occured during Hourly Salary Information Generation\n"+e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbSalarySlipID = new javax.swing.JComboBox();
        cmbEmployeeID = new javax.swing.JComboBox();
        TablePanel = new javax.swing.JPanel();
        parentPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SalaryInformationTable = new javax.swing.JTable();

        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SalaryInformationIcon.png"))); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Salary Slip ID");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Employee ID");

        cmbSalarySlipID.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cmbSalarySlipID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select salary slip ID>" }));
        cmbSalarySlipID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSalarySlipIDItemStateChanged(evt);
            }
        });
        cmbSalarySlipID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSalarySlipIDActionPerformed(evt);
            }
        });

        cmbEmployeeID.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cmbEmployeeID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select employee id>" }));
        cmbEmployeeID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbEmployeeIDItemStateChanged(evt);
            }
        });
        cmbEmployeeID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cmbSalarySlipID, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cmbEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(cmbEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cmbSalarySlipID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        TablePanel.setLayout(new java.awt.CardLayout());

        parentPanel.setPreferredSize(new java.awt.Dimension(764, 250));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("To display an employee's payroll information, please choose his/her Salary Slip ID or Employee ID");

        javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
        parentPanel.setLayout(parentPanelLayout);
        parentPanelLayout.setHorizontalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel3)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        parentPanelLayout.setVerticalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabel3)
                .addGap(216, 216, 216))
        );

        TablePanel.add(parentPanel, "card3");

        SalaryInformationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        SalaryInformationTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(SalaryInformationTable);
        SalaryInformationTable.getAccessibleContext().setAccessibleParent(TablePanel);

        TablePanel.add(jScrollPane1, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        try
        {
            setTitle("Payroll Information Form");
            DBConnection objDB=new DBConnection();
            con = objDB.getConnection();
        }
        catch(Exception exDB)
        {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: "+exDB, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        EmployeeNameDatabase();
    }//GEN-LAST:event_formInternalFrameOpened

    private void cmbSalarySlipIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSalarySlipIDActionPerformed
        String ComboSlipID = cmbSalarySlipID.getSelectedItem().toString();
        String ComboIDNumber = "";
        if (ComboSlipID.startsWith("<"))
        {
            cmbEmployeeID.setSelectedIndex(0);
            parentPanel.setVisible(true);
            jScrollPane1.setVisible(false);
        }
        else
        {
            jScrollPane1.setVisible(true);
            parentPanel.setVisible(false);
            if (ComboSlipID.contains("F"))
            {
                try 
                {
                    PreparedStatement stat = con.prepareStatement("select * from FixedSalaryTable where SalarySlipID=?");
                    stat.setString(1, ComboSlipID);

                    ResultSet rs = stat.executeQuery();

                    if (rs.next()) {
                        ComboIDNumber = rs.getString("EmployeeID").trim();
                        for (int i = 1; i < cmbEmployeeID.getItemCount(); i++) {
                            if (cmbEmployeeID.getItemAt(i).equals(ComboIDNumber)) {
                                cmbEmployeeID.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: " + e, "EPF Number Generation Error in Fixed Salary Table", JOptionPane.WARNING_MESSAGE);
                }
            }

            if (ComboSlipID.contains("H"))
            {
                try 
                {
                    PreparedStatement stat = con.prepareStatement("select * from HourlySalaryTable where SalarySlipID=?");
                    stat.setString(1, ComboSlipID);

                    ResultSet rs = stat.executeQuery();

                    if (rs.next()) {
                        ComboIDNumber = rs.getString("EmployeeID").trim();
                        for (int i = 1; i < cmbEmployeeID.getItemCount(); i++) {
                            if (cmbEmployeeID.getItemAt(i).equals(ComboIDNumber)) {
                                cmbEmployeeID.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                } 
                catch (Exception e) 
                {
                    JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: " + e, "EPF Number Generation Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        
    }//GEN-LAST:event_cmbSalarySlipIDActionPerformed

    private void cmbEmployeeIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeIDActionPerformed
        String ComboEmployeeID = cmbEmployeeID.getSelectedItem().toString();
        String ComboSlipID = "";
        if (ComboEmployeeID.startsWith("<"))
        {
            cmbSalarySlipID.setSelectedIndex(0);
            parentPanel.setVisible(true);
            jScrollPane1.setVisible(false);
        }
        else {
            parentPanel.setVisible(false);
            jScrollPane1.setVisible(true);
            try
            {
                PreparedStatement stat = con.prepareStatement("select * from FixedSalaryTable where EmployeeID=?");
                stat.setString(1, ComboEmployeeID);

                ResultSet rs = stat.executeQuery();
                while (rs.next()) 
                {  
                    ComboSlipID = rs.getString("SalarySlipID").trim();
                    for (int i=1; i<cmbSalarySlipID.getItemCount();i++)
                    {
                        if (cmbSalarySlipID.getItemAt(i).equals(ComboSlipID))
                        {
                            cmbSalarySlipID.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+e,"Employee ID Generation Error", JOptionPane.WARNING_MESSAGE);
            }
            
            try
            {
                                
                PreparedStatement stat1 = con.prepareStatement("select * from HourlySalaryTable where EmployeeID=?");
                stat1.setString(1, ComboEmployeeID);

                ResultSet rs1 = stat1.executeQuery();
                while (rs1.next())
                {
                    ComboSlipID = rs1.getString("SalarySlipID").trim();
                    for (int i=1; i<cmbSalarySlipID.getItemCount();i++)
                    {
                        if (cmbSalarySlipID.getItemAt(i).equals(ComboSlipID))
                        {
                            cmbSalarySlipID.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+ex,"Employee ID Generation Error", JOptionPane.WARNING_MESSAGE);
            }
                
        }
    }//GEN-LAST:event_cmbEmployeeIDActionPerformed

    private void cmbSalarySlipIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSalarySlipIDItemStateChanged
       
        TableResize();
        String ComboSalarySlipID = cmbSalarySlipID.getSelectedItem().toString();
        if(ComboSalarySlipID.contains("<"))
        {
            SalaryInformationTable.setVisible(false);
            parentPanel.setVisible(true);
            jScrollPane1.setVisible(false);
        }
        else
        {
            parentPanel.setVisible(false);
            jScrollPane1.setVisible(true);
            SalaryInformationTable.setVisible(true);
            if(ComboSalarySlipID.contains("F"))
            {
                PopulatingFixedSalaryTable();
            }
            else if (ComboSalarySlipID.contains("H"))
            {
                PopulatingHourlySalaryTable();
            }
        }
    }//GEN-LAST:event_cmbSalarySlipIDItemStateChanged

    private void cmbEmployeeIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbEmployeeIDItemStateChanged

        if (cmbSalarySlipID.getSelectedItem().toString().contains("F")) {
            
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(FixedColumnNames);
            
            if (cmbEmployeeID.getSelectedIndex() > 0) {
                parentPanel.setVisible(false);
                jScrollPane1.setVisible(true);
                try {
                    
                    PreparedStatement stat = con.prepareStatement("SELECT * FROM FixedSalaryTable WHERE EmployeeID=?");
                    stat.setString(1, cmbEmployeeID.getSelectedItem().toString());
                    ResultSet rs = stat.executeQuery();
                    int i = 0;
                    
                    while (rs.next()) {
                        
                        String SlipID = rs.getString("SalarySlipID").trim();
                        String EmployeeID = rs.getString("EmployeeID").trim();
                        double BasicSalary = rs.getDouble("BasicSalary");
                        String SalaryMonth = rs.getString("SalaryMonth").trim();
                        double EPF = rs.getDouble("EPF");
                        double EPFDeduction = rs.getDouble("EPFDeduction");
                        double ETF = rs.getDouble("ETF");
                        double OvertimeRate = rs.getDouble("OvertimeRate");
                        int OvertimeHours = rs.getInt("OvertimeHours");
                        double GrossSalary = rs.getDouble("GrossSalary");
                        double TaxDeduction = rs.getDouble("TaxRate");
                        double NetSalary = rs.getDouble("NetSalary");

                        model.addRow(new Object[]{SlipID, EmployeeID, BasicSalary, SalaryMonth, EPF, EPFDeduction, ETF, OvertimeRate, OvertimeHours, GrossSalary, TaxDeduction, NetSalary});
                        i++;
                        if (i > 0) {
                            System.out.println("Found " + SlipID);
                        }
                    }
                    SalaryInformationTable.setModel(model);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(rootPane, "An error occured during Fixed Salary Information Generation\n" + e);
                }
            }
        }
        
        if (cmbSalarySlipID.getSelectedItem().toString().contains("H")) {
         
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(HourlyColumnNames);

            if (cmbEmployeeID.getSelectedIndex() > 0) {
                parentPanel.setVisible(false);
                jScrollPane1.setVisible(true);
                try {
                    PreparedStatement stat = con.prepareStatement("SELECT * FROM HourlySalaryTable WHERE EmployeeID=?");
                    stat.setString(1, cmbEmployeeID.getSelectedItem().toString());
                    ResultSet rs = stat.executeQuery();
                    int c = 0;
                    while (rs.next()) {
                        String SlipID = rs.getString("SalarySlipID").trim();
                        String EmployeeID = rs.getString("EmployeeID").trim();
                        int HoursWorked = rs.getInt("HoursWorked");
                        double HourlyRate = rs.getDouble("HourlyRate");
                        String SalaryMonth = rs.getString("SalaryMonth").trim();
                        double EPF = rs.getDouble("EPF");
                        double EPFDeduction = rs.getDouble("EPFDeduction");
                        double ETF = rs.getDouble("ETF");
                        double OvertimeRate = rs.getDouble("OvertimeRate");
                        int OvertimeHours = rs.getInt("OvertimeHours");
                        double GrossSalary = rs.getDouble("GrossSalary");
                        double TaxDeduction = rs.getDouble("TaxRate");
                        double NetSalary = rs.getDouble("NetSalary");

                        model.addRow(new Object[]{SlipID, EmployeeID, HoursWorked, HourlyRate, SalaryMonth, EPF, EPFDeduction, ETF, OvertimeRate, OvertimeHours, GrossSalary, TaxDeduction, NetSalary});
                        c++;
                        if (c > 0) {
                            System.out.println("Found " + EmployeeID);
                        }
                    }
                    SalaryInformationTable.setModel(model);
                    
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(rootPane, "An error occured during Hourly Salary Information Generation\n" + e);
                }
            }
        }
    }//GEN-LAST:event_cmbEmployeeIDItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable SalaryInformationTable;
    private javax.swing.JPanel TablePanel;
    private javax.swing.JComboBox cmbEmployeeID;
    private javax.swing.JComboBox cmbSalarySlipID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel parentPanel;
    // End of variables declaration//GEN-END:variables
}
