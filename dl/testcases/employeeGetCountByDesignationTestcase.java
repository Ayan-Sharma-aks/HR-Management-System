import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
class employeeGetCountByDesignationTestcase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
System.out.println(employeeDAO.getCountByDesignation(code));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}