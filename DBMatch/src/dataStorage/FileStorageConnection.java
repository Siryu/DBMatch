package dataStorage;

import java.util.List;

import models.User;

public interface FileStorageConnection
{	
	// crud user created fileSystem
	public boolean createItem(User user, Object object);
	public void deleteItem(Object object);
	public boolean updateItem(Object object);
	public Object retrieveItem(Object object);
	public List retrieveAll();
	// templates
	// templatesfolder
	// tablesfolderbynameofTable -- // databasesfolderbynameofDB
	// serialized table by name of table

}
