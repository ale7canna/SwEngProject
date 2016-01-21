package com.sweng.client;

import java.awt.Dimension;
import java.awt.List;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DateFormatter;

import com.sweng.common.beans.User;

public class prova {
	

	public static class MyTableModel extends AbstractTableModel
	{
	    private ArrayList<String> columnNames = new ArrayList();
	    private ArrayList<List> data = new ArrayList();

	    {
	        columnNames.add("Id");
	        columnNames.add("UserName");
	        columnNames.add("Checked");
	      
	    }

	    public void addRow(List rowData)
	    {
	        data.add(rowData);
	        fireTableRowsInserted(data.size() - 1, data.size() - 1);
	    }

	    public int getColumnCount()
	    {
	        return columnNames.size();
	    }

	    public int getRowCount()
	    {
	        return data.size();
	    }

	    public String getColumnName(int col)
	    {
	        try
	        {
	            return columnNames.get(col);
	        }
	        catch(Exception e)
	        {
	            return null;
	        }
	    }

	   public Object getValueAt(int row, int col)
	    {
			
	        return (data.get(row)).getItem(col);
	        
	    }

	    public boolean isCellEditable(int row, int col)
	    {
	        return false;
	    }

	    public Class getColumnClass(int c)
	    {
	        return getValueAt(0, c).getClass();
	    }
	};
	
	static MyTableModel model;
	public static JCheckBox checkbox;
	
	
	
	
	public ArrayList<CheckBoxId> createCheckboxList(ArrayList<User> lista){
		ArrayList<CheckBoxId> listacheckbox = new ArrayList<CheckBoxId>();
		for (User u : lista){
			String userName = u.getUsername();
			int id = u.getIdUser();
			//Qua non ho capito perché usi quel JCheckBox per aggiungerlo alla lista ???
			//checkbox = new CheckBoxId(id, userName);

			listacheckbox.add(new CheckBoxId(id, userName));
		}
		return listacheckbox;
	}
	
	
	public static class Demo {

	    private void createAndShowGUI() {

	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);

	        SpinnerDateModel model = new SpinnerDateModel();
	        model.setValue(calendar.getTime());

	        JSpinner spinner = new JSpinner(model);

	        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
	        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
	        formatter.setAllowsInvalid(false); // this makes what you want
	        formatter.setOverwriteMode(true);

	        spinner.setEditor(editor);

	        JPanel content = new JPanel();
	        content.add(spinner);

	        JFrame frame = new JFrame("Demo");
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.getContentPane().add(content);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }
	}
	 
	
	
	public static void main(String[] args)
	{
	    model = new MyTableModel();
	 
	    
	  //  new Demo().createAndShowGUI();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(300, 200));
		scrollPane.setBounds(0, 0, 434, 261);
		
		   List l = new List();
		    l.add( "yi",1);
		    l.add("chen", 2);
		    l.add("sleep", 3);
		    l.add("35", 4);
		    l.add("false", 5);
		    
		   
		    model.addRow(l);
		    
			   List m = new List();
			    m.add( "Giorgio",1);
			    m.add("Marzorati", 2);
			    m.add("alive", 3);
			    m.add("15", 4);
			    m.add("false", 5);
			    
			   
			    model.addRow(m);
			    
				   List n = new List();
				    n.add( "Cani",1);
				    n.add("Bomber", 2);
				    n.add("gioca", 3);
				    n.add("44", 4);
				    n.add("true", 5);
				    
				   
				    model.addRow(n);
		    
		    JTable table = new JTable(model);
		    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		    table.setFillsViewportHeight(true);
		    JScrollPane scrollPaneProject = new JScrollPane(table);
		    scrollPaneProject.setBounds(5, 218, 884, 194);
		    
		    
		    JFrame frame = new JFrame();
		    frame.getContentPane().add(scrollPaneProject);
		    frame.setVisible(true);
		    
		    
		   table.addMouseListener(new MouseAdapter() {
		        public void mousePressed(MouseEvent me) {
		            JTable table =(JTable) me.getSource();
		            Point p = me.getPoint();
		            int row = table.rowAtPoint(p);
		            if (me.getClickCount() == 2) {
		                // your valueChanged overridden method 
		            	String riga="";
		            	for(int col = 0; col< table.getColumnCount(); col++){
		            		riga += table.getValueAt(row, col);
		            		riga += " ";
		            	}
		            	JOptionPane.showMessageDialog(null, riga);
		            }
		        }
		    });
		    
		//ArrayList<CheckBoxId> checkboxList = createCheckboxList(friends, friends1);
			
		//CheckBoxList listFriends = new CheckBoxList();
	
//		for (JCheckBox c : checkboxList){
//				listFriends.addCheckbox((CheckBoxId) c);		
//				}
//		JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
//		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
//		timeSpinner.setEditor(timeEditor);
//		timeSpinner.setValue(new Date(0, 0, 0));
//		

		//scrollPane.setRowHeaderView(listFriends);
		//now adding this to the frame where I want to show 
	   
//	    frame.getContentPane().setLayout(null);
//	    frame.getContentPane().add(timeSpinner);
	 

//	    JButton viewButton = new JButton("view\r\n");
//	    viewButton.setSize(new Dimension(100, 100));
//	    viewButton.addMouseListener(new MouseAdapter() {
//	    	@Override
//	    	public void mouseClicked(MouseEvent arg0) {
//	    		ArrayList<Integer> id = listFriends.getSelectedItems();
//	    		
//	    		for(Integer i : id){
//	    			System.out.println(i);
//	    		}
//	    	}
//	    });
	   // scrollPane.setViewportView(viewButton);
//	    comboBox.setBounds(0, 0, 0, 0);
//	    frame.getContentPane().add(comboBox);
//	    comboBox.setModel(new DefaultComboBoxModel(new String[] {"ciao"}));
	   
	    
	    
	    {	/*	ArrayList<Integer> friends = new ArrayList<Integer>();
			friends.add(1);
			friends.add(2);
			friends.add(3);
			
			ArrayList<String> friends1 = new ArrayList<String>();
			friends1.add("Giorgio");
			friends1.add("Edo");
			friends1.add("ale");*/
			
			
		
		    
			   
		   }
	}
	
	 

	
	
	

}
