package com.webchatapp.datastore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.webchatapp.hibernatebeans.Message;
import com.webchatapp.service.MessageDatastoreService;
import com.webchatapp.utils.ChatDate;

public class MessageDataStore implements MessageDatastoreService {
	private static SessionFactory factory;

	@Override
	public void sendMessageToThisUser(String receiverName, String message,
			String senderName) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer messageID = null;
		Message messageObj = null;
		try {
			tx = session.beginTransaction();
			messageObj = new Message();
			messageObj.setMessage(message);
			messageObj.setReceiverName(receiverName);
			messageObj.setSenderName(senderName);
			// get current date and time
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			// set with chat date and time
			ChatDate chatDate = new ChatDate();
			chatDate.setDay(date.getDay());
			chatDate.setMonth(date.getMonth() + 1);
			chatDate.setYear(date.getYear());
			chatDate.setMin(date.getMinutes());
			chatDate.setSec(date.getSeconds());
			chatDate.setHour(date.getHours());
			messageObj.setTime(chatDate);
			messageID = (Integer) session.save(messageObj);
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
	public List getMyLatestMessages(String loggedUserName) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Message> messageList = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Message.class)
					.add(Restrictions.like("receiverName", loggedUserName))
					.addOrder(Order.desc("id")).setMaxResults(5);
			messageList = criteria.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return messageList;
	}

	@Override
	public List getMyPrevMessages(String loggedUserName, String minVal) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Message> messageList = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Message.class)
					.add(Restrictions.like("receiverName", loggedUserName))
					.add(Restrictions.lt("id", Integer.parseInt(minVal)))
					.addOrder(Order.desc("id")).setMaxResults(5);
			messageList = criteria.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return messageList;
	}

	@Override
	public List getMyNextMessages(String loggedUserName, String maxVal) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Message> messageList = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Message.class)
					.add(Restrictions.like("receiverName", loggedUserName))
					.add(Restrictions.gt("id", Integer.parseInt(maxVal)))
					.addOrder(Order.desc("id")).setMaxResults(5);
			messageList = criteria.list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return messageList;
	}

}
