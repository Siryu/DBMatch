package dataStorage;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import models.User;
import tools.PasswordHash;
import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedUserException;
import exceptions.UserDoesNotExistException;

@Stateless
@LocalBean
@Local(SQLDataConnection.class)
public class SQLData implements SQLDataConnection {
	
	@PersistenceContext private EntityManager em;
	
	
	@Override
	public void createUser(User user){
		em.persist(user);
	}
	
	@Override
	public void deleteUser(User user){
		em.remove(user);
	}
	
	@Override
	public User retrieveUser(String email){		
		TypedQuery<User> query = em.createNamedQuery(User.BY_EMAIL, User.class);
		query.setParameter("email", email);
		
		User user = null;
		if(!query.getResultList().isEmpty()){
			user = query.getSingleResult();
		}
		return user;
	}
	
	@Override
	public User login(String username, char[] password) throws InvalidPasswordException, UserDoesNotExistException {
		User compareUserTo = retrieveUser(username);
		if(compareUserTo == null){
			throw new UserDoesNotExistException();
		}
		
		try {
			if(!PasswordHash.validatePassword(password, compareUserTo.getPassword())){
				throw new InvalidPasswordException();
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e){
			e.printStackTrace();
		} 
		return compareUserTo;
	}
	
	@Override
	public void updateUser(User updateUser, User loggedUser) throws UnauthorizedUserException {		
		if(updateUser.getEmail().equals(loggedUser.getEmail())){
			em.merge(updateUser);
			em.persist(updateUser);
		}
		else {
			throw new UnauthorizedUserException(loggedUser.getEmail() + "is trying to access " +
					updateUser.getEmail());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> retrieveAllUsers()
	{
		return (List<User>) em.createQuery("SELECT u FROM User u").getResultList();
		//return em.createQuery("SELECT * FROM clients").getResultList();
	}
}