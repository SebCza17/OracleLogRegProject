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
import entities.UserEntity;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
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

		String ip = request.getRemoteAddr();

		if (ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipAddress = inetAddress.getHostAddress();
			ip = ipAddress;
		}

		System.out.println(ip);

		try {
			String isAny;
			if ((isAny = userDAO.isUser(formEmail)).equals("true")) {
				String token;
				if (!(token = userDAO.login(formEmail, formPassword, ip)).equals("false")) {
					if (!token.equals(ip)) {
						HttpSession session = request.getSession(false);

						UserEntity userEntity = userDAO.getUser(token);
						
						session.setAttribute("user", userEntity);
						
						response.sendRedirect(request.getContextPath());
					} else {
						response.sendRedirect(request.getContextPath() + "?a=login&b=" + ip);
					}
				} else {
					response.sendRedirect(request.getContextPath() + "?a=login&b=PASSWORD");
				}
			} else if(isAny.equals("blocked")){
				response.sendRedirect(request.getContextPath() + "?a=login&b=BLOCKED");
			} else {
				response.sendRedirect(request.getContextPath() + "?a=login&b=ERROR");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
