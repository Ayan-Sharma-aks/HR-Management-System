import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
class employeeGetByEmployeeIdTestcase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDTOInterface employeeDTO;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDTO=employeeDAO.getByEmployeeId(employeeId);
System.out.println("Employee name : "+employeeDTO.getName());
System.out.println("Employee designation code : "+employeeDTO.getDesignationCode());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.println("Employee date of birth : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Employee gender : "+employeeDTO.getGender());
System.out.println("Employee IsIndian : "+employeeDTO.getIsIndian());
System.out.println("Employee sasic salary : "+employeeDTO.getBasicSalary());
System.out.println("Employee PanNumber : "+employeeDTO.getPANNumber());
System.out.println("Employee aadhar card number : "+employeeDTO.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}