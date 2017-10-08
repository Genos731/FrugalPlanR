package servlet;

import java.io.*;
import javax.servlet.http.*;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.container.Account;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("userName") == null) {
			request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
			//response.sendRedirect(request.getContextPath() + "/");
		} else {
			//request.getRequestDispatcher("WEB-INF/pages/OverviewPage.jsp").forward(request, response);
			response.sendRedirect(request.getContextPath() + "/Overview");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);		
		
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        //System.out.println(user + "and" + pwd);
        
        AccountAccessor accessor = new AccountAccessorImpl();
        Account curr = null;
		try {
			curr = accessor.getAccount(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        if (accessor.isValidAccount(curr)) {	
        	//validate if user exists
        	if (curr.getPassword().equals(pwd)) {
        		System.out.println("Login Successful");
        		//request.getRequestDispatcher("WEB-INF/pages/OverviewPage.jsp").forward(request, response);
        		response.sendRedirect(request.getContextPath() + "/Overview");
        		request.getSession().setAttribute("userName", user);
        	} else {
        		System.out.println("Invalid Password");
        		request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
        		//response.sendRedirect(request.getContextPath() + "/");
        	}
        } else {
        	System.out.println("Account does not exist");
        	request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
        	//response.sendRedirect(request.getContextPath() + "/");
        }
        	
        try {
			accessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}