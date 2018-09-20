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
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {
	@RequestMapping(value = "/", method = GET)
	public String handlePostShowHandler(Model model, HttpSession session, HttpServletRequest request) {
		PostDAO postDAO = new PostDAO();
		LikeDAO likeDAO = new LikeDAO();

		List<Post> postList = postDAO.listPost();
		Collections.reverse(postList);
		model.addAttribute("postList", postList);

		User userInSession = (User) session.getAttribute("loggedUser");

		HashMap<Integer, int[]> likeDetails = new HashMap<Integer, int[]>();
		for (Post post : postList) {
			int isLikedByLoggedUser = 0;
			int[] isLikedByLoggedUserAndNumberOfLike = new int[2];
			if (userInSession != null) {
				List<Like> likeListInDB = likeDAO.getLikeByPostIdAndUserId(post.getPostId(), userInSession.getUserId());
				if (likeListInDB.size() > 0) {
					isLikedByLoggedUser = 1;
				}
			}
			List<Like> likeList = likeDAO.getLikeByPostId(post.getPostId());
			int numberOfLike = likeList.size();
			isLikedByLoggedUserAndNumberOfLike[0] = isLikedByLoggedUser;
			isLikedByLoggedUserAndNumberOfLike[1] = numberOfLike;

			likeDetails.put(post.getPostId(), isLikedByLoggedUserAndNumberOfLike);
		}

		model.addAttribute("likeDetails", likeDetails);
		return "post/index";
	}
}
