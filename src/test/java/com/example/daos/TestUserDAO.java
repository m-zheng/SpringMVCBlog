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

import com.example.daos.UserDAO;
import com.example.entity.User;

public class TestUserDAO {
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
	public void testThatInitalSizeOfUserListIsZero() {
		UserDAO userDAO = new UserDAO();
		List<User> userList = userDAO.listUser();
		int listSize = userList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfUserListIsOneAfterAddingOneUser() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setUserId(1);
		user.setUsername("james");
		userDAO.addUser(user);
		List<User> userList = userDAO.listUser();
		int listSize = userList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfUserListIsOneAfterAddingAnInvalidUser() {
		UserDAO userDAO = new UserDAO();
		User user1 = new User();
		user1.setUserId(1);
		User user2 = new User();
		user2.setUserId(1);
		userDAO.addUser(user1);
		try {
			userDAO.addUser(user2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<User> userList = userDAO.listUser();
		int listSize = userList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatSizeOfUserListIsOneAfterAddingTwoValidUsers() {
		UserDAO userDAO = new UserDAO();
		User user1 = new User();
		User user2 = new User();
		userDAO.addUser(user1);
		userDAO.addUser(user2);
		List<User> userList = userDAO.listUser();
		int listSize = userList.size();
		assertEquals(2, listSize);
	}

	@Test
	public void testThatSizeOfUserListIsZeroAfterRemovingAnValidUser() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setUserId(2);
		userDAO.addUser(user);
		userDAO.removeUserByUserId(2);
		List<User> userList = userDAO.listUser();
		int listSize = userList.size();
		assertEquals(0, listSize);
	}

	@Test
	public void testThatSizeOfUserListIsOneAfterRemovingAnInalidUser() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setUserId(3);
		userDAO.addUser(user);

		try {
			userDAO.removeUserByUserId(200);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<User> userList = userDAO.listUser();
		int listSize = userList.size();
		assertEquals(1, listSize);
	}

	@Test
	public void testThatUserIdReturnedFromGetUserByIdIsFour() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setUserId(4);
		userDAO.addUser(user);

		User retrievedUser = userDAO.getUserByUserId(4);
		assertEquals(4, retrievedUser.getUserId());
	}

	@Test
	public void testThatUsernameReturedFromGetUserByUsernameIsFDM() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setUsername("FDM");
		userDAO.addUser(user);

		User retrievedUser = userDAO.getUserByUsername("FDM").get(0);
		assertEquals("FDM", retrievedUser.getUsername());
	}

	@Test
	public void testThatEmailReturedFromGetUserByEmailIsFDM() {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setEmail("FDM");
		userDAO.addUser(user);

		User retrievedUser = userDAO.getUserByEmail("FDM").get(0);
		assertEquals("FDM", retrievedUser.getEmail());
	}

	@Test
	public void testThatUserToBeUpdatedIsUpdated() {
		UserDAO userDAO = new UserDAO();
		User user1 = new User();
		User user2 = new User();
		user1.setUserId(5);
		user1.setUsername("james");
		user2.setUserId(5);
		user2.setUsername("harden");
		userDAO.addUser(user1);

		userDAO.updateUser(user2);

		User retrievedUser = userDAO.getUserByUserId(5);
		assertEquals("harden", retrievedUser.getUsername());
	}

}
