package com.example.daos;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import com.example.daos.CommentDAO;
import com.example.entity.Comment;
import com.example.entity.Post;
import com.example.entity.User;

public class TestCommentDAO {
	@Before
	public void setup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SpringMVCBlog");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Query query2 = entityManager.createQuery("Delete from H_Comment");
		Query query1 = entityManager.createQuery("Delete from H_Like");
		Query query3 = entityManager.createQuery("Delete from H_Post");
		Query query4 = entityManager.createQuery("Delete from H_User");
		entityTransaction.begin();
		query1.executeUpdate();
		query2.executeUpdate();
		query3.executeUpdate();
		query4.executeUpdate();
		entityTransaction.commit();
	}

	@Test
	public void testThatInitalSizeOfCommentListIsZero() {
		CommentDAO commentDAO = new CommentDAO();
		List<Comment> commentList = commentDAO.listComment();
		int listSize = commentList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfCommentListIsOneAfterAddingOneComment() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment.setCommentId(1);
		comment.setUser(user);
		comment.setPost(post);

		commentDAO.addComment(comment);

		List<Comment> commentList = commentDAO.listComment();
		int listSize = commentList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfCommentListIsOneAfterAddingAnInvalidComment() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment1 = new Comment();
		Comment comment2 = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment1.setCommentId(1);
		comment1.setUser(user);
		comment1.setPost(post);

		comment2.setCommentId(1);
		comment2.setUser(user);
		comment2.setPost(post);

		commentDAO.addComment(comment1);

		try {
			commentDAO.addComment(comment2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		List<Comment> commentList = commentDAO.listComment();
		int listSize = commentList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfCommentListIsTwoAfterAddingTwoValidComments() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment1 = new Comment();
		Comment comment2 = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment1.setCommentId(1);
		comment1.setUser(user);
		comment1.setPost(post);

		comment2.setCommentId(2);
		comment2.setUser(user);
		comment2.setPost(post);

		commentDAO.addComment(comment1);

		commentDAO.addComment(comment2);

		List<Comment> commentList = commentDAO.listComment();
		int listSize = commentList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testThatSizeOfCommentListIsZeroAfterRemovingAnValidComment() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment.setCommentId(1);
		comment.setUser(user);
		comment.setPost(post);

		commentDAO.addComment(comment);

		commentDAO.removeCommentByCommentId(1);

		List<Comment> commentList = commentDAO.listComment();
		int listSize = commentList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfCommentListIsOneAfterRemovingAnInalidComment() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment.setCommentId(1);
		comment.setUser(user);
		comment.setPost(post);

		commentDAO.addComment(comment);

		try {
			commentDAO.removeCommentByCommentId(10000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		List<Comment> commentList = commentDAO.listComment();
		int listSize = commentList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatCommentIdReturnedFromGetCommentByCommentIdIsOne() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment.setCommentId(1);
		comment.setUser(user);
		comment.setPost(post);

		commentDAO.addComment(comment);

		Comment retrievedComment = commentDAO.getCommentByCommentId(1);
		assertEquals(1, retrievedComment.getCommentId());
	}

	@Test
	public void testThatTwoCommentsAreReturnedFromGetCommentByPostIdIsWhenThisPostHasTwoComments() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment1 = new Comment();
		Comment comment2 = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment1.setCommentId(1);
		comment1.setUser(user);
		comment1.setPost(post);

		comment2.setCommentId(2);
		comment2.setUser(user);
		comment2.setPost(post);

		commentDAO.addComment(comment1);

		commentDAO.addComment(comment2);

		List<Comment> commentList = commentDAO.getCommentByPostId(1);
		int listSize = commentList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testThatTwoCommentsAreReturnedFromGetCommentByUserIdIsWhenThisUserHasTwoComments() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment1 = new Comment();
		Comment comment2 = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment1.setCommentId(1);
		comment1.setUser(user);
		comment1.setPost(post);

		comment2.setCommentId(2);
		comment2.setUser(user);
		comment2.setPost(post);

		commentDAO.addComment(comment1);

		commentDAO.addComment(comment2);

		List<Comment> commentList = commentDAO.getCommentByUserId(1);
		int listSize = commentList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testContentOfTheCommentToBeUpdatedIsUpdated() {
		CommentDAO commentDAO = new CommentDAO();
		Comment comment = new Comment();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		comment.setCommentId(1);
		comment.setUser(user);
		comment.setPost(post);
		comment.setContent("original content");

		commentDAO.addComment(comment);

		Comment retrievedComment = commentDAO.getCommentByCommentId(1);
		retrievedComment.setContent("content is updated");

		commentDAO.updateCommentContent(retrievedComment);

		Comment afterUpdatedComment = commentDAO.getCommentByCommentId(1);
		assertEquals("content is updated", afterUpdatedComment.getContent());
	}
}
