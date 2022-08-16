package jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DBUtil;

public class customerlog {
public void enter() throws IOException, SQLException{
	String name,password;
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Enetr user name ");
		name=br.readLine();
		System.out.println("Enetr user password ");
		password=br.readLine();
		//here         
		
		
		String createString;
		createString="select * from customer where username='"+name+"' and password='"+password+"'";
		Connection con= DBUtil.getConnection();
		Statement smt;
		smt=con.createStatement();
		try {

		ResultSet rs=smt.executeQuery(createString);
		if(rs.next()) {
			System.out.println("Login!!!");
			menuStart m = new menuStart();
			m.menu(name);
		}
		else {
			System.out.println("Not logged in");
		}
		}
		catch(SQLException ex){
			System.err.println("SQL "+ex.getMessage());
			
		}
		
	}

}
