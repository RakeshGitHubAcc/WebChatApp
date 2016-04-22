package com.webchatapp.datastore;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.webchatapp.hibernatebeans.OnlineUsers;
import com.webchatapp.hibernatebeans.User;
import com.webchatapp.service.UserDatastoreService;

public class UserDatastore implements UserDatastoreService {
	private static SessionFactory factory;

	public UserDatastore() {
		SessionFactory sessionFactory=null;
		try {
			sessionFactory= new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		this.factory = sessionFactory;
	}

	@Override
	/* Method to add an address record in the database */
	public void createUserByHibernate(String userName, String password) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userID = null;
		User user = null;
		try {
			tx = session.beginTransaction();
			user = new User();
			user.setUserName(userName);
			user.setPassWord(password);
			userID = (Integer) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public User loginByUsernameAndPassword(String username, String password) {
		Session session = factory.openSession();
		Transaction tx = null;
		User user = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class)
					.add(Restrictions.eq("userName", username))
					.add(Restrictions.eq("passWord", password));
			user = (User) criteria.list().get(0);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	@Override
	public List getAllUsers(String loggedUserName) {
		Session session = factory.openSession();
		Transaction tx = null;
		List users = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class).add(
					Restrictions.ne("userName", loggedUserName));
			users = criteria.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

	@Override
	public List getOnlineUsers(String loggedUserName) {
		Session session = factory.openSession();
		Transaction tx = null;
		List users = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(OnlineUsers.class)
					.add(Restrictions.eq("liveStatus", 1))
					.add(Restrictions.ne("userId", loggedUserName));
			users = criteria.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

	@Override
	public void changeMyOnlineStatus(int loggedUserId, int status) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer userID = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(OnlineUsers.class).add(
					Restrictions.eq("userId", loggedUserId));
			if (criteria.list().size() == 0) {
				OnlineUsers onlineUsers = new OnlineUsers();
				onlineUsers.setLiveStatus(status);
				onlineUsers.setUserId(loggedUserId);
				userID = (Integer) session.save(onlineUsers);
			} else {
				OnlineUsers onlineUsers = (OnlineUsers) criteria.list().get(0);
				onlineUsers.setLiveStatus(status);
				session.update(onlineUsers);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
