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

public class VendorMenu {
public void menu(String vname) throws NumberFormatException, IOException, SQLException {
	Integer ch;
	System.out.println("Welcome "+vname);
	System.out.println("Enter\n1. For see food details of all \n2.Food by me\n3. Add food\n4. See orders\n5. Change order status\n6. Delete food from menu \n7. Chnage password\n ANY NUBMER to exit");
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	ch=Integer.parseInt(br.readLine());
	switch(ch) {
	case 1:
		FoodAll(vname);
		break;
	case 2:
		Foodme(vname);
		break;
	case 3:
		addfoo(vname);
		break;
	case 4:
		ordme(vname,0);
		break;
	case 5:
		changeord(vname);
		break;
	case 6:
		delFoo(vname);
		break;
	case 7:
		changePass(vname);
		break;
	default:
		System.exit(0);
	}
}
public void FoodAll(String vname) throws SQLException, NumberFormatException, IOException {
	int food_id,food_price,calories_food;
	String food_name,food_type;
	Connection con;
	ResultSet rs=null;
	String createString="select * from food";
	Statement smt;
	con=DBUtil.getConnection();;
	smt=con.createStatement();
	rs=smt.executeQuery(createString);
	boolean result =rs.next();
	if(!result) {
		System.out.println("Nothing to show");
	}
	System.out.println("Sl.no \t\t Food \t\t Type \t price \t calories \t");
	do{
		 food_id=rs.getInt(1);
		food_name=rs.getString(2);
		food_type=rs.getString(3);
		food_price=rs.getInt(4);
		calories_food=rs.getInt(6);
	
	System.out.println(food_id+"\t"+food_name+"\t"+"\t"+food_type+"\t"+food_price+"\t"+calories_food);
	}
	while(rs.next());
	menu(vname);
}
public void Foodme(String vname) throws SQLException, NumberFormatException, IOException {
	int food_id,food_price,calories_food;
	String food_name,food_type;
	Connection con;
	ResultSet rs=null;
	String createString="select * from food where vid=(select vid from 	vendor where vusername='"+vname+"') ";
	Statement smt;
	con=DBUtil.getConnection();;
	smt=con.createStatement();
	rs=smt.executeQuery(createString);
	boolean result =rs.next();
	if(!result) {
		System.out.println("Nothing to show");
	}
	System.out.println("Sl.no \t Food \t Type \t price \t calories \t");
	do{
		 food_id=rs.getInt(1);
		food_name=rs.getString(2);
		food_type=rs.getString(3);
		food_price=rs.getInt(4);
		calories_food=rs.getInt(6);
	
	System.out.println(food_id+"\t"+food_name+"\t"+"\t"+food_type+"\t"+food_price+"\t"+calories_food);
	}
	while(rs.next());
	menu(vname);
}
public void addfoo(String vname) throws SQLException, NumberFormatException, IOException {
	
	Statement smt;
	ResultSet rs=null;
	Connection con=DBUtil.getConnection();;
	smt=con.createStatement();
	String createString="select Vid from vendor where vusername='"+vname+"'"; 
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	System.out.println("Enetr food name ");
	String fname=br.readLine();
	System.out.println("Enter food Type ");
	String type=br.readLine();
	System.out.println("Enter Food price ");
	Integer price=Integer.parseInt(br.readLine());
	System.out.println("Enter Food calories ");
	Integer calories=Integer.parseInt(br.readLine());
	rs=smt.executeQuery(createString);
	rs.next();
	Integer my_id=rs.getInt(1);
	String createStringtwo="insert into food values(default,'"+fname+"','"+type+"',"+price+","+my_id+","+calories+")";
	smt.executeUpdate(createStringtwo);
	menu(vname);
}
public void changePass(String vname) throws IOException, SQLException {
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	System.out.println("Enter new password ");
	String npass=br.readLine();
	String newpass="update vendor set vpassword ='"+npass+"' where vusername='"+vname+"'";
	Statement smt;
	Connection con=DBUtil.getConnection();;
	smt=con.createStatement();
	smt.executeUpdate(newpass);
	System.out.println("Done chaning password for "+vname);
	menu(vname);
}
public void delFoo(String vname) throws NumberFormatException, IOException, SQLException {
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	System.out.println("Enter Food id to be deleted ");
	Integer fid=Integer.parseInt(br.readLine());
	Statement smt;
	ResultSet rs=null;
	Connection con=DBUtil.getConnection();;
	smt=con.createStatement();
	String createString="select Vid from vendor where vusername='"+vname+"'"; 
	rs=smt.executeQuery(createString);
	rs.next();
	Integer my_id=rs.getInt(1);
	String to_be="delete from food where fid="+fid+" and vid="+my_id+"";
	smt.executeUpdate(to_be);
	menu(vname);
}
public void ordme(String vname,int where) throws SQLException, NumberFormatException, IOException {
	System.out.println("the new orders are in the top");
	Statement smt;
	ResultSet rs1=null;
	Connection con=DBUtil.getConnection();;
	smt=con.createStatement();
	String createString="select * from orders where vid=(select vid from vendor where vusername='"+vname+"') order by oid desc";
	rs1=smt.executeQuery(createString);
	boolean result =rs1.next();
	if(!result) {
		System.out.println("Nothing to show");
	}
	do{
		int oid=rs1.getInt(1);
		int vid=rs1.getInt(2);
		int coid=rs1.getInt(3);
		int quan=rs1.getInt(4);
		String eta=rs1.getString(5);
		String date=rs1.getString(6);
		int val=rs1.getInt(7);
		String status=rs1.getString(8);
		int wallet=rs1.getInt(9);
	
	System.out.println(oid+" \t "+vid+" \t "+coid+" \t "+quan+" \t "+eta+" \t "+date+"\t"+val+"\t"+status+"\t"+wallet);
	}
	while(rs1.next());
if(where==0) {
	menu(vname);
}
}

public void changeord(String vname) throws NumberFormatException, SQLException, IOException {
	ordme(vname,1);
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	System.out.println("Enetr order number to be changed ");
	Integer orId=Integer.parseInt(br.readLine());
	System.out.println("Enetr status of the order");
	String orderstatus=br.readLine();
	Statement smt;
	ResultSet rs1=null;
	Connection con=DBUtil.getConnection();;
	smt=con.createStatement();
	String createString="select vid from vendor where vusername='"+vname+"'";
	rs1=smt.executeQuery(createString);
	rs1.next();
	Integer venid=rs1.getInt(1);
	if(orderstatus.equals("delivered")) {
		String to_ord_up="update orders set orderstatus='delivered' where venid="+venid+" and oid="+orId+"";
		smt.executeUpdate(to_ord_up);
	}
	else {
		System.out.println("Enter ETA of the order format 'YYYY-MM-DD HH:MM:SS' ");
		String eta=br.readLine();
	String to_ord_up="update orders set eta='"+eta+"',orderstatus='"+orderstatus+"' where venid="+venid+" and oid="+orId+"";
	smt.executeUpdate(to_ord_up);}
	System.out.println("Updated the record");
	menu(vname);
	
}
}
