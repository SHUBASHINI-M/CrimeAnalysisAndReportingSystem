package com.hexa.util;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;



public class DBConnection {

	public static Connection getConnection() {

		Connection myConnection = null;

	    String url = "jdbc:mysql://localhost:3306/case_study_cars?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		String username = "root";

		String password = "Shubaram1315**";

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");//registering driver

		} catch (ClassNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		try {

			myConnection = DriverManager.getConnection(url, username, password );
			

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return myConnection;

	}

}

