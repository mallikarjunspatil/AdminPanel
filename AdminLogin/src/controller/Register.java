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

@WebServlet("/registerlink")
public class Register extends HttpServlet
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
		String studentName=req.getParameter("studentName");
		String studentPer=req.getParameter("studentPer");
		String degree=req.getParameter("degree");
		String yop=req.getParameter("yop");
		String collage=req.getParameter("collage");
		
		//DECLARE JDBC RESOURCES
		PreparedStatement pstmt=null;
		
		//CREATE SQL QUERY
		String query="insert into student_data values(?,?,?,?,?)";
		
		//FOLLOW JDBC CONNECTIVITY STEPS
		try 
		{
			pstmt=con.prepareStatement(query);
			 
			pstmt.setString(1, studentName);
			pstmt.setString(2, studentPer);
			pstmt.setString(3, degree);
			pstmt.setString(4, yop);
			pstmt.setString(5, collage);
			
			int count=pstmt.executeUpdate();
			
			//resp.sendRedirect("details.jsp");
			
			PrintWriter pw=resp.getWriter();
			pw.print("<h1>Registered Successfully </h1>");
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}			
	}
}
