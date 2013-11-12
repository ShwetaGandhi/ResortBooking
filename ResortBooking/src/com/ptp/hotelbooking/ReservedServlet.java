package com.ptp.hotelbooking;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
/**
 * Servlet implementation class ReservedServlet
 */
@WebServlet("/ReservedServlet")
public class ReservedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public ReservedServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//content type
		response.setContentType("text/html");
		
		//ResultSet rs=null;
		
		Gson gson=new Gson();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String str;
		PrintWriter out=response.getWriter();
		
		while ((str = reader.readLine()) != null) {
			//System.out.println(str);
			sb.append(str);
		}
		
		//System.out.println(sb.toString());
		ReservationDetails details = new Gson().fromJson(sb.toString(),
		ReservationDetails.class);
		//System.out.println("After Gson");
		Connection conn = null;
		try {
			//accessing date values from rootscope
			Date startDateWeb = new SimpleDateFormat("MM/dd/yyyy").parse(details.CheckIn);
		 	Date endDateWeb = new SimpleDateFormat("MM/dd/yyyy").parse(details.CheckOut);
		 	
		 	//Changing date values to database format
		 	String startDateWebStr = new SimpleDateFormat("yyyy-MM-dd").format(startDateWeb);
		 	String endDateWebStr = new SimpleDateFormat("yyyy-MM-dd").format(endDateWeb);
		 		
		 	//Hard coded value. Need to find batter way of doing it
			 	if(details.Room_type.equals("Deluxe"))
			 	{
				 	details.Room_id=1;
				 	System.out.println(details.Room_id);
			 	}
			 	else if(details.Room_type.equals("Deluxe King"))
			 	{
				 	details.Room_id=2;
				 	System.out.println(details.Room_id);
			 	}
			 	else if(details.Room_type.equals("Deluxe Queen"))
			 	{
				 	details.Room_id=3;
				 	System.out.println(details.Room_id);
			 	}
			 	else if(details.Room_type.equals("Queen"))
			 	{
				 	details.Room_id=5;
				 	System.out.println(details.Room_id);
			 	}
			 	else if(details.Room_type.equals("King"))
			 	{
				 	details.Room_id=4;
				 	System.out.println(details.Room_id);
			 	}
			 	else
			 	{
			  	}
			 	
		// Step 1: Allocate a database Connection object
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		conn = DriverManager.getConnection("jdbc:mysql://localhost:8888/hotel", "myuser", "root");
		
		// Step 3: Execute a SQL SELECT query
		Statement st = conn.createStatement();
		//first table updation
		String query="insert into reservation(startdate,enddate,room_no,cust_name) values('"+startDateWebStr+"','"+endDateWebStr+"','"+details.Room_type+"','"+details.email+"')";
		st.executeUpdate(query);
		
		//to get reservation id for future updation or deletion
		String sql1="select * from reservation where startdate='"+startDateWebStr+"'"+"AND enddate='"+endDateWebStr+"'";
		 ResultSet rs=st.executeQuery(sql1);
		 ReservationDetails ResDtl=new ReservationDetails();
			if(rs.next())
			{
				ResDtl.Reserv_id=rs.getInt("res_id");
				System.out.println(ResDtl.Reserv_id);
			}

		//second table updation
		String sql = "insert into roomtable(room_no,type_id,res_id) values ('"+details.Room_type+"','"+details.Room_id+"','"+ResDtl.Reserv_id+"')";
		int temp = st.executeUpdate(sql);
		
		 System.out.println(temp);
			
		
		
		//sending the data back to controller to take desire action
		String json=gson.toJson(ResDtl);
		out.print(json);
		//st.close();
		//out.close();
		 
		//part is copied from mailservlet.
			String[] to={details.email};
    		String[] cc={details.email};
    		String[] bcc={details.email};
    		//This is for google

    		String userName="shwetu09@gmail.com";
    		String passWord="Consultant13";
    		String host="smtp.gmail.com";
    		String port="465";
    		String starttls="true";
    		String auth="true";
    	//	boolean debug=true;
    		String socketFactoryClass="javax.net.ssl.SSLSocketFactory";
    		String fallback="false";
    		String subject="Confirmation";
    		String text="Email id:" +details.email+"\nReservation id"+ResDtl.Reserv_id+"\ncheck in:"+startDateWebStr+"\nCheck Out:"+endDateWebStr;

    		Properties props = new Properties();
    		//Properties props=System.getProperties();
    		props.put("mail.smtp.user", userName);
    		props.put("mail.smtp.host", host);
    		if(!"".equals(port))
    			props.put("mail.smtp.port", port);
    		if(!"".equals(starttls))
    			props.put("mail.smtp.starttls.enable",starttls);
    		props.put("mail.smtp.auth", auth);
    		//changed value from true to false to stop debugging
    		/*if(debug)
    		{
    			props.put("mail.smtp.debug", "false");
    		}
    		else
    		{
    			props.put("mail.smtp.debug", "false");         
    		}*/
    		if(!"".equals(port))
    			props.put("mail.smtp.socketFactory.port", port);
    		if(!"".equals(socketFactoryClass))
    			props.put("mail.smtp.socketFactory.class",socketFactoryClass);
    		if(!"".equals(fallback))
    			props.put("mail.smtp.socketFactory.fallback", fallback);

    		try
    		{
    			Session session = Session.getDefaultInstance(props, null);
    			//session.setDebug(debug);
    			/*MimeMessage msg = new MimeMessage(session);
    			msg.setText(text);
    			msg.setSubject(subject);
    			msg.setFrom(new InternetAddress("shwetu09@gmail.com"));
    			for(int i=0;i<to.length;i++)
    			{
    				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
    			}
    			for(int i=0;i<cc.length;i++)
    			{
    				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
    			}
    			for(int i=0;i<bcc.length;i++)
    			{
    				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
    			}
    			msg.saveChanges();*/
    			Transport transport = session.getTransport("smtp");
    			transport.connect(host, userName, passWord);
    			//transport.sendMessage(msg, msg.getAllRecipients());
    			transport.close();
  			 
    		}
    		catch (Exception mex)
    		{
    			mex.printStackTrace();

    		
    		}

    		Mail.sendMail("shwetu09@gmail.com","Consultant13","smtp.gmail.com","465","true","true",true,
    				"javax.net.ssl.SSLSocketFactory","false",to,cc,bcc,
    				"hi, Test Mail",
    		"Finally Your reservation is done"+text);
		}//closing for try
		catch (Exception E) {
			System.out.println("The error is=" + E.getMessage());
			E.printStackTrace();
		}
	}

}
