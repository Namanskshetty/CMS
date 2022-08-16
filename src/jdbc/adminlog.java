package jdbc;
import com.DBUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class adminlog {
	public void enter() throws IOException, SQLException{
		//@SuppressWarnings("unused")
		String name,password;
		BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
			System.out.println("Enetr user name ");
			name=br.readLine();
			System.out.println("Enetr user password ");
			password=br.readLine();
			if(name.equals("admin") && password.equals("password")) {
				System.out.println("Login sucessfull\n1. For adding vendors\n2.Show all vendors\n3. Deleting vendors \n4. Show customer details \n 5. Delete customer details\n6. Show all orders");
				int ch=Integer.parseInt(br.readLine());
				switch(ch) {
				case 1:
					add_vendors();
					break;
				case 2:
					show_vendors();
					break;
				case 3:
					del_vendors();
					break;
				case 4:
					show_customer();
					break;
				case 5:
					del_customer();
					break;
				case 6:
					show_order();
					break;
				default:
					System.out.println("Invaild choice");
					enter();
					}
				}
			else {
				System.out.println("error\n type yes to try again press any key to exit");
				String check=br.readLine();
				if(check.equals("yes")) {
					enter();
				}
				else {
					System.out.println("BYe!!\n Run againt to start from fresh");
				}
			}
			
	}
	public void add_vendors() throws IOException, SQLException {
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
		
		String to_in="insert into vendor(vname,vusername,vpassword,phone,vaddress) values(?,?,?,?,?)";
		Connection con= DBUtil.getConnection();
		
		PreparedStatement ps=con.prepareStatement(to_in);
		ps.setString(1,name);
		ps.setString(2,uname);
		ps.setString(3,pass);
		ps.setInt(4,phone);
		ps.setString(5,addres);
		ps.executeUpdate();
		System.out.println("Created Profile sucessfully!!!!\n Please go on with the rest");
		enter();
		
	}
	//Delete the vendors using id 
	public void show_vendors() throws SQLException {
		System.out.println("vendors list");
		String to_in="select * from vendor";
		Connection con= DBUtil.getConnection();
		Statement smt;
		ResultSet rs=null;
		smt=con.createStatement();
		rs=smt.executeQuery(to_in);
		boolean result =rs.next();
		if(!result) {
			System.out.println("Nothing to show");
		}
		do{
			 int id=rs.getInt(1);
			String name=rs.getString(2);
			String type=rs.getString(3);
			int price=rs.getInt(5);
			String add=rs.getString(6);
		
		System.out.println(id+"\t"+name+"\t"+type+"\t"+price+"\t"+add);
		}
		while(rs.next());
		}
	public void del_vendors() throws NumberFormatException, IOException, SQLException {
		//First delete food with the vendor
		BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Enter the vendor id to be deleted(Caution deletion of vednor may result in deletion of food from the menu by the vendor!)");
		int del_id=Integer.parseInt(br.readLine());
		String food_del="delete from food where vid="+del_id+"";
		String ord_up="update orders set foid=null,venid=null where venid="+del_id+"";
		String v_del="delete from vendor where vid="+del_id+"";
		Connection con= DBUtil.getConnection();
		Statement smt;
		smt=con.createStatement();
		try {
		smt.executeUpdate(food_del);
		smt.executeUpdate(ord_up);
		System.out.println("Removed the food with id and orders");}
		catch(SQLException e){
			System.out.println(e);
		}
		smt.executeUpdate(v_del);	
		System.out.println("Removed the vendor");
		enter();
	}
		public void del_customer() throws NumberFormatException, IOException, SQLException {
			BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
			System.out.println("Enter the customer id to be deleted");
			int del_id=Integer.parseInt(br.readLine());
			String state="delete from customer where cid="+del_id+"";
			Connection con= DBUtil.getConnection();
			Statement smt;
			smt=con.createStatement();
			smt.executeUpdate(state);
			System.out.println("Deleted customer!!");
			enter();	
		}
		//Shows details of customer
		public void show_customer() throws SQLException, IOException {
			System.out.println("customer list");
			String to_in="select * from customer";
			Connection con= DBUtil.getConnection();
			Statement smt;
			ResultSet rs=null;
			smt=con.createStatement();
			rs=smt.executeQuery(to_in);
			boolean result =rs.next();
			if(!result) {
				System.out.println("Nothing to show");
			}
			do{
				 int id=rs.getInt(1);
				String name=rs.getString(2);
				String type=rs.getString(3);
				int price=rs.getInt(5);
				String add=rs.getString(6);
			
			System.out.println(id+"\t"+name+"\t"+type+"\t"+price+"\t"+add);
			}
			while(rs.next());
			enter();
		}
	public void show_order() throws SQLException, IOException {
		System.out.println("Showing the order details");
		String to_ino="select * from orders";
		Connection con= DBUtil.getConnection();
		Statement smt;
		ResultSet rs=null;
		smt=con.createStatement();
		rs=smt.executeQuery(to_ino);
		boolean result =rs.next();
		if(!result) {
			System.out.println("Nothing to show(order table is empty)");
		}
		System.out.println("orderid \t c_id \t vid \t quantity \t eta \t time \t val \tstatus");
		do{
			 int oid=rs.getInt(1);
			 int cid=rs.getInt(2);
			 int vid=rs.getInt(3);
			 int fid=rs.getInt(4);
			 int quan=rs.getInt(5);
			String eta=rs.getString(6);
			String time=rs.getString(7);
			int val=rs.getInt(8);
			String status=rs.getString(9);
		
		System.out.println(oid+"\t"+cid+"\t"+vid+"\t"+fid+"\t"+quan+"\t"+eta+"\t"+time+"\t"+val+"\t"+status);
		}
		while(rs.next());
		enter();
		
	}
}


