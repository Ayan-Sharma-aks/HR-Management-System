import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
class designationTitleExistsTestcase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
System.out.print("Designation "+title);
if(designationDAO.titleExists(title)==true) System.out.println(" exists");
else System.out.println(" does not exists");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}