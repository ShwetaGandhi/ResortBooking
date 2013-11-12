package com.ptp.hotelbooking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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

    	Connection con=null;
    	Statement st=null;
    	ResultSet rs= null;
    	PrintWriter out=response.getWriter();
    	String url="jdbc:mysql://localhost:8888/hotel";
    	String user="myuser";
    	String pass="root";
    	System.out.println("we startinga program!");
    	Roomdetails usrrinfo=null;
    	String emailValue=request.getReader().readLine();
    	Roomdetails userinfo=new Gson().fromJson(emailValue,Roomdetails.class);
    	System.out.println(userinfo.email);

    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
    		con=DriverManager.getConnection(url,user,pass);
    		st=con.createStatement();
    		//System.out.println("connect to DB");
    		rs=st.executeQuery("select * from user where email='"+userinfo.email+"'"+" and "+"password='"+userinfo.password+"'");

    		System.out.println("execute the query");  
    		System.out.println("hi db connected");
    		userinfo = new Roomdetails();
    		while(rs.next())
    		{

    			userinfo.email=rs.getString("email");
    			userinfo.password=rs.getString("password");

    		}
    		System.out.println("calling"+userinfo.email+""+userinfo.password);
    		String json=gson.toJson(userinfo);
    		System.out.println(json);
    		out.print(json);
    	}
    	catch(Exception ex)
    	{
    		System.out.println("not connect to db");
    	}
     	
	}

}
