package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class has the information to connect to the cssgate server. 
 * 
 * @author Menaka Abraham
 */

public class DataConnection {
	
	private static String USERNAME = "";	// deleted
	private static String PASSWORD = ""; 	// deleted
	private static String SERVERNAME = "";	// deleted
	private static Connection sConnection;
	

	/**
	 * Creates once instance of the connection to be reused 
	 * in the different places in the system. 
	 * 
	 * @throws SQLException
	 */
	private static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", USERNAME);
		connectionProps.put("password", PASSWORD);
		sConnection = DriverManager.getConnection(
				"jdbc:mysql://" + SERVERNAME + "/" + USERNAME + "?user=" + USERNAME + "&password=" + PASSWORD);
	}
	
	/**
	 * Returns a connection to the database so that queries can be executed.
	 * @return Connection to the database
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (sConnection == null) {
			createConnection();
		}
		return sConnection;
	}
	
	/**
	 * Close the connection to the database when done. 
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		if (sConnection != null && !sConnection.isClosed()) {
			sConnection.close();
		}
	}

}
