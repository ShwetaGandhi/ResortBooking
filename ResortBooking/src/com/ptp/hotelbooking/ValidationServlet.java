package com.ptp.hotelbooking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ValidationServlet
 */
@WebServlet("/ValidationServlet")
public class ValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	    //System.out.print("in See");	
    	  
    		
    		response.setContentType("text/html");
    		Gson gson=new Gson();
    		
    		Connection con=null;
    		Statement st=null;
    		ResultSet rs= null;
    		PrintWriter out=response.getWriter();
    		String url="jdbc:mysql://localhost:8888/hotel";
    		String user="myuser";
    		String pass="root";
    		//System.out.println("we startinga program!");
    		Roomdetails usrDtl=null;
    	String emailValue=request.getReader().readLine();
    	Roomdetails usrdtl=new Gson().fromJson(emailValue,Roomdetails.class);
    	//System.out.println(usrdtl.email);
    	
    		try
    		{
    			Class.forName("com.mysql.jdbc.Driver");
    			con=DriverManager.getConnection(url,user,pass);
    			st=con.createStatement();
    			//System.out.println("connect to DB");
    			rs=st.executeQuery("select * from User where email='"+usrdtl.email+"'"+" and "+"password='"+usrdtl.password+"'");
    			/*System.out.println("execute the query");  
    			System.out.println("hi db connected");*/
    			usrDtl = new Roomdetails();
    			while(rs.next())
    			{
    				
    				usrDtl.email=rs.getString("email");
    				usrDtl.password=rs.getString("password");
    		    	 
    			}
    			//System.out.println("calling"+usrDtl.email+""+usrDtl.password);
    		String json=gson.toJson(usrDtl);
    		   System.out.println(json);
    		      out.print(json);
    			}
    		catch(Exception ex)
    		{
    			System.out.println("not connect to db");
    		}


    	}
    	
    }
