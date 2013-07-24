package mrf.controller;

import java.util.ArrayList;
import java.util.List;

import mrf.customtype.Role;
import mrf.exeption.ControllerException;
import mrf.model.Authorization;
import mrf.model.Database;
import mrf.model.Employee;
import mrf.model.EmployeeInfo;
import mrf.model.PersonalInfo;
import mrf.model.Systems;
import mrf.model.User;
import mrf.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

public class AdminController {
	public void saveUser(User user,ArrayList<Authorization> authorizations) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(user);
			
			for(Authorization auth : user.getAuthorizations()){
				session.delete(auth);
			}
			
			for(Authorization auth : authorizations){
				session.save(auth);
			}
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ControllerException("เกิดความผิดพลาดระหว่างการบันทึกข้อมูล");
		} finally {
			session.clear();
			session.close();
		}		
	}
	
	@SuppressWarnings("unchecked")
	public User checkExistUser(User aUser) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			User user = (User) session.createQuery(
					"SELECT DISTINCT user " +
					"FROM User user " +
					"left join fetch user.authorizations " +
					"WHERE user.status = 'NORMAL' " +
					"AND user.username = :pusername")
					.setParameter("pusername", aUser.getUsername())
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
	
	@SuppressWarnings("unchecked")
	public List<User> getPerformerUsers() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<User> user = session.createQuery(
					"SELECT DISTINCT user " +
					"FROM User user " +
					"WHERE user.roleAsString like '%Performer%' " +
					"AND user.status = 'NORMAL' ")
					.list();
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
	
	@SuppressWarnings("unchecked")
	public Database checkExistDatabase(Database aDatabase) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Database database = (Database) session.createQuery(
					"SELECT DISTINCT database " +
					"FROM Database database " +
					"WHERE database.status = 'NORMAL' " +
					"AND database.name = :pdatabasename")
					.setParameter("pdatabasename", aDatabase.getName())
					.uniqueResult();
			tx.commit();
					
			return database;
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
	
	@SuppressWarnings("unchecked")
	public Database checkExistDatabaseDescription(Database aDatabase) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Database database = (Database) session.createQuery(
					"SELECT DISTINCT database " +
					"FROM Database database " +
					"WHERE database.status = 'NORMAL' " +
					"AND database.description = :pdescription")
					.setParameter("pdescription", aDatabase.getDescription())
					.uniqueResult();
			tx.commit();
					
			return database;
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
	
	@SuppressWarnings("unchecked")
	public Systems checkExistSystem(Systems aSystem) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Systems system = (Systems) session.createQuery(
					"SELECT DISTINCT system " +
					"FROM Systems system " +
					"WHERE system.status = 'NORMAL' " +
					"AND system.name = :psystemname")
					.setParameter("psystemname", aSystem.getName())
					.uniqueResult();
			tx.commit();
					
			return system;
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
	
	@SuppressWarnings("unchecked")
	public Systems checkExistSystemDescription(Systems aSystem) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Systems system = (Systems) session.createQuery(
					"SELECT DISTINCT system " +
					"FROM Systems system " +
					"WHERE system.status = 'NORMAL' " +
					"AND system.description = :pdescription")
					.setParameter("pdescription", aSystem.getDescription())
					.uniqueResult();
			tx.commit();
					
			return system;
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
	
	public void deleteUser(User user) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(user);
			
			for(Authorization auth : user.getAuthorizations()){
				session.delete(auth);
			}
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ControllerException("เกิดความผิดพลาดระหว่างการบันทึกข้อมูล");
		} finally {
			session.clear();
			session.close();
		}		
	}
	
	public void saveDatabase(Database database) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(database);
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ControllerException("เกิดความผิดพลาดระหว่างการบันทึกข้อมูล");
		} finally {
			session.clear();
			session.close();
		}		
	}
	
	public void saveSystem(Systems system) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(system);
			
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			throw new ControllerException("เกิดความผิดพลาดระหว่างการบันทึกข้อมูล");
		} finally {
			session.clear();
			session.close();
		}		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<User> getAllUser() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<User> userList = session.createQuery(
					"SELECT DISTINCT user " +
					"FROM User user " +
					"left join fetch user.authorizations " +
					"WHERE user.status = 'NORMAL'" +
					"ORDER BY user.username ")
					.list();
			tx.commit();
					
			return new ArrayList<User>(userList);
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
	
	@SuppressWarnings("unchecked")
	public ArrayList<Database> getAllDatabase() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Database> databaseList = session.createQuery(
					"SELECT DISTINCT database " +
					"FROM Database database " +
					"WHERE database.status = 'NORMAL'" +
					"ORDER BY database.name ")
					.list();
			tx.commit();
					
			return new ArrayList<Database>(databaseList);
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
	
	@SuppressWarnings("unchecked")
	public ArrayList<Systems> getAllSystem() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Systems> dsystemList = session.createQuery(
					"SELECT DISTINCT system " +
					"FROM Systems system " +
					"WHERE system.status = 'NORMAL'" +
					"ORDER BY system.name ")
					.list();
			tx.commit();
					
			return new ArrayList<Systems>(dsystemList);
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
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getCompletePosition(String position) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		position = "%"+position+"%";
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<String> positionList = session.createQuery(
					"SELECT DISTINCT user.position " +
					"FROM User user " +
					"WHERE user.position LIKE :pposition " +
					"ORDER BY user.position ")
					.setParameter("pposition", position)
					.list();
			tx.commit();
					
			return new ArrayList<String>(positionList);
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
	
	public Employee getEmployee(String employeeCode){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Employee emp = (Employee) session.createQuery(
					"SELECT emp " +
					"FROM Employee emp " +
					"WHERE emp.employeeCode = :pempcode")
					.setParameter("pempcode", employeeCode)
					.uniqueResult();
				
			tx.commit();
			
			return emp;
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonalInfo> getPersonalInfo(String empCode){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<PersonalInfo> personalInfo = session.createQuery(
					"SELECT personal " +
					"FROM PersonalInfo personal " +
					"WHERE personal.STAFFCODE = :pempcode")
					.setParameter("pempcode", empCode)
					.list();
			tx.commit();
			
			return personalInfo;
		}  catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
	
	public EmployeeInfo getEmployeeInfo(Long linkP){
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			EmployeeInfo employeeInfo = (EmployeeInfo) session.createQuery(
					"SELECT employee " +
					"FROM EmployeeInfo employee " +
					"WHERE employee.LINKP = :plinkp")
					.setParameter("plinkp", linkP)
					.uniqueResult();
			tx.commit();
			
			return employeeInfo;
		}  catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		} finally {
			session.clear();
			session.close();
		}
	}
}
