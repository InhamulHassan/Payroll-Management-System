/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZCompany;

import java.sql.*;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author RazR
 */
public class EditEmployeeDetails extends javax.swing.JInternalFrame {

    Connection con;
    boolean ParameterCheck = false;
    boolean Success = false;
    
    public EditEmployeeDetails() {
        initComponents();
        FrameUI();
    }

    private void FrameUI()
    {
        setSize(760, 550);
        jPanel1.setVisible(false);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setTitle("Edit Employee Details");
        setClosable(true);
        BasicInternalFrameUI basicInternalFrameUI = ((javax.swing.plaf.basic.BasicInternalFrameUI) getUI());
        for (MouseListener listener : basicInternalFrameUI.getNorthPane().getMouseListeners()) 
        {
            basicInternalFrameUI.getNorthPane().removeMouseListener(listener);
        }

    }
    
    public void EditAction()
    {
        String EmpID = cmbChooseEmployeeID.getSelectedItem().toString();
        if (ParameterCheck == false)
        {
            JOptionPane.showMessageDialog(parentPanel, "Please fill in the employee details form with valid data", "Employee Details Form Incomplete", JOptionPane.PLAIN_MESSAGE);
        }
        else if(ParameterCheck == true)
        {
            try
            {
                PreparedStatement stat=con.prepareStatement("UPDATE EmployeeTable SET EmployeeFirstName=?, EmployeeLastName=?, Department=?, DateOfJoining=?, WorkingPosition=?, ResidentialAddress=?, BankAccountNumber=?, BankName=?, Email=?, ContactNumber=?, DateOfBirth=?, WorkingStatus=? WHERE EmployeeID=?");
                stat.setString(1, txtFirstName.getText().toString());
                stat.setString(2, txtLastName.getText().toString());
                stat.setString(3, cmbDepartment.getSelectedItem().toString());
                stat.setString(4, txfJoinDate.getText().toString());
                stat.setString(5, cmbPosition.getSelectedItem().toString());
                stat.setString(6, txtAddress.getText().toString());
                stat.setString(7, txfAccountNumber.getText().toString());
                stat.setString(8, txtBankName.getText().toString());
                stat.setString(9, txfEmail.getText().toString());
                stat.setString(10, txtContactNumber.getText().toString());
                stat.setString(11, txfDateOfBirth.getText().toString());
                stat.setString(12, cmbWorkingStatus.getSelectedItem().toString());
                stat.setString(13, EmpID);
                int row = 0;
                row = stat.executeUpdate();
                if(row>0)
                {
                    Success=true;
                    System.out.println("Employee Database Updated");
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
            catch(SQLException sqlE)
            {
                JOptionPane.showMessageDialog(rootPane,"A Database exception error occured during saving\nError: "+sqlE,"Database Exception Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    public void FixedSalary()
    {
        
        int SalRow=0;
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
        double percentage = Double.valueOf(TaxRateFixed);
        double BasicSalaryInt = Double.valueOf(BasicSalary);
        double TaxAmount =  (BasicSalaryInt * (percentage/100));
        System.out.println("Tax = "+TaxAmount);
        
        try
        {
            PreparedStatement stat2 = con.prepareStatement("UPDATE FixedSalaryTable SET BasicSalary=?, OvertimeRate=?, TaxRate=? WHERE EmployeeID=?");
            stat2.setDouble(1, Double.valueOf(BasicSalary));
            if(chkOvertimeFixedEligible.isSelected())
            {
                stat2.setDouble(2, Double.valueOf(OvertimeFixed));    
            }
            else if(!chkOvertimeFixedEligible.isSelected())
            {
                stat2.setDouble(2, 0.00);    
            }
            stat2.setDouble(3, TaxAmount);
            stat2.setString(4, cmbChooseEmployeeID.getSelectedItem().toString().trim());
            
            SalRow = stat2.executeUpdate();
            if(SalRow>0)
            {
                Success = true;
                System.out.println("Fixed Salary Updated");
            }
            else
            {
                Success = false;
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
        double percentage = Double.valueOf(TaxRateHourly);
        double HourlySalaryInt = Double.valueOf(HourlySalary);
        double TaxAmount = (HourlySalaryInt * (percentage/100));
        System.out.println("Tax = "+TaxAmount);
        
        try
        {
            PreparedStatement stat2 = con.prepareStatement("UPDATE HourlySalaryTable SET HourlyRate=?, OvertimeRate=?, TaxRate=? WHERE EmployeeID=?");
            stat2.setDouble(1, Double.valueOf(HourlySalary));
            if(chkOvertimeHourlyEligible.isSelected())
            {
                stat2.setDouble(2, Double.valueOf((OvertimeHourly)));    
            }
            else if(!chkOvertimeHourlyEligible.isSelected())
            {
                stat2.setDouble(2, 0.00);    
            }
            stat2.setDouble(3, TaxAmount);
            stat2.setString(4, cmbChooseEmployeeID.getSelectedItem().toString().trim());
            
            SalRow = stat2.executeUpdate();
            if(SalRow>0)
            {
                Success = true;
                System.out.println("Hourly Salary Updated");
            }
            else 
            {
                Success = false;
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
    
    
    
     public void SubmitCheck()
    {
        if (txtFirstName.getText().isEmpty()||txtLastName.getText().isEmpty())
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(PersonalInformationPanel, "Please provide the employee's name in the relevant field", "Employee Name Field Empty", JOptionPane.ERROR_MESSAGE);
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
    
    public void EmployeeNameDatabase()
    {
        try
        {
            Statement st = con.createStatement();
            ResultSet rs=st.executeQuery("select * from EmployeeTable");

 
            while (rs.next()) {  
                cmbChooseEmployeeID.addItem(rs.getString("EmployeeID").trim()); 
                cmbChooseEPFNumber.addItem(rs.getString("EPFNumber").trim()); 
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+e,"Database Connection Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    public void SalaryTypeNoSelect()
    {
        if ((!rdbFixed.isSelected()) && !(rdbHourly.isSelected()))
        {
            ParameterCheck = false;
            JOptionPane.showMessageDialog(parentPanel, "Choose a valid Salary Type before filling out the Salary Information", "Salary Type not chosen", JOptionPane.INFORMATION_MESSAGE);
        }
        else
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
    

    public class EmailValidator {
        
        private Pattern pattern;
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
        jPanel1 = new javax.swing.JPanel();
        ContactInformationPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtContactNumber = new javax.swing.JTextField();
        txfEmail = new javax.swing.JFormattedTextField();
        rdbHourly = new javax.swing.JRadioButton();
        btnEditAndExit = new javax.swing.JButton();
        PersonalInformationPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        txtCalculatedAge = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txfDateOfBirth = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        BankInformationPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtBankName = new javax.swing.JTextField();
        txfAccountNumber = new javax.swing.JFormattedTextField();
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
        btnEditAndClear = new javax.swing.JButton();
        EmployeeInformationPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txfJoinDate = new javax.swing.JFormattedTextField();
        cmbPosition = new javax.swing.JComboBox();
        cmbWorkingStatus = new javax.swing.JComboBox();
        cmbDepartment = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        cmbChooseEmployeeID = new javax.swing.JComboBox();
        cmbChooseEPFNumber = new javax.swing.JComboBox();
        DisplayPanel = new javax.swing.JPanel();
        txtDisplayFullName = new javax.swing.JTextField();
        txtDisplayPosition = new javax.swing.JTextField();
        txtDisplayDepartment = new javax.swing.JTextField();
        txtDisplayStatus = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Edit Employee Details");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/EditEmpIcon.png"))); // NOI18N
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ContactInformationPanelLayout.setVerticalGroup(
            ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContactInformationPanelLayout.createSequentialGroup()
                .addGroup(ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ContactInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        buttonGroup1.add(rdbHourly);
        rdbHourly.setText("Hourly Salary");
        rdbHourly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbHourlyActionPerformed(evt);
            }
        });

        btnEditAndExit.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEditAndExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SaveAndExitIcon.png"))); // NOI18N
        btnEditAndExit.setText("Edit and Exit");
        btnEditAndExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAndExitActionPerformed(evt);
            }
        });

        PersonalInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel4.setText("Residential");

        jLabel2.setText("First Name");

        jLabel3.setText("Last Name");

        jLabel15.setText("Date Of Birth");

        jLabel17.setText("Calculated Age");

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

        txtAddress.setColumns(16);
        txtAddress.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        txtAddress.setLineWrap(true);
        txtAddress.setRows(2);
        txtAddress.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtAddress);

        txtCalculatedAge.setEditable(false);

        jLabel18.setText("Address");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

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
                .addContainerGap()
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                        .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29))
                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                        .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(11, 11, 11)
                                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                                        .addComponent(txfDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCalculatedAge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel18))
                        .addGap(0, 11, Short.MAX_VALUE))))
        );
        PersonalInformationPanelLayout.setVerticalGroup(
            PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PersonalInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel18))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PersonalInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtCalculatedAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addGap(18, 18, 18))
        );

        jLabel13.setText("Salary Type");

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
                .addGap(18, 18, 18)
                .addGroup(BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txfAccountNumber)
                    .addComponent(txtBankName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        BankInformationPanelLayout.setVerticalGroup(
            BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BankInformationPanelLayout.createSequentialGroup()
                .addGroup(BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBankName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BankInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txfAccountNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        try{
            txfOvertimeHourlyPayrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
            .addGroup(OvertimeHourlyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel32)
                .addComponent(jLabel33)
                .addComponent(txfOvertimeHourlyPayrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel34.setText("Hourly Rate");

        jLabel27.setText("LKR");

        try{
            txfHourlyRate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try {
            txfTaxPercentageHourly.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#.#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
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
                        .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(chkOvertimeHourlyEligible))
                            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(txfTaxPercentageHourly, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HourlySalaryPanelLayout.setVerticalGroup(
            HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HourlySalaryPanelLayout.createSequentialGroup()
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel27)
                    .addComponent(txfHourlyRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(txfTaxPercentageHourly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HourlySalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(chkOvertimeHourlyEligible))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(OvertimeHourlyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        try{
            txfOvertimeFixedPayrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0"))));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
            .addGroup(OvertimeFixedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel25)
                .addComponent(jLabel20)
                .addComponent(txfOvertimeFixedPayrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        try{
            txfBasicSalary.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try {
            txfTaxPercentageFixed.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#.#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout FixedSalaryPanelLayout = new javax.swing.GroupLayout(FixedSalaryPanel);
        FixedSalaryPanel.setLayout(FixedSalaryPanelLayout);
        FixedSalaryPanelLayout.setHorizontalGroup(
            FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, FixedSalaryPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18))
                            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24)
                                .addGap(3, 3, 3)))
                        .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkOvertimeFixedEligible)
                            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                                .addComponent(txfTaxPercentageFixed, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23))))
                    .addComponent(OvertimeFixedPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 50, Short.MAX_VALUE))
            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FixedSalaryPanelLayout.setVerticalGroup(
            FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FixedSalaryPanelLayout.createSequentialGroup()
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel24)
                    .addComponent(txfBasicSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addComponent(txfTaxPercentageFixed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FixedSalaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(chkOvertimeFixedEligible))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        btnEditAndClear.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEditAndClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XYZCompany/SaveAndClearIcon.png"))); // NOI18N
        btnEditAndClear.setText("Edit and Clear");
        btnEditAndClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAndClearActionPerformed(evt);
            }
        });

        EmployeeInformationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        jLabel9.setText("Join Date");

        jLabel10.setText("Position");

        jLabel8.setText("Department");

        jLabel16.setText("Working Status");

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

        cmbPosition.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cmbPosition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select position>", "Senior Programmmer", "Junior Programmer", "Software Designer", "System Architect", "QA Engineer", "Database Designer", "Lead Designer", "Network Administrator", "Assistant Manager", "Manager" }));

        cmbWorkingStatus.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cmbWorkingStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select status>", "Active", "Inactive", "On Leave" }));

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
                        .addComponent(jLabel10)
                        .addGap(33, 33, 33)
                        .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(cmbWorkingStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(25, 25, 25)
                        .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfJoinDate, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        EmployeeInformationPanelLayout.setVerticalGroup(
            EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EmployeeInformationPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(txfJoinDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addGroup(EmployeeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16)
                    .addComponent(cmbPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbWorkingStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(ContactInformationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PersonalInformationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(BankInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnEditAndClear, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditAndExit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EmployeeInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel13)
                        .addGap(47, 47, 47)
                        .addComponent(rdbFixed)
                        .addGap(18, 18, 18)
                        .addComponent(rdbHourly)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EmployeeInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(rdbFixed)
                            .addComponent(rdbHourly))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(parentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditAndClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditAndExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(PersonalInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ContactInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BankInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Select an Employee:");

        jLabel36.setText("Employee ID: ");

        jLabel38.setText("EPF Number: ");

        cmbChooseEmployeeID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select employee ID>" }));
        cmbChooseEmployeeID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbChooseEmployeeIDItemStateChanged(evt);
            }
        });
        cmbChooseEmployeeID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChooseEmployeeIDActionPerformed(evt);
            }
        });

        cmbChooseEPFNumber.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<select EPF number>" }));
        cmbChooseEPFNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbChooseEPFNumberActionPerformed(evt);
            }
        });

        txtDisplayFullName.setEditable(false);
        txtDisplayFullName.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
        txtDisplayFullName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDisplayFullName.setToolTipText("");
        txtDisplayFullName.setBorder(null);

        txtDisplayPosition.setEditable(false);
        txtDisplayPosition.setFont(new java.awt.Font("Arial Narrow", 1, 12)); // NOI18N
        txtDisplayPosition.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDisplayPosition.setBorder(null);

        txtDisplayDepartment.setEditable(false);
        txtDisplayDepartment.setFont(new java.awt.Font("Arial Narrow", 1, 12)); // NOI18N
        txtDisplayDepartment.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDisplayDepartment.setBorder(null);

        txtDisplayStatus.setEditable(false);
        txtDisplayStatus.setFont(new java.awt.Font("Arial Narrow", 1, 12)); // NOI18N
        txtDisplayStatus.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDisplayStatus.setBorder(null);

        javax.swing.GroupLayout DisplayPanelLayout = new javax.swing.GroupLayout(DisplayPanel);
        DisplayPanel.setLayout(DisplayPanelLayout);
        DisplayPanelLayout.setHorizontalGroup(
            DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayPanelLayout.createSequentialGroup()
                .addContainerGap(261, Short.MAX_VALUE)
                .addGroup(DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDisplayPosition, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDisplayFullName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDisplayDepartment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDisplayStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        DisplayPanelLayout.setVerticalGroup(
            DisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DisplayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDisplayFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDisplayPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDisplayDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDisplayStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel38))
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbChooseEPFNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbChooseEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(cmbChooseEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(cmbChooseEPFNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(DisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtContactNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactNumberKeyTyped
        char TestChar = evt.getKeyChar();
        if (!(Character.isDigit(TestChar)))
        evt.consume();
    }//GEN-LAST:event_txtContactNumberKeyTyped

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

    private void rdbHourlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbHourlyActionPerformed
        parentPanel.removeAll();
        parentPanel.add(HourlySalaryPanel);
        SalaryTypeYesSelect();
        parentPanel.repaint();
        parentPanel.revalidate();
    }//GEN-LAST:event_rdbHourlyActionPerformed

    private void btnEditAndExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAndExitActionPerformed
        SubmitCheck();
        EditAction();
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnEditAndExitActionPerformed

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
                JOptionPane.showMessageDialog(parentPanel, "Error: "+ ex);
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

    private void chkOvertimeFixedEligibleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkOvertimeFixedEligibleMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_chkOvertimeFixedEligibleMousePressed

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
        }
    }//GEN-LAST:event_chkOvertimeFixedEligibleActionPerformed

    private void FixedSalaryPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FixedSalaryPanelMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_FixedSalaryPanelMousePressed

    private void chkOvertimeHourlyEligibleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkOvertimeHourlyEligibleMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_chkOvertimeHourlyEligibleMousePressed

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
        }
    }//GEN-LAST:event_chkOvertimeHourlyEligibleActionPerformed

    private void HourlySalaryPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HourlySalaryPanelMousePressed
        SalaryTypeNoSelect();
    }//GEN-LAST:event_HourlySalaryPanelMousePressed

    private void rdbFixedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbFixedActionPerformed
        parentPanel.removeAll();
        parentPanel.add(FixedSalaryPanel);
        SalaryTypeYesSelect();
        parentPanel.repaint();
        parentPanel.revalidate();
    }//GEN-LAST:event_rdbFixedActionPerformed

    private void btnEditAndClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAndClearActionPerformed
        SubmitCheck();
        EditAction();
        cmbChooseEmployeeID.setSelectedIndex(0);
        cmbChooseEPFNumber.setSelectedIndex(0);
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtAddress.setText(null);
        txfDateOfBirth.setText(null);
        txtCalculatedAge.setText(null);
        txtContactNumber.setText(null);
        txfEmail.setText(null);
        txfAccountNumber.setText(null);
        txtBankName.setText(null);
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
        rdbFixed.setSelected(false);
        rdbHourly.setSelected(false);
    }//GEN-LAST:event_btnEditAndClearActionPerformed

    private void txfJoinDateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txfJoinDateFocusLost

        if (txfJoinDate.getText().contains(" "))
        {
            System.out.println("Join Date contains spaces");
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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        setTitle("Edit Employee Details");
        try
        {
        DBConnection objDB=new DBConnection();
        con = objDB.getConnection();
        }
        catch(Exception exDB)
        {
            JOptionPane.showMessageDialog(rootPane, "Exception error occured during execution\nError: "+exDB, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
        
        EmployeeNameDatabase();
    }//GEN-LAST:event_formInternalFrameOpened

    private void cmbChooseEmployeeIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbChooseEmployeeIDActionPerformed
        String ComboEmployeeID = cmbChooseEmployeeID.getSelectedItem().toString();
        String ComboEPFNumber = "";
        if (ComboEmployeeID.startsWith("<"))
        {
            cmbChooseEPFNumber.setSelectedIndex(0);
            jPanel1.setVisible(false);
            txtDisplayFullName.setText(null);
            txtDisplayDepartment.setText(null);
            txtDisplayPosition.setText(null);
            txtDisplayStatus.setText(null);
        }
        else
        {
            jPanel1.setVisible(true);
        }
        try
        {
            PreparedStatement stat = con.prepareStatement("select * from EmployeeTable where EmployeeID=?");
            stat.setString(1, ComboEmployeeID);
                
            ResultSet rs = stat.executeQuery();
 
            while (rs.next()) {  
                ComboEPFNumber = rs.getString("EPFNumber");
                for (int i=1; i<cmbChooseEPFNumber.getItemCount();i++)
                {
                    if (cmbChooseEPFNumber.getItemAt(i).equals(ComboEPFNumber))
                    {
                        cmbChooseEPFNumber.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+e,"Employee ID Generation Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_cmbChooseEmployeeIDActionPerformed

    private void cmbChooseEPFNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbChooseEPFNumberActionPerformed
        String ComboEPFNumber = cmbChooseEPFNumber.getSelectedItem().toString();
        String ComboIDNumber = "";
        if (ComboEPFNumber.startsWith("<"))
        {
            cmbChooseEmployeeID.setSelectedIndex(0);
            jPanel1.setVisible(false);
            txtDisplayFullName.setText(null);
            txtDisplayDepartment.setText(null);
            txtDisplayPosition.setText(null);
            txtDisplayStatus.setText(null);
        }
        else
        {
            jPanel1.setVisible(true);
        }
        try
        {
            PreparedStatement stat = con.prepareStatement("select * from EmployeeTable where EPFNumber=?");
            stat.setString(1, ComboEPFNumber);
                
            ResultSet rs = stat.executeQuery();
 
            while (rs.next()) {  
                ComboIDNumber = rs.getString("EmployeeID").trim();
                for (int i=1; i<cmbChooseEmployeeID.getItemCount();i++)
                {
                    if(cmbChooseEmployeeID.getItemAt(i).equals(ComboIDNumber))
                    {
                        cmbChooseEmployeeID.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(rootPane,"Exception error occured during execution\nError: "+e,"EPF Number Generation Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_cmbChooseEPFNumberActionPerformed

    private void cmbChooseEmployeeIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbChooseEmployeeIDItemStateChanged
        String EmployeeID = cmbChooseEmployeeID.getSelectedItem().toString().trim();
        String SalaryType = null;
        try
        {
            PreparedStatement stat=con.prepareStatement("SELECT * FROM EmployeeTable WHERE EmployeeID=?");
            stat.setString(1, EmployeeID);
            ResultSet rs=stat.executeQuery();
            
            while (rs.next())        
            {
                txtFirstName.setText(rs.getString("EmployeeFirstName").trim());
                txtLastName.setText(rs.getString("EmployeeLastName").trim());
                String Department = rs.getString("Department").trim();
                txfJoinDate.setText(rs.getDate("DateOfJoining").toString());
                String WorkingPosition = rs.getString("WorkingPosition").trim();
                txtAddress.setText(rs.getString("ResidentialAddress").trim());
                txfAccountNumber.setText(rs.getString("BankAccountNumber").trim());
                txtBankName.setText(rs.getString("BankName").trim());
                SalaryType = rs.getString("SalaryType").trim();
                txfEmail.setText(rs.getString("Email").trim());
                txtContactNumber.setText(rs.getString("ContactNumber").trim());
                txfDateOfBirth.setText(rs.getDate("DateOfBirth").toString());
                String WorkingStatus = rs.getString("WorkingStatus").trim();
                
                txtDisplayFullName.setText((txtLastName.getText()+", "+txtFirstName.getText()).trim());
                txtDisplayDepartment.setText(Department);
                txtDisplayStatus.setText(WorkingStatus);
                txtDisplayPosition.setText(WorkingPosition);
                cmbDepartment.setEditable(true);
                cmbWorkingStatus.setEditable(true);
                cmbPosition.setEditable(true);
                cmbDepartment.setSelectedItem(Department);
                cmbWorkingStatus.setSelectedItem(WorkingStatus);
                cmbPosition.setSelectedItem(WorkingPosition);
                System.out.println("SalaryType = "+SalaryType);
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(rootPane, "An error occured during the Employee Detail Generation\n"+ex);
        }
        
        try
        {
            if(SalaryType.equals("Fixed"))
            {
                rdbFixed.setSelected(true);
                FixedSalaryPanel.setVisible(true);
                HourlySalaryPanel.setVisible(false);
                txfHourlyRate.setText(null);
                txfOvertimeHourlyPayrate.setText(null);
                chkOvertimeHourlyEligible.setSelected(false);
                txfOvertimeHourlyPayrate.setEnabled(false);
                txfTaxPercentageHourly.setText(null);
                PreparedStatement stat1=con.prepareStatement("SELECT * FROM FixedSalaryTable WHERE EmployeeID=?");
                stat1.setString(1, EmployeeID);
                ResultSet rs1=stat1.executeQuery();
                while(rs1.next())
                {
                    double BasicSalary = rs1.getInt("BasicSalary");
                    int OvertimeFixed = rs1.getInt("OvertimeRate");
                    double TaxRate = rs1.getInt("TaxRate");
                    double TaxPercentage = (double) ((TaxRate/BasicSalary)*100);
                    txfBasicSalary.setText(String.valueOf(BasicSalary));
                    txfTaxPercentageFixed.setText(String.valueOf(TaxPercentage));
                    System.out.println("TaxPercentage = "+TaxPercentage);
                    if(OvertimeFixed == 0)
                    {
                        chkOvertimeFixedEligible.setSelected(false);
                        FixedSalaryPanel.setEnabled(false);
                        txfOvertimeFixedPayrate.setEnabled(false);
                        txfOvertimeFixedPayrate.setText(null);
                    }
                    else
                    {
                        chkOvertimeFixedEligible.setSelected(true);
                        FixedSalaryPanel.setEnabled(true);
                        txfOvertimeFixedPayrate.setEnabled(true);
                        txfOvertimeFixedPayrate.setText(String.valueOf(OvertimeFixed));
                    }
                    
                }
            }
                
            else if(SalaryType.equals("Hourly"))    
            {
                rdbHourly.setSelected(true);
                HourlySalaryPanel.setVisible(true);
                FixedSalaryPanel.setVisible(false);
                txfBasicSalary.setText(null);
                txfOvertimeFixedPayrate.setText(null);
                txfOvertimeFixedPayrate.setEnabled(false);
                chkOvertimeFixedEligible.setSelected(false);
                txfTaxPercentageFixed.setText(null);
                PreparedStatement stat2=con.prepareStatement("SELECT * FROM HourlySalaryTable WHERE EmployeeID=?");
                stat2.setString(1, EmployeeID);
                ResultSet rs2=stat2.executeQuery();
                while(rs2.next())
                {
                    double HourlyRate = rs2.getDouble("HourlyRate");
                    int OvertimeHourly = rs2.getInt("OvertimeRate");
                    double TaxRate = rs2.getDouble("TaxRate");
                    double TaxPercentageHourly = (double) ((TaxRate/HourlyRate)*100);
                    txfHourlyRate.setText(String.valueOf((HourlyRate)));
                    txfTaxPercentageHourly.setText(String.valueOf(TaxPercentageHourly));
                    System.out.println("TaxPercentageHourly = "+TaxPercentageHourly);
                    if(OvertimeHourly == 0)
                    {
                        chkOvertimeHourlyEligible.setSelected(false);
                        HourlySalaryPanel.setEnabled(false);
                        txfOvertimeHourlyPayrate.setEnabled(false);
                        txfOvertimeHourlyPayrate.setText(null);
                    }
                    else
                    {
                        chkOvertimeHourlyEligible.setSelected(true);
                        HourlySalaryPanel.setEnabled(true);
                        txfOvertimeHourlyPayrate.setEnabled(true);
                        txfOvertimeHourlyPayrate.setText(String.valueOf(OvertimeHourly));
                    }
                }
            }
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(rootPane, "An error occured during the Employee Salary Details Generation\n"+ex);
        }
        catch(NullPointerException n)
        {
            System.out.println("No Type, null type - "+n);
        }
    }//GEN-LAST:event_cmbChooseEmployeeIDItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BankInformationPanel;
    private javax.swing.JPanel ContactInformationPanel;
    private javax.swing.JPanel DisplayPanel;
    private javax.swing.JPanel EmployeeInformationPanel;
    private javax.swing.JPanel FixedSalaryPanel;
    private javax.swing.JPanel HourlySalaryPanel;
    private javax.swing.JPanel OvertimeFixedPanel;
    private javax.swing.JPanel OvertimeHourlyPanel;
    private javax.swing.JPanel PersonalInformationPanel;
    private javax.swing.JButton btnEditAndClear;
    private javax.swing.JButton btnEditAndExit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkOvertimeFixedEligible;
    private javax.swing.JCheckBox chkOvertimeHourlyEligible;
    private javax.swing.JComboBox cmbChooseEPFNumber;
    private javax.swing.JComboBox cmbChooseEmployeeID;
    private javax.swing.JComboBox cmbDepartment;
    private javax.swing.JComboBox cmbPosition;
    private javax.swing.JComboBox cmbWorkingStatus;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JRadioButton rdbFixed;
    private javax.swing.JRadioButton rdbHourly;
    private javax.swing.JFormattedTextField txfAccountNumber;
    private javax.swing.JFormattedTextField txfBasicSalary;
    private javax.swing.JFormattedTextField txfDateOfBirth;
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
    private javax.swing.JTextField txtDisplayDepartment;
    private javax.swing.JTextField txtDisplayFullName;
    private javax.swing.JTextField txtDisplayPosition;
    private javax.swing.JTextField txtDisplayStatus;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    // End of variables declaration//GEN-END:variables
}
