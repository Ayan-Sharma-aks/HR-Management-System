import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
class employeeEmployeeIdExistsTestcase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
if(employeeDAO.employeeIdExists(employeeId)) System.out.println(employeeId+" exists");
else System.out.println(employeeId+" doest not exists");


}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}