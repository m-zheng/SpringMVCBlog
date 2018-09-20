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
public class CommentController {
	@RequestMapping(value = "post/{postId}", method = POST)
	public String submitCommentCreationHandler(Model model, Comment comment, HttpSession session,
			HttpServletRequest request, @PathVariable int postId) {
		if (session.getAttribute("loggedUser") == null) {
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
		
		model.addAttribute("comment", comment);
		AuthValidator authValidator = new AuthValidator();
		String content = comment.getContent();

		boolean isInputWrong = false;
		boolean isBlankContent = authValidator.isBlank(content);

		if (isBlankContent) {
			isInputWrong = true;
			request.setAttribute("commentContentErrorMsg", "Invalid content");
		}

		CommentDAO commentDAO = new CommentDAO();
		LikeDAO likeDAO = new LikeDAO();
		UserDAO userDAO = new UserDAO();
		User userInSession = (User) session.getAttribute("loggedUser");
		
		
		if (isInputWrong) {
			model.addAttribute("postInDB", postInDB);
			model.addAttribute("comment", new Comment());
			
			List<Comment> commentList = commentDAO.getCommentByPostId(postId);

			for (int i = 0; i < commentList.size(); i++) {
				model.addAttribute("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
			}
			List<Like> likeList = likeDAO.getLikeByPostId(postId);
			model.addAttribute("numberOfLike", likeList.size());

			boolean isLikedByLoggedUser = false;

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
		} else {
			User userInDB = userDAO.getUserByUserId(userInSession.getUserId());
			comment.setUser(userInDB);
			comment.setPost(postInDB);
			comment.setCreatedDateTime(Calendar.getInstance());
			commentDAO.addComment(comment);

			model.addAttribute("postInDB", postInDB);
			model.addAttribute("comment", new Comment());
			List<Comment> commentList = commentDAO.getCommentByPostId(postId);

			for (int i = 0; i < commentList.size(); i++) {
				model.addAttribute("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
			}

			List<Like> likeList = likeDAO.getLikeByPostId(postId);
			model.addAttribute("numberOfLike", likeList.size());

			boolean isLikedByLoggedUser = false;

			if (userInSession != null) {
				List<Like> likeListInDB = likeDAO.getLikeByPostIdAndUserId(postInDB.getPostId(),
						userInSession.getUserId());
				if (likeListInDB.size() > 0) {
					isLikedByLoggedUser = true;
				}
			}

			model.addAttribute("isLikedByLoggedUser", isLikedByLoggedUser);
			model.addAttribute("commentList", commentList);
			
			return "redirect:" + postId;
		}
	}

	@RequestMapping(value = "comment/{commentId}/edit", method = POST)
	public String submitCommentEditionHandler(Model model, Comment commentInDB, HttpSession session,
			HttpServletRequest request, @PathVariable int commentId) {
		User userInSession = (User) session.getAttribute("loggedUser");

		if (userInSession == null) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}

		CommentDAO commentDAO = new CommentDAO();
		Comment commentInDBWithAllDetails = commentDAO.getCommentByCommentId(commentId);
		
		if(commentInDBWithAllDetails == null){
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		if (commentInDBWithAllDetails.getUser().getUserId() != userInSession.getUserId()) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}
		
		PostDAO postDAO = new PostDAO();
		Post postInDB = postDAO.getPostByPostId(commentInDBWithAllDetails.getPost().getPostId());
		
		if (postInDB == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		if (commentInDBWithAllDetails.getUser().getUserId() == userInSession.getUserId()
				&& commentInDB.getContent().length() != 0) {
			commentInDB.setCreatedDateTime(Calendar.getInstance());
			commentDAO.updateCommentContent(commentInDB);
		}

		model.addAttribute("postInDB", postInDB);
		model.addAttribute("comment", new Comment());
		int postId = commentInDBWithAllDetails.getPost().getPostId();
		List<Comment> commentList = commentDAO.getCommentByPostId(postId);

		for (int i = 0; i < commentList.size(); i++) {
			model.addAttribute("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
		}

		LikeDAO likeDAO = new LikeDAO();
		List<Like> likeList = likeDAO.getLikeByPostId(postId);
		model.addAttribute("numberOfLike", likeList.size());

		boolean isLikedByLoggedUser = false;

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

	@RequestMapping(value = "comment/{commentId}/delete", method = POST)
	public String submitCommentDeletionHandler(Model model, Comment commentInDB, HttpSession session,
			HttpServletRequest request, @PathVariable int commentId) {
		User userInSession = (User) session.getAttribute("loggedUser");

		if (userInSession == null) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}

		CommentDAO commentDAO = new CommentDAO();
		Comment commentInDBWithAllDetails = commentDAO.getCommentByCommentId(commentId);
		
		if(commentInDBWithAllDetails == null){
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		if (commentInDBWithAllDetails.getUser().getUserId() != userInSession.getUserId()) {
			request.setAttribute("permissionErrorMessage", "Sorry, You have no permission.");
			model.addAttribute("user", new User());
			return "auth/login";
		}
		
		PostDAO postDAO = new PostDAO();
		Post postInDB = postDAO.getPostByPostId(commentInDBWithAllDetails.getPost().getPostId());
		
		if (postInDB == null) {
			model.addAttribute("user", new User());
			request.setAttribute("pageNotExistErrorMessage", "Oops, page not found");
			return "auth/login";
		}

		if (commentInDBWithAllDetails.getUser().getUserId() == userInSession.getUserId()) {
			commentDAO.removeCommentByCommentId(commentInDBWithAllDetails.getCommentId());
		}

		model.addAttribute("postInDB", postInDB);
		model.addAttribute("comment", new Comment());
		int postId = commentInDBWithAllDetails.getPost().getPostId();
		List<Comment> commentList = commentDAO.getCommentByPostId(postId);

		for (int i = 0; i < commentList.size(); i++) {
			model.addAttribute("comment" + commentList.get(i).getCommentId() + "InDB", commentList.get(i));
		}

		LikeDAO likeDAO = new LikeDAO();
		List<Like> likeList = likeDAO.getLikeByPostId(postId);
		model.addAttribute("numberOfLike", likeList.size());

		boolean isLikedByLoggedUser = false;

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
