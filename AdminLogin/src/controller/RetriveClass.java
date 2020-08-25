package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminlink")
public class RetriveClass extends HttpServlet
{
	Connection con;
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		//db resources initialization
		ServletContext context=config.getServletContext();
		String drivername=context.getInitParameter("driver");
		String url=context.getInitParameter("url");
		String user=context.getInitParameter("user");
		String password=context.getInitParameter("password");
			try 
			{
			Class.forName(drivername);
			con=DriverManager.getConnection(url,user,password);
			} 
			catch (ClassNotFoundException | SQLException e)
			{
				e.printStackTrace();
			}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException       
	{
		//FETCH THE VALUE FROM HTML
		String jobid=req.getParameter("jobid");
		
		String rollName=req.getParameter("rollName");
		String description=req.getParameter("description");
		String interviewDate=req.getParameter("idate");
		String experiDate=req.getParameter("edate");
		int jobid1=Integer.parseInt(jobid);
		String percentage=req.getParameter("percentage");
		float percentage1=Float.parseFloat(percentage);
		
		String comparea=req.getParameter("add");
		String comparep=req.getParameter("exit");
		String logout=req.getParameter("logout");
		
		//DECLARE JDBC RESOURCES
		PreparedStatement pstmt=null;
		
		//CREATE SQL QUERY
		String query="insert into admin values(?,?,?,?,?,?)";
		
		//FOLLOW JDBC CONNECTIVITY STEPS
		try 
		{
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, jobid1);
			pstmt.setString(2, rollName);
			pstmt.setString(3, description);
			pstmt.setString(4, interviewDate);
			pstmt.setString(5, experiDate);
			pstmt.setFloat(6, percentage1);
			
			int count=pstmt.executeUpdate();
			
//			PrintWriter pw=resp.getWriter();
//			pw.print(count+"<h1> Job Posted Successfully </h1>");
			if(comparea!=null)
			{
				resp.sendRedirect("welcome.html");
			}
			else if(comparep!=null)
			{
				//If admin will click post page it will get apply page
				resp.sendRedirect("jobpost.html");
			}
			else if(logout!=null)
			{
				resp.sendRedirect("index.html");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}			
	}
}
