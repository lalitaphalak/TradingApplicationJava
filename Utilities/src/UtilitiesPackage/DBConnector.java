package UtilitiesPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	static private Connection connection;
	
	public static Connection getConnection() throws SQLException,ClassNotFoundException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = DriverManager.getConnection
				("jdbc:mysql://localhost:3306/trading-db?useSSL=false","root", "lalita@123");
		return connection;
		
	}

}
