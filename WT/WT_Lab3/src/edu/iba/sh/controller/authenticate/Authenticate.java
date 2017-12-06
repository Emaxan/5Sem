package edu.iba.sh.controller.authenticate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.iba.sh.bean.User;
import edu.iba.sh.dao.DAOException;
import edu.iba.sh.dao.DAOFactory;

@WebServlet("/Authenticate")
public class Authenticate extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String userId= request.getParameter("user");
			String password= request.getParameter("password");	
			User user=DAOFactory.getUserDAO().getUserByIdAndPassword(userId, password);
			if(user==null){
				request.setAttribute("message", "incorrect user or password");
				request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			}else{
				request.getSession().setAttribute("user", user);
				response.sendRedirect("Welcome");
			}
		}catch(DAOException e){
			throw new ServletException(e);
		}
	}
}
