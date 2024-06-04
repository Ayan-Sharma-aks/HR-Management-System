import java.awt.*;
import javax.swing.*;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
class DesignationModelTestcase extends JFrame
{
private JTable tb;
private DesignationModel designationModel;
private JScrollPane jsp;
private Container container;
DesignationModelTestcase()
{
designationModel=new DesignationModel();
tb=new JTable(designationModel);
jsp=new JScrollPane(tb,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setLocation(100,100);
setSize(500,500);
setVisible(true);

}

public static void main(String gg[])
{
DesignationModelTestcase dmtc=new DesignationModelTestcase();

}
}