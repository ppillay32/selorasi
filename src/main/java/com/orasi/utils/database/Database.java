package com.orasi.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.orasi.utils.TestReporter;

public abstract class Database {

	protected String driver = null;	
	private String dbHost = null;
	private String dbPort= null;
	private String dbService= null;
	private String dbUser= null;
	private String dbPassword= null;
	protected String connectionString= null; 

	protected abstract void setDbDriver(String driver);

	protected String getDbDriver(){
		return driver;
	}

	protected void setDbHost(String host){
		dbHost = host;
	}

	protected String getDbHost(){
		return dbHost;
	}

	protected void setDbPort(String port){
		dbPort = port;
	}

	protected String getDbPort(){
		return dbPort;
	}

	protected void setDbService(String serivce){
		dbService = serivce;
	}

	protected String getDbService(){
		return dbService;
	}

	public void setDbUserName(String user){
		dbUser = user;
	}

	protected String getDbUserName(){
		return dbUser;
	}

	public void setDbPassword(String pass){
		dbPassword = pass;
	}

	protected String getDbPassword(){
		return dbPassword;
	}

	protected abstract void setDbConnectionString(String connection);

	protected String getDbConnectionString(){
		return connectionString;
	}

	public Object[][] getResultSet(String query) {
		TestReporter.logTrace("Entering Database#getResultSet");
		loadDriver();

		Connection connection = null;
		try {
			TestReporter.logTrace("Attempt to connect to database [ " + connectionString + " ]");
			connection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
			TestReporter.logTrace("Connection successful");
		} catch (SQLException e) {
			throw new DatabaseException("Failed to establish database connection to " + connectionString, e);
		}

		TestReporter.logTrace("Running query");
		TestReporter.logTrace(query);	
		ResultSet rs = null;
		try {
			rs = runQuery(connection, query);
			TestReporter.logTrace("Query results returned with no errors. Parsing results");
			Object[][] parsedRs =extract(rs); 
			TestReporter.logTrace("Exiting Database#getResultSet");
			return parsedRs;
		} catch (Exception e) {
			throw new DatabaseException("Failed to extract data into a Recordset", e);
		}finally{
			if(rs!=null)  try { rs.close(); } catch (Exception e) { /* ignored */ }
			if(connection!=null)  try { connection.close(); } catch (Exception e) { /* ignored */ }
		}	
	}


	private void loadDriver(){			
		TestReporter.logTrace("Entering Database#loadDriver");	
		try{
			TestReporter.logTrace("Attempting to load driver [ " + driver + " ]");	
			Class.forName(driver);
			TestReporter.logTrace("Successfully loaded driver [ " + driver + " ]");
		} catch(ClassNotFoundException cnfe) {
			throw new DatabaseException("Error loading driver", cnfe);
		}		 
		TestReporter.logTrace("Exiting Database#loadDriver");			 
	}

	private static ResultSet runQuery(Connection connection, String query) {
		TestReporter.logDebug("Entering Database#runQuery");
		try {
			TestReporter.logDebug("Attempting to create Database Statement object from current connection");
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			TestReporter.logDebug("Successfully created Database Statement");

			TestReporter.logDebug("Attempting to execute query");
			ResultSet resultSet = statement.executeQuery(query);
			TestReporter.logDebug("Successfully executed query");

			TestReporter.logDebug("Exiting Database#runQuery");

			return(resultSet);

		} catch(SQLException sqle) {
			throw new DatabaseException("Failed to execute SQL Query", sqle);     
		} 
	}

	/** 
	 * Returns an ArrayList of ArrayLists of Strings extracted from a 
	 * ResultSet retrieved from the database. 
	 * @param resultSet ResultSet to extract Strings from 
	 * @return an ArrayList of ArrayLists of Strings 
	 * @throws SQLException if an SQL exception occurs 
	 */  
	private static Object[][] extract(ResultSet resultSet){  
		TestReporter.logTrace("Entering Database#extract");
		// get row and column count
		int rowCount = 0;
		try {
			TestReporter.logTrace("Determining number of rows in results");
			resultSet.last();
			rowCount = resultSet.getRow();
			resultSet.beforeFirst();
			TestReporter.logTrace("Rows to to be extracted [ " + rowCount + " ] ");
		}
		catch(Exception ex) {
			rowCount = 0;
		}
		try{

			TestReporter.logTrace("Determining number of columns in results");
			int columnCount = resultSet.getMetaData().getColumnCount();  
			TestReporter.logTrace("Columns to to be extracted [ " + columnCount + " ] ");

			TestReporter.logTrace("Generating Object array for the size of String[" + (rowCount + 1) +"][" + columnCount + "] (One row added for column headers)");
			Object[][] table = new String[rowCount+1][columnCount];  

			TestReporter.logTrace("Retrieve Result Set metadata");
			ResultSetMetaData rsmd = resultSet.getMetaData();        

			TestReporter.logTrace("Extacting data from ResultSet and storing in Object[][]");
			for(int rowNum = 0; rowNum <= rowCount; rowNum++) {  
				for(int colNum = 0, rsColumn = 1; colNum < columnCount; colNum++, rsColumn++){

					if(rowNum == 0){
						table[rowNum][colNum] = resultSet.getMetaData().getColumnName(rsColumn);   
					}else if(resultSet.getString(colNum+1) == null){
						//System.out.println("null");
						table[rowNum][colNum] = "NULL";
					}else{
						try{

							//
							switch (rsmd.getColumnType(rsColumn)){

							case Types.DATE:
								table[rowNum][colNum] = String.valueOf(resultSet.getTimestamp(rsColumn));
								break;

							case Types.TIMESTAMP:
								table[rowNum][colNum] = String.valueOf(resultSet.getTimestamp(rsColumn));
								break;

							case Types.TIME:
								table[rowNum][colNum] = resultSet.getTime(rsColumn);
								break;	            				            	

							default:
								table[rowNum][colNum] = resultSet.getString(rsColumn).intern();
								break;
							}
						}catch (Exception e){
							table[rowNum][colNum] = resultSet.getString(rsColumn).intern();
						}
						//System.out.println(resultSet.getString(c).intern());
						//table[rowNum][colNum] = resultSet.getString(colNum+1).intern();              
					}
				}
				resultSet.next();
			}  

			TestReporter.logTrace("Extraction complete");
			TestReporter.logTrace("Exiting Database#extract");
			return table;  
		}catch(SQLException sql){throw new DatabaseException("Failed to generate result set", sql);}
	}  

}
