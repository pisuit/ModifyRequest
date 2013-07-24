package mrf.controller;

import mrf.exeption.ControllerException;
import mrf.model.User;
import mrf.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class LoginController {

	public User getUser(String username) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			User user = (User) session.createQuery(
					"FROM User user " +
					"left join fetch user.authorizations " +
					"WHERE user.username = :pusername " +
					"AND user.status = 'NORMAL'")
					.setParameter("pusername", username)
					.uniqueResult();
			tx.commit();
			
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ControllerException("เกิดความผิดพลาดระหว่างการดึงข้อมูล");
		} finally {
			session.clear();
			session.close();
		}
	}
}
