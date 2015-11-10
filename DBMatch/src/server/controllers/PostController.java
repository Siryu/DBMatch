package server.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.apache.openjpa.jdbc.schema.PrimaryKey;
import org.apache.taglibs.standard.tag.common.xml.IfTag;

import dataStorage.*;
import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedUserException;
import exceptions.UserDoesNotExistException;
import models.User;
import models.database.Column;
import models.database.DataType;
import models.database.Database;
import models.database.Table;
import server.ThreadLocalUser;
import server.annotations.ControllerAction;
import tools.PasswordHash;
import tools.DataTypeFinder;

@Stateless
@LocalBean
public class PostController implements Controller {

	@Inject private SQLDataConnection data;
	@Inject private FileStorage fileStorage;
	@Inject private TemplateFileStorage templateStorage;
	
	@ControllerAction("/login")
	public ModelAndView loginUser(HttpServletRequest request) {
		String username = request.getParameter("username");
		char[] password = request.getParameter("password").toCharArray();
		User user = null;
		ModelAndView toReturn;
		try
		{
			user = data.login(username, password);
			HttpSession s = request.getSession();
			s.invalidate();
			s = request.getSession();
			s.setAttribute("user", user);
			s.setAttribute("lastActivityTime", Calendar.getInstance().getTime());
			toReturn = new ModelAndView(null, "/user/dash", true);
		} 
		catch (InvalidPasswordException | UserDoesNotExistException | NullPointerException e)
		{
			//invalid user
			toReturn = new ModelAndView(new ModelAndView("Sorry, login information was incorrect"), "/WEB-INF/ErrorRedirect.jsp", false);
		}
		return toReturn;
	}
	
	@ControllerAction("/register")
	public ModelAndView registerUser(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passVerify = request.getParameter("passwordVerification");
		Object model = null;
		String location;
		ModelAndView toReturn;
		if(data.retrieveUser(username) == null && password != null && !password.isEmpty() && password.equals(passVerify)) {
			try {
				User user = new User(username, PasswordHash.createHash(password));
				data.createUser(user);
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("lastActivityTime", Calendar.getInstance().getTime());
				location = "/success";
				toReturn = new ModelAndView(null, location, true);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
				model = "Sorry, something went wrong with the server! Please try again later";
				location = "/WEB-INF/ErrorRedirect.jsp";
				toReturn = new ModelAndView(model, location, false);
			}
		} else {
			//invalid user
			if(password.equals(passVerify)) {
				model = new ModelAndView("Sorry, that email is invalid or in use.");
			} else {
				model = new ModelAndView("Please make sure your passwords match.");
			}
			location = "/WEB-INF/ErrorRedirect.jsp";
			toReturn = new ModelAndView(model, location, false);
		}
		return toReturn;
	}
	
	@ControllerAction("/checkEmail")
	public ModelAndView checkEmail(HttpServletRequest request) {
		String email = request.getParameter("email");
		String model; 
		if(email == null || email.isEmpty()) {
			model = "<span style=\"color:red\">Please enter a valid email.</span>";
		} else {
			if(data.retrieveUser(email) == null) {
				model = "<span style=\"color:green\">Email available!</span>";
			} else {
				model = "<span style=\"color:red\">Sorry, that email already exists in our database.</span>";
			}
		}
		return new ModelAndView(model, "/WEB-INF/checkEmailResponse.jsp", false);
	}
	
	// temporary action to test column input!
	@ControllerAction("/user/column")
	public ModelAndView getColumnPage(HttpServletRequest request)
	{
		Column column = new Column();
		column.setName(request.getParameter("columnName"));
		if(request.getParameter("valueType").equals("numbers"))
		{
			try
			{
				long minValue = Long.parseLong(request.getParameter("minValue"));
				long maxValue = Long.parseLong(request.getParameter("maxValue"));
				column.setDataType(DataTypeFinder.findNumberDataType(minValue, maxValue));
			}
			catch (NumberFormatException ex)
			{
				column.setDataType(DataType.VARCHAR);
			}
		}
		else
		{
			String dataExample = request.getParameter("sampleText");
			column.setDataType(DataTypeFinder.findStringDataType(dataExample));
		}
		
		Table workingTable = null;
		Database db = null;
		if(ThreadLocalUser.getUser().isAdmin())
		{
			workingTable = (Table) request.getSession().getAttribute("chosenDB");
		}
		else
		{
			db = (Database) request.getSession().getAttribute("chosenDB");
			for(Table table : db.getTables())
			{
				if(table.getName().equals(request.getParameter("tableName")))
				{
					workingTable = table;
				}
			}		
		}
			
		workingTable.addColumns(column);		
		
		if(request.getParameterMap().containsKey("nextColumn"))
		{
			return new ModelAndView(workingTable.getName(), "/WEB-INF/ColumnInput.jsp", false);
		}
		else
		{
			if(ThreadLocalUser.getUser().isAdmin())
			{
				templateStorage.updateItem(workingTable);
			}
			else
			{
				fileStorage.updateItem(db);
			}
			request.setAttribute("user", ThreadLocalUser.getUser());
			return new ModelAndView((Database)request.getSession().getAttribute("chosenDB"), "/WEB-INF/DatabaseView.jsp", false);
		}
	}
	
