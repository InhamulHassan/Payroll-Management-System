/*
 * To change this license header, choose License Headers in Pr
            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }ject Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZCompany;

import java.sql.*;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author RazR
 */
public class AddEmployeeDetails extends javax.swing.JInternalFrame {

    Connection con;
    boolean ParameterCheck = false;
    boolean Success = false;
    public AddEmployeeDetails() {
       
        initComponents();     
        FrameUI();
    }


    public void EmployeeIDGeneration()
    {
        try
        {
            PreparedStatement stat=con.prepareStatement("SELECT EmployeeID FROM EmployeeTable");
            ResultSet rs=stat.executeQuery();
            String EID = null;
            
            while (rs.next())
                    {
                        EID = rs.getString("EmployeeID");
                    }
            EID = EID.trim();
            
            String x =EID.substring(1);
            
            int ID = Integer.parseInt(x);
            String IDS = null;           

                ID = ID + 1;
                IDS = "E"+ID;

            
            txtEmployeeID.setText(IDS);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(rootPane, "An error occured during the Employee ID Generation\n"+ex);
        }
    }
    public void SalaryTypeNoSelect()
    {
        if ((!rdbFixed.isSelected()) && !(rdbHourly.isSelected()))
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(parentPanel, "Choose a valid Salary Type before filling out the Salary Information", "Salary Type not chosen", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(rdbFixed.isSelected() || rdbHourly.isSelected())
        {
            ParameterCheck = true;
        }
    }
    public void SalaryTypeYesSelect()
    {
        {
            txfBasicSalary.setEnabled(true);
            txfHourlyRate.setEnabled(true);
            txfTaxPercentageFixed.setEnabled(true);
            txfTaxPercentageHourly.setEnabled(true);
            chkOvertimeFixedEligible.setEnabled(true);
            chkOvertimeHourlyEligible.setEnabled(true);
        }
    }
    
    private void FrameUI()
    {
        setSize(760, 550);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setTitle("Add Employee Details");
        setClosable(true);
        BasicInternalFrameUI basicInternalFrameUI = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI());
        for (MouseListener listener : basicInternalFrameUI.getNorthPane().getMouseListeners()) 
        {
            basicInternalFrameUI.getNorthPane().removeMouseListener(listener);
        }

    }
    public class EmailValidator {
        
        private final Pattern pattern;
        private Matcher matcher;
        
        private static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        /**
         *
         */
        public EmailValidator() {
            pattern = Pattern.compile(EMAIL_PATTERN);
        }

        /**
         * Validate hex with regular expression
         * 
         * @param hex
         *            hex for validation
         * @return true valid hex, false invalid hex
         */
        public boolean validate(final String hex) {

            matcher = pattern.matcher(hex);
            return matcher.matches();

        }
    }
    
    public class GetDate {
        Calendar c = Calendar.getInstance();
        Date todayNow;
        public GetDate()
        {
            // set the calendar to start of today
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            // and get that as a Date
            todayNow = c.getTime();
        }
    }
    
