package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.sql.*;
import com.thinking.machines.enums.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation code does not exists");
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} 
String name=employeeDTO.getName();
if(name==null)
{
try
{
connection.close();
throw new DAOException("name is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
name=name.trim();
if(name.length()==0) 
{
try
{
connection.close();
throw new DAOException("length of name is zero");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
throw new DAOException("date of birth is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
char gender=employeeDTO.getGender();
if(gender==' ')
{
try
{
connection.close();
throw new DAOException("Gender not specified : MALE/FEMALE");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
throw new DAOException("basic salary is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
if(basicSalary.signum()==-1)
{
try
{
connection.close();
throw new DAOException("basic salary is negative");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)
{
try
{
connection.close();
throw new DAOException("pan number is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
try
{
connection.close();
throw new DAOException("length of pan number is zero");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)
{
try
{
connection.close();
throw new DAOException("aadhar card number is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
try
{
connection.close();
throw new DAOException("length of aadhar card number is zero");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
try
{
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
preparedStatement=connection.prepareStatement("Select gender from employee where pan_card_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()) panNumberExists=true;
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("Select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()) aadharCardNumberExists=true;
resultSet.close();
preparedStatement.close();
if(aadharCardNumberExists && panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number ("+aadharCardNumber+") and PAN card number ("+panNumber+") exists");
}
if(aadharCardNumberExists) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number ("+panNumber+") exists");
}
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,gender,is_indian,pan_card_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeID=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
employeeDTO.setEmployeeId("A"+(1000000+generatedEmployeeID));
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("employeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
int actualEmployeeID=Integer.parseInt(employeeId.substring(1))-1000000;
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code");
Connection connection=null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection=DAOConnection.getConnection();
preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation code does not exists");
}
resultSet.close();
preparedStatement.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} 
String name=employeeDTO.getName();
if(name==null)
{
try
{
connection.close();
throw new DAOException("name is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
name=name.trim();
if(name.length()==0) 
{
try
{
connection.close();
throw new DAOException("length of name is zero");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
java.util.Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)
{
try
{
connection.close();
throw new DAOException("date of birth is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
char gender=employeeDTO.getGender();
if(gender==' ')
{
try
{
connection.close();
throw new DAOException("Gender not specified : MALE/FEMALE");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)
{
try
{
connection.close();
throw new DAOException("basic salary is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
if(basicSalary.signum()==-1)
{
try
{
connection.close();
throw new DAOException("basic salary is negative");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null)
{
try
{
connection.close();
throw new DAOException("pan number is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
try
{
connection.close();
throw new DAOException("length of pan number is zero");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null)
{
try
{
connection.close();
throw new DAOException("aadhar card number is null");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
try
{
connection.close();
throw new DAOException("length of aadhar card number is zero");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
try
{
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
preparedStatement=connection.prepareStatement("Select gender from employee where pan_card_number=? and employee_id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeID);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()) panNumberExists=true;
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("Select gender from employee where aadhar_card_number=? and employee_id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeID);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()) aadharCardNumberExists=true;
resultSet.close();
preparedStatement.close();
if(aadharCardNumberExists && panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number ("+aadharCardNumber+") and PAN card number ("+panNumber+") exists againts other employee");
}
if(aadharCardNumberExists) 
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists againts other employee");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("pan number ("+panNumber+") exists againts other employee");
}
preparedStatement=connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,basic_salary=?,gender=?,is_indian=?,pan_card_number=?,aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth=new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setBigDecimal(4,basicSalary);
preparedStatement.setString(5,String.valueOf(gender));
preparedStatement.setBoolean(6,isIndian);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeID);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}

}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("employeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
int actualEmployeeID;
try
{
actualEmployeeID=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee ID");
}
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
preparedStatement=connection.prepareStatement("select gender from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeID);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee ID");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeID);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from employees");
EmployeeDTOInterface employeeDTO;
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
if(designationCode<=0) return employees;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code : "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
EmployeeDTOInterface employeeDTO;
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}
public boolean isDesignationAlloted(int designation) throws DAOException
{
boolean designationAlloted=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designation);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code : "+designation);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select gender from employees where designation_code=?");
preparedStatement.setInt(1,designation);
resultSet=preparedStatement.executeQuery();
designationAlloted=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return designationAlloted;
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("EmployeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employeeId is zero");
int actualEmployeeId;
try
{
actualEmployeeID=Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid employee ID");
}
EmployeeDTOInterface employeeDTO;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from employees where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee ID : "+employeeId);
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN number is zero");
EmployeeDTOInterface employeeDTO;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from employees where pan_card_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN Card Number : "+panNumber);
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("aadhar card is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of aadhar card number is zero");
EmployeeDTOInterface employeeDTO;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from employees where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
java.sql.Date sqlDateOfBirth;
java.util.Date utilDateOfBirth;
String gender;
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber);
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth=resultSet.getDate("date_of_birth");
utilDateOfBirth=new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
gender=resultSet.getString("gender");
if(gender.equals("M")) employeeDTO.setGender(GENDER.MALE);
else if(gender.equals("F")) employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setPANNumber(resultSet.getString("pan_card_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeDTO;
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()==0) return false;
int actualEmployeeId=Integer.parseInt(employeeId.substring(1))-1000000;
boolean employeeIdExists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employees where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet=preparedStatement.executeQuery();
employeeIdExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employeeIdExists;
}
public boolean PANNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
boolean PanNumberExists=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employees where pan_card_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet=preparedStatement.executeQuery();
PanNumberExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return PanNumberExists;
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
boolean aadharCardNumberExistsFlag=false;
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select gender from employees where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet=preparedStatement.executeQuery();
aadharCardNumberExistsFlag=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return aadharCardNumberExistsFlag;
}
public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select count(*) as count from employee");
resultSet.next();
int count=resultSet.getInt("count");
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
if(designationCode<=0) return 0;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select count(*) as count from employee");
resultSet.next();
int count=resultSet.getInt("count");
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}