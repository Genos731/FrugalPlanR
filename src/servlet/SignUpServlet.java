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
		request.getRequestDispatcher("WEB-INF/pages/SignUpPage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String user = request.getParameter("user");
        String pwd = String.valueOf(request.getParameter("pwd").hashCode());
        String email = request.getParameter("email");
        
        AccountAccessor accessor = new AccountAccessorImpl();
        Account curr = null;
		try {
			curr = accessor.getAccount(user);
		} catch (SQLException e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		
        if (accessor.isValidAccount(curr)) {
        	request.setAttribute("message", "Username already exists");
        } else {
        	try {
				accessor.create(user, pwd, email);
	        	request.setAttribute("success", "Account successfully created!");
				request.getRequestDispatcher("WEB-INF/pages/LoginPage.jsp").forward(request, response);
			} catch (IllegalArgumentException | InvalidEmailException | DuplicateUsernameException
					| DuplicateEmailException | SQLException e) {
	    		request.setAttribute("message", e.getMessage());
				e.printStackTrace();
			}       
        }	
        try {
			accessor.close();
		} catch (Exception e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
        
        if (request.getAttribute("message") != null) doGet(request, response);
	}

}