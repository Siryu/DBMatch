package dataStorage;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import tools.PasswordHash;
import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedUserException;
import exceptions.UserDoesNotExistException;
import models.User;

@Stateless
@LocalBean
//@Local(SQLDataConnection.class)
public class OldSQLData implements SQLDataConnection
{
	private final static String username = "cmassey";
	private final static String password = "password";
	private final static String databaseURL = "jdbc:mysql://hardcode.ninja:3306/userdata";
	private Connection conn;
	
	private void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(databaseURL, username, password.toString());
			//conn = DriverManager.getConnection(tempURL);
			conn.setAutoCommit(false);
		} 
		catch (SQLException | ClassNotFoundException e)
		{
			System.out.println("error connecting to SQL Database");
			e.printStackTrace();
		}	
	}
	
	private void disconnect()
	{
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("Error closing the connection to our SQL");
			e.printStackTrace();
		}
	}

	@Override
	public void createUser(User user)
	{
		if(this.conn == null)
			this.connect();
		
		PreparedStatement pst = null;
		
		try
		{
			pst = conn.prepareStatement("INSERT INTO clients(email, password, admin) VALUES(?, ?, ?)");
			//pst.setInt(1, 3);
			pst.setString(1, user.getEmail());
			pst.setString(2, user.getPassword().toString());
			pst.setBoolean(3, false);
			pst.execute();
			
			conn.commit();
			pst.close();
		}
		catch (SQLException e1)
		{
			System.out.println("Error creating user on SQL side.");
			e1.printStackTrace();
		}
	}

	@Override
	public void updateUser(User updateUser, User loggedUser) throws UnauthorizedUserException
	{		
		if(updateUser.getEmail().equals(loggedUser.getEmail()))
		{
			if(this.conn == null)
				this.connect();
			
			try
			{
				Statement stmt = conn.createStatement();
				
				stmt.executeUpdate("UPDATE clients SET password = " + updateUser.getPassword().toString() +
						"WHERE email = " + loggedUser.getEmail());
				stmt.executeUpdate("UPDATE clients SET email = " + updateUser.getEmail() +
						"WHERE email = " + loggedUser.getEmail());
				
				conn.commit();
				stmt.close();
			} 
			catch (SQLException e)
			{
				System.out.println("Error updating user on SQL side.");
				e.printStackTrace();
			}
		}
		else
		{
			throw new UnauthorizedUserException(loggedUser.getEmail() + "is trying to access " +
					updateUser.getEmail());
		}
	}

	@Override
	public void deleteUser(User user)
	{
		if(this.conn == null)
			this.connect();
		
		try
		{
			Statement stmt = conn.createStatement();
			stmt.execute("DELETE FROM clients WHERE email = '" + user.getEmail() + "'");
			
			conn.commit();
			stmt.close();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User retrieveUser(String email)
	{		
		if(this.conn == null)
			this.connect();

		final String sqlQuery = "SELECT * FROM clients WHERE email = '" + email + "';";
		ResultSet rs = null;
		User userInfo = null;
		
		try
		{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while(rs.next())
			{
				userInfo = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
			}
		} 
		catch (SQLException e)
		{
			System.out.println("error retrieving user from DB");
			e.printStackTrace();
		}
		return userInfo;
	}

	@Override
	public User login(String username, char[] password) throws InvalidPasswordException, UserDoesNotExistException
	{
		if(this.conn == null)
			this.connect();
		
		User compareUserTo = retrieveUser(username);
		if(compareUserTo == null)
		{
			throw new UserDoesNotExistException();
		}
		
		try {
			if(!PasswordHash.validatePassword(password, compareUserTo.getPassword()))
			{
				throw new InvalidPasswordException();
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		} 
		return compareUserTo;
	}

	@Override
	public List<User> retrieveAllUsers()
	{
		if(this.conn == null)
			this.connect();
		
		final String sqlQuery = "SELECT * FROM clients";
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		
		try
		{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			while(rs.next())
			{
				users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
			}
		} 
		catch (SQLException e)
		{
			System.out.println("error retrieving user from DB");
			e.printStackTrace();
		}
		return users;
	}
}
