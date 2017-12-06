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

/**
 * Servlet implementation class UserForm
 */
@WebServlet("/UserForm")
public class UserForm extends HttpServlet {

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserForm() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String userId = request.getParameter("id");
			User user;
			if (userId != null) {
				user = DAOFactory.getUserDAO().getUserId(userId);
			}else{
				user=new User();
			}
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/pages/users/form.jsp")
					.forward(request, response);
		} catch (DAOException e) {
			new DAOException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
