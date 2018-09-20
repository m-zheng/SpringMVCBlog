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

import com.example.daos.PostDAO;
import com.example.entity.Post;
import com.example.entity.User;

public class TestPostDAO {
	@Before
	public void setup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SpringMVCBlog");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Query query3 = entityManager.createQuery("Delete from H_Post");
		Query query1 = entityManager.createQuery("Delete from H_Like");
		Query query2 = entityManager.createQuery("Delete from H_Comment");
		Query query4 = entityManager.createQuery("Delete from H_User");
		entityTransaction.begin();
		query1.executeUpdate();
		query2.executeUpdate();
		query3.executeUpdate();
		query4.executeUpdate();
		entityTransaction.commit();
	}

	@Test
	public void testThatInitalSizeOfPostListIsZero() {
		PostDAO postDAO = new PostDAO();
		List<Post> postList = postDAO.listPost();
		int listSize = postList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfPostListIsOneAfterAddingOnePost() {
		PostDAO postDAO = new PostDAO();

		User user = new User();
		user.setUserId(1);

		Post post = new Post();
		post.setPostId(1);
		post.setUser(user);

		postDAO.addPost(post);
		List<Post> postList = postDAO.listPost();
		int listSize = postList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfPostListIsOneAfterAddingAnInvalidPost() {
		PostDAO postDAO = new PostDAO();
		User user = new User();
		user.setUserId(2);

		Post post1 = new Post();
		Post post2 = new Post();

		post1.setPostId(2);
		post2.setPostId(2);

		post1.setUser(user);
		post2.setUser(user);

		postDAO.addPost(post1);

		try {
			postDAO.addPost(post2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		List<Post> postList = postDAO.listPost();
		int listSize = postList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfPostListIsTwoAfterAddingTwoValidPosts() {
		PostDAO postDAO = new PostDAO();
		Post post1 = new Post();
		Post post2 = new Post();

		post1.setPostId(3);
		post2.setPostId(4);

		User user = new User();
		user.setUserId(1);

		post1.setUser(user);
		post2.setUser(user);

		postDAO.addPost(post1);

		postDAO.addPost(post2);

		List<Post> postList = postDAO.listPost();
		// System.out.println(postList.get(0).getPostId());
		// System.out.println(postList.get(1).getPostId());
		int listSize = postList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testThatSizeOfPostListIsZeroAfterRemovingAnValidPost() {
		PostDAO postDAO = new PostDAO();
		Post post = new Post();
		post.setPostId(1);
		User user = new User();
		user.setUserId(1);
		post.setUser(user);
		postDAO.addPost(post);
		postDAO.removePostByPostId(1);
		List<Post> postList = postDAO.listPost();
		int listSize = postList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfPostListIsZeroAfterRemovingAnInvalidPost() {
		PostDAO postDAO = new PostDAO();
		Post post = new Post();
		post.setPostId(1);
		User user = new User();
		user.setUserId(1);
		post.setUser(user);
		postDAO.addPost(post);
		postDAO.removePostByPostId(1);
		try {
			postDAO.removePostByPostId(200);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		List<Post> postList = postDAO.listPost();
		int listSize = postList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatPostIdReturnedFromGetPostByPostIdIsOne() {
		PostDAO postDAO = new PostDAO();
		Post post = new Post();
		post.setPostId(1);
		User user = new User();
		user.setUserId(1);
		post.setUser(user);
		postDAO.addPost(post);

		Post retrievedPost = postDAO.getPostByPostId(1);
		assertEquals(1, retrievedPost.getPostId());
	}

	public void testThatTwoPostsAreReturnedFromGetPostByUserIdWhenThisUserHasTwoPosts() {
		PostDAO postDAO = new PostDAO();
		Post post1 = new Post();
		Post post2 = new Post();

		post1.setPostId(1);
		post2.setPostId(2);

		User user = new User();
		user.setUserId(1);

		post1.setUser(user);
		post2.setUser(user);

		postDAO.addPost(post1);
		postDAO.addPost(post2);

		List<Post> postList = postDAO.getPostByUserId(1);
		int listSize = postList.size();
		assertEquals(2, listSize);
	}

	public void testThatSubjectOfThePostToBeUpdatedIsUpdated() {
		PostDAO postDAO = new PostDAO();
		Post post = new Post();
		post.setPostId(1);
		post.setSubject("test subject from post 1");
		User user = new User();
		user.setUserId(1);
		post.setUser(user);
		postDAO.addPost(post);

		Post retrievedPost = postDAO.getPostByPostId(1);
		retrievedPost.setSubject("test subject from new post");

		postDAO.updatePostSubjectAndContent(retrievedPost);

		Post afterUpdatedPost = postDAO.getPostByPostId(1);
		assertEquals("test subject from new post", afterUpdatedPost.getContent());
	}

	public void testThatContentOfThePostToBeUpdatedIsUpdated() {
		PostDAO postDAO = new PostDAO();
		Post post = new Post();
		post.setPostId(1);
		post.setContent("test content from post 1");
		post.setSubject("test subject from post 1");
		User user = new User();
		user.setUserId(1);
		post.setUser(user);
		postDAO.addPost(post);

		Post retrievedPost = postDAO.getPostByPostId(1);
		retrievedPost.setContent("test content from new post");
		retrievedPost.setSubject("test subject from new post");

		postDAO.updatePostSubjectAndContent(retrievedPost);

		Post afterUpdatedPost = postDAO.getPostByPostId(1);
		assertEquals("test content from new post", afterUpdatedPost.getContent());
	}

}
