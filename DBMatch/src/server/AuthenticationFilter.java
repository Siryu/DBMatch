package server;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

	private static final long timeout = 1000 * 60 * 10; //ten minute timeout (in milis)
	
    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
    	super();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			//check if logged in to go to this location
			if(req.getRequestURI().contains("/user")) {
				Object userObject = req.getSession().getAttribute("user");
				Object lastActivityTimeObject = req.getSession().getAttribute("lastActivityTime");
				boolean notTimeout = verifyTime(lastActivityTimeObject);
				if(userObject != null && userObject instanceof User && notTimeout) {
					ThreadLocalUser.setUser((User) userObject);
					chain.doFilter(request, response);
					req.getSession().setAttribute("lastActivityTime", Calendar.getInstance().getTime());
				} else {
					req.getSession().invalidate();
					req.getSession().removeAttribute("user");
					req.getSession().removeAttribute("lastActivityTime");
					((HttpServletResponse) response).sendError(403);
				} 
			} else {
				chain.doFilter(request, response);
			}
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
		ThreadLocalUser.remove();
	}
	
	private boolean verifyTime(Object lastTime) {
		boolean toReturn = false;
		if(lastTime != null && lastTime instanceof Date) {
			Date time = (Date) lastTime; 
			Calendar c = Calendar.getInstance();
			if(c.getTime().getTime() - time.getTime() < timeout) {
				toReturn = true;
			}
		}
		return toReturn;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
