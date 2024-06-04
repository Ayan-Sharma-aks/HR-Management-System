package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
public class EmployeeUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel,searchLabel,searchErrorLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JTable employeeTable;
private EmployeeModel employeeModel;
private JScrollPane scrollPane;
private Container container;
private EmployeePanel employeePanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private ImageIcon deleteIcon;
private ImageIcon crossIcon;
public EmployeeUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
employeePanel.setViewMode();
}
private void initComponents()
{
logoIcon=new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
addIcon=new ImageIcon(getClass().getClassLoader().getResource("add_Icon.png"));
editIcon=new ImageIcon(getClass().getClassLoader().getResource("edit_Icon.png"));
deleteIcon=new ImageIcon(getClass().getClassLoader().getResource("delete_Icon.png"));
saveIcon=new ImageIcon(getClass().getClassLoader().getResource("save_Icon.png"));
cancelIcon=new ImageIcon(getClass().getClassLoader().getResource("cancel_Icon.png"));
pdfIcon=new ImageIcon(getClass().getClassLoader().getResource("pdf_Icon.png"));
crossIcon=new ImageIcon(getClass().getClassLoader().getResource("cross_Icon.png"));

setIconImage(logoIcon.getImage());
employeeModel=new EmployeeModel();
titleLabel=new JLabel("Employee");
searchLabel=new JLabel("Search");
searchErrorLabel=new JLabel("");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton(crossIcon);
employeeTable=new JTable(employeeModel);
scrollPane=new JScrollPane(employeeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
employeePanel=new EmployeePanel();
container=getContentPane();
}
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.PLAIN,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
JTableHeader header=employeeTable.getTableHeader();
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
employeeTable.setFont(dataFont);
employeeTable.setRowHeight(40);
employeeTable.getColumnModel().getColumn(0).setPreferredWidth(20);
employeeTable.getColumnModel().getColumn(1).setPreferredWidth(300);
employeeTable.getColumnModel().getColumn(2).setPreferredWidth(100);
employeeTable.setRowSelectionAllowed(true);
employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
container.setLayout(null);

int lm,tm;
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchErrorLabel.setBounds(lm+10+100+10+400-75,tm+10+30,100,20);
searchLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(lm+10+100+10+400,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30,565,220);
employeePanel.setBounds(lm+10,tm+10+40+10+30+220+10,565,350);

container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(employeePanel);
int w,h;
w=600;
h=720;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(d.width/2-w/2,d.height/2-h/2);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
employeeTable.getSelectionModel().addListSelectionListener(this);
}
private void searchDesignation()
{
searchErrorLabel.setText("");
String name=searchTextField.getText().trim();
if(name.length()==0) return;
int index;
try
{
index=employeeModel.indexOfName(name,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
employeeTable.setRowSelectionInterval(index,index);
Rectangle rectangle=employeeTable.getCellRect(index,0,true);
employeeTable.scrollRectToVisible(rectangle);
}
public void changedUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent ev)
{
searchDesignation();
}
public void valueChanged(ListSelectionEvent ev)
{
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(employeeModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
employeeTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
employeeTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
employeeTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
employeeTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
employeeTable.setEnabled(false);
}
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
employeeTable.setEnabled(false);
}

// Inner classes starts
class EmployeePanel extends JPanel
{
private JLabel employeeIDCaptionLabel,nameLabel,nameCaptionLabel,designationCaptionLabel,isIndianCaptionLabel,basicSalaryCaptionLabel,genderCaptionLabel,aadharCardNumberCaptionLabel,PANCardNumberCaptionLabel,dateOfBirthCaptionLabel;
private JLabel employeeIDLabel,designationLabel,isIndainLabel,basicSalaryLabel,genderLabel,aadharCardNumberLabel,PANCardNumberLabel,dateOfBirthLabel;
private JTextField nameTextField,employeeIDTextField,designationTextField,basicSalaryTextField,aadharCardNumberTextField,PANCardNumberTextField;
private JButton clearEmployeeIDTextFieldButton,addButton,editButton,cancelButton,deleteButton,exportToPDFButton,clearNameTextFieldButton,clearDesignationTextFieldButton,clearBasicSalaryTextFieldButton,clearPANCardNumberTextFieldButton,clearAadharCardNumberTextFieldButton;
private JPanel buttonsPanel;
private EmployeeInterface employee;
EmployeePanel()
{
setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
initComponents();
setAppearance();
addListeners();
}
/*
public void setEmployee(EmployeeInterface employee)
{
this.employee=employee;
nameLabel.setText(employee.getName());
}
public void clearEmployee()
{
this.employee=null;
nameLabel.setText("");
}
*/
private void initComponents()
{
employee=null;
employeeIDCaptionLabel=new JLabel("Employee ID");
nameCaptionLabel=new JLabel("Name");
designationCaptionLabel=new JLabel("Designation");
isIndianCaptionLabel=new JLabel("Indian");
basicSalaryCaptionLabel=new JLabel("Salary");
genderCaptionLabel=new JLabel("Gender");
aadharCardNumberCaptionLabel=new JLabel("Aadhar card No.");
PANCardNumberCaptionLabel=new JLabel("Pan No.");
dateOfBirthCaptionLabel=new JLabel("Date of Birth");
nameLabel=new JLabel("");
nameTextField=new JTextField();
employeeIDTextField=new JTextField();
designationTextField=new JTextField();
basicSalaryTextField=new JTextField();
aadharCardNumberTextField=new JTextField();
PANCardNumberTextField=new JTextField();
employeeIDLabel=new JLabel("");
designationLabel=new JLabel("");
isIndainLabel=new JLabel("");
basicSalaryLabel=new JLabel("");
genderLabel=new JLabel("");
aadharCardNumberLabel=new JLabel("");
PANCardNumberLabel=new JLabel("");
dateOfBirthLabel=new JLabel("");
clearEmployeeIDTextFieldButton=new JButton(crossIcon);
clearNameTextFieldButton=new JButton(crossIcon);
clearDesignationTextFieldButton=new JButton(crossIcon);
clearBasicSalaryTextFieldButton=new JButton(crossIcon);
clearPANCardNumberTextFieldButton=new JButton(crossIcon);
clearAadharCardNumberTextFieldButton=new JButton(crossIcon);
buttonsPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,12);
Font dataFont=new Font("Verdana",Font.PLAIN,12);
employeeIDCaptionLabel.setFont(captionFont);
nameLabel.setFont(dataFont);
nameTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
employeeIDCaptionLabel.setBounds(lm+10,tm+20,100,30);
employeeIDLabel.setBounds(lm+10+5+100,tm+20,400,30);
employeeIDTextField.setBounds(lm+10+100+5,tm+20,150,30);
clearEmployeeIDTextFieldButton.setBounds(lm+10+100+5+150+5,tm+20,30,30);

nameCaptionLabel.setBounds(lm+10,tm+20+30+10,100,30);
nameLabel.setBounds(lm+5+100,tm+20+30+10,400,30);
nameTextField.setBounds(lm+10+100+5,tm+20+30+10,150,30);
clearNameTextFieldButton.setBounds(lm+10+100+5+150+5,tm+20+30+10,30,30);

designationCaptionLabel.setBounds(lm+10,tm+20+30+10+30+10,100,30);
designationLabel.setBounds(lm+5+100,tm+20+30+10+30+10,400,30);
designationTextField.setBounds(lm+10+100+5,tm+20+30+10+30+10,150,30);
clearDesignationTextFieldButton.setBounds(lm+10+100+5+150+5,tm+20+30+10+30+10,30,30);

dateOfBirthCaptionLabel.setBounds(lm+10,tm+20+30+10+30+10+30+10,100,30);

basicSalaryCaptionLabel.setBounds(lm+10,tm+20+30+10+30+10+30+10+30+10,100,30);
basicSalaryLabel.setBounds(lm+5+100,tm+20+30+10+30+10+30+10+30+10,400,30);
basicSalaryTextField.setBounds(lm+10+100+5,tm+20+30+10+30+10+30+10+30+10,150,30);
clearBasicSalaryTextFieldButton.setBounds(lm+10+100+5+150+5,tm+20+30+10+30+10+30+10+30+10,30,30);

genderCaptionLabel.setBounds(lm+10+100+5+150+5+50,tm+20,100,30);

isIndianCaptionLabel.setBounds(lm+10+100+5+150+5+50,tm+20+30+10,100,30);

aadharCardNumberCaptionLabel.setBounds(lm+10+100+5+150+5+50,tm+20+30+10+30+10,100,30);
aadharCardNumberLabel.setBounds(lm+5+100+5+150+5+50+100,tm+20+30+10+30+10,400,30);
aadharCardNumberTextField.setBounds(lm+10+100+5+150+5+50+100+5,tm+20+30+10+30+10,150,30);
clearAadharCardNumberTextFieldButton.setBounds(lm+10+100+5+150+5+50+100+5+150+5,tm+20+30+10+30+10,30,30);

PANCardNumberCaptionLabel.setBounds(lm+10+100+5+150+5+50,tm+20+30+10+30+10+30+10,100,30);
PANCardNumberLabel.setBounds(lm+5+100+5+150+5+50+10+100,tm+20+30+10+30+10+30+10,400,30);
PANCardNumberTextField.setBounds(lm+10+100+5+150+5+50+10+100+5,tm+20+30+10+30+10+30+10,150,30);
clearPANCardNumberTextFieldButton.setBounds(lm+10+100+5+150+5+50+10+100+5+150+5,tm+20+30+10+30+10+30+10,30,30);

buttonsPanel.setBounds(50,tm+20+30+30+80+80,465,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(employeeIDCaptionLabel);
add(nameCaptionLabel);
add(designationCaptionLabel);
add(basicSalaryCaptionLabel);
add(dateOfBirthCaptionLabel);
add(genderCaptionLabel);
add(isIndianCaptionLabel);
add(aadharCardNumberCaptionLabel);
add(PANCardNumberCaptionLabel);
add(employeeIDTextField);
add(nameTextField);
add(designationTextField);
add(basicSalaryTextField);
add(aadharCardNumberTextField);
add(PANCardNumberTextField);
add(employeeIDLabel);
add(nameLabel);
add(designationLabel);
add(basicSalaryLabel);
add(aadharCardNumberLabel);
add(PANCardNumberLabel);
add(clearEmployeeIDTextFieldButton);
add(clearNameTextFieldButton);
add(clearDesignationTextFieldButton);
add(clearBasicSalaryTextFieldButton);
add(clearAadharCardNumberTextFieldButton);
add(clearPANCardNumberTextFieldButton);
add(buttonsPanel);
}
private boolean addEmployee()
{
return true;
}
private boolean updateEmployee()
{
return true;
}
private void removeEmployee()
{
}
private void addListeners()
{
}
private void setViewMode()
{
EmployeeUI.this.setViewMode();
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.employeeIDTextField.setVisible(true); // idhar false karna hai
this.clearEmployeeIDTextFieldButton.setVisible(true); // isko bhi false karna hai
this.employeeIDLabel.setVisible(true);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(employeeModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
private void setAddMode()
{
EmployeeUI.this.setAddMode();
this.employeeIDTextField.setText("");
this.employeeIDLabel.setVisible(false);
this.employeeIDTextField.setVisible(true);
this.clearEmployeeIDTextFieldButton.setVisible(true);
addButton.setIcon(saveIcon);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
exportToPDFButton.setEnabled(false);
}
private void setEditMode()
{
if(employeeTable.getSelectedRow()<0 || employeeTable.getSelectedRow()>=employeeModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select employee to edit");
return;
}
EmployeeUI.this.setEditMode();
this.employeeIDTextField.setText(this.employee.getName());
this.employeeIDLabel.setVisible(false);
this.employeeIDTextField.setVisible(true);
this.clearEmployeeIDTextFieldButton.setVisible(true);
addButton.setEnabled(false);
editButton.setEnabled(true);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
exportToPDFButton.setEnabled(false);
editButton.setIcon(saveIcon);
}
private void setDeleteMode()
{
if(employeeTable.getSelectedRow()<0 || employeeTable.getSelectedRow()>=employeeModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select employee to delete");
return;
}
EmployeeUI.this.setDeleteMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
removeEmployee();
EmployeeUI.this.setViewMode();
this.setViewMode();
}
private void setExportToPDFMode()
{
EmployeeUI.this.setExportToPDFMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
}
//Inner classes ends





}