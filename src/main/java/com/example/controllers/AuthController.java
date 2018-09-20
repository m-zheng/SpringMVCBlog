package com.example.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daos.PostDAO;
import com.example.daos.UserDAO;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.validators.AuthValidator;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Calendar;
import java.util.List;

@Controller
public class AuthController {
	@RequestMapping(value = "register", method = GET)
	public String registrationHandler(Model model, HttpSession session) {
		model.addAttribute("user", new User());
		if (session.getAttribute("loggedUser") == null) {
			return "auth/register";
		} else {
			User userInSession = (User) session.getAttribute("loggedUser");
			PostDAO postDAO = new PostDAO();
			List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());
			model.addAttribute("postList", postList);
			return "dashboard";
		}
	}

	@RequestMapping(value = "register", method = POST)
	public String submitRegistrationHandler(Model model, User user, HttpServletRequest request, HttpSession session) {
		model.addAttribute("user", user);
		AuthValidator authValidator = new AuthValidator();

		String username = user.getUsername();
		String email = user.getEmail();
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();

		boolean isInputWrong = false;

		boolean isBlankUsername = authValidator.isBlank(username);
		boolean isBlankEmail = authValidator.isBlank(email);
		boolean isBlankPassword = authValidator.isBlank(password);
		boolean isBlankConfirmPassword = authValidator.isBlank(confirmPassword);

		if (isBlankUsername) {
			isInputWrong = true;
			request.setAttribute("usernameErrorMessage", "This is not a valid username");
		}

		if (isBlankEmail) {
			isInputWrong = true;
			request.setAttribute("emailErrorMessage", "This is not a valid email");
		}

		if (isBlankPassword || isBlankConfirmPassword) {
			isInputWrong = true;
			request.setAttribute("passwordBlankErrorMessage", "This is not a valid password");
		}

		if (!password.equals(confirmPassword)) {
			isInputWrong = true;
			request.setAttribute("passwordInconsistenceErrorMessage", "Passwords don't match");
		}

		if (authValidator.isExistingUser(username)) {
			isInputWrong = true;
			request.setAttribute("existingErrorMessage", "This user already exists");
		}

		if (isInputWrong) {
			return "auth/register";
		} else {
			user.setCreatedDateTime(Calendar.getInstance());
			UserDAO userDAO = new UserDAO();
			userDAO.addUser(user);

			User loggedUser = null;
			if (userDAO.getUserByUsername(username).size() == 1) {
				loggedUser = userDAO.getUserByUsername(username).get(0);
			}

			session.setAttribute("loggedUser", loggedUser);
			session.setMaxInactiveInterval(60 * 60 * 10000);
			User userInSession = (User) session.getAttribute("loggedUser");
			PostDAO postDAO = new PostDAO();
			List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());
			model.addAttribute("postList", postList);
			return "dashboard";
		}
	}

	@RequestMapping(value = "login", method = GET)
	public String loginHandler(Model model, HttpSession session) {
		model.addAttribute("user", new User());
		if (session.getAttribute("loggedUser") == null) {
			return "auth/login";
		} else {
			User userInSession = (User) session.getAttribute("loggedUser");
			PostDAO postDAO = new PostDAO();
			List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());
			model.addAttribute("postList", postList);
			return "dashboard";
		}
	}

	@RequestMapping(value = "login", method = POST)
	public String loginSubmitHandler(Model model, User user, HttpServletRequest request, HttpSession session) {
		model.addAttribute("user", user);
		AuthValidator authValidator = new AuthValidator();

		boolean isPasswordBlank = authValidator.isBlank(user.getPassword());
		boolean isPasswordCorrect = authValidator.isPasswordCorrect(user.getUsername(), user.getPassword());

		boolean isInputWrong = false;

		if (user.getUsername().length() == 0) {
			isInputWrong = true;
			request.setAttribute("inputUsernameErrorMessage", "This is not a valid username");
		}

		if (isPasswordBlank) {
			isInputWrong = true;
			request.setAttribute("inputPasswordErrorMessage", "This is not a valid password");
		}

		if (!isPasswordCorrect) {
			isInputWrong = true;
			request.setAttribute("wrongPasswordErrorMessage", "Please check the credentials");
		}

		if (isInputWrong == true)
			return "auth/login";
		else {
			UserDAO userDAO = new UserDAO();
			User loggedUser = null;
			if (userDAO.getUserByUsername(user.getUsername()).size() == 1) {
				loggedUser = userDAO.getUserByUsername(user.getUsername()).get(0);
			}
			session.setAttribute("loggedUser", loggedUser);
			session.setMaxInactiveInterval(60 * 60 * 10000);
			User userInSession = (User) session.getAttribute("loggedUser");
			PostDAO postDAO = new PostDAO();
			List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());
			model.addAttribute("postList", postList);
			return "dashboard";
		}
	}

	@RequestMapping(value = "dashboard", method = GET)
	public String dashboardHandler(Model model, HttpSession session) {

		if (session.getAttribute("loggedUser") == null) {
			model.addAttribute("user", new User());
			return "auth/login";
		} else {
			User userInSession = (User) session.getAttribute("loggedUser");
			PostDAO postDAO = new PostDAO();
			List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());
			model.addAttribute("postList", postList);
			return "dashboard";
		}
	}

	@RequestMapping(value = "logout", method = GET)
	public String logoutHandler(Model model, HttpSession session) {
		session.invalidate();
		model.addAttribute("user", new User());
		return "auth/login";
	}
}
