package com.sweng.client;

import javax.swing.JCheckBox;

public class CheckBoxId extends JCheckBox{
	
	private int id;
	
	public CheckBoxId(int id){
		super();
		this.id= id;
	}
	
	public int getId(){
		return id;
	}
	
	
}
