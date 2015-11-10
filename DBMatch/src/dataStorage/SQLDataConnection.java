package dataStorage;

import java.util.List;

import models.User;
import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedUserException;
import exceptions.UserDoesNotExistException;

public interface SQLDataConnection
{
	// crud user	
	public void createUser(User user);
	public void updateUser(User updateUser, User loggedUser) throws UnauthorizedUserException;
	public void deleteUser(User user);
	public User retrieveUser(String email);
	public User login(String username, char[] password) throws InvalidPasswordException, UserDoesNotExistException;
	public List<User> retrieveAllUsers();
}
