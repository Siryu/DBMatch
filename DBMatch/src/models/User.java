package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
//import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@NamedQueries({
	@NamedQuery(name=User.BY_EMAIL, query="SELECT u FROM User u WHERE u.email = :email")
})

@Entity
@Table(name="User")
public class User
{
	public static final String BY_EMAIL = "byEmail";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userID;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private boolean isAdmin;
		
	public User()
	{
		
	}
	
	public User(String username, String password)
	{
		this.email = username;
		this.password = password;
	}

	public User(int id, String email, String password, boolean setAdmin)
	{
		this.userID = id;
		this.email = email;
		this.password = password;
		this.setAdmin(setAdmin);
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getUserID()
	{
		return userID;
	}

	public void setUserID(int userID)
	{
		this.userID = userID;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}
}
