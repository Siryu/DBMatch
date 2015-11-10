package demo;

import dataStorage.SQLData;
import dataStorage.SQLDataConnection;
import models.User;

public class SQLDemo
{
	SQLDataConnection data;
	
	public SQLDemo()
	{
		data = new SQLData();
	}
	
	public void run()
	{
		User test1 = new User("corey@gmail.com", "hello");
	
		System.out.println("creating user on the database...");
		
		data.createUser(test1);
		//data.createUser(test2);
		
		User retrieved = (data.retrieveUser(test1.getEmail()));
		System.out.println("retrieving user from database...");
		System.out.println("username : " + retrieved.getEmail());
		System.out.println("password : " + retrieved.getPassword());
		
		data.deleteUser(test1);
		//data.deleteUser(test2);
		User deletedUser = data.retrieveUser(test1.getEmail());
		System.out.print("Database deleted the user : " + (deletedUser == null ? true : false));
		
	}
	
	public static void main(String[] args)
	{
		SQLDemo demo = new SQLDemo();
		demo.run();
	}
}
