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

import com.example.daos.LikeDAO;
import com.example.entity.Like;
import com.example.entity.Post;
import com.example.entity.User;

public class TestLikeDAO {
	@Before
	public void setup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SpringMVCBlog");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		Query query1 = entityManager.createQuery("Delete from H_Like");
		Query query2 = entityManager.createQuery("Delete from H_Comment");
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
	public void testThatInitalSizeOfLikeListIsZero() {
		LikeDAO likeDAO = new LikeDAO();
		List<Like> likeList = likeDAO.listLike();
		int listSize = likeList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfLikeListIsOneAfterAddingOneLike() {
		LikeDAO likeDAO = new LikeDAO();
		Like like = new Like();
		User user = new User();
		Post post = new Post();

		user.setUserId(7);

		post.setPostId(2);
		post.setUser(user);

		like.setLikeId(1);
		like.setPost(post);
		like.setUser(user);

		likeDAO.addLike(like);

		List<Like> likeList = likeDAO.listLike();
		int listSize = likeList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfLikeListIsOneAfterAddingAnInvalidLike() {
		LikeDAO likeDAO = new LikeDAO();
		Like like1 = new Like();
		Like like2 = new Like();
		User user = new User();
		Post post = new Post();

		user.setUserId(2);

		post.setPostId(1);
		post.setUser(user);

		like1.setLikeId(2);
		like1.setPost(post);
		like1.setUser(user);

		like2.setLikeId(2);
		like2.setPost(post);
		like2.setUser(user);

		likeDAO.addLike(like1);

		try {
			likeDAO.addLike(like2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		List<Like> likeList = likeDAO.listLike();
		int listSize = likeList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfLikeListIsTwoAfterAddingTwoValidLikes() {
		LikeDAO likeDAO = new LikeDAO();

		Like like1 = new Like();
		Like like2 = new Like();

		User user = new User();

		Post post1 = new Post();
		Post post2 = new Post();

		user.setUserId(1);

		post1.setPostId(1);
		post1.setUser(user);

		post2.setPostId(2);
		post2.setUser(user);

		like1.setLikeId(3);
		like1.setPost(post1);
		like1.setUser(user);

		like2.setLikeId(4);
		like2.setPost(post2);
		like2.setUser(user);

		likeDAO.addLike(like1);

		likeDAO.addLike(like2);

		List<Like> likeList = likeDAO.listLike();
		int listSize = likeList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testThatSizeOfLikeListIsZeroAfterRemovingAnValidLike() {
		LikeDAO likeDAO = new LikeDAO();
		Like like = new Like();
		User user = new User();
		Post post = new Post();

		user.setUserId(1);

		post.setPostId(1);
		post.setUser(user);

		like.setLikeId(1);
		like.setPost(post);
		like.setUser(user);

		likeDAO.addLike(like);
		likeDAO.removeLikeByLikeId(1);
		List<Like> likeList = likeDAO.listLike();
		int listSize = likeList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfLikeListIsOneAfterRemovingAnInvalidLike() {
		LikeDAO likeDAO = new LikeDAO();
		Like like = new Like();
		User user = new User();
		Post post = new Post();

		user.setUserId(1);

		post.setPostId(1);
		post.setUser(user);

		like.setLikeId(1);
		like.setPost(post);
		like.setUser(user);

		likeDAO.addLike(like);
		try {
			likeDAO.removeLikeByLikeId(1000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		List<Like> likeList = likeDAO.listLike();
		int listSize = likeList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatTwoLikesAreReturnedFromGetLikeByUserIdWhenThisUserHasTwoLikes() {
		LikeDAO likeDAO = new LikeDAO();
		Like like1 = new Like();
		Like like2 = new Like();
		User user = new User();
		Post post1 = new Post();
		Post post2 = new Post();

		user.setUserId(1);

		post1.setPostId(1);
		post1.setUser(user);

		post2.setPostId(2);
		post2.setUser(user);

		like1.setLikeId(1);
		like1.setPost(post1);
		like1.setUser(user);

		like2.setLikeId(2);
		like2.setPost(post2);
		like2.setUser(user);

		likeDAO.addLike(like1);
		likeDAO.addLike(like2);

		List<Like> likeList = likeDAO.getLikeByUserId(1);
		int listSize = likeList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testThatTwoLikesAreReturnedFromGetLikeByPostIdWhenThisPostHasTwoLikes() {
		LikeDAO likeDAO = new LikeDAO();
		Like like1 = new Like();
		Like like2 = new Like();

		User user1 = new User();
		User user2 = new User();

		Post post = new Post();

		user1.setUserId(1);
		user2.setUserId(2);

		post.setPostId(1);
		post.setUser(user1);

		like1.setLikeId(1);
		like1.setPost(post);
		like1.setUser(user1);

		like2.setLikeId(2);
		like2.setPost(post);
		like2.setUser(user2);

		likeDAO.addLike(like1);
		likeDAO.addLike(like2);

		List<Like> likeList = likeDAO.getLikeByPostId(1);
		int listSize = likeList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testThatSizeOfLikeListIsOneAfterGetLikeByUserIdAndPostId() {
		LikeDAO likeDAO = new LikeDAO();
		Like like = new Like();
		User user = new User();
		Post post = new Post();

		user.setUserId(7);

		post.setPostId(2);
		post.setUser(user);

		like.setLikeId(1);
		like.setPost(post);
		like.setUser(user);

		likeDAO.addLike(like);

		List<Like> likeListResult = likeDAO.getLikeByPostIdAndUserId(2, 7);
		int likeListResultSize = likeListResult.size();

		assertEquals(1, likeListResultSize);
	}
	
	@Test
	public void testThatLikeIdIsTheLikeGetFromGetLikeByLikeId(){
		LikeDAO likeDAO = new LikeDAO();
		Like like = new Like();
		User user = new User();
		Post post = new Post();

		user.setUserId(7);

		post.setPostId(2);
		post.setUser(user);

		like.setLikeId(1);
		like.setPost(post);
		like.setUser(user);

		likeDAO.addLike(like);

		Like likeRetrieved = likeDAO.getLikeByLikeId(1);

		assertEquals(1, likeRetrieved.getLikeId());
	}
}
