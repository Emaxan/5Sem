package edu.iba.sh.controller.veiw.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.DAOFactory;

/**
 * Servlet implementation class UserDelete
 */
@WebServlet("/UserDelete")
public class UserDelete extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String oldUser = request.getParameter("oldUser");
			DAOFactory.getUserDAO().deleteUserById(oldUser);

			response.sendRedirect("UserList");
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
