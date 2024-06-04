import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
class designationUpdateTestcase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
String title=gg[1];
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
designationDTO.setCode(code);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println("Designation "+title+" updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}

}