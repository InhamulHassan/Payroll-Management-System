/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZCompany;

import java.sql.*;
import javax.swing.JOptionPane;

public class DBConnection {

    public Connection con;

    public Connection getConnection() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection("jdbc:odbc:SalaryManagementDB");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database " + ex);
        }
        return con;
    }
}
