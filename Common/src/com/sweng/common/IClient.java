package com.sweng.common;

import java.io.Serializable;

public interface IClient extends Serializable {
	public void update();
	
	public int getId();
	public String getUsername();
}
