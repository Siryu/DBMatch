package demo;

import dataStorage.FileStorage;
import models.User;
import models.database.Database;

public class FileWriteTest
{
	public static void main(String[] args)
	{
		User user = new User();
		user.setUserID(1);
		Database db = new Database();
		db.setName("testDB");
		FileStorage fs = new FileStorage();
		fs.createItem(user, db);
		System.out.println("done");
	}
}
