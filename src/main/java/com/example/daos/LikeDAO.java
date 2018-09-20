package com.example.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.example.entity.Like;
import com.example.entity.Post;
import com.example.entity.User;

public class LikeDAO {
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SpringMVCBlog");
	private EntityManager entityManager = null;
	private EntityTransaction entityTransaction = null;
	private List<Like> likeList = new ArrayList<Like>();

	public LikeDAO() {
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	public List<Like> listLike() {
		TypedQuery<Like> likeQuery = entityManager.createQuery("Select u from H_Like u", Like.class);
		likeList = likeQuery.getResultList();
		return likeList;
	}

	public Like getLikeByLikeId(int likeId) {
		Like retrievedLike = entityManager.find(Like.class, likeId);
		return retrievedLike;
	}

	public List<Like> getLikeByUserId(int userId) {
		TypedQuery<Like> query = entityManager.createQuery("Select u from H_Like u where u.user.userId= :userId",
				Like.class);
		query.setParameter("userId", userId);
		likeList = query.getResultList();
		return likeList;
	}

	public List<Like> getLikeByPostId(int postId) {
		TypedQuery<Like> query = entityManager.createQuery("Select u from H_Like u where u.post.postId= :postId",
				Like.class);
		query.setParameter("postId", postId);
		likeList = query.getResultList();
		return likeList;
	}
	
	public List<Like> getLikeByPostIdAndUserId(int postId, int userId) {
		TypedQuery<Like> query = entityManager.createQuery("Select u from H_Like u where u.post.postId= :postId and u.user.userId= :userId",
				Like.class);
		query.setParameter("postId", postId);
		query.setParameter("userId", userId);
		likeList = query.getResultList();
		return likeList;
	}
	
	

	public void addLike(Like like) {
		entityTransaction.begin();
		entityManager.merge(like);
		entityTransaction.commit();
	}

	public void removeLikeByLikeId(int likeId) {
		Like like = entityManager.find(Like.class, likeId);
		entityTransaction.begin();
		entityManager.remove(like);
		entityTransaction.commit();
	}
}
