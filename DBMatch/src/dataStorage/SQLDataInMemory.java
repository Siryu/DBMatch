package dataStorage;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import models.User;
import tools.PasswordHash;
import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedUserException;
import exceptions.UserDoesNotExistException;

@Stateless
@LocalBean
public class SQLDataInMemory implements SQLDataConnection {
	
	private List<User> data = new ArrayList<>();

	@Override
	public void createUser(User user) {
		data.add(user);
	}

	@Override
	public void updateUser(User updateUser, User loggedUser)
			throws UnauthorizedUserException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User retrieveUser(String email) {
		for(User u : data) {
			if(u.getEmail().equals(email)) {
				return u;
			}
		}
		return null;
	}

	@Override
	public User login(String username, char[] password)
			throws InvalidPasswordException, UserDoesNotExistException {
		User u = retrieveUser(username);
		if(u == null) {
			throw new UserDoesNotExistException();
		}
		try {
			if(PasswordHash.validatePassword(password, u.getPassword())) {
				return u;
			} else {
				throw new InvalidPasswordException();
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> retrieveAllUsers()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
