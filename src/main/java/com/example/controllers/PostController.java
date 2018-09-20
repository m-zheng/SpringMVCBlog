package com.example.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.daos.CommentDAO;
import com.example.daos.LikeDAO;
import com.example.daos.PostDAO;
import com.example.daos.UserDAO;
import com.example.entity.Comment;
import com.example.entity.Like;
import com.example.entity.Post;
import com.example.entity.User;
import com.example.validators.AuthValidator;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Controller
public class PostController {
	@RequestMapping(value = "post/create", method = GET)
	public String createPostHandler(Model model, HttpSession session) {

		if (session.getAttribute("loggedUser") == null) {
			model.addAttribute("user", new User());
			return "auth/login";
		} else {
			model.addAttribute("post", new Post());
			return "post/create";
		}
	}

	@RequestMapping(value = "post/create", method = POST)
	public String submitPostCreationHandler(Model model, Post post, HttpSession session, HttpServletRequest request) {
		if (session.getAttribute("loggedUser") == null) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}

		model.addAttribute("post", post);

		AuthValidator authValidator = new AuthValidator();
		String subject = post.getSubject();
		String content = post.getContent();

		boolean isInputWrong = false;
		boolean isBlankSubject = authValidator.isBlank(subject);
		boolean isBlankContent = authValidator.isBlank(content);

		if (isBlankSubject) {
			isInputWrong = true;
			request.setAttribute("postSubjectErrorMsg", "Invalid subject");
		}

		if (isBlankContent) {
			isInputWrong = true;
			request.setAttribute("postContentErrorMsg", "Invalid content");
		}

		if (isInputWrong) {
			return "post/create";
		} else {
			User userInSession = (User) session.getAttribute("loggedUser");
			UserDAO userDAO = new UserDAO();
			User userInDB = userDAO.getUserByUserId(userInSession.getUserId());

			post.setUser(userInDB);
			post.setCreatedDateTime(Calendar.getInstance());

			PostDAO postDAO = new PostDAO();
			postDAO.addPost(post);
			List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());
			model.addAttribute("postList", postList);
			return "dashboard";
		}
	}

	@RequestMapping(value = "post/{postId}/edit", method = GET)
	public String handlePostEditionHandler(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable int postId) {
		if (session.getAttribute("loggedUser") == null) {
			model.addAttribute("user", new User());
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			return "auth/login";
		}

		PostDAO postDAO = new PostDAO();
		Post postInDB = postDAO.getPostByPostId(postId);
		if (postInDB == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		User userInSession = (User) session.getAttribute("loggedUser");
		if (userInSession.getUserId() != postInDB.getUser().getUserId()) {
			model.addAttribute("user", new User());
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission");
			return "auth/login";
		} else {
			model.addAttribute("postInDB", postInDB);
			return "post/edit";
		}
	}

	@RequestMapping(value = "post/{postId}/edit", method = POST)
	public String submitPostEditionHandler(Model model, Post postInDB, HttpSession session, HttpServletRequest request,
			@PathVariable int postId) {
		if (session.getAttribute("loggedUser") == null) {
			model.addAttribute("user", new User());
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission");
			return "auth/login";
		}

		PostDAO postDAO = new PostDAO();
		Post postInDBWithDetails = postDAO.getPostByPostId(postId);
		if (postInDBWithDetails == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		} else {
			model.addAttribute("postInDB", postInDB);

			AuthValidator authValidator = new AuthValidator();
			String subject = postInDB.getSubject();
			String content = postInDB.getContent();

			boolean isInputWrong = false;
			boolean isBlankSubject = authValidator.isBlank(subject);
			boolean isBlankContent = authValidator.isBlank(content);

			if (isBlankSubject) {
				isInputWrong = true;
				request.setAttribute("postSubjectErrorMsg", "Invalid subject");
			}

			if (isBlankContent) {
				isInputWrong = true;
				request.setAttribute("postContentErrorMsg", "Invalid content");
			}

			User userInSession = (User) session.getAttribute("loggedUser");

			if (userInSession == null || userInSession.getUserId() != postInDBWithDetails.getUser().getUserId()) {
				model.addAttribute("user", new User());
				request.setAttribute("permissionErrorMessage", "Sorry, You have no permission");
				return "auth/login";
			}

			if (isInputWrong) {
				return "post/edit";
			} else {
				postInDB.setCreatedDateTime(Calendar.getInstance());
				postDAO.updatePostSubjectAndContent(postInDB);
				List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());
				model.addAttribute("postList", postList);
				return "dashboard";
			}
		}
	}

	@RequestMapping(value = "post/{postId}", method = GET)
	public String handlePostShowHandler(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable int postId) {
		PostDAO postDAO = new PostDAO();
		Post postInDB = postDAO.getPostByPostId(postId);
		if (postInDB == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		} else {
			model.addAttribute("postInDB", postInDB);
			model.addAttribute("comment", new Comment());
			CommentDAO commentDAO = new CommentDAO();
			List<Comment> commentList = commentDAO.getCommentByPostId(postId);

			for (int i = 0; i < commentList.size(); i++) {
				model.addAttribute("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
			}

			LikeDAO likeDAO = new LikeDAO();
			List<Like> likeList = likeDAO.getLikeByPostId(postId);
			model.addAttribute("numberOfLike", likeList.size());

			boolean isLikedByLoggedUser = false;

			User userInSession = (User) session.getAttribute("loggedUser");

			if (userInSession != null) {
				List<Like> likeListInDB = likeDAO.getLikeByPostIdAndUserId(postInDB.getPostId(),
						userInSession.getUserId());
				if (likeListInDB.size() > 0) {
					isLikedByLoggedUser = true;
				}
			}

			model.addAttribute("isLikedByLoggedUser", isLikedByLoggedUser);

			model.addAttribute("commentList", commentList);
			return "post/show";
		}
	}

	@RequestMapping(value = "post/{postId}/delete", method = GET)
	public String handlePostDeletionHandler(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable int postId) {
		if (session.getAttribute("loggedUser") == null) {
			model.addAttribute("user", new User());
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission");
			return "auth/login";
		}
		PostDAO postDAO = new PostDAO();
		Post postInDB = postDAO.getPostByPostId(postId);
		if (postInDB == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		User userInSession = (User) session.getAttribute("loggedUser");

		if (userInSession.getUserId() != postInDB.getUser().getUserId()) {
			model.addAttribute("user", new User());
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission");
			return "auth/login";
		} else {
			LikeDAO likeDAO = new LikeDAO();
			List<Like> likeList = likeDAO.getLikeByPostId(postId);
			for (Like like : likeList) {
				likeDAO.removeLikeByLikeId(like.getLikeId());
			}

			CommentDAO commentDAO = new CommentDAO();
			List<Comment> commentList = commentDAO.getCommentByPostId(postId);
			for (int i = 0; i < commentList.size(); i++) {
				commentDAO.removeCommentByCommentId(commentList.get(i).getCommentId());
			}

			postDAO.removePostByPostId(postId);
			List<Post> postList = postDAO.getPostByUserId(userInSession.getUserId());

			model.addAttribute("postList", postList);
			return "dashboard";
		}
	}
}
