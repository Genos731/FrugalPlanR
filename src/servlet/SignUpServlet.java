package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.container.Account;
import backend.exception.DuplicateEmailException;
import backend.exception.DuplicateUsernameException;
import backend.exception.InvalidEmailException;

/**
 * Servlet implementation class SignUpPageServlet
 */
@WebServlet("/SignUp")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("WEB-INF/pages/SignUpPage.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        //System.out.println(user + "and" + pwd);
        
        AccountAccessor accessor = new AccountAccessorImpl();
        Account curr = null;
		try {
			curr = accessor.getAccount(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        if (accessor.isValidAccount(curr)) {	
        	System.out.println("Username already exists");
			response.sendRedirect(request.getContextPath() + "/SignUp");
        } else {
        	try {
				accessor.create(user, pwd, email);
				System.out.println("Account created successfully");
				response.sendRedirect(request.getContextPath() + "/Login");
			} catch (IllegalArgumentException | InvalidEmailException | DuplicateUsernameException
					| DuplicateEmailException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}       
        }	
        try {
			accessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}