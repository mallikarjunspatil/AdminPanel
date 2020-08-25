package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/displaylink")
public class DisplayCandidate extends HttpServlet
{
	Connection con;
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		ServletContext context=config.getServletContext();
		
		String driver=context.getInitParameter("driver");
		String url=context.getInitParameter("url");
		String user=context.getInitParameter("user");
		String password=context.getInitParameter("password");
		
		try 
		{
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,password);
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		//declare database resources
		String query="select studentName,studentPercentage from student_data";
		ResultSet rs=null;
		Statement stmt=null;
		PrintWriter pw=null;
		pw=resp.getWriter();
		try 
		{
			stmt=con.createStatement();
			rs=stmt.executeQuery(query);
			
			//table Tag of html
			pw.print("<table style='latter-spacing:5px' border='2' width='100%'>");
			pw.print("<h1>ShortListed Candidate</h1>");
			pw.print("<thead>");
			pw.print("<tr>");
			pw.print("<th>Name</th>");
			pw.print("<th>Percentage</th>");
			pw.print("</tr>");
			pw.print("</thead>");
			pw.print("<tbody>");
			while(rs.next())
			{
				String name=rs.getString(1);
				float percentage=rs.getFloat(2);
				if(percentage>=60)
				{
				pw.print("<tr>");
				pw.print("<td>"+name+"</td>");
				pw.print("<td>"+percentage+"</td>");
				pw.print("</tr>");
				}
			}
			pw.print("</tbody>");
			pw.print("</table>");
			
			rs=stmt.executeQuery(query);
			
			pw.print("<table style='latter-spacing:5px' border='2' width='100%'>");
			pw.print("<h1>Rejected Candidate</h1>");
			pw.print("<thead>");
			pw.print("<tr>");
			pw.print("<th>Name</th>");
			pw.print("<th>Percentage</th>");
			pw.print("</tr>");
			pw.print("</thead>");
			pw.print("<tbody>");
			while(rs.next())
			{
				String name=rs.getString(1);
				float percentage=rs.getFloat(2);
				if(percentage<60)
				{
				pw.print("<tr>");
				pw.print("<td>"+name+"</td>");
				pw.print("<td>"+percentage+"</td>");
				pw.print("</tr>");
				}
			}
			pw.print("</tbody>");
			pw.print("</table>");
			
		
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
	}
}
