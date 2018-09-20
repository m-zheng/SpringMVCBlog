package com.example.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.example.entity.User;

public class UserDAO {
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SpringMVCBlog");
	private EntityManager entityManager = null;
	private EntityTransaction entityTransaction = null;
	private List<User> userList = new ArrayList<User>();

	public UserDAO() {
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	public List<User> listUser() {
		TypedQuery<User> userQuery = entityManager.createQuery("Select u from H_User u", User.class);
		userList = userQuery.getResultList();
		return userList;
	}

	public User getUserByUserId(int userId) {
		User retrievedUser = entityManager.find(User.class, userId);
		return retrievedUser;
	}

	public List<User> getUserByUsername(String username) {
		TypedQuery<User> query = entityManager.createQuery("Select u from H_User u where u.username= :username",
				User.class);
		query.setParameter("username", username);
		userList = query.getResultList();
		return userList;
	}

	public List<User> getUserByEmail(String email) {
		TypedQuery<User> query = entityManager.createQuery("Select u from H_User u where u.email= :email", User.class);
		query.setParameter("email", email);
		userList = query.getResultList();
		return userList;
	}

	public void addUser(User user) {
		entityTransaction.begin();
		entityManager.persist(user);
		entityTransaction.commit();
	}

	public void removeUserByUserId(int userId) {
		User user = entityManager.find(User.class, userId);
		entityTransaction.begin();
		entityManager.remove(user);
		entityTransaction.commit();
	}

	public void updateUser(User user) {
		User retrievedUser = entityManager.find(User.class, user.getUserId());
		entityTransaction.begin();
		retrievedUser.setUsername(user.getUsername());
		retrievedUser.setPassword(user.getPassword());
		retrievedUser.setEmail(user.getEmail());
		retrievedUser.setModifiedDateTime(user.getCreatedDateTime());
		entityTransaction.commit();
	}
}
