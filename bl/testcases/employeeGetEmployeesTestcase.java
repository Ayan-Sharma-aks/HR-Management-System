import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.enums.*;
class employeeGetEmployeesTestcase
{
public static void main(String gg[])
{
try
{
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
Set<EmployeeInterface> employees=employeeManager.getEmployees();
employees.forEach((employee)->{
System.out.println("Employee ID : "+employee.getEmployeeId());
System.out.println("Name : "+employee.getName());
System.out.println("Designation : "+employee.getDesignation().getTitle());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.println("Date of Birth : "+sdf.format(employee.getDateOfBirth()));
System.out.println("Gender : "+employee.getGender());
System.out.println("Is Indian : "+employee.getIsIndian());
System.out.println("Salary : "+employee.getBasicSalary());
System.out.println("PAN Number : "+employee.getPANNumber());
System.out.println("Aadhar Card Number : "+employee.getAadharCardNumber());
});
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
properties.forEach((property)->{
System.out.println(blException.getException(property));
});
}
}
}