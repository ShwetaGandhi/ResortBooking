package com.ptp.hotelbooking;

import java.io.BufferedReader;
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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
ResultSet rs=null;
		
		Gson gson=new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String str;
		PrintWriter out=response.getWriter();
		
		while ((str = reader.readLine()) != null) {
			sb.append(str);
		}
			ReservationDetails details = new Gson().fromJson(sb.toString(),ReservationDetails.class);
			System.out.println(sb.toString());
					//System.out.println("After Gson");
					Connection conn = null;
				try{	
					// Step 1: Allocate a database Connection object
					DriverManager.registerDriver(new com.mysql.jdbc.Driver());
					conn = DriverManager.getConnection("jdbc:mysql://localhost:8888/hotel", "myuser", "root");
					
					// Step 3: Execute a SQL SELECT query
					Statement st = conn.createStatement();
					//System.out.println("before my query");
					System.out.println(details.Reserv_id);
					String query="delete reservation, roomtable from reservation inner join roomtable where reservation.res_id = roomtable.res_id and reservation.res_id='"+details.Reserv_id+"'";
					//System.out.println("Hello");
					//System.out.println(query);
					int r= st.executeUpdate(query);
					//System.out.println(r+"..................");
					
					String json=gson.toJson(details);
					out.print(json);
	}//closing for try
	catch (Exception E) {
		System.out.println("The error is=" + E.getMessage());
		E.printStackTrace();
	}
		
	}

}
