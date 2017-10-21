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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		response.setContentType("text/html");
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
        	//validate current password
        	if (curr.getPassword().equals(currpwd)) {
        		System.out.println("Correct Password Entered");
        		try {
					accessor.updatePassword(curr, newpwd);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		try {
					accessor.updateEmail(curr, email);
				} catch (InvalidEmailException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		response.sendRedirect(request.getContextPath() + "/Overview");
        	//wrong current password entered
        	} else {
        		System.out.println("Incorrect Password Entered");
        		request.setAttribute("message", "Incorrect Password Entered");
        		request.getRequestDispatcher("WEB-INF/pages/EditProfilePage.jsp").forward(request, response);
        		//response.sendRedirect(request.getContextPath() + "/");
        	}
        }         	
        try {
			accessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //doGet(request, response);
	}

}
