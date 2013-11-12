package com.ptp.hotelbooking;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class SaveUser
 */
@WebServlet("/SaveUser")
public class SaveUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUser() {
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
		StringBuilder sb = new StringBuilder();
    	BufferedReader reader = request.getReader();
    	String str;
    	while((str = reader.readLine()) != null) {
    			sb.append(str);
    		System.out.println(str);
    	}
    	Roomdetails userinfo = new Gson().fromJson(sb.toString(), Roomdetails.class);
    	//System.out.println("After Gson");
	   Connection conn = null;
	   try {
	      // Step 1: Allocate a database Connection object
		  DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
	      conn = DriverManager.getConnection("jdbc:mysql://localhost:8888/hotel", "myuser", "root");

	      // Step 3: Execute a SQL SELECT query
	      String sql = "insert into user (name, password, email, address, age, creditcardnumber) values (?,?,?,?,?,?)";
	      PreparedStatement prep =  conn.prepareStatement(sql); 
	   
	      // Setting the values which we got from JSP form
	    		 
	    		  prep.setString(1, userinfo.name);
	    		  prep.setString(2,userinfo.password);
	    		  prep.setString (3, userinfo.email);
	    		  prep.setString(4,userinfo.address);
	    		  prep.setInt(5, userinfo.age);
	    		  prep.setInt(6, userinfo.creditcardnumber);
	    		  prep.executeUpdate();
	    		  prep.close();
	    	
	    	PrintWriter out = response.getWriter();
	    	out.println("{}");
	    	out.close();
	    		    }
	   catch(Exception E){
	    		System.out.println("The error is="+E.getMessage());
	    		    	 
	   }
	}

}
