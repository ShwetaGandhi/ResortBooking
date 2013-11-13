package com.ptp.hotelbooking;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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
		
		//Takes Json String from Request.
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String str;
		PrintWriter out=response.getWriter();
		
		while ((str = reader.readLine()) != null) {
				sb.append(str);
		}
		
		//ReservationDetails is a POJO class and this line help in mapping POJO to JSON
		ReservationDetails details = new Gson().fromJson(sb.toString(),ReservationDetails.class);
		
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
		 
			if(rs.next())
			{
				details.Reserv_id=rs.getInt("res_id");
				System.out.println(details.Reserv_id);
			}

		//second table updation
		String sql = "insert into roomtable(room_no,type_id,res_id) values ('"+details.Room_type+"','"+details.Room_id+"','"+details.Reserv_id+"')";
		int temp = st.executeUpdate(sql);
		
		 System.out.println(temp);
		 
		//this is my methodname which I used for sending mail
		 sendConfirmationEmail(details);
		 
		 Gson gson = new Gson();
			String json=gson.toJson(details);
			out.print(json);
			
		//sending the data back to controller to take desire action
		
			 
		}//closing for try
		catch (Exception E) {
			System.out.println("The error is=" + E.getMessage());
			E.printStackTrace();
		}
		
	}//closing for post
	
	//If above part works it will send mail confirmation.
		private void sendConfirmationEmail(ReservationDetails returnInfo) {
			final String username = "shwetu09@gmail.com";
			final String password = "Consultant13";
			
				
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	 
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
	 
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("shwetu09@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(returnInfo.email));
				
				message.setSubject("Utopia~way of living reservation confirmation");
				
				String htmlText;
				//I used htmlText instead of message.setText coz I wanted to send image. Not sure if that is right way but, it worked for now.
				//adds check in, check out, room type, image and website link. 
				htmlText ="hello Your Check In date is"+ returnInfo.CheckIn+"\n<br>"+"Check Out is" +returnInfo.CheckOut+"\n<br>"+"Room Tyep is"+returnInfo.Room_type+"\n<br>"+"Visit us at http://localhost:9998/ResortBooking/ for resrvation or just find out deals !";
				htmlText+="<p align=center><img src=\"cid:image\"> </p>";
				
				//-----Message with attachments------------------------------
				MimeMultipart multipart = new MimeMultipart("related");

		        // first part  (the html)
		     BodyPart messageBodyPart = new MimeBodyPart();
		     messageBodyPart.setContent(htmlText,"text/html");

		        // add it
		        multipart.addBodyPart(messageBodyPart);
		        
		        // second part (the image)
		        messageBodyPart = new MimeBodyPart();
		        DataSource fds = new FileDataSource
		          ("C:\\pic-hotel\\Logo.png");
		        messageBodyPart.setDataHandler(new DataHandler(fds));
		        messageBodyPart.addHeader("Content-ID","<image>");
		        
		        // add it
		        multipart.addBodyPart(messageBodyPart);

		        // put everything together
		         message.setContent(multipart );
				Transport.send(message);
	 
				System.out.println("Done");
	 
			}//end try 
			catch (MessagingException e) {
				throw new RuntimeException(e);
			}//end catch
		}//end sendconfirmation method

	}//end class
	

