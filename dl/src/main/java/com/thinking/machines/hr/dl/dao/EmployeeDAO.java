package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.util.*;
import com.thinking.machines.enums.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="Employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code");
DesignationDAOInterface designationDAO=new DesignationDAO();
if(!designationDAO.codeExists(designationCode)) throw new DAOException("Designation code does not exists");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("length of name is zero");
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender not specified : MALE/FEMALE");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
String lastGeneratedEmployeeIdString="";
String recordCountString="";
int lastGeneratedEmployeeId=10000000,recordCount=0;
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
String fpanNumber,faadharCardNumber;
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int e=0;e<7;e++) randomAccessFile.readLine();
fpanNumber=randomAccessFile.readLine();
faadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false && fpanNumber.equalsIgnoreCase(panNumber)) panNumberExists=true;
if(aadharCardNumberExists==false && faadharCardNumber.equalsIgnoreCase(aadharCardNumber)) aadharCardNumberExists=true;
if(aadharCardNumberExists && panNumberExists) break;
}
if(aadharCardNumberExists && panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+aadharCardNumber+") and Aadhar card number ("+panNumber+") exists");
}
if(aadharCardNumberExists) 
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number ("+panNumber+") exists");
}
lastGeneratedEmployeeId++;
String employeeId="A"+String.format("%-10d",lastGeneratedEmployeeId);
employeeId=employeeId.trim();
recordCount++;
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(sdf.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(String.valueOf(lastGeneratedEmployeeIdString)+"\n");
randomAccessFile.writeBytes(String.valueOf(recordCountString)+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}


}


public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("employeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code");
DesignationDAOInterface designationDAO=new DesignationDAO();
if(!designationDAO.codeExists(designationCode)) throw new DAOException("Designation code does not exists");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("length of name is zero");
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender not specified : MALE/FEMALE");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employeeId");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid employeeId");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
String fAadharCardNumber;
int x;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainst="";
String aadharCardNumberFoundAgainst="";
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(int e=0;e<6;e++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId)) employeeIdFound=true;
if(panNumberFound==false && fPanNumber.equalsIgnoreCase(panNumber))
{
panNumberFound=true;
panNumberFoundAgainst=fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAgainst=fEmployeeId;
}
if(employeeIdFound && aadharCardNumberFound && panNumberFound) break;
}
if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("invalid employee id");
}
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
if(panNumberFound && panNumberFoundAgainst.equalsIgnoreCase(employeeId)==false) panNumberExists=true;
if(aadharCardNumberFound && aadharCardNumberFoundAgainst.equalsIgnoreCase(employeeId)==false) aadharCardNumberExists=true;
if(aadharCardNumberExists && panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+aadharCardNumber+") and Aadhar card number ("+panNumber+") exists");
}
if(aadharCardNumberExists) 
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number ("+panNumber+") exists");
}
randomAccessFile.seek(foundAt);
for(int e=0;e<9;e++) randomAccessFile.readLine();
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length()) tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(sdf.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length()) randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}




}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("employeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employeeId");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid employeeId");
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
String fEmployeeId;
int x;
boolean employeeIdFound=false;
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(int e=0;e<8;e++) randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
break;
}
}
if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("invalid employee id");
}
randomAccessFile.seek(foundAt);
for(int e=0;e<9;e++) randomAccessFile.readLine();
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length()) tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length()) randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.seek(0);
randomAccessFile.readLine();
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}






}


public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> treeSet=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return treeSet;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) return treeSet;
randomAccessFile.readLine();
randomAccessFile.readLine();
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(sdf.parse(randomAccessFile.readLine()));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
treeSet.add(employeeDTO);
}
return treeSet;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface> treeSet=new TreeSet<>();
if(designationCode<=0) return treeSet;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return treeSet;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return treeSet;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String employeeId,name;
int fDesignationCode;
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeId=randomAccessFile.readLine();
name=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode)
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(fDesignationCode);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(sdf.parse(randomAccessFile.readLine()));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
treeSet.add(employeeDTO);
}else for(int e=0;e<6;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
return treeSet;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isDesignationAlloted(int designation) throws DAOException
{
if(designation<=0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
boolean designationExists=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designation)
{
designationExists=true;
}
for(int e=0;e<6;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
if(designationExists) return true;
return false;


}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("EmployeeId is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employeeId is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employeeId");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid employeeId");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(sdf.parse(randomAccessFile.readLine()));
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M') employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.MALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(int e=0;e<8;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("invalid employeeId");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid PAN number");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid PAN number");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPanNumber,employeeId,name,aadharCardNumber;
int designationCode;
Date dateOfBirth;
BigDecimal basicSalary;
boolean isIndian;
char gender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeId=randomAccessFile.readLine().trim();
name=randomAccessFile.readLine();
designationCode=Integer.parseInt(randomAccessFile.readLine());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
dateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
gender=randomAccessFile.readLine().charAt(0);
isIndian=Boolean.parseBoolean(randomAccessFile.readLine());
basicSalary=new BigDecimal(randomAccessFile.readLine());
fPanNumber=randomAccessFile.readLine();
aadharCardNumber=randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.MALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("invalid PAN number");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("aadhar card is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid aadhar card number");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("invalid aadhar card number");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String panNumber,employeeId,name,fAadharCardNumber;
int designationCode;
Date dateOfBirth;
BigDecimal basicSalary;
boolean isIndian;
char gender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeId=randomAccessFile.readLine().trim();
name=randomAccessFile.readLine();
designationCode=Integer.parseInt(randomAccessFile.readLine());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
try
{
dateOfBirth=sdf.parse(randomAccessFile.readLine());
}catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
gender=randomAccessFile.readLine().charAt(0);
isIndian=Boolean.parseBoolean(randomAccessFile.readLine());
basicSalary=new BigDecimal(randomAccessFile.readLine());
panNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
else employeeDTO.setGender(GENDER.MALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("invalid aadhar card number");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
randomAccessFile.close();
return true;
}
for(int e=0;e<8;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean PANNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPanNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int e=0;e<7;e++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fAadharCardNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int e=0;e<8;e++) randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
if(designationCode<=0) return 0;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int designationCount=0;
int fDesignationCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine().trim());
if(fDesignationCode==designationCode) designationCount++;
for(int e=0;e<6;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
return designationCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

}