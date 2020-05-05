package model;

import java.sql.*;

public class Hospital {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertHospital(String hospitalCode, String hospitalName, String hospitalAdress, String hospitalPhoneNo)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting hospital."; } 
	 
			// create a prepared statement    
			String query = " insert into hospitals(`HospitalID`,`hospitalCode`,`hospitalName`,`hospitalAdress`,`hospitalPhoneNo`)" + " values (?, ?, ?, ?, ?)"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, 0);    
			preparedStmt.setString(2, hospitalCode);    
			preparedStmt.setString(3, hospitalName);    
			preparedStmt.setString(4, hospitalAdress);    
			preparedStmt.setString(5, hospitalPhoneNo);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newHospitals = readHospital(); 
			output =  "{\"status\":\"success\", \"data\": \"" +        
					newHospitals + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the hospital.\"}";  
			System.err.println(e.getMessage());   
		} 
		
	  return output;  
	} 

	public String readHospital() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading hospitals.";
			}
			// Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>hospitalCode</th><th>hospitalName</th><th>hospitalAdress</th>"+ "<th>hospitalPhoneNo</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from hospitals";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String HospitalID = Integer.toString(rs.getInt("HospitalID"));
				String hospitalCode = rs.getString("hospitalCode");
				String hospitalName = rs.getString("hospitalName");
				String hospitalAdress = rs.getString("hospitalAdress");
				String hospitalPhoneNo = rs.getString("hospitalPhoneNo");
				// Add into the html table
				output += "<tr><td><input id='hidHospitalIDUpdate'name='hidHospitalIDUpdate' type='hidden'value='" + HospitalID + "'>" + hospitalCode + "</td>";
				output += "<td>" + hospitalName + "</td>";
				output += "<td>" + hospitalAdress + "</td>";
				output += "<td>" + hospitalPhoneNo + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-hospitalid='"+ HospitalID + "'>" + "</td></tr>"; 			}
			con.close();
			
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the hospitals.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateHospital(String HospitalID, String hospitalCode, String hospitalName, String hospitalAdress, String hospitalPhoneNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating hospitals.";
			}
			
			// create a prepared statement
			String query = "UPDATE hospitals SET hospitalCode=?,hospitalName=?,hospitalAdress=?,hospitalPhoneNo=? WHERE HospitalID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, hospitalCode);
			preparedStmt.setString(2, hospitalName);
			preparedStmt.setString(3, hospitalAdress);
			preparedStmt.setString(4, hospitalPhoneNo);
			preparedStmt.setInt(5, Integer.parseInt(HospitalID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			//output = "Updated successfully";
			
			String newHospitals = readHospital();
			 output = "{\"status\":\"success\", \"data\": \"" +
					 newHospitals + "\"}"; 
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the hospital.\"}";
			 System.err.println(e.getMessage()); 
		}
		return output;
	}

	public String deleteHospital(String HospitalID)  
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for deleting."; } 
	 
			// create a prepared statement    
			String query = "delete from hospitals where HospitalID=?"; 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(HospitalID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newHospitals = readHospital();    
			output = "{\"status\":\"success\", \"data\": \"" +       
					newHospitals + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the hospital.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	 
	 
}




