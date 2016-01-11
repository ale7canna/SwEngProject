package com.sweng.server;

import java.sql.SQLException;

import com.sweng.common.beans.User;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		DBManager db = new DBManager();
		db.addUser(new User(0, "Alessandro", "Canicatti", "ale77canna", "ale"));
	}

}
