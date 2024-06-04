import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
class designationCodeExistsTestcase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
System.out.print("Code "+code);
if(designationDAO.codeExists(code)==true) System.out.println(" exists");
else System.out.println(" does not exists");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}