package server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RoutingFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			if(req.getRequestURI().endsWith(".css") || req.getRequestURI().endsWith(".html") || req.getRequestURI().endsWith(".jsp") 
					|| req.getRequestURI().endsWith(".jpeg") || req.getRequestURI().endsWith(".png") || req.getRequestURI().endsWith(".jpg") || req.getRequestURI().contains(".") && !req.getRequestURI().contains("sql")) {
				chain.doFilter(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/app/" + req.getRequestURI());
				rd.forward(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


}
