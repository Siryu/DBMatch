package server;

import models.User;

public class ThreadLocalUser {
	
	private static final ThreadLocal<User> user = new ThreadLocal<User>()  {
		@Override
		protected User initialValue() {
			return null;
		}
	};
	
	private ThreadLocalUser() {
		//no instantiation
	}
	
	public static User getUser() {
		return user.get();
	}
	
	public static void setUser(User u) {
		user.set(u);
	}
	
	public static void remove() {
		user.remove();
	}

}
