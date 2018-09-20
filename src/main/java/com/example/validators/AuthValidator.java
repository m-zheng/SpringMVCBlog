package com.example.validators;

import java.util.List;

import com.example.daos.UserDAO;
import com.example.entity.User;

public class AuthValidator {
	public boolean isBlank(String input) {
		if (input.length() == 0) {
			return true;
		}
		return false;
	}

	public boolean isPasswordCorrect(String username, String password) {
		if (username.length() == 0 || password.length() == 0) {
			return false;
		}

		UserDAO userDAO = new UserDAO();
		List<User> retrievedUser = userDAO.getUserByUsername(username);
		if (retrievedUser.size() != 0 && retrievedUser.get(0).getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public boolean isExistingUser(String username) {
		UserDAO userDAO = new UserDAO();
		List<User> retrievedUser = userDAO.getUserByUsername(username);
		if (retrievedUser.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

}
