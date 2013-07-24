package mrf.controller;

import java.util.ArrayList;
import java.util.List;

import mrf.exeption.ControllerException;
import mrf.model.Database;
import mrf.model.Download;
import mrf.model.Form;
import mrf.model.Photo;
import mrf.model.Systems;
import mrf.model.Upload;
import mrf.model.User;
import mrf.utils.CalendarUtils;
import mrf.utils.HibernateUtil;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

public class FormController {
	@SuppressWarnings("unchecked")
	public ArrayList<Form> getAllForms() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"ORDER BY form.id desc")
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Form> getAllAllForms() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"ORDER BY form.id desc")
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	
	public Form saveForm(Form form) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(form);
			
			tx.commit();
			return form;
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
	public ArrayList<Form> getRequestingForms(User user) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"AND form.processStatus = 'Requesting' " +
					"AND form.requester = :puser " +
					"ORDER BY form.id desc")
					.setParameter("puser", user)
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Form> getAllRequestingForms() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"AND form.processStatus = 'Requesting' " +
					"ORDER BY form.id desc")
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Form> getPerformedForms(User user) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"AND form.processStatus = 'Performed' " +
					"AND form.performer = :puser " +
					"ORDER BY form.id desc")
					.setParameter("puser", user)
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Form> getPerformedFormsForRequester(User user) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"AND form.processStatus = 'Performed' " +
					"AND form.requester = :puser " +
					"ORDER BY form.id desc")
					.setParameter("puser", user)
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Form> getAllPerformedForms() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"AND form.processStatus = 'Performed' " +
					"ORDER BY form.id desc")
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Form> getApprovedForms(User user) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"AND form.processStatus = 'Approved' " +
					"AND form.approver = :puser " +
					"ORDER BY form.id desc")
					.setParameter("puser", user)
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Form> getAllApprovedForms() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Form> formList = session.createQuery(
					"SELECT DISTINCT form " +
					"FROM Form form " +
					"left join fetch form.requester " +
					"left join fetch form.performer " +
					"left join fetch form.approver " +
					"left join fetch form.databaseTo " +
					"left join fetch form.databaseFrom " +
					"left join fetch form.system " +
					"WHERE form.status = 'NORMAL' " +
					"AND form.processStatus = 'Approved' " +
					"ORDER BY form.id desc")
					.list();			
			tx.commit();
			
			return new ArrayList<Form>(formList);
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
	public ArrayList<Database> getDatabaseList() throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Database> databaseList = session.createQuery(
					"SELECT DISTINCT database " +
					"FROM Database database " +
					"WHERE database.status = 'NORMAL' " +
					"ORDER BY database.name")
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
	public ArrayList<Systems> getSystemList() throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Systems> systemList = session.createQuery(
					"SELECT DISTINCT system " +
					"FROM Systems system " +
					"WHERE system.status = 'NORMAL' " +
					"ORDER BY system.name ")
					.list();
			tx.commit();
			
			return new ArrayList<Systems>(systemList);
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
	
	public Database getDatabase(Long databaseID) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			

			Database database = (Database) session.createQuery(
					"FROM Database database " +
					"WHERE database.id = :pdatabaseid")
					.setParameter("pdatabaseid", databaseID)
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
	
	public Systems getSystem(Long systemID) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			

			Systems system = (Systems) session.createQuery(
					"FROM Systems system " +
					"WHERE system.id = :psystemid")
					.setParameter("psystemid", systemID)
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
	
	public int getNextFormNumber() throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Integer maxFormNumber = (Integer) session.createQuery(
					"SELECT MAX(form.formNumber) " +
					"FROM Form form ")
					.uniqueResult();
			tx.commit();
			
			if (maxFormNumber == null ){
				maxFormNumber = Integer.parseInt(Integer.toString(DateTime.now().getYear())+"0001");
			} else {
				if(!Integer.toString(DateTime.now().getYear()).equals(StringUtils.substring(Integer.toString(maxFormNumber), 0, 4))){
					maxFormNumber = Integer.parseInt(Integer.toString(DateTime.now().getYear())+"0001");
				} else {
					maxFormNumber = Integer.valueOf(maxFormNumber.intValue()+1);
				}			
			}
			
			return maxFormNumber;
		}  catch (Exception e) {
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
		
	public User getUser(String username) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			User user = (User) session.createQuery(
					"SELECT DISTINCT user " +
					"FROM User user " +
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
	
	public void saveUpload(Upload upload) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			session.saveOrUpdate(upload);
			
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
	public ArrayList<Upload> getRequesterUploadedFiles(Form form) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Upload> uploadList = session.createQuery(
					"SELECT DISTINCT upload " +
					"FROM Upload upload " +
					"left join fetch upload.form " +
					"WHERE upload.form = :pform " +
					"AND upload.uploader = 'Requester'")
					.setParameter("pform", form)
					.list();
			tx.commit();
		
			return new ArrayList<Upload>(uploadList);
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
	public ArrayList<Upload> getPerformerUploadedFiles(Form form) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			List<Upload> uploadList = session.createQuery(
					"SELECT DISTINCT upload " +
					"FROM Upload upload " +
					"left join fetch upload.form " +
					"WHERE upload.form = :pform " +
					"AND upload.uploader = 'Performer'")
					.setParameter("pform", form)
					.list();
			tx.commit();
			
			return new ArrayList<Upload>(uploadList);
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
	
	public void deleteFile(Download download) throws ControllerException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Upload upload = (Upload) session.createQuery(
					"SELECT DISTINCT upload " +
					"FROM Upload upload " +
					"WHERE upload.physicalName =:pphyname " +
					"AND upload.logicalName = :plogicname")
					.setParameter("pphyname", download.getPhysicalName())
					.setParameter("plogicname", download.getLogicName())
					.uniqueResult();
			
				session.delete(upload);
			
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
	
	public Photo getPhoto(String staffCode) throws ControllerException{
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		
		try{
			session = sf.openSession();
			tx = session.beginTransaction();
			
			Photo photo = (Photo) session.createQuery(
					"SELECT photo " +
					"FROM Photo photo " +
					"WHERE photo.staffCode = :pstaffcode")
					.setParameter("pstaffcode", staffCode)
					.uniqueResult();
			
			tx.commit();
			
			return photo;
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
}
