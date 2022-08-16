package jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.DBUtil;
public class LoginSelection {
public static void main(String args[]) throws NumberFormatException, IOException, SQLException {
	Integer ch;
	System.out.println("http://namanskshetty.me/");
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	System.out.println("Enter \n 1. For customer Login \n 2. For Vendor Login \n 3. For adding new customer\n 4. Admin pannel ");
	ch=Integer.parseInt(br.readLine());
	switch(ch) {
	case 1:
		customerlog cl= new customerlog();
		cl.enter();
		break;
	case 2:
		vendorlogin vn=new vendorlogin();
		vn.enter();
		break;
	case 3:
		try {
			add_customer();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		break;
	case 4:
		adminlog ad=new adminlog();
		ad.enter();
		break;
	}
	
}
public static void add_customer() throws IOException, SQLException {
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	System.out.println("Enetr name ");
	String name=br.readLine();
	System.out.println("Enetr Uname ");
	String uname=br.readLine();
	System.out.println("Enetr Password");
	String pass=br.readLine();
	System.out.println("Enetr Phone");
	int phone=Integer.parseInt(br.readLine());
	System.out.println("Enetr Address ");
	String addres=br.readLine();
	System.out.println("Enetr wallet ");
	int wallet=Integer.parseInt(br.readLine());
	
	String to_in="insert into customer(name,username,password,phone,address,wallet) values(?,?,?,?,?,?)";
	Connection con= DBUtil.getConnection();
	
		PreparedStatement ps=con.prepareStatement(to_in);
	ps.setString(1,name);
	ps.setString(2,uname);
	ps.setString(3,pass);
	ps.setInt(4,phone);
	ps.setString(5,addres);
	ps.setInt(6,wallet);
	ps.executeUpdate();
	System.out.println("Created Profile sucessfully!!!!\n Please Login");
	customerlog cl= new customerlog();
	cl.enter();	
}
}
