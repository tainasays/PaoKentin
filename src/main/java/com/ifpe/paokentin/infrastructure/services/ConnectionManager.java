package com.ifpe.paokentin.infrastructure.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static final String URL = "jdbc:postgresql://localhost:5435/paokentin";
	private static final String USER = "postgres";
	// Abaixo colocar a senha do banco de dados.
	private static final String PASSWORD = "admin";

	private static Connection conn = null;

	public static Connection getCurrentConnection() throws SQLException {

		if (conn == null)
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return conn;

	}

	public static Connection getNewConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
