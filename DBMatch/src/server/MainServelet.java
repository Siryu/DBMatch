package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.annotations.ControllerAction;
import server.controllers.Controller;
import server.controllers.GetController;
import server.controllers.ModelAndView;
import server.controllers.PostController;

/**
 * Servlet implementation class MainServelet
 */
public class MainServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private GetController getController;
	@Inject private PostController postController;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServelet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	GetController controller = new GetController(request, response);
		executeModelAndView(getController, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	//	PostController controller = new PostController(request, response);
		executeModelAndView(postController, request, response);
	}
	
	protected void executeModelAndView(Controller controller, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("executing....");
		//List<Object> modelViews = callMethods(controller, request.getRequestURI(), null);
		List<Object> modelViews = callMethods(controller, request.getRequestURI(), request);
		if(modelViews.size() > 0) {
			if(modelViews.get(0) instanceof ModelAndView) {
				ModelAndView toUse = (ModelAndView) modelViews.get(0);
				request.setAttribute("model", toUse.getModel());
				if(toUse.getIsTextFile()) {
					response.setContentType("text/plain");
					response.setContentLength(toUse.getModel().toString().length());
					PrintWriter bw = new PrintWriter(response.getWriter());
					bw.print(toUse.getModel().toString());
				} else if(toUse.isRedirect()) {
					response.sendRedirect(request.getContextPath() + toUse.getViewLocation());
				} else {
					RequestDispatcher rd = request.getRequestDispatcher(toUse.getViewLocation());
					rd.forward(request, response);
				}
			}
		}
	}
	
	private List<Object> callMethods(Object ob, String toMatch, Object ... parameters) {
		Method[] methods = ob.getClass().getMethods();
		List<Object> toReturn = new ArrayList<Object>();
		for(Method m : methods) {
			try {
				if(Class.forName(ob.getClass().getCanonicalName().substring(0,ob.getClass().getCanonicalName().indexOf("$"))).getMethod(m.getName(), HttpServletRequest.class).isAnnotationPresent(ControllerAction.class)) {
					// issue finding annotation now......
					// it comes back as null now for the annotation...
					// however the method returns just fine...???
					String annotationValue = Class.forName(ob.getClass().getCanonicalName().substring(0,ob.getClass().getCanonicalName().indexOf("$"))).getMethod(m.getName(), HttpServletRequest.class).getAnnotation(ControllerAction.class).value();
					Matcher match = Pattern.compile(annotationValue).matcher(toMatch);
					if(match.find()) {
						try {
							toReturn.add(m.invoke(ob, parameters));
						} catch (IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
							System.out.println(e.getMessage());
						}
					} 
				}
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				// don't even worry about it
			}
			
		}
		return toReturn;
	}

}
