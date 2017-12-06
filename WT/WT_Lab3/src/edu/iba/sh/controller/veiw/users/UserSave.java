package edu.iba.sh.controller.veiw.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.iba.sh.bean.User;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.DAOFactory;

@WebServlet("/UserSave")
public class UserSave extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String oldUser = request.getParameter("oldUser");
			User user = createUser(request);
			if (oldUser.length() != 0) {
				DAOFactory.getUserDAO().updateUser(oldUser, user);
			} else {
				DAOFactory.getUserDAO().addUser(user);
			}
			response.sendRedirect("UserList");

		} catch (DAOException e) {
			throw new ServletException(e);
		}

	}

	private User createUser(HttpServletRequest request) {

		String id = request.getParameter("user");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

		User user = new User();
		user.setPassword(password);
		user.setId(id);
		user.setRole(role);
		return user;
	}

}
