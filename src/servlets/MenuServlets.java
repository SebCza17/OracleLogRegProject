package servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AllowedIPDAO;
import dao.LogonDAO;
import dao.UserDAO;
import entities.AllowedIPEnitity;
import entities.LogonEntity;
import entities.UserEntity;

/**
 * Servlet implementation class MenuServlets
 */
@WebServlet("/Menu")
public class MenuServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MenuServlets() {
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
		HttpSession session = request.getSession(false);
		String token = ((UserEntity) session.getAttribute("user")).getToken();

		String formType = request.getParameter("type");

		if (formType.equals("ip")) {

			AllowedIPDAO allowedIPDAO = new AllowedIPDAO();

			try {
				ArrayList<AllowedIPEnitity> ipList = allowedIPDAO.getAllowedIP(token);
				session.setAttribute("ipList", ipList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect(request.getContextPath() + "?m=ip");
		}else if (formType.equals("history")) {
			LogonDAO logonEntity = new LogonDAO();

			try {
				ArrayList<LogonEntity> logonEntityList = logonEntity.getLogonList(token);
				session.setAttribute("logonEntityList", logonEntityList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect(request.getContextPath() + "?m=history");
		}else if (formType.equals("password")) {
			UserDAO userDAO = new UserDAO();

			String formOldPassword = request.getParameter("oldPassword");
			String formNewPassword = request.getParameter("newPassword");
			String formReNewPassword = request.getParameter("reNewPassword");
			try {
				if (formNewPassword.equals(formReNewPassword)) {
					String result = userDAO.chagnePassword(token, formOldPassword, formNewPassword);
					if(result.equals("true")) {
						response.sendRedirect(request.getContextPath() + "?b=");
					} else if(result.equals("same")) {
						response.sendRedirect(request.getContextPath() + "?b=SAME");
					} else {
						response.sendRedirect(request.getContextPath() + "?b=PASSWORD");
					}
				} else {
					response.sendRedirect(request.getContextPath() + "?b=NEWPASSWORD");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
