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
import backend.exception.InvalidEmailException;

/**
 * Servlet implementation class EditProfileServlet
 */
@WebServlet("/EditProfile")
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("userName") == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
		} else {
			request.getRequestDispatcher("WEB-INF/pages/EditProfilePage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String currpwd = String.valueOf(request.getParameter("currpwd").hashCode());
        String newpwd = String.valueOf(request.getParameter("newpwd").hashCode());
        String email = request.getParameter("email");
		
        AccountAccessor accessor = new AccountAccessorImpl();
        Account curr = null;
        
        String currUser = (String) request.getSession().getAttribute("userName");
		if (currUser != null) {
        	try {
    			curr = accessor.getAccount(currUser);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
        	// validate current password
        	if (curr.getPassword().equals(currpwd)) {
        		try {
        			if (request.getParameter("newpwd").length() == 0) {
        	        	request.setAttribute("message", "Password is too short");
        			} else {
        				accessor.updatePassword(curr, newpwd);
        			}
				} catch (SQLException e) {
		    		request.setAttribute("message", e.getMessage());
					e.printStackTrace();
				}
        		try {
					accessor.updateEmail(curr, email);
				} catch (InvalidEmailException | SQLException e) {
		    		request.setAttribute("message", e.getMessage());
					e.printStackTrace();
				}
	    		request.setAttribute("success", "Profile details successfully changed!");
    		// wrong password
        	} else {
        		request.setAttribute("message", "Incorrect password entered");
        	}
        }         	
        try {
			accessor.close();
		} catch (Exception e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
        
		request.getRequestDispatcher("WEB-INF/pages/EditProfilePage.jsp").forward(request, response);
	}

}
