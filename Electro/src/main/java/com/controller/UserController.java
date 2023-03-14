package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.User;
import com.dao.UserDao;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("register"))
		{
			
			boolean flag = UserDao.checkEmail(request.getParameter("email"));
			
			if(flag==true)
			{
				request.setAttribute("msg", "Email-id already Registered.");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
			else if(request.getParameter("password").equals(request.getParameter("cpassword")))
			{
				User u = new User();
				u.setFname(request.getParameter("fname"));
				u.setLname(request.getParameter("lname"));
				u.setEmail(request.getParameter("email"));
				u.setMobile(Long.parseLong(request.getParameter("mobile")));
				u.setEmail(request.getParameter("email"));
				u.setPassword(request.getParameter("password"));
				u.setCpassword(request.getParameter("cpassword"));
				u.setGender(request.getParameter("gender"));
				u.setAddress(request.getParameter("address"));
				UserDao.insertUser(u);
				request.setAttribute("msg", "Registration Successfull.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else
			{
				request.setAttribute("msg", "Password and Confirm Password is Incorrect.");
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("login"))
		{
			User u = UserDao.checkLogin(request.getParameter("email"), request.getParameter("password"));
			if(u==null)
			{
				request.setAttribute("msg", "Invalid Username or Password.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			else
			{
				HttpSession session = request.getSession();
				session.setAttribute("u", u);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}
		else if(action.equalsIgnoreCase("change password"))
		{
			HttpSession session = request.getSession();
			User u = (User)session.getAttribute("u");
			
			if(u.getPassword().equals(request.getParameter("oldpassword")))
			{
				if(request.getParameter("newpassword").equals(request.getParameter("cnewpassword")))
				{
					UserDao.changePassword(u.getEmail(), request.getParameter("newpassword"));
					request.setAttribute("msg", "Password changed successfully.");
					request.getRequestDispatcher("login.jsp").forward(request, response);					
				}
				else
				{
					request.setAttribute("msg", " New and Confirm New Password does not Matched.");
					request.getRequestDispatcher("changepassword.jsp").forward(request, response);
				}
			}
			else
			{
				request.setAttribute("msg", " Old Password is Incorrect");
				request.getRequestDispatcher("changepassword.jsp").forward(request, response);
			}
			
		}
		
		
	}

}
