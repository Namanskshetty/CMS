package jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.DBUtil;
//This is project is done by Naman@hexaware.com
public class vendorlogin {
	int count=0;
	public void enter() throws IOException, SQLException{
		if (count<=3) {
	String name,password;
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Enetr user name ");
		name=br.readLine();
		System.out.println("Enetr user password ");
		password=br.readLine();
		String to_ino="select * from vendor where vusername='"+name+"' and vpassword='"+password+"'";
		Connection con= DBUtil.getConnection();
		Statement smt;
		smt=con.createStatement();
		ResultSet rs=smt.executeQuery(to_ino);
		if(rs.next()) {
			System.out.println("Login!!!");
			VendorMenu v = new VendorMenu();
			v.menu(name);
		}
		else {
			System.out.println("Not logged in");
			count=count+1;
			enter();
		}
}
	else {
		System.out.println("Too many Unsucessfull atempts!!");
	}}
}