	@ControllerAction("/user/new_table")
	public ModelAndView createTable(HttpServletRequest request){
		Table newTable = new Table();
		Database db = (Database) request.getSession().getAttribute("chosenDB");
		newTable.setName(request.getParameter("tableName"));
		db.addTable(newTable);
		request.setAttribute("user", ThreadLocalUser.getUser());
		return new ModelAndView(newTable, "/WEB-INF/TableView.jsp", false);
	}
	
	@ControllerAction("/user/new_database")
	public ModelAndView createDatabase(HttpServletRequest request)
	{
		Database newDB = new Database();
		newDB.setName(request.getParameter("databaseName"));
		if(null != request.getSession().getAttribute("chosenDB"))
		{
			Database oldDB = (Database) request.getSession().getAttribute("chosenDB");
			fileStorage.updateItem(oldDB);
		}
		request.getSession().setAttribute("chosenDB", newDB);
		fileStorage.createItem(ThreadLocalUser.getUser(), newDB);
		return new ModelAndView(request.getSession().getAttribute("chosenDB"), "/WEB-INF/DatabaseView.jsp", false);
	}
	
	@ControllerAction("/user/template")
	public ModelAndView createTemplateColumns(HttpServletRequest request)
	{
		Column newColumn = new Column();
		newColumn.setDataType(Enum.valueOf(DataType.class, request.getParameter("valueType")));
		newColumn.setName(request.getParameter("columnName"));
		newColumn.setNullable(null != request.getParameter("nullableBox"));
		Table templateTable = (Table) request.getSession().getAttribute("chosenDB");
		templateTable.addColumns(newColumn);
		templateStorage.updateItem(templateTable);
		if(null != request.getParameter("nextColumn"))
		{
			return new ModelAndView(null, "/WEB-INF/CreateTemplate.jsp", false);
		}
		else
		{
			request.setAttribute("user", ThreadLocalUser.getUser());
			return new ModelAndView(templateTable, "/WEB-INF/TableView.jsp", false);
		}
	}
	
	@ControllerAction("/user/primarykey")
	public ModelAndView setPrimaryKey(HttpServletRequest request) {
		String column = request.getParameter("column");
		String table = request.getParameter("table");
		
		Database current = (Database) request.getSession().getAttribute("chosenDB");
		if(current != null) {
			Table t = current.getTableByName(table);
			if(t != null) {
				Column c = t.getColumnByName(column);
				if(c != null) {
					List<Column> keys = t.getPrimaryKey();
					if(t.getIsPrimary(c)) {
						keys.remove(c);
					} else {
						keys.add(c);
					}
					t.setPrimaryKey(keys);
					fileStorage.updateItem(current);
				} else {
					System.out.println("ERROR: column '" + column + "' not found");
				}
			} else {
				System.out.println("ERROR: table '" + table + "' not found");
			}
		} else {
			System.out.println("ERROR: database not found");			
		}
		return null;
	}
	
	@ControllerAction("/user/foreignkey")
	public ModelAndView setForeignyKey(HttpServletRequest request) {
		String pcolumn = request.getParameter("parentColumn");
		String ptable = request.getParameter("parentTable");
		String ccolumn = request.getParameter("childColumn");
		String ctable = request.getParameter("childTable");
		String error = null;
		
		Database current = (Database) request.getSession().getAttribute("chosenDB");
		if(current != null) {
			Table pt = current.getTableByName(ptable);
			Table ct = current.getTableByName(ctable);
			if(pt != null && ct != null) {
				Column pc = pt.getColumnByName(pcolumn);
				if(pc != null) {
					Column cc = new Column();
					cc.setDataType(pc.getDataType());
					cc.setNullable(pc.isNullable());
					cc.setName(ccolumn);
					cc.setForeignKey(true);
					ct.addColumns(cc);
					current.addForeignKey(pt, pc, ct, cc);
					fileStorage.updateItem(current);
				} else {
					error = "ERROR: column '" + pcolumn + "' not found";
				}
			} else {
				error = "ERROR: table '" + ptable + "' or table '" + ctable + "' not found";
			}
		} else {
			error = "ERROR: database not found";			
		}
		return error == null ?  new ModelAndView(null, "/user/table_view?tableName=" + ctable, true) : new ModelAndView(new ModelAndView(error), "/WEB-INF/ErrorRedirect.jsp", false);
	}

	@ControllerAction("/user/account_Settings")
	public ModelAndView changeAccountSettings(HttpServletRequest request){
		
		String newEmail = request.getParameter("newEmail");
		String currentPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		
		User user = new User();
		
		if(currentPassword.equals(ThreadLocalUser.getUser().getPassword())){
			user.setUserID(ThreadLocalUser.getUser().getUserID());
			user.setEmail(newEmail);
			user.setPassword(newPassword);
		}
		
		try {
			data.updateUser(user, ThreadLocalUser.getUser());
		} catch (UnauthorizedUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView(null, request.getContextPath() + "/user/dash", true);
	
		return mav;
	}
}
