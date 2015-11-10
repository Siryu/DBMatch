package server.controllers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import models.User;
import models.database.Database;
import models.database.Table;
import server.ThreadLocalUser;
import server.annotations.ControllerAction;
import tools.ScriptGenerator;
import dataStorage.FileStorage;
import dataStorage.SQLDataConnection;
import dataStorage.TemplateFileStorage;
import exceptions.UnauthorizedUserException;


@Stateless
@LocalBean
public class GetController implements Controller {
	
//	HttpServletRequest request;
//	HttpServletResponse response;
	@Inject private FileStorage fileStorage;
	@Inject private TemplateFileStorage templateStorage;
	@Inject private SQLDataConnection sqlConnection;
	
	@ControllerAction("/$")
	public ModelAndView getHomePage(HttpServletRequest request) {
		System.out.println(request.getContextPath());
		return new ModelAndView(null, "/home.jsp", false);
	}
	
	@ControllerAction("/login")
	public ModelAndView getLoginPage(HttpServletRequest request) {
		return new ModelAndView(null, "/WEB-INF/testFile.jsp", false);
	}
	
	@ControllerAction("/register")
	public ModelAndView getRegisterPage(HttpServletRequest request) {
		return new ModelAndView(null, "/WEB-INF/registration.jsp", false);
	}
	
	@ControllerAction("/redirect")
	public ModelAndView getRedirect(HttpServletRequest request) {
		return new ModelAndView(null, "/WEB-INF/ErrorRedirect.jsp", false);
	}
	
	@ControllerAction("/registerfail") 
	public ModelAndView getRegisterFail(HttpServletRequest request) {
		return new ModelAndView(null, "/WEB-INF/TestRegisterFail.jsp", false);
	}
	
	@ControllerAction("/fail") 
	public ModelAndView getFail(HttpServletRequest request) {
		Object model = request.getParameter("message");
		return new ModelAndView(model.toString(), "/WEB-INF/testFail.jsp", false);
	}
	
	@ControllerAction("/success") 
	public ModelAndView getSuccess(HttpServletRequest request) {
		return new ModelAndView(null, "/WEB-INF/testSuccess.jsp", false);
	}
	@ControllerAction("/user/column")
	public ModelAndView getColumnTable(HttpServletRequest request) 
	{
		String tableName = request.getParameter("table");
		return new ModelAndView(tableName, "/WEB-INF/ColumnInput.jsp", false);
	}
	
	@ControllerAction("/user/table_view")
	public ModelAndView getTableView(HttpServletRequest request) 
	{
		Database currentDB = (Database)request.getSession().getAttribute("chosenDB");
		Table model = currentDB.getTableByName(request.getParameter("tableName"));
		request.setAttribute("user", ThreadLocalUser.getUser());
		return new ModelAndView(model, "/WEB-INF/TableView.jsp", false);
	}
	
	@ControllerAction("/user/database_view")
	public ModelAndView getDatabaseView(HttpServletRequest request)
	{
		if(null != request.getParameter("chosen"))
		{
			Database currentDB = (Database) request.getSession().getAttribute("chosenDB");
			String chosenTemplate = (String) request.getParameter("chosen");
			Table templateTable = (Table) templateStorage.retrieveItem(chosenTemplate);
			currentDB.addTable(templateTable);
			fileStorage.updateItem(currentDB);
		}
		Database model = null;
		if(null != request.getParameter("databaseName"))
		{
			if(!ThreadLocalUser.getUser().isAdmin())
			{
				Database newDB = new Database();
				newDB.setName(request.getParameter("databaseName"));
				request.getSession().setAttribute("chosenDB", newDB);
				fileStorage.createItem(ThreadLocalUser.getUser(), newDB);
				model = newDB;
			}
		}
		else
		{
			model = (Database) request.getSession().getAttribute("chosenDB");
		}
		return new ModelAndView(model, "/WEB-INF/DatabaseView.jsp", false);
	}
	
	@ControllerAction("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("lastActivityTime");
		return new ModelAndView(null, "/", true);
	}
	
	@ControllerAction("/user/dash")
	public ModelAndView dashboard(HttpServletRequest request)
	{
		if(ThreadLocalUser.getUser().isAdmin())
		{
			request.setAttribute("databases", templateStorage.retrieveAll());
		}
		else
		{
			request.setAttribute("databases", fileStorage.retrieveAll());
		}
		ModelAndView mv = new ModelAndView(ThreadLocalUser.getUser(), "/WEB-INF/Dashboard.jsp", false);
		return mv;
	}
	
	@ControllerAction("/user/selection")
	public ModelAndView view(HttpServletRequest request)
	{
		ModelAndView mv = null;
		
		if(ThreadLocalUser.getUser().isAdmin()){
			Table model = (Table) templateStorage.retrieveItem(request.getParameter("chosen"));
			if(null == model)
			{
				model = new Table();
				model.setName(request.getParameter("chosen"));
				templateStorage.createItem(ThreadLocalUser.getUser(), model);
			}
			request.getSession().setAttribute("chosenDB", model);
			request.setAttribute("user", ThreadLocalUser.getUser());
			mv = new ModelAndView(model, "/WEB-INF/TableView.jsp", false);
		}
		else{
			//ArrayList<Database> model = (ArrayList<Database>) fileStorage.retrieveAll();
			request.getSession().setAttribute("chosenDB", fileStorage.retrieveItem(request.getParameter("chosen")));
			Database model = (Database) request.getSession().getAttribute("chosenDB");
			mv = new ModelAndView(model, "/WEB-INF/DatabaseView.jsp", false);
		}
		
		return mv;
	}
	
