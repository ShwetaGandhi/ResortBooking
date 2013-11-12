package com.ptp.hotelbooking;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.*;
import javax.activation.*;
import com.sun.activation.*;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


@WebServlet("/MailServlet")
public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MailServlet() {
        super();
        
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       	response.setContentType("text/html");
    	Gson gson=new Gson();

    	Connection con=null;
    	Statement st=null;
    	ResultSet rs= null;
    	PrintWriter out=response.getWriter();
    	String url="jdbc:mysql://localhost:8888/hotel";
    	String user="myuser";
    	String pass="root";
    	
    	Roomdetails usrrinfo=null;
    	String emailValue=request.getReader().readLine();
    	Roomdetails userinfo=new Gson().fromJson(emailValue,Roomdetails.class);
    	
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
    		con=DriverManager.getConnection(url,user,pass);
    		st=con.createStatement();
     		rs=st.executeQuery("select * from user where email='"+userinfo.email+"'"+" and "+"password='"+userinfo.password+"'");

    	 	userinfo = new Roomdetails();
    		while(rs.next())
    		{

    			userinfo.email=rs.getString("email");
    			userinfo.password=rs.getString("password");

    		}
    		String json=gson.toJson(userinfo);
     		out.print(json);
    		String[] to={userinfo.email};
    		String[] cc={userinfo.email};
    		String[] bcc={userinfo.email};
    		//This is for google

    		String userName="shwetu09@gmail.com";
    		String passWord="Consultant13";
    		String host="smtp.gmail.com";
    		String port="465";
    		String starttls="true";
    		String auth="true";
    		boolean debug=true;
    		String socketFactoryClass="javax.net.ssl.SSLSocketFactory";
    		String fallback="false";
    		String subject="hi, Test Mail";
    		String text="This is my reply";

    		Properties props = new Properties();
    		//Properties props=System.getProperties();
    		props.put("mail.smtp.user", userName);
    		props.put("mail.smtp.host", host);
    		if(!"".equals(port))
    			props.put("mail.smtp.port", port);
    		if(!"".equals(starttls))
    			props.put("mail.smtp.starttls.enable",starttls);
    		props.put("mail.smtp.auth", auth);
    		if(debug)
    		{
    			props.put("mail.smtp.debug", "true");
    		}
    		else
    		{
    			props.put("mail.smtp.debug", "false");         
    		}
    		if(!"".equals(port))
    			props.put("mail.smtp.socketFactory.port", port);
    		if(!"".equals(socketFactoryClass))
    			props.put("mail.smtp.socketFactory.class",socketFactoryClass);
    		if(!"".equals(fallback))
    			props.put("mail.smtp.socketFactory.fallback", fallback);

    		try
    		{
    			Session session = Session.getDefaultInstance(props, null);
    			session.setDebug(debug);
    			MimeMessage msg = new MimeMessage(session);
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
    			msg.saveChanges();
    			Transport transport = session.getTransport("smtp");
    			transport.connect(host, userName, passWord);
    			transport.sendMessage(msg, msg.getAllRecipients());
    			transport.close();
  			 
    		}
    		catch (Exception mex)
    		{
    			mex.printStackTrace();

    		}

    		Mail.sendMail("shwetu09@gmail.com","Consultant13","smtp.gmail.com","465","true","true",true,
    				"javax.net.ssl.SSLSocketFactory","false",to,cc,bcc,
    				"hi, Test Mail",
    				"Finally Your reservation is done");	
    	}
    	catch(Exception ex)
    	{
    		System.out.println("not connect to db");
    	}
     	
    }
}

