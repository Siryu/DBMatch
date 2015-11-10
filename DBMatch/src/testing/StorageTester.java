package testing;

import models.User;
import models.database.Database;
import dataStorage.FileStorage;

public class StorageTester {
	FileStorage fs = new FileStorage();
	
	public void storeTestObject(){
		User youzer = new User("hi", "lo");
		youzer.setUserID(007);
		Database db = new Database();
		db.setName("testDB");
		fs.createItem(youzer, db);
	}
	
	public static void main(String[] args){
		new StorageTester().storeTestObject();
	}
}
