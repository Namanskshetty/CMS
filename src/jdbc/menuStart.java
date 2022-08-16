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
//import com.DBUtil;
public class menuStart {
	public void menu(String cname) throws NumberFormatException, IOException, SQLException {
		int ch;
		BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Welcome "+cname);
		System.out.println("Enter 1. For menu \n2. To order \n 3. All my orders \n 4.My wallet and personal details \n 5. Change password \n 6.TO EXIT");
		ch=Integer.parseInt(br.readLine());
		switch(ch) {
		case 1:
			menush(cname,0);
			break;
		case 2:
			placeOrder(cname);
			break;
		case 3:
			allord(cname);
			break;
		case 4:
			MY_det(cname);
			break;
		case 5:
			changePass(cname);
			break;
		case 6:
			System.out.println("Exiting the system");
			System.exit(0);
			break;
		default:
			System.out.println("Wrong selection");
			menu(cname);
		}

		
	}
	public void menush(String cname,int where) throws NumberFormatException, IOException, SQLException {
		int food_id,food_price,calories_food;
		String food_name,food_type;
		System.out.println("insise");
		ResultSet rs=null;
		String createString="select * from food";
		try {
			Connection con= DBUtil.getConnection();
			Statement smt;
			smt=con.createStatement();
		rs=smt.executeQuery(createString);
		boolean result =rs.next();
		if(!result) {
			System.out.println("Nothing to show");
		}
		//System.out.println("Sl.no \t Food \t Type \t price \t calories \t");
		do{
			 food_id=rs.getInt(1);
			food_name=rs.getString(2);
			food_type=rs.getString(3);
			food_price=rs.getInt(4);
			calories_food=rs.getInt(6);
		
		System.out.println(food_id+"\t"+food_name+"\t"+"\t"+food_type+"\t"+food_price+"\t"+calories_food);
		}
		while(rs.next());
		}
		catch(SQLException ex){
			System.err.println("SQL "+ex.getMessage());
			
		}
	if(where==0) {
		menu(cname);
	}
		
	}
	//change password for customer
	public void changePass(String cname) throws IOException, SQLException {
		BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
		System.out.println("Enter new Password ");
		String pass=br.readLine();
		String CPass="update customer set password ='"+pass+"' where username='"+cname+"'";
		Connection con= DBUtil.getConnection();
		Statement smt;
		smt=con.createStatement();
		smt.executeUpdate(CPass);
		System.out.println("Done chaning password for "+cname);
		menu(cname);
	}
	public void MY_det(String cname) throws SQLException, NumberFormatException, IOException {
		String CPass1="select * from customer where username='"+cname+"'";
		Connection con= DBUtil.getConnection();
		Statement smt;
		ResultSet rs=null;
		smt=con.createStatement();
		rs=smt.executeQuery(CPass1);
		boolean result =rs.next();
		if(!result) {
			System.out.println("Nothing to show");
		}
		System.out.println("Name \t Username \t Password \t Phone \t Address \t wallet");
		do{
			String name=rs.getString(2);
			String type=rs.getString(3);
			int phone=rs.getInt(5);
			String add=rs.getString(6);
			int wallet=rs.getInt(7);
		
		System.out.println(name+" \t "+type+" \t "+"  ********"+" \t "+phone+" \t "+add+" \t "+wallet);
		}
		while(rs.next());
		menu(cname);
	}
public void placeOrder(String cname) throws SQLException, IOException {
	menush(cname,1);
	BufferedReader br= new BufferedReader (new InputStreamReader(System.in));
	System.out.println("Enter the food you like to order ");
	Integer foodid=Integer.parseInt(br.readLine());
	System.out.println("Enter the food Quantity you like to order ");
	Integer time=Integer.parseInt(br.readLine());
	
	
	String CPass1="select cid,wallet from customer where username='"+cname+"'";
	Connection con= DBUtil.getConnection();
	Statement smt;
	ResultSet rs=null;
	smt=con.createStatement();
	rs=smt.executeQuery(CPass1);
	rs.next();
	int cid=rs.getInt(1);
	int wallet_bal=rs.getInt(2);
	String foodSta="select * from food where fid="+foodid+"";
	ResultSet rs2=smt.executeQuery(foodSta);
	rs2.next();
	int fid=rs2.getInt(1);
	String fname=rs2.getString(2);
	String ftype=rs2.getString(3);
	int price=rs2.getInt(4);
	int vidd=rs2.getInt(5);
	Integer total_price;
	total_price=price*time;
	if(wallet_bal>=total_price) {
		Integer updated_w=wallet_bal-total_price;
		String to_ord="insert into orders values(default,"+vidd+","+cid+","+fid+","+time+",null,default,"+total_price+",'placed')";
		smt.executeUpdate(to_ord);
		System.out.println("Inserted the sql to orders");
		String walup="update customer set wallet="+updated_w+" where cid="+cid+"";
		smt.executeUpdate(walup);
		System.out.println("Updated the wallet balance of "+cname);
		String lastone="select oid from orders where coid="+cid+" order by oid limit 1";
		ResultSet rs3=smt.executeQuery(lastone);
		rs3.next();
		int ordid=rs3.getInt(1);
		System.out.println("Your order number is #"+ordid+"\nYou have orderd "+fname+" Type "+ftype+"\n price for one="+price+"\n total for "+time+" is "+total_price);
		menu(cname);
	}
	else {
		System.out.println("Wallet balance too low cannot proceed with the order");
		menu(cname);
	}
	
}
public void allord(String cname) throws SQLException, NumberFormatException, IOException {
	String CPass1="select cid from customer where username='"+cname+"'";
	Connection con= DBUtil.getConnection();
	Statement smt;
	ResultSet rs=null;
	ResultSet prep= null;
	smt=con.createStatement();
	rs=smt.executeQuery(CPass1);
	rs.next();
	int cid=rs.getInt(1);
	System.out.println(cid);
	String togivefor="select o.oid,f.fname,f.ftype,o.quant,o.eta,o.dntime,o.ordervalue,o.orderstatus from orders o,food f where o.foid=f.fid and o.coid="+cid+ "";
	prep=smt.executeQuery(togivefor);
	boolean result =prep.next();
	if(!result) {
		System.out.println("Nothing to show(order table is empty)");
	}
	System.out.println("If the date is shown as null that means the vendor has not acknoledge the order wait for it to be acklodge");
	System.out.println("order id\tFood name\tfood Type\tQuantity\tEta\tOrder date\tStatus");
	do {
		Integer oid=prep.getInt(1);
		String fname=prep.getString(2);
		String ftype=prep.getString(3);
		Integer quan=prep.getInt(4);
		String eta=prep.getString(5);
		String dnt=prep.getString(6);
		Integer ordval=prep.getInt(7);
		String stateus=prep.getString(8);
		System.out.println(oid+"\t"+fname+"\t"+ftype+"\t"+quan+"\t"+eta+"\t"+dnt+"\t"+ordval+"\t"+stateus);
	}
	while(prep.next());
	menu(cname);
}
}


