import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
class employeeGetAllTestcase
{
public static void main(String gg[])
{
try
{
Set<EmployeeDTOInterface> treeSet;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
treeSet=employeeDAO.getAll();
treeSet.forEach((employeeDTO)->{
System.out.println("Employee ID : "+employeeDTO.getEmployeeId());
System.out.println("Employee name : "+employeeDTO.getName());
System.out.println("Employee designation code : "+employeeDTO.getDesignationCode());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.println("Employee date of birth : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Employee gender : "+employeeDTO.getGender());
System.out.println("Employee IsIndian : "+employeeDTO.getIsIndian());
System.out.println("Employee sasic salary : "+employeeDTO.getBasicSalary());
System.out.println("Employee PanNumber : "+employeeDTO.getPANNumber());
System.out.println("Employee aadhar card number : "+employeeDTO.getAadharCardNumber());
System.out.println("---------------------------------------------------------------------------------------------------------");
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}