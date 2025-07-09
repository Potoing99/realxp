package realxp.service;

import realxp.dao.UserDAO;
import realxp.model.User;


public class UserService {
	private UserDAO userDAO;
	
	public UserService() {
		userDAO = new UserDAO();
	}
	
	public boolean register(User user) {
		if(userDAO.getUserByUsername(user.getUsername()) != null) {
			return false;
		}
		userDAO.insertUser(user);
		return true;
	}
	
	public User login(String username, String password) {
		User user = userDAO.getUserByUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}

}
