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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class SearchRoom
 */
@WebServlet("/SearchRoom")
public class SearchRoom extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRoom() {
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
		String checkIn = request.getParameter("from");
		String checkOut = request.getParameter("to");
		PrintWriter out = response.getWriter();
		
		//set the MIME type for the response message
		response.setContentType("text/html");
		
		Gson gson = new Gson();
		JsonObject jsonobj = new JsonObject();
		
		/*Roomdetails roomInInfo = getInfo(checkIn);
		Roomdetails roomOutInfo =getInfo(checkOut);*/
		
		Roomdetails roomInfo=getInfo(checkIn, checkOut);
		String json = gson.toJson(roomInfo);
		//String jsonOut = gson.toJson(roomOutInfo);
		
		out.println(json
				);
		//out.println(jsonOut);
		
		out.close();
	}
	
	private Roomdetails getInfo(String checkIn, String checkOut){
		List<Roomdetails> roomList=new ArrayList<>();
		Connection conn = null;
		Roomdetails roomObj=null;
		try{
			 DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
	   	      conn = DriverManager.getConnection("jdbc:mysql://localhost:8888/hotel", "myuser", "root");
	   	      
	   	      String selectSQL = "select * from roomdetails as rd join roomtable as rt on rd.room_id=" +
	   	      		"rt.type_id left join (select count (rt.room_no) as total, rt.type_id from reservation" +
	   	      		"as r join roomtable as rt on r.room_no = rt.room_no where startdate between ? and ? group by rt.type_id" +
	   	      		") as rot on rd.room_id = rot.type_id where(rot.total is null or rot.total < rd.same_room) group by rt.type_id";
	   	   PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
	     	preparedStatement.setString(1,checkIn);
	     	preparedStatement.setString(2,checkOut);
			
	     	System.out.println(preparedStatement.toString());
	       	ResultSet rs = preparedStatement.executeQuery();
	       	
	       	roomObj = new Roomdetails();
	       	if(rs!=null){
	       		while(rs.next()){
	       			roomObj.description=rs.getString("description");
	       			roomObj.imgurl=rs.getString("imgurl");
	       			roomObj.weekday_pz=rs.getDouble("weekday_pz");
	       			}
	       		}
	       	rs.close();
	       	preparedStatement.close();
	       	conn.close();
	       	     	}//try closing
		 catch(Exception e){
			 System.out.println(e);
			 }                                  
		 
        return roomObj;
		}
		
		
	}


