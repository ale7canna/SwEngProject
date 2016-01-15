package com.sweng.client;

import javax.swing.JCheckBox;

public class CheckBoxId extends JCheckBox{
	
	private int id;
	//private String userName;
	
	public CheckBoxId(int id, String string){
		super(string);
		this.id= id;
		//this.userName= userName;
	}
	
	public int getId(){
		return id;
	}
	
	
}
