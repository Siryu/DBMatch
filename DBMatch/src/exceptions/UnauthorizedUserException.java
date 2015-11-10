package exceptions;

public class UnauthorizedUserException extends Exception
{
	private static final long serialVersionUID = 1L;

	public UnauthorizedUserException(String issue)
	{
		super(issue);
	}

}
