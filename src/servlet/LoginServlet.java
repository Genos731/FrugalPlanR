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
		if (request.getSession().getAttribute("userName") == null) {
			request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/Overview");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = String.valueOf(request.getParameter("pwd").hashCode());
        
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
        		response.sendRedirect(request.getContextPath() + "/Overview");
        		request.getSession().setAttribute("userName", user);
        	} else {
        		request.setAttribute("message", "Invalid Password");
        		request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
        	}
        } else {
        	request.setAttribute("message","Account does not exist");
        	request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
        }
        	
        try {
			accessor.close();
		} catch (Exception e) {
			e.printStackTrace();
    		request.setAttribute("message", e.getMessage());
		}
		
		if (request.getAttribute("message") != null) request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
		else doGet(request, response);
	}

}