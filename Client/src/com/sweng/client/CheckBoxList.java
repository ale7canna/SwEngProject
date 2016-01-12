package com.sweng.client;


import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CheckBoxList extends JList
{
   protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

   public CheckBoxList()
   {
      setCellRenderer(new CellRenderer());

      addMouseListener(new MouseAdapter()
         {
            public void mousePressed(MouseEvent e)
            {
               int index = locationToIndex(e.getPoint());

               if (index != -1) {
                  JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
                  checkbox.setSelected( !checkbox.isSelected());
                  repaint();
               }
            }
         }
      );

      setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }

   protected class CellRenderer implements ListCellRenderer
   {
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
      {
         JCheckBox checkbox = (JCheckBox) value;
         checkbox.setBackground(isSelected ? getSelectionBackground() : getBackground());
         checkbox.setForeground(isSelected ? getSelectionForeground() : getForeground());
         checkbox.setEnabled(isEnabled());
         checkbox.setFont(getFont());
         checkbox.setFocusPainted(false);
         checkbox.setBorderPainted(true);
         checkbox.setBorder(isSelected ?
          UIManager.getBorder(
           "List.focusCellHighlightBorder") : noFocusBorder);
         return checkbox;
      }
   }
   
   
   public void addCheckbox(CheckBoxId checkBox) {
	    ListModel currentList = this.getModel();
	    JCheckBox[] newList = new JCheckBox[currentList.getSize() + 1];
	    for (int i = 0; i < currentList.getSize(); i++) {
	        newList[i] = (JCheckBox) currentList.getElementAt(i);
	        
	    }
	    newList[newList.length - 1] = checkBox;
	    setListData(newList);
	    
	}
   
   public  ArrayList<Integer> getSelectedItems(){
	   
	   ArrayList<Integer> checkedId = new ArrayList<Integer>();
	   
	   for(int i=0; i<getModel().getSize(); i++){
		   CheckBoxId checkbox= (CheckBoxId)getModel().getElementAt(i);
		   if( checkbox.isSelected()){
			   checkedId.add(checkbox.getId());
		   }	   
	   }
	   return checkedId;
   }
}
