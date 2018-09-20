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
import java.util.List;

@Controller
public class LikeController {
	@RequestMapping(value = "post/{postId}/upvote", method = POST)
	public String submitLikeCreationHandler(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable int postId) {
		User userInSession = (User) session.getAttribute("loggedUser");
		if (userInSession == null) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}

		PostDAO postDAO = new PostDAO();
		Post postInDB = postDAO.getPostByPostId(postId);
		if (postInDB == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		if (postInDB.getUser().getUserId() == userInSession.getUserId()) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}

		UserDAO userDAO = new UserDAO();
		User userInDB = userDAO.getUserByUserId(userInSession.getUserId());

		LikeDAO likeDAO = new LikeDAO();
		List<Like> likeListInDB = likeDAO.getLikeByPostIdAndUserId(postInDB.getPostId(), userInDB.getUserId());

		boolean isLikedByLoggedUser = false;
		
		if (likeListInDB.size() == 0) {
			Like like = new Like();
			like.setPost(postInDB);
			like.setUser(userInDB);
			like.setCreatedDateTime(Calendar.getInstance());
			likeDAO.addLike(like);
			isLikedByLoggedUser = true;
		}

		model.addAttribute("postInDB", postInDB);
		model.addAttribute("comment", new Comment());

		CommentDAO commentDAO = new CommentDAO();
		List<Comment> commentList = commentDAO.getCommentByPostId(postInDB.getPostId());

		for (int i = 0; i < commentList.size(); i++) {
			model.addAttribute("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
		}

		List<Like> likeList = likeDAO.getLikeByPostId(postId);
		model.addAttribute("numberOfLike", likeList.size());
		model.addAttribute("commentList", commentList);
		
		model.addAttribute("isLikedByLoggedUser", isLikedByLoggedUser);
		return "post/show";
	}

	@RequestMapping(value = "post/{postId}/downvote", method = POST)
	public String submitLikeDeletionHandler(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable int postId) {
		User userInSession = (User) session.getAttribute("loggedUser");
		if (userInSession == null) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}

		PostDAO postDAO = new PostDAO();
		Post postInDB = postDAO.getPostByPostId(postId);
		if (postInDB == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		if (postInDB.getUser().getUserId() == userInSession.getUserId()) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}

		UserDAO userDAO = new UserDAO();
		User userInDB = userDAO.getUserByUserId(userInSession.getUserId());

		LikeDAO likeDAO = new LikeDAO();
		List<Like> likeListInDB = likeDAO.getLikeByPostIdAndUserId(postInDB.getPostId(), userInDB.getUserId());
		for (Like like : likeListInDB) {
			likeDAO.removeLikeByLikeId(like.getLikeId());
		}
		
		boolean isLikedByLoggedUser = false;

		model.addAttribute("postInDB", postInDB);
		model.addAttribute("comment", new Comment());

		CommentDAO commentDAO = new CommentDAO();
		List<Comment> commentList = commentDAO.getCommentByPostId(postInDB.getPostId());

		for (int i = 0; i < commentList.size(); i++) {
			model.addAttribute("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
		}

		List<Like> likeList = likeDAO.getLikeByPostId(postId);
		model.addAttribute("numberOfLike", likeList.size());
		model.addAttribute("commentList", commentList);
		model.addAttribute("isLikedByLoggedUser", isLikedByLoggedUser);
		return "post/show";
	}
}
