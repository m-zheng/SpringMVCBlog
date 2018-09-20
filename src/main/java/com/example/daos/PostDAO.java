package com.example.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.example.entity.Post;
import com.example.entity.User;

public class PostDAO {
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SpringMVCBlog");
	private EntityManager entityManager = null;
	private EntityTransaction entityTransaction = null;
	private List<Post> postList = new ArrayList<Post>();

	public PostDAO() {
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	public List<Post> listPost() {
		TypedQuery<Post> postQuery = entityManager.createQuery("Select u from H_Post u", Post.class);
		postList = postQuery.getResultList();
		return postList;
	}

	public Post getPostByPostId(int postId) {
		Post retrievedPost = entityManager.find(Post.class, postId);
		return retrievedPost;
	}

	public List<Post> getPostByUserId(int userId) {
		TypedQuery<Post> query = entityManager.createQuery("Select u from H_Post u where u.user.userId= :userId",
				Post.class);
		query.setParameter("userId", userId);
		postList = query.getResultList();
		return postList;
	}

	public void addPost(Post post) {
		entityTransaction.begin();
		entityManager.merge(post);
		entityTransaction.commit();
	}

	public void removePostByPostId(int postId) {
		Post post = entityManager.find(Post.class, postId);
		entityTransaction.begin();
		entityManager.remove(post);
		entityTransaction.commit();
	}

	public void updatePostSubjectAndContent(Post post) {
		Post retrievedPost = entityManager.find(Post.class, post.getPostId());
		entityTransaction.begin();
		retrievedPost.setSubject(post.getSubject());
		retrievedPost.setContent(post.getContent());
		retrievedPost.setModifiedDateTime(post.getCreatedDateTime());
		entityTransaction.commit();
	}

}
