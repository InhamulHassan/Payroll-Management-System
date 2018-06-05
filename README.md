Payroll Management System
=========================
This is a payroll management software made using Java and Microsoft SQL Server as the back-end RDBMS.

Functions:
- Add new employee details into the database.
- Modify existing employee details.
- Add/modify employee's salary details.
- Supports both fixed and hourly salary calculations.
- Generate indivdual employee pay-slip.
- Look up employee details and salary details stored in the database.
- User login capability [with admin access capabilities].
- Admin is able to add new users to the system.

Required:
- Netbeans IDE (7.4+).
- JDBC/ODBC Driver for Microsoft SQL Server.

Setup:
1. Execute the 'schema.sql' file in the sql folder.
2. Open the folder as a netbeans project.
3. Configure your ODBC data source:
	- Open ODBC Data Source Administrator.
	- Add a new data source.
	- Choose 'SQL Native Client 11.0' or 'SQL Server' or 'ODBC Driver 11 for SQL Server' as your driver.
	- Enter the name of the data source as 'SalaryManagementDB'.
	- Choose your Microsoft SQL Server as the server.
	- Provide your SQL Server Authentication credentials (if any), or proceed with Integrated Windows authentication.
	- Choose the default database name as 'SalaryManagement' (the database which you created earlier in Step 1.
	- Give it READWRITE permission.
	- Test the Data Source to ensure it is working.
	- Run the project through Netbeans IDE.

