package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationMap;
private Map<String,DesignationInterface> titleWiseDesignationMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructure();
}
private void populateDataStructure() throws BLException
{
this.codeWiseDesignationMap=new HashMap<>();
this.titleWiseDesignationMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
codeWiseDesignationMap.put(new Integer(designation.getCode()),designation);
titleWiseDesignationMap.put(designation.getTitle().toUpperCase(),designation);
designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0) blException.addException("code","Code should be zero");
if(title==null)
{
blException.addException("title","Title is required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","Title is required");
}
if(title.length()>0) if(titleWiseDesignationMap.containsKey(title.toUpperCase())) blException.addException("title","Designation "+title+" exists");
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
Designation dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
codeWiseDesignationMap.put(new Integer(code),dsDesignation);
titleWiseDesignationMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(!(codeWiseDesignationMap.containsKey(code)))
{
blException.addException("code","Invalid code");
throw blException;
}
if(title==null)
{
blException.addException("title","Title is required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","Title is required");
}
if(title.length()>0)
{
if(titleWiseDesignationMap.containsKey(title.toUpperCase()))
{
DesignationInterface titledesignation=titleWiseDesignationMap.get(title.toUpperCase());
if(titledesignation.getCode()!=code) blException.addException("title","Designation " +title+" already exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
Designation dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
DesignationInterface tmpDesignation=codeWiseDesignationMap.get(code);
String tmpTitle=tmpDesignation.getTitle();
codeWiseDesignationMap.remove(code);
titleWiseDesignationMap.remove(tmpTitle.toUpperCase());
designationsSet.remove(tmpDesignation);
codeWiseDesignationMap.put(new Integer(code),dsDesignation);
titleWiseDesignationMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(!codeWiseDesignationMap.containsKey(code))
{
blException.addException("code","Invalid code");
throw blException;
}
try
{
DesignationInterface tmpDesignation=codeWiseDesignationMap.get(code);
new DesignationDAO().delete(code);
String tmpTitle=tmpDesignation.getTitle();
codeWiseDesignationMap.remove(code);
titleWiseDesignationMap.remove(tmpTitle.toUpperCase());
designationsSet.remove(tmpDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation=codeWiseDesignationMap.get(code);
if(designation==null)
{
BLException blException=new BLException();
blException.addException("code","Invalid code : "+code);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}

DesignationInterface getDSDesignationByCode(int code) throws BLException
{
DesignationInterface designation=codeWiseDesignationMap.get(code);
if(designation==null)
{
BLException blException=new BLException();
blException.addException("code","Invalid code : "+code);
throw blException;
}
return designation;
}


public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation=titleWiseDesignationMap.get(title.toUpperCase());
if(designation==null)
{
BLException blException=new BLException();
blException.addException("title","Invalid designation : "+title);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
public int getDesignationCount()
{
return designationsSet.size();
}
public boolean designationCodeExists(int code)
{
return codeWiseDesignationMap.containsKey(code);
}
public boolean designationTitleExists(String title)
{
return titleWiseDesignationMap.containsKey(title.toUpperCase());
}
public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations=new TreeSet<>();
designationsSet.forEach((designation)->{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
});
return designations;
}
}