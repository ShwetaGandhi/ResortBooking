package com.ptp.hotelbooking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class feedbackServlet
 */
@WebServlet("/feedbackServlet")
public class feedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public feedbackServlet() {
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
		ResultSet rs=null;
				
				Gson gson=new Gson();
				List<Comment> ls=new ArrayList<>();
				StringBuilder sb = new StringBuilder();
				BufferedReader reader = request.getReader();
				String str;
				PrintWriter out=response.getWriter();
				
				while ((str = reader.readLine()) != null) {
					sb.append(str);
				}
					Comment ct = new Gson().fromJson(sb.toString(),Comment.class);
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
							//System.out.println(details.Reserv_id);
							String query="insert into comment (name,email,rate,comment) values ('"+ct.name+"','"+ct.email+"','"+ct.rate+"','"+ct.comment+"')";
						
							//System.out.println("Hello");
							//System.out.println(query);
							 st.executeUpdate(query);
							 rs = st.executeQuery("select * from comment");
							/*Comment cmt = new Comment();*/
							 while(rs.next()){
									Comment cmt = new Comment();
									cmt.name = rs.getString("name");
									cmt.rate=rs.getString("rate");
									cmt.comment=rs.getString("comment");
									cmt.cmtdate=rs.getString("mydate");
									System.out.println(rs.getString("mydate"));
									ls.add(cmt);
									//System.out.print(ls);
								}
							//System.out.println(r+"..................");
							
							String json=gson.toJson(ls);
							out.print(json);
			}//closing for try
			catch (Exception E) {
				System.out.println("The error is=" + E.getMessage());
				E.printStackTrace();
			}
	}

}
