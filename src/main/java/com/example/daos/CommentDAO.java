package com.example.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.example.entity.Comment;

public class CommentDAO {
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SpringMVCBlog");
	private EntityManager entityManager = null;
	private EntityTransaction entityTransaction = null;
	private List<Comment> commentList = new ArrayList<Comment>();

	public CommentDAO() {
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	public List<Comment> listComment() {
		TypedQuery<Comment> commentQuery = entityManager.createQuery("Select u from H_Comment u", Comment.class);
		commentList = commentQuery.getResultList();
		return commentList;
	}

	public Comment getCommentByCommentId(int commentId) {
		Comment retrievedComment = entityManager.find(Comment.class, commentId);
		return retrievedComment;
	}

	public List<Comment> getCommentByPostId(int postId) {
		TypedQuery<Comment> query = entityManager.createQuery("Select u from H_Comment u where u.post.postId= :postId",
				Comment.class);
		query.setParameter("postId", postId);
		commentList = query.getResultList();
		return commentList;
	}

	public List<Comment> getCommentByUserId(int userId) {
		TypedQuery<Comment> query = entityManager.createQuery("Select u from H_Comment u where u.user.userId= :userId",
				Comment.class);
		query.setParameter("userId", userId);
		commentList = query.getResultList();
		return commentList;
	}

	public void addComment(Comment comment) {
		entityTransaction.begin();
		entityManager.merge(comment);
		entityTransaction.commit();
	}

	public void removeCommentByCommentId(int commentId) {
		Comment comment = entityManager.find(Comment.class, commentId);
		entityTransaction.begin();
		entityManager.remove(comment);
		entityTransaction.commit();
	}

	public void updateCommentContent(Comment comment) {
		Comment retrievedComment = entityManager.find(Comment.class, comment.getCommentId());
		entityTransaction.begin();
		retrievedComment.setContent(comment.getContent());
		retrievedComment.setModifiedDateTime(comment.getCreatedDateTime());
		entityTransaction.commit();
	}

}
