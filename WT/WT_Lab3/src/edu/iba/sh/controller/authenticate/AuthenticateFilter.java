package edu.iba.sh.controller.authenticate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="authFilter")
public class AuthenticateFilter implements Filter {

    public AuthenticateFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(!(request instanceof HttpServletRequest)){
			chain.doFilter(request, response);
		}
		HttpServletRequest httprequest=(HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		String uri=httprequest.getRequestURI();
		HttpSession session=httprequest.getSession();
		if(session.getAttribute("user")!=null||
                uri.matches(".+\\..+")||
                uri.endsWith("Authenticate")||
                uri.endsWith("Login")||
                uri.endsWith("Registration")){
			chain.doFilter(httprequest, httpresponse);
		}else{
			httpresponse.sendRedirect("Login");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
