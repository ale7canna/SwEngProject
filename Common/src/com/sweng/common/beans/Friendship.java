package com.sweng.common.beans;

public class Friendship {

	private int idUtente1;
	private int idUtente2;
	
	public Friendship (int idU1, int idU2)
	{
		setIdUtente1(idU1);
		setIdUtente2(idU2);
	}

	public int getIdUtente1() {
		return idUtente1;
	}

	public void setIdUtente1(int idUtente1) {
		this.idUtente1 = idUtente1;
	}

	public int getIdUtente2() {
		return idUtente2;
	}

	public void setIdUtente2(int idUtente2) {
		this.idUtente2 = idUtente2;
	}
}
