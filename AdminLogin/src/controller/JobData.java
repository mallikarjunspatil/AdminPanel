package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.LoginAdmin;

@WebServlet("/loginlink")
public class JobData extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String username=req.getParameter("user");
		String password=req.getParameter("password");
		LoginAdmin login=new LoginAdmin();
		boolean b= login.validation(username, password);
		
		if(b)
		{
			resp.sendRedirect("jobpost.html");
		}
		else
		{
			resp.sendRedirect("index.html");
		}
	}
}