    public void SubmitCheck()
    {
        if (txtFirstName.getText().isEmpty()||txtLastName.getText().isEmpty())
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(PersonalInformationPanel, "Please provide the employee's name in the relevant field", "Employee Name Field Empty", JOptionPane.ERROR_MESSAGE);
        }
        else if(!(rdbFemale.isSelected()) && !(rdbMale.isSelected()))
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(PersonalInformationPanel, "Please provide the employee's gender in the relevant field", "Employee Gender Field Empty", JOptionPane.ERROR_MESSAGE);
        }
        else if(rdbFixed.isSelected() && (txfBasicSalary.getText().isEmpty() || txfTaxPercentageFixed.getText().isEmpty()))
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(parentPanel, "Please provide the employee's salary details in the relevant field", "Fixed Salary Information Field Empty", JOptionPane.ERROR_MESSAGE);
        }
        else if (txtContactNumber.getText().isEmpty())
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(ContactInformationPanel, "Please provide the employee's contact details in the relevant field", "Contact Number Field Empty", JOptionPane.ERROR_MESSAGE);
        }
        else if(txfAccountNumber.getText().isEmpty() || txtBankName.getText().isEmpty())
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(BankInformationPanel, "Please provide the employee's bank details in the relevant field", "Bank Information Field Empty", JOptionPane.ERROR_MESSAGE);
        }
        else if(txfEPFNumber.getText().isEmpty())
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(EmployeeInformationPanel, "Please provide the employee's EPF number in the relevant field", "EPF Number Field Empty", JOptionPane.ERROR_MESSAGE);
        }
        else if(rdbHourly.isSelected() && (txfHourlyRate.getText().isEmpty() || txfTaxPercentageHourly.getText().isEmpty()))
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(parentPanel, "Please provide the employee's salary details in the relevant field", "Hourly Salary Field Empty", JOptionPane.ERROR_MESSAGE);
        }
        else if(chkOvertimeFixedEligible.isSelected() && txfOvertimeFixedPayrate.getText().isEmpty())
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(parentPanel, "Please provide the employee's overtime pay details in the relevant field\nIf you wish to not provide overtime for this employee, please uncheck the overtime eligibility checkbox", "Overtime Information Field Empty", JOptionPane.PLAIN_MESSAGE);
        }
        else if(chkOvertimeHourlyEligible.isSelected() && txfOvertimeHourlyPayrate.getText().isEmpty())
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(parentPanel, "Please provide the employee's overtime pay details in the relevant field\nIf you wish to not provide overtime for this employee, please uncheck the overtime eligibility checkbox", "Overtime Information Field Empty", JOptionPane.PLAIN_MESSAGE);
        }
        else if(cmbDepartment.getSelectedItem().equals(("<select department>"))  || cmbPosition.getSelectedItem().equals(("<select position>")) || cmbWorkingStatus.getSelectedItem().equals(("<select status>")))
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(EmployeeInformationPanel, "Please provide the employee's valid details in the relevant combo boxes", "Employee Information Combo Boxes Empty", JOptionPane.PLAIN_MESSAGE);
        }
        else
        {
            ParameterCheck = true;
        }
    }
    
    public String SlipIDFixed()
    {
        String SalarySlipID;
        String NewFID=null;
        try
        {
                PreparedStatement stat1=con.prepareStatement("SELECT SalarySlipID FROM FixedSalaryTable");
                ResultSet rs1=stat1.executeQuery();
                while (rs1.next())
                    {
                        SalarySlipID = rs1.getString("SalarySlipID").trim();
                        String SlipID = SalarySlipID.substring(1, 5);
                        int ID = Integer.parseInt(SlipID);
                        ID = ID+1;
                        NewFID = String.valueOf("F"+ID);
                    }
            }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(rootPane, "An error occured during the Employee ID Generation\n"+ex);
        }
        return NewFID;
    }
    
    public String SlipIDHourly()
    {
        String SalarySlipID;
        String NewHID=null;
        try
        {
                PreparedStatement stat1=con.prepareStatement("SELECT SalarySlipID FROM HourlySalaryTable");
                ResultSet rs1=stat1.executeQuery();
                while (rs1.next())
                    {
                        SalarySlipID = rs1.getString("SalarySlipID").trim();
                        String SlipID = SalarySlipID.substring(1, 5);
                        int ID = Integer.parseInt(SlipID);
                        ID = ID+1;
                        NewHID = String.valueOf("H"+ID);
                    }
            }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(rootPane, "An error occured during the Employee ID Generation\n"+ex);
        }
        return NewHID;
    }
    
    
    public void FixedSalary()
    {
        int SalRow=0;
        String SalarySlipID = SlipIDFixed();
        String BasicSalary=txfBasicSalary.getText();
        for(int i=0;i<BasicSalary.length();i++)
        {
            char a = BasicSalary.charAt(i);    
            if(a==',')    
            {    
                BasicSalary = BasicSalary.replace(Character.toString(a), "");        
            }    
        }
        
        String OvertimeFixed = txfOvertimeFixedPayrate.getText();
        
        for(int i=0;i<OvertimeFixed.length();i++)
        {
            char a = OvertimeFixed.charAt(i);
            if(a==',' || a=='.')    
            {    
                OvertimeFixed = OvertimeFixed.replace(Character.toString(a), "");        
            }    
        }
        
        String TaxRateFixed = txfTaxPercentageFixed.getText();
        double percentage = Double.parseDouble(TaxRateFixed);
        int BasicSalaryInt = Integer.parseInt(BasicSalary);
        int TaxAmount = (int) (BasicSalaryInt * (percentage/100));
        System.out.println("Tax = "+TaxAmount);
        
        try
        {
            PreparedStatement stat2 = con.prepareStatement("INSERT INTO FixedSalaryTable VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stat2.setString(1, SalarySlipID);
            stat2.setString(2, txtEmployeeID.getText().toString().trim());
            stat2.setInt(3, Integer.parseInt(BasicSalary));
            stat2.setString(4, "Unspecified");
            stat2.setInt(5, 0);
            stat2.setInt(6, 0);
            stat2.setInt(7, 0);
            
            if(chkOvertimeFixedEligible.isSelected())
            {
                stat2.setInt(8, Integer.parseInt(OvertimeFixed));    
            }
            else if(!chkOvertimeFixedEligible.isSelected())
            {
                stat2.setInt(8, 0);    
            }
            
            stat2.setInt(9, 0);
            stat2.setInt(10, 0);
            stat2.setInt(11, TaxAmount);
            stat2.setInt(12, 0);
            
            SalRow = stat2.executeUpdate();
            if(SalRow>0)
            {
                Success = true;
                System.out.println("Fixed Salary Updated");
            }
        }
        catch(NumberFormatException Ne)
        {
            Ne.printStackTrace();
        }
        catch(SQLException SQLe)
        {
            JOptionPane.showMessageDialog(rootPane,"A Database exception error occured during execution\nError: "+SQLe,"Fixed Salary Database Exception Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void HourlySalary()
    {
        int SalRow=0;
        String SalarySlipID = SlipIDHourly();
        String HourlySalary = txfHourlyRate.getText();
        
        for(int i=0;i<HourlySalary.length();i++)
        {
            char a = HourlySalary.charAt(i);    
            if(a==',')    
            {    
                HourlySalary = HourlySalary.replace(Character.toString(a), "");        
            }    
        }    
         
        String OvertimeHourly = txfOvertimeHourlyPayrate.getText();
        for(int i=0;i<OvertimeHourly.length();i++)
        {
            char a = OvertimeHourly.charAt(i);    
            if(a==',')    
            {    
                OvertimeHourly = OvertimeHourly.replace(Character.toString(a), "");        
            }    
        }
        
        String TaxRateHourly = txfTaxPercentageHourly.getText();
        double percentage = Double.parseDouble(TaxRateHourly);
        int HourlySalaryInt = Integer.parseInt(HourlySalary);
        int TaxAmount = (int) (HourlySalaryInt * (percentage/100));
        System.out.println("Tax = "+TaxAmount);
        
        try
        {
            PreparedStatement stat2 = con.prepareStatement("INSERT INTO HourlySalaryTable VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stat2.setString(1, SalarySlipID);
            stat2.setString(2, txtEmployeeID.getText().toString().trim());
            stat2.setInt(3, 0);
            stat2.setInt(4, HourlySalaryInt);
            stat2.setString(5, "Unspecified");
            stat2.setInt(6, 0);
            stat2.setInt(7, 0);
            stat2.setInt(8, 0);
            
            if(chkOvertimeHourlyEligible.isSelected())
            {
                stat2.setInt(9, Integer.parseInt(OvertimeHourly));    
            }
            else if(!chkOvertimeHourlyEligible.isSelected())
            {
                stat2.setInt(9, 0);    
            }
            
            stat2.setInt(10, 0);
            stat2.setInt(11, 0);
            stat2.setInt(12, TaxAmount);
            stat2.setInt(13, 0);
            
            SalRow = stat2.executeUpdate();
            if(SalRow>0)
            {
                Success = true;
                System.out.println("Hourly Salary Updated");
            }
        }
        catch(NumberFormatException Ne)
        {
            Ne.printStackTrace();
        }
        catch(SQLException SQLe)
        {
            JOptionPane.showMessageDialog(rootPane,"A Database exception error occured during execution\nError: "+SQLe,"Hourly Salary Database Exception Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void SaveAction()
    {
        String EmployeeID=txtEmployeeID.getText().toString();
        if (ParameterCheck == false)
        {
            JOptionPane.showMessageDialog(parentPanel, "Please fill in the employee details form with valid data", "Employee Details Form Incomplete", JOptionPane.PLAIN_MESSAGE);
        }
        else if (ParameterCheck == true)
        {
            String Gender = null;
            if(rdbFemale.isSelected())
            {
                Gender = "Female";
            }
            else if(rdbMale.isSelected())
            {
                Gender = "Male";
            }
            
            String SalaryType = null;
            if(rdbFixed.isSelected())
            {
                SalaryType = "Fixed";
            }
            else if(rdbHourly.isSelected())
            {
                SalaryType = "Hourly";
            }
            try
            {
                
                String EPFNo = txfEPFNumber.getText().toString();
                EPFNo = EPFNo.trim();
                
                String ContactNo = txtContactNumber.getText(0, 10).toString();
                int Emprow = 0;
                PreparedStatement stat = con.prepareStatement("INSERT INTO EmployeeTable VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                stat.setString(1, EmployeeID);
                stat.setString(2, txtFirstName.getText());
                stat.setString(3, txtLastName.getText());
                stat.setString(4, cmbDepartment.getSelectedItem().toString());
                stat.setString(5, txfJoinDate.getText().toString());
                stat.setString(6, cmbPosition.getSelectedItem().toString());
                stat.setString(7, EPFNo);
                stat.setString(8, txtAddress.getText().toString());
                stat.setString(9, Gender);
                stat.setString(10, txfAccountNumber.getText().toString());
                stat.setString(11, txtBankName.getText().toString());
                stat.setString(12, SalaryType);
                stat.setString(13, txfEmail.getText().toString());
                stat.setString(14, ContactNo);
                stat.setString(15, txfDateOfBirth.getText().toString());
                stat.setString(16, cmbWorkingStatus.getSelectedItem().toString());
                Emprow = stat.executeUpdate();
 
                if(Emprow>0)
                {
                    Success = true;
                    System.out.println("Employee Registration Successful");
                    if (rdbFixed.isSelected())
                    {
                        FixedSalary();
                        txfHourlyRate.setText(null);
                        txfTaxPercentageHourly.setText(null);
                        txfOvertimeHourlyPayrate.setText(null);
                    }
                    else if (rdbHourly.isSelected())
                    {
                        HourlySalary();
                        txfBasicSalary.setText(null);
                        txfTaxPercentageFixed.setText(null);
                        txfOvertimeFixedPayrate.setText(null);
                    }
                }
                
                if (Success == true)
                {
                    JOptionPane.showMessageDialog(rootPane, "Employee's details have been submitted", "Employee Registration Succesfull", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
            catch(java.sql.SQLException SQLe)
            {
                JOptionPane.showMessageDialog(rootPane,"A Database exception error occured during execution\nError: "+SQLe,"Database Exception Error", JOptionPane.ERROR_MESSAGE);
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+ex,"Information Submission Error", JOptionPane.ERROR_MESSAGE);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        BankInformationPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtBankName = new javax.swing.JTextField();
        txfAccountNumber = new javax.swing.JFormattedTextField();
        PersonalInformationPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        txtCalculatedAge = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        rdbMale = new javax.swing.JRadioButton();
        rdbFemale = new javax.swing.JRadioButton();
        txfDateOfBirth = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        ContactInformationPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtContactNumber = new javax.swing.JTextField();
        txfEmail = new javax.swing.JFormattedTextField();
        EmployeeInformationPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txfJoinDate = new javax.swing.JFormattedTextField();
        cmbPosition = new javax.swing.JComboBox();
        cmbWorkingStatus = new javax.swing.JComboBox();
        txfEPFNumber = new javax.swing.JFormattedTextField();
        cmbDepartment = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        parentPanel = new javax.swing.JPanel();
        HourlySalaryPanel = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        chkOvertimeHourlyEligible = new javax.swing.JCheckBox();
        OvertimeHourlyPanel = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txfOvertimeHourlyPayrate = new javax.swing.JFormattedTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txfHourlyRate = new javax.swing.JFormattedTextField();
        txfTaxPercentageHourly = new javax.swing.JFormattedTextField();
        FixedSalaryPanel = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        OvertimeFixedPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txfOvertimeFixedPayrate = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        chkOvertimeFixedEligible = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txfBasicSalary = new javax.swing.JFormattedTextField();
        txfTaxPercentageFixed = new javax.swing.JFormattedTextField();
        rdbFixed = new javax.swing.JRadioButton();
        rdbHourly = new javax.swing.JRadioButton();
        btnSaveAndClear = new javax.swing.JButton();
        btnSaveAndExit = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setClosable(true);
        setTitle("Add Employee Details");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/AddEmployeeIcon.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(760, 550));
        setMinimumSize(new java.awt.Dimension(760, 550));
        setPreferredSize(new java.awt.Dimension(760, 550));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
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

        BankInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bank Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel6.setText("Account Number");

        jLabel5.setText("Bank Name");

        try {
            txfAccountNumber.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txfAccountNumber.setToolTipText("");

        javax.swing.GroupLayout BankInformationPanelLayout = new javax.swing.GroupLayout(BankInformationPanel);
        BankInformationPanel.setLayout(BankInformationPanelLayout);
        BankInformationPanelLayout.setHorizontalGroup(
            BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BankInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txfAccountNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(txtBankName))
                .addContainerGap())
        );
        BankInformationPanelLayout.setVerticalGroup(
            BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BankInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBankName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txfAccountNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        PersonalInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel4.setText("   Residential");

        jLabel2.setText("First Name");

        jLabel3.setText("Last Name");

        jLabel15.setText("Date Of Birth");

        jLabel17.setText("Calculated Age");

        jLabel11.setText("Gender");

        txtFirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFirstNameKeyTyped(evt);
            }
        });

        txtLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLastNameKeyTyped(evt);
            }
        });

        txtAddress.setColumns(18);
        txtAddress.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txtAddress.setRows(2);
        jScrollPane1.setViewportView(txtAddress);

        txtCalculatedAge.setEditable(false);

        jLabel18.setText("   Address");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        buttonGroup2.add(rdbMale);
        rdbMale.setText("Male");

        buttonGroup2.add(rdbFemale);
        rdbFemale.setText("Female");

        try
        {
            txfDateOfBirth = new JFormattedTextField(new MaskFormatter("####-##-##"));
        }catch(java.text.ParseException e) {
            e.printStackTrace();
        }
        txfDateOfBirth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txfDateOfBirthFocusLost(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 51, 51));
        jLabel26.setText("yyyy-mm-dd");

        javax.swing.GroupLayout PersonalInformationPanelLayout = new javax.swing.GroupLayout(PersonalInformationPanel);
        PersonalInformationPanel.setLayout(PersonalInformationPanelLayout);
        PersonalInformationPanelLayout.setHorizontalGroup(
            PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                        .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel18)
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15)))
                        .addGap(11, 11, 11)
                        .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addComponent(rdbMale)
                                .addGap(18, 18, 18)
                                .addComponent(rdbFemale))
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txfDateOfBirth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                .addGap(14, 14, 14)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCalculatedAge, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(23, 23, 23)
                                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(24, 24, 24)
                                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PersonalInformationPanelLayout.setVerticalGroup(
            PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(20, 20, 20)
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel18))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                        .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txfDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15))
                            .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCalculatedAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PersonalInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(rdbMale, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbFemale))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ContactInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel14.setText("Contact Number");

        jLabel7.setText("E-mail");

        txtContactNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContactNumberKeyTyped(evt);
            }
        });

        txfEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txfEmailFocusLost(evt);
            }
        });

        javax.swing.GroupLayout ContactInformationPanelLayout = new javax.swing.GroupLayout(ContactInformationPanel);
        ContactInformationPanel.setLayout(ContactInformationPanelLayout);
        ContactInformationPanelLayout.setHorizontalGroup(
            ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContactInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        ContactInformationPanelLayout.setVerticalGroup(
            ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContactInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        EmployeeInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel9.setText("Join Date");

        jLabel10.setText("Position");

        jLabel1.setText("Employee ID");

        jLabel12.setText("EPF Number");

        jLabel8.setText("Department");

        jLabel16.setText("Working Status");

        txtEmployeeID.setEditable(false);

        try
        {
            txfJoinDate = new JFormattedTextField(new MaskFormatter("####-##-##"));
        }catch(java.text.ParseException e) {
            e.printStackTrace();
        }
        txfJoinDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txfJoinDateFocusLost(evt);
            }
        });

        cmbPosition.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbPosition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select position>", "Senior Programmmer", "Junior Programmer", "Software Designer", "System Architect", "QA Engineer", "Database Designer", "Lead Designer", "Network Administrator", "Assistant Manager", "Manager" }));

        cmbWorkingStatus.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbWorkingStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select status>", "Active", "Inactive", "On Leave" }));

        try {
            txfEPFNumber.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("UU/UUU/#####/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        cmbDepartment.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cmbDepartment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select department>", "Administraion", "Information Technology", "Design", "Quality Assurance", "Human Resources", "Engineering", "Research" }));
        cmbDepartment.setToolTipText("");

        jLabel28.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 51, 51));
        jLabel28.setText("yyyy-mm-dd");

        javax.swing.GroupLayout EmployeeInformationPanelLayout = new javax.swing.GroupLayout(EmployeeInformationPanel);
        EmployeeInformationPanel.setLayout(EmployeeInformationPanelLayout);
        EmployeeInformationPanelLayout.setHorizontalGroup(
            EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                        .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(25, 25, 25)
                        .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfJoinDate, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txfEPFNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbWorkingStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        EmployeeInformationPanelLayout.setVerticalGroup(
            EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel12)
                    .addComponent(txtEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfEPFNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(txfJoinDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16)
                    .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbWorkingStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jLabel13.setText("Salary Type");

        parentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salary Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        parentPanel.setLayout(new java.awt.CardLayout());

        HourlySalaryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hourly Salary Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        HourlySalaryPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                HourlySalaryPanelMousePressed(evt);
            }
        });

        jLabel29.setText("%");

        jLabel30.setText("Tax Percentage");

        jLabel31.setText("Overtime Eligibility");

        chkOvertimeHourlyEligible.setText("Eligible");
        chkOvertimeHourlyEligible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                chkOvertimeHourlyEligibleMousePressed(evt);
            }
        });
        chkOvertimeHourlyEligible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOvertimeHourlyEligibleActionPerformed(evt);
            }
        });

        OvertimeHourlyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Overtime Pay Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        OvertimeHourlyPanel.setEnabled(false);

        jLabel32.setText("Overtime Pay Rate");

        jLabel33.setText("LKR");

        txfOvertimeHourlyPayrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        javax.swing.GroupLayout OvertimeHourlyPanelLayout = new javax.swing.GroupLayout(OvertimeHourlyPanel);
        OvertimeHourlyPanel.setLayout(OvertimeHourlyPanelLayout);
        OvertimeHourlyPanelLayout.setHorizontalGroup(
            OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeHourlyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(44, 44, 44)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txfOvertimeHourlyPayrate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        OvertimeHourlyPanelLayout.setVerticalGroup(
            OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeHourlyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(txfOvertimeHourlyPayrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel34.setText("Hourly Rate");

        jLabel27.setText("LKR");

        txfHourlyRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        try{
            try {
                txfTaxPercentageHourly.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#.#")));
            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        javax.swing.GroupLayout HourlySalaryPanelLayout = new javax.swing.GroupLayout(HourlySalaryPanel);
        HourlySalaryPanel.setLayout(HourlySalaryPanelLayout);
        HourlySalaryPanelLayout.setHorizontalGroup(
            HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                .addComponent(OvertimeHourlyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txfHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                                .addComponent(txfTaxPercentageHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29))
                            .addComponent(chkOvertimeHourlyEligible))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HourlySalaryPanelLayout.setVerticalGroup(
            HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel27)
                    .addComponent(txfHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(txfTaxPercentageHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(chkOvertimeHourlyEligible))
                .addGap(17, 17, 17)
                .addComponent(OvertimeHourlyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        parentPanel.add(HourlySalaryPanel, "card2");

        FixedSalaryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fixed Salary Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        FixedSalaryPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FixedSalaryPanelMousePressed(evt);
            }
        });

        jLabel19.setText("Basic Salary");

        OvertimeFixedPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Overtime Pay Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N
        OvertimeFixedPanel.setEnabled(false);

        jLabel20.setText("Overtime Pay Rate");

        jLabel25.setText("LKR");

        txfOvertimeFixedPayrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        javax.swing.GroupLayout OvertimeFixedPanelLayout = new javax.swing.GroupLayout(OvertimeFixedPanel);
        OvertimeFixedPanel.setLayout(OvertimeFixedPanelLayout);
        OvertimeFixedPanelLayout.setHorizontalGroup(
            OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeFixedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(44, 44, 44)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txfOvertimeFixedPayrate, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        OvertimeFixedPanelLayout.setVerticalGroup(
            OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OvertimeFixedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel20)
                    .addComponent(txfOvertimeFixedPayrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel21.setText("Tax Percentage");

        jLabel22.setText("Overtime Eligibility");

        chkOvertimeFixedEligible.setText("Eligible");
        chkOvertimeFixedEligible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                chkOvertimeFixedEligibleMousePressed(evt);
            }
        });
        chkOvertimeFixedEligible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOvertimeFixedEligibleActionPerformed(evt);
            }
        });

        jLabel23.setText("%");

        jLabel24.setText("LKR");

        txfBasicSalary.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getIntegerInstance())));

        try{
            try {
                txfTaxPercentageFixed.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#.#")));
            } catch (java.text.ParseException ex) {
                ex.printStackTrace();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        javax.swing.GroupLayout FixedSalaryPanelLayout = new javax.swing.GroupLayout(FixedSalaryPanel);
        FixedSalaryPanel.setLayout(FixedSalaryPanelLayout);
        FixedSalaryPanelLayout.setHorizontalGroup(
            FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel21))
                                .addGap(18, 18, 18))
                            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24)
                                .addGap(3, 3, 3)))
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                                .addComponent(txfTaxPercentageFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))
                            .addComponent(txfBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkOvertimeFixedEligible))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(OvertimeFixedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 50, Short.MAX_VALUE))
        );
        FixedSalaryPanelLayout.setVerticalGroup(
            FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel24)
                    .addComponent(txfBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addComponent(txfTaxPercentageFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(chkOvertimeFixedEligible))
                .addGap(17, 17, 17)
                .addComponent(OvertimeFixedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        parentPanel.add(FixedSalaryPanel, "card2");

        buttonGroup1.add(rdbFixed);
        rdbFixed.setText("Fixed Salary");
        rdbFixed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbFixedActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbHourly);
        rdbHourly.setText("Hourly Salary");
        rdbHourly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbHourlyActionPerformed(evt);
            }
        });

        btnSaveAndClear.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSaveAndClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SaveAndClearIcon.png"))); // NOI18N
        btnSaveAndClear.setText("Save and Clear");
        btnSaveAndClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAndClearActionPerformed(evt);
            }
        });

        btnSaveAndExit.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSaveAndExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SaveAndExitIcon.png"))); // NOI18N
        btnSaveAndExit.setText("Save and Exit");
        btnSaveAndExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAndExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BankInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ContactInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PersonalInformationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addGap(47, 47, 47)
                        .addComponent(rdbFixed)
                        .addGap(18, 18, 18)
                        .addComponent(rdbHourly))
                    .addComponent(EmployeeInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSaveAndClear, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaveAndExit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(EmployeeInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(rdbFixed)
                            .addComponent(rdbHourly))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSaveAndExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveAndClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PersonalInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ContactInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BankInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdbFixedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbFixedActionPerformed
        parentPanel.removeAll();
        parentPanel.add(FixedSalaryPanel);
        SalaryTypeYesSelect();
        parentPanel.repaint();
        parentPanel.revalidate();
    }//GEN-LAST:event_rdbFixedActionPerformed

    private void rdbHourlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbHourlyActionPerformed
        parentPanel.removeAll();
        parentPanel.add(HourlySalaryPanel);
        SalaryTypeYesSelect();
        parentPanel.repaint();
        parentPanel.revalidate();
    }//GEN-LAST:event_rdbHourlyActionPerformed

    private void chkOvertimeHourlyEligibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOvertimeHourlyEligibleActionPerformed
        if (chkOvertimeHourlyEligible.isSelected())
        {
            OvertimeHourlyPanel.setEnabled(true);
            txfOvertimeHourlyPayrate.setEnabled(true);
        }
        if (!chkOvertimeHourlyEligible.isSelected())
        {
            OvertimeHourlyPanel.setEnabled(false);
            txfOvertimeHourlyPayrate.setEnabled(false);
            txfOvertimeHourlyPayrate.setText(null);
        }
    }//GEN-LAST:event_chkOvertimeHourlyEligibleActionPerformed

    private void chkOvertimeFixedEligibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOvertimeFixedEligibleActionPerformed
        if (chkOvertimeFixedEligible.isSelected())
        {
            OvertimeFixedPanel.setEnabled(true);
            txfOvertimeFixedPayrate.setEnabled(true);
        }
        if (!chkOvertimeFixedEligible.isSelected())
        {
            OvertimeFixedPanel.setEnabled(false);
            txfOvertimeFixedPayrate.setEnabled(false);
            txfOvertimeFixedPayrate.setText(null);
        }
    }//GEN-LAST:event_chkOvertimeFixedEligibleActionPerformed

    private void HourlySalaryPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HourlySalaryPanelMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_HourlySalaryPanelMousePressed

    private void FixedSalaryPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FixedSalaryPanelMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_FixedSalaryPanelMousePressed

    private void chkOvertimeHourlyEligibleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkOvertimeHourlyEligibleMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_chkOvertimeHourlyEligibleMousePressed

    private void chkOvertimeFixedEligibleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkOvertimeFixedEligibleMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_chkOvertimeFixedEligibleMousePressed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
        try
        {
            setTitle("Add Employee Details");
            DBConnection objDB=new DBConnection();
            con = objDB.getConnection();
        }
        catch(Exception exDB)
        {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: "+exDB, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        
        try
        {
            EmployeeIDGeneration();
        }
        catch(Exception exDB)
        {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: "+exDB, "Employee ID Generation Error", JOptionPane.ERROR_MESSAGE);
        }
        txfBasicSalary.setEnabled(false);        
        txfHourlyRate.setEnabled(false);
        txfOvertimeFixedPayrate.setEnabled(false);
        txfOvertimeHourlyPayrate.setEnabled(false);
        txfTaxPercentageFixed.setEnabled(false);
        txfTaxPercentageHourly.setEnabled(false);
        chkOvertimeFixedEligible.setEnabled(false);
        chkOvertimeHourlyEligible.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameOpened

    private void txfDateOfBirthFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txfDateOfBirthFocusLost
        int CurrentYear;
        CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (txfDateOfBirth.getText().contains(" "))
        {
            System.out.println("DOB contains spaces");
        }
        else
        {
            try
            {
                String AgeYearString = txfDateOfBirth.getText().substring(0, 4);
                int AgeYear = Integer.parseInt(AgeYearString);
                int PersonAge = CurrentYear - AgeYear;
                String Age = String.valueOf(PersonAge);
                txtCalculatedAge.setText(Age);
                if (PersonAge < 18)
                {
                    ParameterCheck = false;
                    System.out.print("Employee is under 18");
                    JOptionPane.showMessageDialog(PersonalInformationPanel, "Please enter a valid date of birth in the Date Of Birth field.\nThe Employee is under 18 years of age", "Employee is under 18", JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    ParameterCheck = true;
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(parentPanel, "Error: "+ ex,"Age Generation Error",JOptionPane.ERROR_MESSAGE);
            }

            try
            {
                GetDate obj = new GetDate();

                int year = Integer.parseInt(txfDateOfBirth.getText().substring(0, 4));
                int month = Integer.parseInt(txfDateOfBirth.getText().substring(5, 7));
                int dayOfMonth = Integer.parseInt(txfDateOfBirth.getText().substring(8, 10));
                // reuse the calendar to set user specified date
                obj.c.set(Calendar.YEAR, year);
                obj.c.set(Calendar.MONTH, month-1);
                obj.c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // and get that as a Date
                Date dateSpecified = obj.c.getTime();

                // test your condition
                if (dateSpecified.after(obj.todayNow)) 
                {
                    ParameterCheck = false;
                    JOptionPane.showMessageDialog(PersonalInformationPanel, "Date specified [" + dateSpecified + "] is after today's date [" + obj.todayNow + "]", "Specified Date ", JOptionPane.PLAIN_MESSAGE);
                    System.err.println("Date specified [" + dateSpecified + "] is after today [" + obj.todayNow + "]");
                } 
                else 
                {     
                    ParameterCheck = true;
                    System.err.println("Date specified [" + dateSpecified + "] is NOT before today [" + obj.todayNow + "]");
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(parentPanel, "Error "+ ex);
            }
        }
    }//GEN-LAST:event_txfDateOfBirthFocusLost

    private void txtFirstNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFirstNameKeyTyped
        char TestChar = evt.getKeyChar();
        if (!(Character.isAlphabetic(TestChar)))
            evt.consume();
    }//GEN-LAST:event_txtFirstNameKeyTyped

    private void txtLastNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLastNameKeyTyped
        char TestChar = evt.getKeyChar();
        if (!(Character.isAlphabetic(TestChar)))
            evt.consume();
    }//GEN-LAST:event_txtLastNameKeyTyped

    private void txtContactNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactNumberKeyTyped
    
        if (txtContactNumber.getText().length() >=10)
            evt.consume();
        char TestChar = evt.getKeyChar();
        if (!(Character.isDigit(TestChar)))
            evt.consume();
        
    }//GEN-LAST:event_txtContactNumberKeyTyped

    private void txfJoinDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txfJoinDateFocusLost
        
        if (txfJoinDate.getText().contains(" "))
        {
            System.out.println("JoinDate contains space");
        }
        else
        {
            try
            {
                GetDate obj = new GetDate();

                int year = Integer.parseInt(txfJoinDate.getText().substring(0, 4));
                int month = Integer.parseInt(txfJoinDate.getText().substring(5, 7));
                int dayOfMonth = Integer.parseInt(txfJoinDate.getText().substring(8, 10));
                // reuse the calendar to set user specified date
                obj.c.set(Calendar.YEAR, year);
                obj.c.set(Calendar.MONTH, month-1);
                obj.c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // and get that as a Date
                Date dateSpecified = obj.c.getTime();

                // test your condition
                if (dateSpecified.after(obj.todayNow)) 
                {
                    ParameterCheck = false;
                    JOptionPane.showMessageDialog(PersonalInformationPanel, "Date specified [" + dateSpecified + "] is after today's date [" + obj.todayNow + "]", "Specified Date ", JOptionPane.PLAIN_MESSAGE);
                    System.err.println("Date specified [" + dateSpecified + "] is after today [" + obj.todayNow + "]");
                } 
                else 
                {   
                    ParameterCheck = true;
                    System.err.println("Date specified [" + dateSpecified + "] is NOT before today [" + obj.todayNow + "]");
                }
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(parentPanel, "Error "+ ex);
            }
        }
    }//GEN-LAST:event_txfJoinDateFocusLost

    private void txfEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txfEmailFocusLost
        EmailValidator emailValidator = new EmailValidator();
        if (txfEmail.getText().isEmpty())
        {
            ParameterCheck = false;
        }
        else
        {
            if(!emailValidator.validate(txfEmail.getText().trim())) 
            {
                ParameterCheck = false;
                System.out.print("Invalid Email ID");
                JOptionPane.showMessageDialog(ContactInformationPanel, "Please enter a valid email address in the Email field\nExample : someone@example.com", "Invalid Email ID", JOptionPane.PLAIN_MESSAGE);
                /*
               Action that you want to take. For ex. make email id field red
               or give message box saying invalid email id.
                */
            }
            else
            {
                ParameterCheck = true;
            }
        }
    }//GEN-LAST:event_txfEmailFocusLost

    private void btnSaveAndExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAndExitActionPerformed
        SubmitCheck();
        SaveAction();
        EmployeeIDGeneration();
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnSaveAndExitActionPerformed

    private void btnSaveAndClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAndClearActionPerformed
        SubmitCheck();
        SaveAction();
        EmployeeIDGeneration();      
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtAddress.setText(null);
        txfDateOfBirth.setText(null);
        txtCalculatedAge.setText(null);
        txtContactNumber.setText(null);
        txfEmail.setText(null);
        txfAccountNumber.setText(null);
        txtBankName.setText(null);
        txfEPFNumber.setText(null);
        txfJoinDate.setText(null);
        txfBasicSalary.setText(null);
        txfHourlyRate.setText(null);
        txfOvertimeFixedPayrate.setText(null);
        txfOvertimeHourlyPayrate.setText(null);
        txfTaxPercentageFixed.setText(null);
        txfTaxPercentageHourly.setText(null);
        cmbDepartment.setSelectedIndex(0);
        cmbPosition.setSelectedIndex(0);
        cmbWorkingStatus.setSelectedIndex(0);
        chkOvertimeFixedEligible.setSelected(false);
        chkOvertimeHourlyEligible.setSelected(false);
        rdbFemale.setSelected(false);
        rdbMale.setSelected(false);
        rdbFixed.setSelected(false);
        rdbHourly.setSelected(false);
    }//GEN-LAST:event_btnSaveAndClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BankInformationPanel;
    private javax.swing.JPanel ContactInformationPanel;
    private javax.swing.JPanel EmployeeInformationPanel;
    private javax.swing.JPanel FixedSalaryPanel;
    private javax.swing.JPanel HourlySalaryPanel;
    private javax.swing.JPanel OvertimeFixedPanel;
    private javax.swing.JPanel OvertimeHourlyPanel;
    private javax.swing.JPanel PersonalInformationPanel;
    private javax.swing.JButton btnSaveAndClear;
    private javax.swing.JButton btnSaveAndExit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkOvertimeFixedEligible;
    private javax.swing.JCheckBox chkOvertimeHourlyEligible;
    private javax.swing.JComboBox cmbDepartment;
    private javax.swing.JComboBox cmbPosition;
    private javax.swing.JComboBox cmbWorkingStatus;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JRadioButton rdbFemale;
    private javax.swing.JRadioButton rdbFixed;
    private javax.swing.JRadioButton rdbHourly;
    private javax.swing.JRadioButton rdbMale;
    private javax.swing.JFormattedTextField txfAccountNumber;
    private javax.swing.JFormattedTextField txfBasicSalary;
    private javax.swing.JFormattedTextField txfDateOfBirth;
    private javax.swing.JFormattedTextField txfEPFNumber;
    private javax.swing.JFormattedTextField txfEmail;
    private javax.swing.JFormattedTextField txfHourlyRate;
    private javax.swing.JFormattedTextField txfJoinDate;
    private javax.swing.JFormattedTextField txfOvertimeFixedPayrate;
    private javax.swing.JFormattedTextField txfOvertimeHourlyPayrate;
    private javax.swing.JFormattedTextField txfTaxPercentageFixed;
    private javax.swing.JFormattedTextField txfTaxPercentageHourly;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtBankName;
    private javax.swing.JTextField txtCalculatedAge;
    private javax.swing.JTextField txtContactNumber;
    private javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    // End of variables declaration//GEN-END:variables
}