	@ControllerAction("/user/users")
	public ModelAndView userView(HttpServletRequest request)
	{
		return new ModelAndView(sqlConnection.retrieveAllUsers(), "/WEB-INF/UserView.jsp", false);
	}
	
	@ControllerAction("/user/view/update")
	public ModelAndView usersUpdate(HttpServletRequest request)
	{
		String editUserEmail = request.getParameter("userEmail");
		String action = request.getParameter("action");
		
		if(action.equals("delete"))
		{
			sqlConnection.deleteUser(sqlConnection.retrieveUser(editUserEmail));
		}
		else
		{
			try
			{
				sqlConnection.updateUser(sqlConnection.retrieveUser(editUserEmail), ThreadLocalUser.getUser());
			} 
			catch (UnauthorizedUserException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ModelAndView(sqlConnection.retrieveAllUsers(), "/WEB-INF/UserView.jsp", false);
	}
	
	@ControllerAction("/user/setup.sql")
	public ModelAndView downloadScript(HttpServletRequest request) {
		Database currentDB = (Database)request.getSession().getAttribute("chosenDB");
		Object model = null;
		if(currentDB != null) {
			model = ScriptGenerator.generateScript(currentDB);
		}
		return model == null ? new ModelAndView("Sorry, there was a problem generating your set-up script") : new ModelAndView(model, "", false, true);
	}
		
	@ControllerAction("/user/template_view")
	public ModelAndView templateView(HttpServletRequest request)
	{
		return new ModelAndView(templateStorage.retrieveAll(), "/WEB-INF/TemplateView.jsp", false);
	}
	
	@ControllerAction("/user/create_template_column")
	public ModelAndView templateColumnCreated(HttpServletRequest request)
	{
		return new ModelAndView(request.getAttribute("chosenDB"), "/WEB-INF/CreateTemplate.jsp", false);
	}
	
	@ControllerAction("/user/delete_Table")
	public ModelAndView deleteTable(HttpServletRequest request)
	{
		Database currentDB = (Database) request.getSession().getAttribute("chosenDB");
		currentDB.removeTableByName((String) request.getParameter("tableName"));
		fileStorage.updateItem(currentDB);
		return new ModelAndView(currentDB, "/WEB-INF/DatabaseView.jsp", false);
	}
	
	@ControllerAction("/user/delete_Column")
	public ModelAndView deleteColumn(HttpServletRequest request){
		Table model = null;
		if(ThreadLocalUser.getUser().isAdmin())
		{
			model = (Table) request.getSession().getAttribute("chosenDB");
			String columnName = request.getParameter("columnName");
			model.removeColumn(columnName);
			templateStorage.updateItem(model);
		}
		else
		{
			Database currentDB = (Database)request.getSession().getAttribute("chosenDB");
			model = currentDB.getTableByName(request.getParameter("tableName"));
			String columnName = request.getParameter("columnName");
			if(model.getColumnByName(columnName).getIsForeignKey()) {
				currentDB.removeForeignKey(columnName);
			}
			model.removeColumn(columnName);
			fileStorage.updateItem(currentDB);
		}
		request.setAttribute("user", ThreadLocalUser.getUser());
		return new ModelAndView(model, "/WEB-INF/TableView.jsp", false);
	}
	
	@ControllerAction("/user/delete_Database")
	public ModelAndView deleteDatabaseOrTemplate(HttpServletRequest request)
	{
		String databaseName = (String) request.getParameter("tableName");
		if(ThreadLocalUser.getUser().isAdmin())
		{
			templateStorage.deleteItem(databaseName);
			//request.setAttribute("databases", templateStorage.retrieveAll());
			request.getSession().setAttribute("chosenDB", null);
		}
		else
		{
			request.getSession().setAttribute("chosenDB", null);
			fileStorage.deleteItem(databaseName);
			//request.setAttribute("databases", fileStorage.retrieveAll());
		}
		return new ModelAndView(ThreadLocalUser.getUser(), "/user/dash", true);
	}
	
	@ControllerAction("/user/account_Settings")
	public ModelAndView changeAccountSettings(HttpServletRequest request){
		
		return new ModelAndView(ThreadLocalUser.getUser(), "/WEB-INF/AccountSettings.jsp", false);
	}
	
	@ControllerAction("/user/foreignkey")
	public ModelAndView getForeginKey(HttpServletRequest request) 
	{
		Database currentDB = (Database)request.getSession().getAttribute("chosenDB");
		return new ModelAndView(currentDB, "/WEB-INF/ForeignKeyInput.jsp", false);
	}
}
