package mrf.manager;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;


import mrf.controller.AdminController;
import mrf.customtype.DatabaseType;
import mrf.customtype.Role;
import mrf.customtype.RoleKey;
import mrf.customtype.ServerType;
import mrf.customtype.Status;
import mrf.exeption.ControllerException;
import mrf.exeption.InvalidLoginException;
import mrf.ldap.LDAPConnect;
import mrf.ldap.LDAPUser;
import mrf.model.Authorization;
import mrf.model.Database;
import mrf.model.Employee;
import mrf.model.EmployeeInfo;
import mrf.model.PersonalInfo;
import mrf.model.Systems;
import mrf.model.User;
import mrf.utils.HibernateUtil;

@ManagedBean(name="adminManager")
@SessionScoped
public class AdminManager {
	
	private ArrayList<SelectItem> authorizationSelectCheckboxList = new ArrayList<SelectItem>();
	private List<String> selectedNewAuthorizationList = new ArrayList<String>();
	private List<String> selectedEditAuthorizationList = new ArrayList<String>();
	private AdminController adminController = new AdminController();
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<Database> databaseList = new ArrayList<Database>();
	private ArrayList<Systems> systemList = new ArrayList<Systems>();
	private ArrayList<SelectItem> databaseTypeSelectRadioList = new ArrayList<SelectItem>();
	private String newDatabaseType = null;
	private String editDatabaseType = null;
	private User editUser = new User();
	private User newUser = new User();
	private User deleteUser = new User();
	private Database editDatabase = new Database();
	private Database newDatabase = new Database();
	private Database deleteDatabase = new Database();
	private Systems newSystem = new Systems();
	private Systems editSystem = new Systems();
	private Systems deleteSystem = new Systems();
	private String currentSystem = null;
	private String currentDatabase = null;
	private String currentUser = null;
	private String displayName = "";
	private String username = "";
	private boolean isFound = false;
	private String displayPosition = "";
	
	public ArrayList<SelectItem> getDatabaseTypeSelectRadioList() {
		return databaseTypeSelectRadioList;
	}

	public void setDatabaseTypeSelectRadioList(
			ArrayList<SelectItem> databaseTypeSelectRadioList) {
		this.databaseTypeSelectRadioList = databaseTypeSelectRadioList;
	}

	public String getDisplayPosition() {
		return displayPosition;
	}

