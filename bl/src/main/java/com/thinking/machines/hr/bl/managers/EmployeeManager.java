package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManager employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructure();
}
private void populateDataStructure() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
if(dlEmployee.getGender()=='M' || dlEmployee.getGender()=='m') employee.setGender(GENDER.MALE);
else if(dlEmployee.getGender()=='F' || dlEmployee.getGender()=='f') employee.setGender(GENDER.FEMALE);
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}

public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee required");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId!=null && employeeId.trim().length()!=0) blException.addException("employeeId","employee id should be NIL/empty");
String name=employee.getName();
if(name==null)
{
blException.addException("name","name is required");
name="";
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","name is required");
}
int designationCode;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation=employee.getDesignation();
if(designation==null) blException.addException("designation","designation is required");
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false) blException.addException("designation","invalid designation");
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null) blException.addException("date of birth","date of birth is required");
char gender=employee.getGender();
if(gender==' ' || (gender!='m' && gender!='f')) blException.addException("gender","gender is invalid");
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null || basicSalary.signum()==-1) blException.addException("basic salary","invalid salary");
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("PAN Number","invalid pan number");
panNumber="";
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("PAN Number","invalid pan number");
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadhar card number","invalid aadhar card number");
aadharCardNumber="";
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadhar card number","invalid aadhar card number");
}
if(panNumber.length()>0) if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())) blException.addException("pan number","PAN Number "+panNumber+" exists");
if(aadharCardNumber.length()>0) if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase())) blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" exists");
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designation.getCode());
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M' || gender=='m') employeeDTO.setGender(GENDER.MALE);
else if(gender=='F' || gender=='f') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.add(employeeDTO);
employee.setEmployeeId(employeeDTO.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
if(employee.getGender()=='M' || employee.getGender()=='m') dsEmployee.setGender(GENDER.MALE);
else if(employee.getGender()=='F' || employee.getGender()=='f') dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee required");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId==null) blException.addException("employee Id","employee Id is required");
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employee Id","employee Id is required");
}
if(blException.hasExceptions()) throw blException;
String name=employee.getName();
if(name==null)
{
blException.addException("name","name is required");
name="";
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","name is required");
}
int designationCode;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation=employee.getDesignation();
if(designation==null) blException.addException("designation","designation is required");
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false) blException.addException("designation","invalid designation");
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null) blException.addException("date of birth","date of birth is required");
char gender=employee.getGender();
if(gender==' ' || (gender!='m' && gender!='f')) blException.addException("gender","gender is invalid");
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null || basicSalary.signum()==-1) blException.addException("basic salary","invalid salary");
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("PAN Number","invalid pan number");
panNumber="";
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("PAN Number","invalid pan number");
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadhar card number","invalid aadhar card number");
aadharCardNumber="";
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadhar card number","invalid aadhar card number");
}
if(employeeId.length()>0) if(!(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase()))) blException.addException("employee Id","invalid employee Id");
EmployeeInterface tmpEmployee;
if(panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))
{
tmpEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(!(tmpEmployee.getEmployeeId().equalsIgnoreCase(employeeId))) blException.addException("pan number","PAN Number "+panNumber+" exists against : "+tmpEmployee.getEmployeeId());
}
}
if(aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase()))
{
tmpEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(!(tmpEmployee.getEmployeeId().equalsIgnoreCase(employeeId)))  blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" exists against : "+tmpEmployee.getEmployeeId());
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designation.getCode());
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M' || gender=='m') employeeDTO.setGender(GENDER.MALE);
else if(gender=='F' || gender=='f') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
tmpEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
if(employee.getGender()=='M' || employee.getGender()=='m') dsEmployee.setGender(GENDER.MALE);
else if(employee.getGender()=='F' || employee.getGender()=='f') dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employeeIdWiseEmployeesMap.remove(tmpEmployee.getEmployeeId().toUpperCase());
panNumberWiseEmployeesMap.remove(tmpEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(tmpEmployee.getAadharCardNumber().toUpperCase());
employeesSet.remove(tmpEmployee);
Set<EmployeeInterface> tmpets;
tmpets=designationCodeWiseEmployeesMap.get(tmpEmployee.getDesignation().getCode());
tmpets.remove(tmpEmployee);
if(tmpets.size()==0) designationCodeWiseEmployeesMap.remove(tmpEmployee.getDesignation().getCode());
employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),dsEmployee);
panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),dsEmployee);
aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),dsEmployee);
employeesSet.add(dsEmployee);
tmpets=this.designationCodeWiseEmployeesMap.get(employee.getDesignation().getCode());
if(tmpets==null)
{
tmpets=new TreeSet<>();
tmpets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),tmpets);
}
else
{
tmpets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null) blException.addException("employee Id","employee Id is required");
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employee Id","employee Id is required");
}
if(employeeId.length()>0) if(!(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase()))) blException.addException("employee Id","invalid employee Id");
if(blException.hasExceptions()) throw blException;
EmployeeInterface tmpEmployee;
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
tmpEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
employeeIdWiseEmployeesMap.remove(tmpEmployee.getEmployeeId().toUpperCase());
panNumberWiseEmployeesMap.remove(tmpEmployee.getPANNumber().toUpperCase());
aadharCardNumberWiseEmployeesMap.remove(tmpEmployee.getAadharCardNumber().toUpperCase());
employeesSet.remove(tmpEmployee);
Set<EmployeeInterface> tmpets;
tmpets=designationCodeWiseEmployeesMap.get(tmpEmployee.getDesignation().getCode());
tmpets.remove(tmpEmployee);
if(tmpets.size()==0) designationCodeWiseEmployeesMap.remove(tmpEmployee.getDesignation().getCode());
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null) blException.addException("employee ID","invalid employee ID");
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employeeId","invalid employee ID");
}
if(blException.hasExceptions()) throw blException;
EmployeeInterface employee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(employee==null)
{
blException.addException("employee ID","employee ID does not exists");
throw blException;
}
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(designationManager.getDesignationByCode(employee.getDesignation().getCode()));
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
if(employee.getGender()=='m') dsEmployee.setGender(GENDER.MALE);
else dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dsEmployee;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
if(panNumber==null) blException.addException("PAN Number","invalid PAN Number");
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("PAN Number","invalid PAN Number");
}
if(blException.hasExceptions()) throw blException;
EmployeeInterface employee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(employee==null)
{
blException.addException("PAN Number","PAN Number does not exists");
throw blException;
}
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(designationManager.getDesignationByCode(employee.getDesignation().getCode()));
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
if(employee.getGender()=='m') dsEmployee.setGender(GENDER.MALE);
else dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dsEmployee;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
if(aadharCardNumber==null) blException.addException("aadhar card number","invalid aadhar card number");
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadhar card number","invalid aadhar card number");
}
if(blException.hasExceptions()) throw blException;
EmployeeInterface employee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(employee==null)
{
blException.addException("aadhar card number","aadhar card number does not exists");
throw blException;
}
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
dsEmployee.setDesignation(designationManager.getDesignationByCode(employee.getDesignation().getCode()));
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
if(employee.getGender()=='m') dsEmployee.setGender(GENDER.MALE);
else dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dsEmployee;
}
public int getEmployeeCount()
{
return employeesSet.size();
}
public boolean employeeIdExists(String employeeId)
{
return (employeeIdWiseEmployeesMap.containsKey(employeeId)==true);
}
public boolean employeePANNumberExists(String panNumber)
{
return (panNumberWiseEmployeesMap.containsKey(panNumber)==true);
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return (aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber)==true);
}
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
employeesSet.forEach((employee)->{
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
DesignationInterface dsDesignation=employee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
dsEmployee.setDesignation(designation);
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
if(employee.getGender()=='m') dsEmployee.setGender(GENDER.MALE);
else dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(dsEmployee);
});
return employees;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
BLException blException=new BLException();
blException.setGenericException("Invalid Designation Code");
throw blException;
}
Set<EmployeeInterface> employees=new TreeSet<>();
ets.forEach((employee)->{
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
DesignationInterface dsDesignation=employee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
dsEmployee.setDesignation(designation);
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
if(employee.getGender()=='m') dsEmployee.setGender(GENDER.MALE);
else dsEmployee.setGender(GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(dsEmployee);
});
return employees;
}
public int getEmployeesCountByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets;
ets=designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
BLException blException=new BLException();
blException.setGenericException("Invalid Designation code");
throw blException;
}
return ets.size();
}
public boolean isDesignationAlloted(int designationCode) throws BLException
{
return designationCodeWiseEmployeesMap.containsKey(designationCode);
}
}