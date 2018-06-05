create database SalaryManagement


create table UserTable
(
Username VARCHAR (20) NOT NULL UNIQUE,
[Password] VARCHAR (16) NOT NULL,
AccessType VARCHAR (20) NOT NULL,
PRIMARY KEY (Username)
)

insert into UserTable values ('SAdmin','Adm123S','Administrator')
insert into UserTable values ('DBAdmin','Database26','Administrator')
insert into UserTable values ('HREmployee','WeAreHR118','User')
insert into UserTable values ('ManagerHR','xyz123456','User')




create table EmployeeTable
(
EmployeeID VARCHAR(10) NOT NULL UNIQUE,
EmployeeFirstName VARCHAR(30) NOT NULL,
EmployeeLastName VARCHAR(30) NOT NULL,
Department VARCHAR(60) NOT NULL,
DateofJoining DATE NOT NULL,
WorkingPosition VARCHAR(60) NOT NULL,
EPFNumber VARCHAR(30) NOT NULL UNIQUE,
ResidentialAddress VARCHAR(80) NOT NULL,
Gender VARCHAR(10) NOT NULL,
BankAccountNumber VARCHAR(15) NOT NULL,
BankName VARCHAR(50) NOT NULL,
SalaryType VARCHAR(10) NOT NULL,
Email VARCHAR(40) NOT NULL,
ContactNumber VARCHAR(15) NOT NULL,
DateOfBirth DATE NOT NULL,
WorkingStatus VARCHAR(30) NOT NULL,
PRIMARY KEY (EmployeeID)
)


insert into EmployeeTable values ('E984783100', 'Prakash','Selvam','Administration','2010-10-05','Network Administrator', 'WP/CMB/22014/1003', '17B, Kotikawatta, Colombo','Male', '1103789645', 'Commercial Bank', 'Fixed', 'PRSelvam@hotmail.org', '0769891234','1988-10-12','Active')
insert into EmployeeTable values ('E984783101', 'Mohammed','Jameel','Information Technology','2011-01-05', 'Senior Programmer', 'WP/CMB/22014/1013', '1N, Borella, Colombo ','Male', '3268153687', 'Bank of Ceylon', 'Fixed', 'JameelMH@hotmail.com', '0759881237','1991-01-05','Active')
insert into EmployeeTable values ('E984783102', 'Rangith','Abeyagoon','Administration','2011-07-07', 'System Analyst', 'WP/GAM/22014/1004', 'B70, Pleenum Building, Gampaha','Male', '1793759691', 'Commercial Bank', 'Hourly', 'Rangith78@hmail.edu', '0778298238','1997-11-27','On Leave')
insert into EmployeeTable values ('E984783103', 'Anthony','Ranasinghe','Quality Assurance','2015-10-21', 'QA Engineer', 'WP/CMB/22014/1007', '8B, Dematagoda, Colombo','Male', '1108951126', 'Hatton National Bank', 'Fixed', 'Antho12Rana@gmail.com', '0761269871','1972-07-01','Inactive')
insert into EmployeeTable values ('E984783104', 'Ajay','Devsri','Design','2017-04-14', 'System Architect', 'UP/NGB/22014/1024', '1964, Beachfront Villa, Negombo','Male', '1017398647', 'Nations Trust Bank', 'Hourly', 'Dev1s@ymail.com', '0719894325','1978-02-02','Active')
insert into EmployeeTable values ('E984783105', 'Natalie','Perera','Human Resources','2016-11-25', 'Assistant Manager', 'WP/CMB/22014/1001', '9/2/C, Platinum Building, Colombo','Female', '1103789222', 'Seylan Bank', 'Fixed', 'NataliePerera@hotmail.org', '0769981345','1980-08-19','Active')
insert into EmployeeTable values ('E984783106', 'Piyumi','Kumari','Design','2016-01-25', 'Lead Designer', 'SP/GAL/22014/1010','589, Galle Road, Galle','Female', '7333789654', 'Cargills Bank', 'Fixed', 'PRumari8@rocketmail.com', '0720127158','1980-08-19','Inactive')







create table FixedSalaryTable
(
SalarySlipID VARCHAR(5) NOT NULL UNIQUE,
EmployeeID VARCHAR(10) NOT NULL,
BasicSalary MONEY NOT NULL,
SalaryMonth VARCHAR(15) NOT NULL,
EPF MONEY NOT NULL,
EPFDeduction MONEY NOT NULL,
ETF MONEY NOT NULL,
OvertimeRate MONEY,
OvertimeHours INT,
GrossSalary Money NOT NULL,
TaxRate MONEY NOT NULL,
NetSalary MONEY NOT NULL
PRIMARY KEY (SalarySlipID) 
)




create table HourlySalaryTable
(
SalarySlipID VARCHAR(5) NOT NULL UNIQUE,
EmployeeID VARCHAR(10) NOT NULL,
HoursWorked INT NOT NULL,
HourlyRate MONEY NOT NULL,
SalaryMonth VARCHAR(15) NOT NULL,
EPF MONEY NOT NULL,
EPFDeduction MONEY NOT NULL,
ETF MONEY NOT NULL,
OvertimeRate MONEY,
OvertimeHours INT,
GrossSalary MONEY NOT NULL,
TaxRate MONEY NOT NULL,
NetSalary MONEY NOT NULL
PRIMARY KEY (SalarySlipID) 
)










insert into FixedSalaryTable values('F1003','E984783100',78000.00,'March',2700.00,1000.00,700.00,2000.00,11,81000.00,1900.00,78100.00)
insert into FixedSalaryTable values('F1004','E984783103',60000.00,'February',2800.00,1300.00,800.00,3000.00,10,67000.00,2900.00,60100.00)
insert into FixedSalaryTable values('F1005','E984783101',87000.00,'March',4200.00,2100.00,1000.00,3500.00,7,99000.00,2200.00,82500.00)

insert into HourlySalaryTable values('H1003','E984783104',160,2850.00,'March',3200.00,1900.00,810.00,3490.00,18,82000.00,3850.00,78000.00)
insert into HourlySalaryTable values('H1004','E984783105',188,3200.00,'February',4000.00,2050.00,500.00,4400.00,12,89000.00,4020.00,89000.00)
insert into HourlySalaryTable values('H1005','E984783106',170,1800.00,'February',2900.00,750.00,620.00,3300.00,9,77000.00,3000.00,82000.00)























alter table FixedSalaryTable
add constraint FK_Employee_FixedSalary
FOREIGN KEY (EmployeeID) REFERENCES EmployeeTable (EmployeeID)








alter table HourlySalaryTable
add constraint FK_Employee_HourlySalary
FOREIGN KEY (EmployeeID) REFERENCES EmployeeTable (EmployeeID)
