package com.ptp.hotelbooking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ReservationServlet
 */
@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
       
   
    public ReservationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 	response.setContentType("text/html");
 	Gson gson=new Gson();
 	List <ReservationDetails> ls= new ArrayList<>();
 	Connection conn=null;
 	//Statement st=null;
 	ResultSet rs= null;
 	PrintWriter out=response.getWriter();
 	String url="jdbc:mysql://localhost:8888/hotel";
 	String user="myuser";
 	String pass="root";
    StringBuilder sb=new StringBuilder();
 	String str=null;
   	while((str=request.getReader().readLine())!=null)
 	   {
 	sb.append(str);
     	}
    ReservationDetails reservdtl=new Gson().fromJson(sb.toString(),ReservationDetails.class);
 	System.out.println(reservdtl.CheckIn);
 	System.out.println(reservdtl.CheckOut);
 	System.out.println(reservdtl.Room_type);
 	 try{
	 	Date startDateWeb = new SimpleDateFormat("MM/dd/yyyy").parse(reservdtl.CheckIn);
	 	Date endDateWeb = new SimpleDateFormat("MM/dd/yyyy").parse(reservdtl.CheckOut);
	 	String startDateWebStr = new SimpleDateFormat("yyyy-MM-dd").format(startDateWeb);
	 	String endDateWebStr = new SimpleDateFormat("yyyy-MM-dd").format(endDateWeb);
 
 	
	 // Step 1: Allocate a database Connection object
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		conn = DriverManager.getConnection("jdbc:mysql://localhost:8888/hotel", "myuser", "root");
 	
		Statement st = conn.createStatement();
 	//st=conn.createStatement();
  	/*rs=st.executeQuery("select * from roomdetails as rd JOIN roomtable as rt ON rd.room_id=rt.type_id LEFT JOIN(select count(rt.room_no) as total,rt.type_id from reservation as r JOIN roomtable as rt ON r.room_no=rt.room_no WHERE startdate BETWEEN '"+startDateWebStr+"' AND '"+endDateWebStr+"' GROUP BY rt.type_id)as rot ON rd.room_id=rot.type_id WHERE (rot.total IS NULL OR rot.total<rd.same_room)AND rd.room = '"+reservdtl.Room_type+"'GROUP BY rt.type_id");
 	System.out.println(rs);  
 	System.out.println("hi db connected");*/
		String query = "select * from roomdetails as rd JOIN roomtable as rt ON rd.room_id=rt.type_id LEFT JOIN(select count(rt.room_no) as total,rt.type_id from reservation as r JOIN roomtable as rt ON r.room_no=rt.room_no WHERE startdate BETWEEN '"+startDateWebStr+"' AND '"+endDateWebStr+"' GROUP BY rt.type_id)as rot ON rd.room_id=rot.type_id WHERE (rot.total IS NULL OR rot.total<rd.same_room)AND rd.room = '"+reservdtl.Room_type+"'GROUP BY rt.type_id";
		//String query="select * from roomdetails as rd JOIN roomtable as rt ON rd.room_id=rt.type_id LEFT JOIN(select count(rt.room_no) as total,rt.type_id from reservation as r JOIN roomtable as rt ON r.room_no=rt.room_no WHERE startdate BETWEEN '"+startDateWebStr+"' AND '"+endDateWebStr+"' GROUP BY rt.type_id)as rot ON rd.room_id=rot.type_id WHERE (rot.total IS NULL OR rot.total<rd.same_room)GROUP BY rt.type_id";
		System.out.println(query);
		rs =st.executeQuery(query);
	ReservationDetails resDtl = new ReservationDetails();
 	while(rs.next())	
 	{
 
 	
 	 //Right side comes from POJO and left side comes from DB
 	resDtl.totalOccupied=rs.getInt("total");
 	resDtl.sameroom=rs.getInt("same_room");//Changed from sameRoom to sameroom
 	System.out.println(rs.getString(12));
 	System.out.println(rs.getInt(7));
 	resDtl.availableRoom=resDtl.sameroom-resDtl.totalOccupied;
 	
 	//ls.add(resDtl);
 	
 	//System.out.println(resDtl.CheckIn);
 	   	 
 	}
 	
 	/*System.out.println("hi");
 	System.out.println("calling"+ls);*/
 	String json=gson.toJson(resDtl);
 	  System.out.println(json);
 	     out.print(json);
 	}
 	catch(Exception ex)
 	{
 	System.out.println("not connect to db");
 	ex.printStackTrace();
 	}


}

}