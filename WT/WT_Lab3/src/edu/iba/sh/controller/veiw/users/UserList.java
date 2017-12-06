package edu.iba.sh.controller.veiw.users;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.iba.sh.bean.User;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.DAOFactory;

@WebServlet("/UserList")
public class UserList extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			List<User> list;
			try {
				list = DAOFactory.getUserDAO().getAllUsers();
				request.setAttribute("users", list);
				request.getRequestDispatcher("/WEB-INF/pages/users/users.jsp").forward(request, response);
			} catch (DAOException e) {
				 new DAOException();
			}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