	public void setDisplayPosition(String displayPosition) {
		this.displayPosition = displayPosition;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getNewDatabaseType() {
		return newDatabaseType;
	}

	public void setNewDatabaseType(String newDatabaseType) {
		this.newDatabaseType = newDatabaseType;
	}

	public String getEditDatabaseType() {
		return editDatabaseType;
	}

	public void setEditDatabaseType(String editDatabaseType) {
		this.editDatabaseType = editDatabaseType;
	}

	public User getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(User deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Database getDeleteDatabase() {
		return deleteDatabase;
	}

	public void setDeleteDatabase(Database deleteDatabase) {
		this.deleteDatabase = deleteDatabase;
	}

	public Systems getDeleteSystem() {
		return deleteSystem;
	}

	public void setDeleteSystem(Systems deleteSystem) {
		this.deleteSystem = deleteSystem;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Database> getDatabaseList() {
		return databaseList;
	}

	public void setDatabaseList(ArrayList<Database> databaseList) {
		this.databaseList = databaseList;
	}

	public ArrayList<Systems> getSystemList() {
		return systemList;
	}

	public void setSystemList(ArrayList<Systems> systemList) {
		this.systemList = systemList;
	}

	public Database getEditDatabase() {
		return editDatabase;
	}

	public void setEditDatabase(Database editDatabase) {
		this.editDatabase = editDatabase;
	}

	public Database getNewDatabase() {
		return newDatabase;
	}

	public void setNewDatabase(Database newDatabase) {
		this.newDatabase = newDatabase;
	}

	public Systems getNewSystem() {
		return newSystem;
	}

	public void setNewSystem(Systems newSystem) {
		this.newSystem = newSystem;
	}

	public Systems getEditSystem() {
		return editSystem;
	}

	public void setEditSystem(Systems editSystem) {
		this.editSystem = editSystem;
	}

	public String getCurrentSystem() {
		return currentSystem;
	}

	public void setCurrentSystem(String currentSystem) {
		this.currentSystem = currentSystem;
	}

	public String getCurrentDatabase() {
		return currentDatabase;
	}

	public void setCurrentDatabase(String currentDatabase) {
		this.currentDatabase = currentDatabase;
	}

	public List<String> getSelectedNewAuthorizationList() {
		return selectedNewAuthorizationList;
	}

	public void setSelectedNewAuthorizationList(
			List<String> selectedNewAuthorizationList) {
		this.selectedNewAuthorizationList = selectedNewAuthorizationList;
	}

	public List<String> getSelectedEditAuthorizationList() {
		return selectedEditAuthorizationList;
	}

	public void setSelectedEditAuthorizationList(
			List<String> selectedEditAuthorizationList) {
		this.selectedEditAuthorizationList = selectedEditAuthorizationList;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public ArrayList<SelectItem> getAuthorizationSelectCheckboxList() {
		return authorizationSelectCheckboxList;
	}

	public void setAuthorizationSelectCheckboxList(
			ArrayList<SelectItem> authorizationSelectCheckboxList) {
		this.authorizationSelectCheckboxList = authorizationSelectCheckboxList;
	}

	public User getEditUser() {
		return editUser;
	}

	public void setEditUser(User editUser) {
		this.editUser = editUser;
	}

	public AdminManager(){
		createAuthorizationCheckboxList();
		createUserList();
		createDatabaseList();
		createSystemList();
		createDatabaseTypeSelectRadioList();
	}
	
	private void createAuthorizationCheckboxList(){
		authorizationSelectCheckboxList.clear();
		authorizationSelectCheckboxList.add(new SelectItem(Role.REQUESTER.getID(),Role.REQUESTER.getValue()));
		authorizationSelectCheckboxList.add(new SelectItem(Role.PERFORMER.getID(),Role.PERFORMER.getValue()));
		authorizationSelectCheckboxList.add(new SelectItem(Role.APPROVER.getID(),Role.APPROVER.getValue()));
		authorizationSelectCheckboxList.add(new SelectItem(Role.ADMIN.getID(),Role.ADMIN.getValue()));
	}
	
	private void createDatabaseTypeSelectRadioList(){
		databaseTypeSelectRadioList.clear();
		SelectItem selectItem;
		for(DatabaseType databaseType : DatabaseType.values()){
			selectItem = new SelectItem();
			selectItem.setValue(databaseType.getID());
			selectItem.setLabel(databaseType.getValue());
			databaseTypeSelectRadioList.add(selectItem);
		}
	}
	
	private void createDatabaseList(){
		try {
			if(databaseList != null) databaseList.clear();
			databaseList = adminController.getAllDatabase();
			
			
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	private void createSystemList(){
		try {
			if(systemList != null) systemList.clear();
			systemList = adminController.getAllSystem();
			
			
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	private void createUserList(){
		try {
			if(userList != null) userList.clear();
			userList = adminController.getAllUser();
		
			
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void saveNewUser(){
		System.out.println("saveUser");
		try {
			ArrayList<Authorization> authorizationList = new ArrayList<Authorization>();
			Authorization newAuthorizations;
			for(String auth : selectedNewAuthorizationList){
				newAuthorizations = new Authorization();
				newAuthorizations.setUser(newUser);
				newAuthorizations.setRoleKey(RoleKey.MAIN_ROLE);
				newAuthorizations.setRole(Role.find(auth));
				authorizationList.add(newAuthorizations);
			}
			
			newUser.setRoleAsString(StringUtils.join(selectedNewAuthorizationList, ","));
			adminController.saveUser(newUser,authorizationList);
	        
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","User "+newUser.getUsername()+" was added to the system");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createUserList();
			clearData();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
	}
	
	public void saveNewDatabase(){
		try {
			adminController.saveDatabase(newDatabase);
	        
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","Database "+newDatabase.getName()+" was added to the system");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createDatabaseList();
			clearData();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
	}
	
	public void saveNewSystem(){
		try {
			adminController.saveSystem(newSystem);
	        
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","System "+newSystem.getName()+" was added to the system");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
	        createSystemList();	
	        clearData();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
	}
	
	public void saveEditDatabase(){
		try {
			adminController.saveDatabase(editDatabase);
	        
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","Database "+newDatabase.getName()+" has been updated");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createDatabaseList();		
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
	}
	
	public void saveEditSystem(){
		try {
			adminController.saveSystem(editSystem);
	        
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","System "+newSystem.getName()+" has been updated");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createSystemList();		
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
	}
	
	public void saveEditUser(){
		System.out.println("saveUser");
		try {
			ArrayList<Authorization> authorizationList = new ArrayList<Authorization>();
			Authorization newAuthorizations;
			for(String auth : selectedEditAuthorizationList){
				newAuthorizations = new Authorization();
				newAuthorizations.setUser(editUser);
				newAuthorizations.setRoleKey(RoleKey.MAIN_ROLE);
				newAuthorizations.setRole(Role.find(auth));
				authorizationList.add(newAuthorizations);
				System.out.println("save auth = "+auth);
			}

			editUser.setRoleAsString(StringUtils.join(selectedEditAuthorizationList, ","));
			adminController.saveUser(editUser,authorizationList);
				        
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","User "+editUser.getUsername()+" has been updated");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createUserList();	
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
	}
	
	public void clearData(){
		editUser = new User();
		newUser = new User();
		editDatabase = new Database();
		editSystem = new Systems();
		newDatabase = new Database();
		newSystem = new Systems();
		deleteDatabase = new Database();
		deleteSystem = new Systems();
		deleteUser = new User();
		currentSystem = null;
		currentDatabase = null;
		currentUser = null;
		newDatabaseType = null;
		editDatabaseType = null;
		selectedNewAuthorizationList = new ArrayList<String>();
		selectedEditAuthorizationList = new ArrayList<String>();
		displayName = "";
		displayPosition = "";
		username = "";
		isFound = false;
	}
	
	public void setCurrentUser(){
		selectedEditAuthorizationList = new ArrayList<String>();
		selectedEditAuthorizationList = editUser.getAuthorizationsAsStringList();
		currentUser = editUser.getUsername();
	}
	
	public void setCurrentDatabase(){
		currentDatabase = editDatabase.getName();
		editDatabaseType = editDatabase.getDatabaseType().getID();
		System.out.println(currentDatabase);
	}
	
	public void setCurrentSystem(){
		currentSystem = editSystem.getName();
		System.out.println(currentSystem);
	}
	
	public void newDatabaseTypeSelected(){
		if(newDatabaseType != null){
			newDatabase.setDatabaseType(DatabaseType.find(newDatabaseType));
		}
	}
	
	public void editDatabaseTypeSelected(){
		if(editDatabaseType != null){
			editDatabase.setDatabaseType(DatabaseType.find(editDatabaseType));
		}
	}
		
	public void deleteUser(){
		try {
			System.out.println("delete user");
			deleteUser.setStatus(Status.DELETED);
			adminController.deleteUser(deleteUser);
			System.out.println(editUser.getUsername());	        
	       
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","User "+deleteUser.getUsername()+" has been deleted");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createUserList();
			clearData();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void deleteDatabase(){
		try {
			deleteDatabase.setStatus(Status.DELETED);
			adminController.saveDatabase(deleteDatabase);       
	       
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","Database "+deleteDatabase.getName()+" has been deleted");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createDatabaseList();
			clearData();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void deleteSystem(){
		try {
			deleteSystem.setStatus(Status.DELETED);
			adminController.saveSystem(deleteSystem); 
	       
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","System "+deleteSystem.getName()+" has been deleted");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
			
			createSystemList();
			clearData();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
//	public void validateNewUser(){
//		try {
//			User user = adminController.checkExistUser(newUser);
//			if(user != null){
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Username was already exist");  	          
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//			} else if(newUser.getUsername() == null || newUser.getUsername().equals("")){	
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Username is required");  	          
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//			} else if(newUser.getFirstname() == null || newUser.getFirstname().equals("")){
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","First Name is required");  	          
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//			} else if(newUser.getLastname() == null || newUser.getLastname().equals("")){
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Last Name is required");  	          
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//			} else if(newUser.getPosition() == null || newUser.getPosition().equals("")){
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Position is required");  	          
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//			} else if(selectedNewAuthorizationList.size() == 0) {
//				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select at least one authorization");  	          
//		        FacesContext.getCurrentInstance().addMessage(null, message);
//			} else {
//				saveNewUser();
//			}		
//		} catch (ControllerException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void validateNewUser(){
		try {
			if(username == null || username.equals("")){	
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Username is required");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(isFound == true){
				User user = adminController.checkExistUser(newUser);
				if(user != null){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Username was already exist");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(selectedNewAuthorizationList.size() == 0) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select at least one authorization");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else {
					saveNewUser();
				}
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","User not found");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} 		
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}
	
	public void validateEditUser(){
		try {
			User user = adminController.checkExistUser(editUser);
			if (user != null){
				if(!editUser.getId().equals(user.getId())){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Username was already exist");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editUser.getUsername() == null || editUser.getUsername().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Username is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editUser.getFirstname() == null || editUser.getFirstname().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","First Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editUser.getLastname() == null || editUser.getLastname().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Last Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editUser.getPosition() == null || editUser.getPosition().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Position is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(selectedEditAuthorizationList.size() == 0) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select at least one authorization");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else {
					saveEditUser();
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("userEditDialog.hide()");
				}				
			} else {
				if(editUser.getUsername() == null || editUser.getUsername().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Username is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editUser.getFirstname() == null || editUser.getFirstname().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","First Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editUser.getLastname() == null || editUser.getLastname().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Last Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editUser.getPosition() == null || editUser.getPosition().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Position is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(selectedEditAuthorizationList.size() == 0) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select at least one authorization");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else {
					saveEditUser();
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("userEditDialog.hide()");
				}
			}
		}  catch (ControllerException e) {
			e.printStackTrace();
		}	
	}
	
	public void validateNewSystem(){
		try {
			Systems system = adminController.checkExistSystem(newSystem);
			Systems systemDes = adminController.checkExistSystemDescription(newSystem);
			if(systemDes != null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System description was already exist");  	          
			    FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(system != null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System was already exist");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(newSystem.getName() == null || newSystem.getName().equals("")){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System Name is required");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(newSystem.getDescription() == null || newSystem.getDescription().equals("")){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Description is required");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				saveNewSystem();
			}
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}
	
	public void validateEditSystem(){
		try {
			Systems system = adminController.checkExistSystem(editSystem);
			Systems systemDes = adminController.checkExistSystemDescription(editSystem);
			if(system != null){
				if(!system.getId().equals(editSystem.getId())){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System was already exist");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editSystem.getName() == null || editSystem.getName().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editSystem.getDescription() == null || editSystem.getDescription().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Description is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(systemDes != null){
					if(!systemDes.getId().equals(editSystem.getId())){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System description was already exist");  	          
						FacesContext.getCurrentInstance().addMessage(null, message);
					} else {
						saveEditSystem();
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("systemEditDialog.hide()");
					}
				} else {
					saveEditSystem();
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("systemEditDialog.hide()");
				}
			} else {
				if(editSystem.getName() == null || editSystem.getName().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editSystem.getDescription() == null || editSystem.getDescription().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Description is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(systemDes != null){
					if(!systemDes.getId().equals(editSystem.getId())){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","System description was already exist");  	          
						FacesContext.getCurrentInstance().addMessage(null, message);
					} else {
						saveEditSystem();
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("systemEditDialog.hide()");
					}
				} else {
					saveEditSystem();
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("systemEditDialog.hide()");
				}
			}
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}
	
	public void validateNewDatabase(){
		try {
			Database database = adminController.checkExistDatabase(newDatabase);
			Database databaseDes = adminController.checkExistDatabaseDescription(newDatabase);
			if(databaseDes != null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database description was already exist");  	          
			    FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(database != null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database was already exist");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(newDatabase.getName() == null || newDatabase.getName().equals("")){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database Name is required");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(newDatabase.getDescription() == null || newDatabase.getDescription().equals("")){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Description is required");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else if(newDatabaseType == null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select database type");  	          
		        FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				saveNewDatabase();
			}
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}
	
	public void validateEditDatabase(){
		try {
			Database database = adminController.checkExistDatabase(editDatabase);
			Database databaseDes = adminController.checkExistDatabaseDescription(editDatabase);
			if(database != null ){
				if(!database.getId().equals(editDatabase.getId())){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database was already exist");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editDatabase.getName() == null || editDatabase.getName().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editDatabase.getDescription() == null || editDatabase.getDescription().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Description is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(databaseDes != null){
					if(!databaseDes.getId().equals(editDatabase.getId())){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database description was already exist");  	          
					    FacesContext.getCurrentInstance().addMessage(null, message);
					} else {
						saveEditDatabase();
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("databaseEditDialog.hide()");
					}
				} else {
					saveEditDatabase();
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("databaseEditDialog.hide()");
				}
			} else {
				if(editDatabase.getName() == null || editDatabase.getName().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database Name is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(editDatabase.getDescription() == null || editDatabase.getDescription().equals("")){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Description is required");  	          
			        FacesContext.getCurrentInstance().addMessage(null, message);
				} else if(databaseDes != null){
					if(!databaseDes.getId().equals(editDatabase.getId())){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Database description was already exist");  	          
					    FacesContext.getCurrentInstance().addMessage(null, message);
					} else {
						saveEditDatabase();
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("databaseEditDialog.hide()");
					}
				} else {
					saveEditDatabase();
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("databaseEditDialog.hide()");
				}
			}
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}
	
	public void createAllList(){
		createAuthorizationCheckboxList();
		createUserList();
		createDatabaseList();
		createSystemList();
		createDatabaseTypeSelectRadioList();
		
//		return "admin?faces-redirect=true";
	}
		
	public List<String> positionComplete(String input){
		List<String> result = new ArrayList<String>();
		try {
			result = adminController.getCompletePosition(input);
			return result;
		} catch (ControllerException e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
		
	}
	
	public void pollUserData(){
		try {
			LDAPConnect connect = new LDAPConnect();
			LDAPUser ldapUser = connect.getUserData(username);
			connect.disconnect();
			EmployeeInfo emp = new EmployeeInfo();
			PersonalInfo person = new PersonalInfo();
			if(ldapUser != null){
				List<PersonalInfo> personalInfo = adminController.getPersonalInfo(StringUtils.leftPad(ldapUser.getEmployeeCode(), 6, '0'));
				for(PersonalInfo info : personalInfo){
					EmployeeInfo employeeInfo = adminController.getEmployeeInfo(info.getSN());
					if(employeeInfo != null){
						emp = employeeInfo;
						person = info;
					}
				}				
				newUser.setUsername(username);
				newUser.setFirstname(person.getTNAME());
				newUser.setLastname(person.getTSURNAME());
				newUser.setPosition(emp.getPOSITIONX());
				displayName = person.getTNAME()+" "+person.getTSURNAME();
				displayPosition = emp.getPOSITIONX();
				isFound = true;
			} else {
				displayName = "User not found !!";
				displayPosition = "";
				isFound = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			LDAPConnect connect = new LDAPConnect();
			LDAPUser ldapUser = connect.getUserData("manop");
			connect.disconnect();
				//PersonalInfo personalInfo = adminController.getPersonalInfo(ldapUser.getEmployeeCode());
				//EmployeeInfo employeeInfo = adminController.getEmployeeInfo(personalInfo.getSN());
				System.out.println(ldapUser.getEmployeeCode());
				//System.out.println(personalInfo.getTNAME());		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
