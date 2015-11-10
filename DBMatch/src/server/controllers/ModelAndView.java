package server.controllers;

public class ModelAndView {
	
	private Object model;
	private String viewLocation;
	private boolean isRedirect;
	private boolean isTextFile;
	
	public ModelAndView(Object model, String viewLocation, boolean redirect) {
		this.model = model;
		this.viewLocation = viewLocation;
		this.isRedirect = redirect;
	}
	
	public ModelAndView(Object model, String viewLocation, boolean redirect, boolean isToBeReturned) {
		this.model = model;
		this.viewLocation = viewLocation;
		this.isRedirect = redirect;
		this.isTextFile = isToBeReturned;
	}
	
	public ModelAndView(Object model) {
		this.model = model;
		this.viewLocation = "/fail";
		this.isRedirect = false;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public String getViewLocation() {
		return viewLocation;
	}

	public void setViewLocation(String viewLocation) {
		this.viewLocation = viewLocation;
	}
	
	public boolean isRedirect() {
		return isRedirect;
	}
	
	public void setIsRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	public String toString() {
		return model.toString();
	}
	
	public boolean getIsTextFile() {
		return isTextFile;
	}

}
