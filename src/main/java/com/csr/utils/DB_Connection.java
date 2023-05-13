/**
 * 
 */
package com.csr.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.csr.base.DriverFactory;

/**
 * @author akaushi3
 *
 */
public class DB_Connection {
	
	/**
	 * 
	 * @return
	 */
	public static String getEnv(){
		
		String env = null;
		
		String curr_url = DriverFactory.getInstance().getDriver().getCurrentUrl();
		
		if(curr_url.contains("svt")){
			
			return env = "svt";
		}
		
		else if(curr_url.contains("sit")){
			
			return env = "sit";
		}
		
		else if(curr_url.contains("bvt")){
			
			return env = "bvt";
		}
		return env;
	}

	/**
	 * This method helps to execute java program.
	 * 
	 * @param args
	 */
	public static List<String[]> getDB_Data(String query) {
		
		List<String[]> table = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		// Instantiate ResultSet Object
		ResultSet resultSet = null;
		String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
		String url = null;
		String user = null;
		String password = null;
		String env = getEnv();
		
		if(env.equals("svt")){
			url = PropertiesOperations.getPropertyValueByKey("svt_url");
			user = PropertiesOperations.getPropertyValueByKey("svt_user");
			password = PropertiesOperations.getPropertyValueByKey("svt_password");
		}
		
		else if(env.equals("sit")){
			url = PropertiesOperations.getPropertyValueByKey("sit_url");
			user = PropertiesOperations.getPropertyValueByKey("sit_user");
			password = PropertiesOperations.getPropertyValueByKey("sit_password");
		}
		
		else if(env.equals("bvt")){
			url = PropertiesOperations.getPropertyValueByKey("bvt_url");
			user = PropertiesOperations.getPropertyValueByKey("bvt_user");
			password = PropertiesOperations.getPropertyValueByKey("bvt_password");
		}
		Connection connection = null;
		try {
			// Load class into memory
			Class.forName(jdbcClassName);
			// Establish connection
			connection = DriverManager.getConnection(url, user, password);

			statement = connection.createStatement();

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			int nCol = resultSet.getMetaData().getColumnCount();
			table = new ArrayList<>();
			while (resultSet.next()) {
				String[] row = new String[nCol];
				for (int iCol = 1; iCol <= nCol; iCol++) {
					Object obj = resultSet.getObject(iCol);
					row[iCol - 1] = (obj == null) ? null : obj.toString();
				}
				table.add(row);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				System.out.println("Connected");
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return table;
	}

	// ===================================================================================//

	/**
	 * Establish the connection
	 * 
	 * @return
	 */
	public static Connection getDB_Data_C() {

		// Instantiate ResultSet Object

		String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
		String url = "jdbc:db2://udecommdb3:60000/DZCSG00";
		String user = "agupta58";
		String password = "Akank@123";

		Connection connection = null;
		try {
			// Load class into memory
			Class.forName(jdbcClassName);
			// Establish connection
			connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}

	/**
	 * Fetch the data from Database
	 * 
	 * @param query
	 * @return
	 */
	public static List<String[]> fetchData(String query) {
		ResultSet resultSet = null;
		List<String[]> table = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;

		try {

			statement = getDB_Data_C().createStatement();

			preparedStatement = getDB_Data_C().prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			int nCol = resultSet.getMetaData().getColumnCount();
			table = new ArrayList<>();
			while (resultSet.next()) {
				String[] row = new String[nCol];
				for (int iCol = 1; iCol <= nCol; iCol++) {
					Object obj = resultSet.getObject(iCol);
					row[iCol - 1] = (obj == null) ? null : obj.toString();
				}
				table.add(row);
			}
		} catch (Exception e) {
			// TODO: handle exception

		}
		return table;
	}

	/**
	 * Close DB Connection
	 */
	public void closeDBConnection() {

		if (getDB_Data_C() != null) {
			System.out.println("Connection is alive");
			try {
				getDB_Data_C().close();
				System.out.println("Connection is closed successfully");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// ==========================Test=========================//
	public static void main(String[] args) {

		List<String[]> list = fetchData(
				"select NickName, Address1, City, Country, State, Zipcode from address where member_id=512923572 and Status = 'P' and IsPrimary = '0' Order By NickName");
		List<String> addressDetails = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {

			String[] array = list.get(i);
			for (int j = 0; j < array.length; j++) {
				addressDetails.add(array[j]);

			}
		}

		for (int i = 0; i < addressDetails.size(); i++) {
			System.out.println(addressDetails.get(i));
		}
	}
}