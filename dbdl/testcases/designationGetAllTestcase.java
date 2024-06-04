import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
class designationGetAllTestcase
{
public static void main(String gg[])
{
try
{
Set<DesignationDTOInterface> designations;
DesignationDAOInterface designationDAO=new DesignationDAO();
designations=designationDAO.getAll();
designations.forEach((designationDTO)->{
System.out.println("Code "+designationDTO.getCode()+" Designation "+designationDTO.getTitle());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}