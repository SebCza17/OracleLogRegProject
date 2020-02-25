package servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import model.Hash;
import model.PasswordValidator;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserDAO userDAO = new UserDAO();

		String formEmail = request.getParameter("email");
		String formPassword = request.getParameter("password");
		String formRePassword = request.getParameter("rePassword");
		String formUsername = request.getParameter("username");
		String birthDay = request.getParameter("birthDay");

		String ip = request.getRemoteAddr();

		if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipAddress = inetAddress.getHostAddress();
			ip = ipAddress;
		}

		PasswordValidator passwordValidator = new PasswordValidator();

		try {
			if (formPassword.equals(formRePassword)) {
					if (userDAO.registration(formEmail, formPassword, formUsername, birthDay, ip)) {
						response.sendRedirect(request.getContextPath());
					} else {
						response.sendRedirect(request.getContextPath() + "?a=register&b=EMAIL");
					}
			} else {
				response.sendRedirect(request.getContextPath() + "?a=register&b=PASSWORD2");
			}
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
