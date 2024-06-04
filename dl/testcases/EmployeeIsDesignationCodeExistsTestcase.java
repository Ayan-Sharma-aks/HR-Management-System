import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
class employeeIsDesignationCodeExistsTestcase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
if(employeeDAO.isDesignationAlloted(designationCode)) System.out.println(designationCode+" exists");
else System.out.println(designationCode+" doest not exists");


}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}