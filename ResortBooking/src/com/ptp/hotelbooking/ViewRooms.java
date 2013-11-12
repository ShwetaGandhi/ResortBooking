package com.ptp.hotelbooking;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ViewRooms
 */
@WebServlet("/ViewRooms")
public class ViewRooms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewRooms() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
response.setContentType("text/html");
		
		Gson gson=new Gson();
		
		List <Roomdetails> ls= new ArrayList<>();
		ResultSet rset=null;
		Connection conn = null;
		PrintWriter out=response.getWriter();
		Roomdetails rd;
		
		   try {
		      // Step 1: Allocate a database Connection object
			  DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
		      conn = DriverManager.getConnection("jdbc:mysql://localhost:8888/hotel", "myuser", "root");
		      String sql= "select * from RoomDetails";
		      
		      PreparedStatement prep =  conn.prepareStatement(sql);
		      rset=prep.executeQuery();
		      while(rset.next())
		      {
		    	  rd = new Roomdetails();
		    	  rd.room=rset.getString("room");
		    	  rd.description=rset.getString("description");
		    	  rd.weekday_pz=rset.getDouble("weekday_pz");
		    	  rd.weekend_pz=rset.getDouble("weekend_pz");
		    	  rd.petallowed=rset.getString("petallowed");
		    	  rd.imgurl =rset.getString("imgurl");
		    	  ls.add(rd);
		      
		      }
		      String json=gson.toJson(ls);
		      System.out.println(json);
		      out.print(json);
		   }	   catch(Exception E){
	    		System.out.println("The error is="+E.getMessage());
		    	 
	   }
	}

}
