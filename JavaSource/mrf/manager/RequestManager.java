package mrf.manager;

import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.apache.bcel.classfile.Constant;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.tmatesoft.svn.core.SVNException;

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo;
import com.google.code.jgntp.GntpClient;
import com.google.code.jgntp.GntpNotificationInfo;

import mrf.controller.AdminController;
import mrf.controller.FormController;
import mrf.customtype.Role;
import mrf.customtype.RoleKey;
import mrf.customtype.ServerType;
import mrf.customtype.Status;
import mrf.exeption.ControllerException;
import mrf.ldap.LDAPConnect;
import mrf.ldap.LDAPUser;
import mrf.model.Authorization;
import mrf.model.Database;
import mrf.model.Download;
import mrf.model.Form;
import mrf.model.Systems;
import mrf.model.Upload;
import mrf.model.User;
import mrf.push.Growl;
import mrf.push.IPMessage;
import mrf.session.UserSession;
import mrf.utils.CalendarUtils;
import mrf.utils.Constants;
import mrf.utils.SVNUtils;

@ManagedBean(name="requestManager")
@SessionScoped
public class RequestManager {
	
	private ArrayList<SelectItem> databaseSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> systemSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> serverTypeSelectCheckboxList = new ArrayList<SelectItem>();
	private FormController formController = new FormController();
	private AdminController adminController = new AdminController();
	
	private Long newDatabaseFromID = Long.valueOf(0);
	private Long newDatabaseToID = Long.valueOf(0);
	private Long newSystemID = Long.valueOf(0);
	private String newServerTypeFrom = null;
	private String newServerTypeTo = null;
	
	private Long editDatabaseFromID = Long.valueOf(0);
	private Long editDatabaseToID = Long.valueOf(0);
	private Long editSystemID = Long.valueOf(0);
	private String editServerTypeFrom = null;
	private String editServerTypeTo = null;
	
	private Long viewDatabaseFromID = Long.valueOf(0);
	private Long viewDatabaseToID = Long.valueOf(0);
	private Long viewSystemID = Long.valueOf(0);
	private String viewServerTypeFrom = null;
	private String viewServerTypeTo = null;
	
	private Form editForm = new Form();
	private Form newForm = new Form();
	private Form viewForm = new Form();
	private Form deleteForm = new Form();
	private ArrayList<Form> formList = new ArrayList<Form>();
	private ArrayList<Form> viewFormList = new ArrayList<Form>();
	private ArrayList<Download> downloadList = new ArrayList<Download>();
	private ArrayList<Download> viewRequesterDownloadList = new ArrayList<Download>();
	private ArrayList<Download> viewPerformerDownloadList = new ArrayList<Download>();
	private Download deletedFile = new Download();
	private Form downloadedForm = new Form();
	private Date postDate = new Date();
	private String postDateOption = "1";
	private boolean isUseCurrentDate = false;
	private UploadedFile file;
	
	public String getNewServerTypeFrom() {
		return newServerTypeFrom;
	}
	public void setNewServerTypeFrom(String newServerTypeFrom) {
		this.newServerTypeFrom = newServerTypeFrom;
	}
	public String getNewServerTypeTo() {
		return newServerTypeTo;
	}
	public void setNewServerTypeTo(String newServerTypeTo) {
		this.newServerTypeTo = newServerTypeTo;
	}
	public String getEditServerTypeFrom() {
		return editServerTypeFrom;
	}
	public void setEditServerTypeFrom(String editServerTypeFrom) {
		this.editServerTypeFrom = editServerTypeFrom;
	}
	public Long getNewDatabaseFromID() {
		return newDatabaseFromID;
	}
	public void setNewDatabaseFromID(Long newDatabaseFromID) {
		this.newDatabaseFromID = newDatabaseFromID;
	}
	public Long getNewDatabaseToID() {
		return newDatabaseToID;
	}
	public void setNewDatabaseToID(Long newDatabaseToID) {
		this.newDatabaseToID = newDatabaseToID;
	}
	public Long getEditDatabaseFromID() {
		return editDatabaseFromID;
	}
	public void setEditDatabaseFromID(Long editDatabaseFromID) {
		this.editDatabaseFromID = editDatabaseFromID;
	}
	public Long getEditDatabaseToID() {
		return editDatabaseToID;
	}
	public void setEditDatabaseToID(Long editDatabaseToID) {
		this.editDatabaseToID = editDatabaseToID;
	}
	public Long getViewDatabaseFromID() {
		return viewDatabaseFromID;
	}
	public void setViewDatabaseFromID(Long viewDatabaseFromID) {
		this.viewDatabaseFromID = viewDatabaseFromID;
	}
	public Long getViewDatabaseToID() {
		return viewDatabaseToID;
	}
	public void setViewDatabaseToID(Long viewDatabaseToID) {
		this.viewDatabaseToID = viewDatabaseToID;
	}
	public String getEditServerTypeTo() {
		return editServerTypeTo;
	}
	public void setEditServerTypeTo(String editServerTypeTo) {
		this.editServerTypeTo = editServerTypeTo;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getViewServerTypeFrom() {
		return viewServerTypeFrom;
	}
	public void setViewServerTypeFrom(String viewServerTypeFrom) {
		this.viewServerTypeFrom = viewServerTypeFrom;
	}
	public boolean isUseCurrentDate() {
		return isUseCurrentDate;
	}
	public void setUseCurrentDate(boolean isUseCurrentDate) {
		this.isUseCurrentDate = isUseCurrentDate;
	}
	public String getPostDateOption() {
		return postDateOption;
	}
	public void setPostDateOption(String postDateOption) {
		this.postDateOption = postDateOption;
	}
	public String getViewServerTypeTo() {
		return viewServerTypeTo;
	}
	public void setViewServerTypeTo(String viewServerTypeTo) {
		this.viewServerTypeTo = viewServerTypeTo;
	}
	public ArrayList<Download> getViewRequesterDownloadList() {
		return viewRequesterDownloadList;
	}
	public void setViewRequesterDownloadList(
			ArrayList<Download> viewRequesterDownloadList) {
		this.viewRequesterDownloadList = viewRequesterDownloadList;
	}
	public ArrayList<Download> getViewPerformerDownloadList() {
		return viewPerformerDownloadList;
	}
	public void setViewPerformerDownloadList(
			ArrayList<Download> viewPerformerDownloadList) {
		this.viewPerformerDownloadList = viewPerformerDownloadList;
	}
	public Form getDownloadedForm() {
		return downloadedForm;
	}
	public void setDownloadedForm(Form downloadedForm) {
		this.downloadedForm = downloadedForm;
	}
	public Download getDeletedFile() {
		return deletedFile;
	}
	public void setDeletedFile(Download deletedFile) {
		this.deletedFile = deletedFile;
	}
	public ArrayList<Download> getDownloadList() {
		return downloadList;
	}
	public void setDownloadList(ArrayList<Download> downloadList) {
		this.downloadList = downloadList;
	}
	public Form getDeleteForm() {
		return deleteForm;
	}
	public void setDeleteForm(Form deleteForm) {
		this.deleteForm = deleteForm;
	}
	public Long getViewSystemID() {
		return viewSystemID;
	}
	public void setViewSystemID(Long viewSystemID) {
		this.viewSystemID = viewSystemID;
	}
	public Form getViewForm() {
		return viewForm;
	}
	public void setViewForm(Form viewForm) {
		this.viewForm = viewForm;
	}	
	public ArrayList<Form> getViewFormList() {
		return viewFormList;
	}
	public void setViewFormList(ArrayList<Form> viewFormList) {
		this.viewFormList = viewFormList;
	}
	public ArrayList<Form> getFormList() {
		return formList;
	}
	public void setFormList(ArrayList<Form> formList) {
		this.formList = formList;
	}
	public Long getNewSystemID() {
		return newSystemID;
	}
	public void setNewSystemID(Long newSystemID) {
		this.newSystemID = newSystemID;
	}
	public Long getEditSystemID() {
		return editSystemID;
	}
	public void setEditSystemID(Long editSystemID) {
		this.editSystemID = editSystemID;
	}
	public Form getNewForm() {
		return newForm;
	}
	public void setNewForm(Form newForm) {
		this.newForm = newForm;
	}
	public Form getEditForm() {
		return editForm;
	}
	public void setEditForm(Form editForm) {
		this.editForm = editForm;
	}
	public ArrayList<SelectItem> getServerTypeSelectCheckboxList() {
		return serverTypeSelectCheckboxList;
	}
	public void setServerTypeSelectCheckboxList(
			ArrayList<SelectItem> serverTypeSelectCheckboxList) {
		this.serverTypeSelectCheckboxList = serverTypeSelectCheckboxList;
	}
	public ArrayList<SelectItem> getDatabaseSelectItemList() {
		return databaseSelectItemList;
	}
	public void setDatabaseSelectItemList(
			ArrayList<SelectItem> databaseSelectItemList) {
		this.databaseSelectItemList = databaseSelectItemList;
	}
	public ArrayList<SelectItem> getSystemSelectItemList() {
		return systemSelectItemList;
	}
	public void setSystemSelectItemList(ArrayList<SelectItem> systemSelectItemList) {
		this.systemSelectItemList = systemSelectItemList;
	}
	
	public RequestManager(){
		createDatabaseSelectItemList();
		createSystemSelectItemList();
		createServerTypeSelectCheckboxList();
		createFormList();
		createViewFormList();		 
	}
	
	private User getCurrentUser(){
		try {
			UserSession userSession = (UserSession) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.AUTH_KEY);
			User user = formController.getUser(userSession.getUsername());
			return user;
		} catch (ControllerException e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
	
	private UserSession getCurrentUserSession(){
			UserSession userSession = (UserSession) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.AUTH_KEY);
			return userSession;	
	}
	
	private void createFormList(){
		try {
			if(formList != null) formList.clear();
			formList = formController.getRequestingForms(getCurrentUser());
			clearRequestData();
		}catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void createViewFormList(){
		try {
			if(viewFormList != null) viewFormList.clear();
			viewFormList = formController.getPerformedFormsForRequester(getCurrentUser());
		}catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	private void createDatabaseSelectItemList(){
		try {
			databaseSelectItemList.clear();
			databaseSelectItemList.add(new SelectItem(Long.valueOf(0),"Select One"));
			
			SelectItem selectItem;
			ArrayList<Database> databaseList = formController.getDatabaseList();
			
			for(Database data : databaseList){
				selectItem = new SelectItem();
				selectItem.setValue(data.getId());
				selectItem.setLabel(data.getName());
				databaseSelectItemList.add(selectItem);
			}
		} catch (ControllerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
		
	private void createSystemSelectItemList(){
		try {
			systemSelectItemList.clear();
			systemSelectItemList.add(new SelectItem(Long.valueOf(0),"Select One"));
			
			SelectItem selectItem;
			ArrayList<Systems> systemList = formController.getSystemList();
			
			for(Systems system : systemList){
				selectItem = new SelectItem();
				selectItem.setValue(system.getId());
				selectItem.setLabel(system.getName());
				systemSelectItemList.add(selectItem);
			}
		} catch (ControllerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}
	
	private void createServerTypeSelectCheckboxList(){
		serverTypeSelectCheckboxList.clear();
		SelectItem selectItem;
		for(ServerType serverType : ServerType.values()){
			selectItem = new SelectItem();
			selectItem.setValue(serverType.getID());
			selectItem.setLabel(serverType.getValue());
			serverTypeSelectCheckboxList.add(selectItem);
		}
	}
	
	public void validateNewForm(){
		if(newForm.getDatabaseTo() == null || newForm.getDatabaseTo().equals(Long.valueOf(0))){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select database(to)");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(newForm.getSystem() == null || newForm.getSystem().equals(Long.valueOf(0))) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select system");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(newServerTypeTo == null || newForm.getServerTypeTo() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select server type(to)");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(newForm.isCreateLogin() == false
				&& newForm.isCreateTable() == false
				&& newForm.isCreateView() == false
				&& newForm.isGrantData() == false
				&& newForm.isModifyTable() == false
				&& newForm.isQueryData() == false
				&& newForm.isDeleteData() == false
				&& newForm.isInsertData() == false
				&& newForm.isUpdateData() == false
				&& newForm.isDropIndex() == false
				&& newForm.isDropTable() == false
				&& newForm.isDropView() == false
				&& newForm.isRevokeData() == false
				&& newForm.isRevokeLogin() == false
				&& newForm.isCreateIndex() == false
				&& newForm.isTransferData() == false
				&& newForm.isBackupData() == false) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select objective");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(newForm.getReason() == null || newForm.getReason().equals("")){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Reason is required");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			saveNewForm();
			RequestContext context = RequestContext.getCurrentInstance();  
			context.execute("formCreateDialog.hide()");
		}
	}
	
	public void validateEditForm(){
		if(editForm.getDatabaseTo() == null || editForm.getDatabaseTo().equals(Long.valueOf(0))){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select database(to)");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(editForm.getSystem() == null || editForm.getSystem().equals(Long.valueOf(0))) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select system");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(editServerTypeTo == null || editForm.getServerTypeTo() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select server type(to)");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(editForm.isCreateLogin() == false
				&& editForm.isCreateTable() == false
				&& editForm.isCreateView() == false
				&& editForm.isGrantData() == false
				&& editForm.isModifyTable() == false
				&& editForm.isQueryData() == false
				&& editForm.isDeleteData() == false
				&& editForm.isInsertData() == false
				&& editForm.isUpdateData() == false
				&& editForm.isDropIndex() == false
				&& editForm.isDropTable() == false
				&& editForm.isDropView() == false
				&& editForm.isRevokeData() == false
				&& editForm.isRevokeLogin() == false
				&& editForm.isCreateIndex() == false
				&& editForm.isTransferData() == false
				&& editForm.isBackupData() == false) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Please select objective");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else if(editForm.getReason() == null || editForm.getReason().equals("")){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed !!","Reason is required");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			saveEditForm();
			RequestContext context = RequestContext.getCurrentInstance();  
			context.execute("formEditDialog.hide()");
		}
	}
		
	public void saveNewForm(){
		try {
			int nextFormNumber = formController.getNextFormNumber();
			
			if(postDateOption.equals("1")){
				newForm.setPostDate(CalendarUtils.getDateTimeInstance().getTime());
			} else {
				newForm.setPostDate(postDate);
			}			
			newForm.setFormNumber(nextFormNumber);
			newForm.setRequester(getCurrentUser());
			Form form = formController.saveForm(newForm);
													
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","Form "+form.getFormattedFormNumber()+" has been created");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        		       
	        try {
	        	List<User> performerUsers = adminController.getPerformerUsers();
	        	if(performerUsers != null){
	        		System.out.println("Performers = "+performerUsers.size());
	        		for(User user : performerUsers){
	        			LDAPConnect connect = new LDAPConnect();
	        			LDAPUser ldapUser = connect.getUserData(user.getUsername());
	        			connect.disconnect();        
	        			System.out.println("LDAPuser = "+ldapUser.getEmployeeCode());
	        			
	        			try {
	        				Growl.sendNotification("New Request !!", "Requester: "+form.getRequester().getFirstname()+" "+form.getRequester().getLastname()+
		    		        		"\n"+"Request Number: "+form.getFormattedFormNumber()+
		    		        		"\n"+"System: "+form.getSystem().getName()+
		    		        		"\n"+"Database Name: "+form.getDatabaseTo().getName()+
		    		        		"\n"+"Server Type: "+form.getServerTypeTo().getValue(),getCurrentUserSession().getStaffCode(), StringUtils.leftPad(ldapUser.getEmployeeCode(), 6, "0"));  
	        			} catch (Exception e) {
							e.printStackTrace();
	        				// TODO: handle exception
						}
	        			
	        			try {
	        				IPMessage.sendMessage("New Request !!"+ 
		    	        			"\n"+"Requester: "+form.getRequester().getFirstname()+" "+form.getRequester().getLastname()+
		    		        		"\n"+"Request Number: "+form.getFormattedFormNumber()+
		    		        		"\n"+"System: "+form.getSystem().getName()+
		    		        		"\n"+"Database Name: "+form.getDatabaseTo().getName()+
		    		        		"\n"+"Server Type: "+form.getServerTypeTo().getValue(), StringUtils.leftPad(ldapUser.getEmployeeCode(), 6, "0"));
	        			} catch (Exception e) {
							e.printStackTrace();
	        				// TODO: handle exception
						}  					
		        	}
	        	}	        	       
	        } catch (Exception e) {
	        	e.printStackTrace();
				// TODO: handle exception
			}
	        
			createFormList();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void saveEditForm(){
		try {
			Form form = formController.saveForm(editForm);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","Form "+form.getFormattedFormNumber()+" has been updated");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        
	        createFormList();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void deleteForm(){
		try {
			deleteForm.setStatus(Status.DELETED);
			Form form = formController.saveForm(deleteForm);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","Form "+form.getFormattedFormNumber()+" has been deleted");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        
	        createFormList();
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void newDatabaseToListSelected(){
		try {
			if(!newDatabaseToID.equals(Long.valueOf(0))){
				Database selectedDatabase = formController.getDatabase(newDatabaseToID);
				newForm.setDatabaseTo(selectedDatabase);
				System.out.println(selectedDatabase.getId());
			}
		} catch (ControllerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void newDatabaseFromListSelected(){
		try {
			if(!newDatabaseFromID.equals(Long.valueOf(0))){
				Database selectedDatabase = formController.getDatabase(newDatabaseFromID);
				newForm.setDatabaseFrom(selectedDatabase);
				newDatabaseToID = selectedDatabase.getId();
				newForm.setDatabaseTo(selectedDatabase);
				System.out.println(selectedDatabase.getId());
			}
		} catch (ControllerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void editDatabaseToListSelected(){
		try {
			if(!editDatabaseToID.equals(Long.valueOf(0))){
				Database selectedDatabase = formController.getDatabase(editDatabaseToID);
				editForm.setDatabaseTo(selectedDatabase);
				System.out.println(selectedDatabase.getId());
			}
		} catch (ControllerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void editDatabaseFromListSelected(){
		try {
			if(!editDatabaseFromID.equals(Long.valueOf(0))){
				Database selectedDatabase = formController.getDatabase(editDatabaseFromID);
				editForm.setDatabaseFrom(selectedDatabase);
				editDatabaseToID = selectedDatabase.getId();
				editForm.setDatabaseTo(selectedDatabase);
				System.out.println(selectedDatabase.getId());
			}
		} catch (ControllerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void newSystemListSelected(){
		try {
			if(!newSystemID.equals(Long.valueOf(0))){
				Systems selectedSystem = formController.getSystem(newSystemID);
				newForm.setSystem(selectedSystem);
				System.out.println(selectedSystem.getId());
			}		
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void editSystemListSelected(){
		try {
			if(!editSystemID.equals(Long.valueOf(0))){
				Systems selectedSystem = formController.getSystem(editSystemID);
				editForm.setSystem(selectedSystem);
				System.out.println(selectedSystem.getId());
			}		
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void newServerTypeFromSelected(){
		if(newServerTypeFrom != null){
			newForm.setServerTypeFrom(ServerType.find(newServerTypeFrom));
		}		
	}
	
	public void newServerTypeToSelected(){
		if(newServerTypeTo != null){
			newForm.setServerTypeTo(ServerType.find(newServerTypeTo));
		}		
	}
	
	public void editServerTypeFromSelected(){
		if(editServerTypeFrom != null){
			editForm.setServerTypeFrom(ServerType.find(editServerTypeFrom));
		}		
	}
	
	public void editServerTypeToSelected(){
		if(editServerTypeTo != null){
			editForm.setServerTypeTo(ServerType.find(editServerTypeTo));
		}		
	}
	
	public void clearRequestData(){
		System.out.println("clear data");
		editForm = new Form();
		newForm = new Form();
		deleteForm = new Form();
		newDatabaseToID = Long.valueOf(0);
		newDatabaseFromID = Long.valueOf(0);
		newSystemID = Long.valueOf(0);
		newServerTypeFrom = null;
		newServerTypeTo = null;
		
		editDatabaseFromID = Long.valueOf(0);
		editDatabaseToID = Long.valueOf(0);
		editSystemID = Long.valueOf(0);
		editServerTypeFrom = null;
		editServerTypeTo = null;
			
		viewDatabaseToID = Long.valueOf(0);
		viewDatabaseFromID = Long.valueOf(0);
		viewSystemID = Long.valueOf(0);
		viewServerTypeFrom = null;
		viewServerTypeTo = null;
		
		postDateOption = "1";
		postDate = CalendarUtils.getDateTimeInstance().getTime();
		isUseCurrentDate = false;
	}
	
	public void setCurrentForm(){
		System.out.println("set value");
		editDatabaseToID = editForm.getDatabaseTo().getId();
		if(editForm.getDatabaseFrom() != null){
			editDatabaseFromID = editForm.getDatabaseFrom().getId();
		}	
		editSystemID = editForm.getSystem().getId();
		if(editForm.getServerTypeFrom() != null){
			editServerTypeFrom = editForm.getServerTypeFrom().getID();
		}
		if(editForm.getServerTypeTo() != null){
			editServerTypeTo = editForm.getServerTypeTo().getID();
		}
	}
	
	public void setCurrentForm2(){
		viewDatabaseToID = viewForm.getDatabaseTo().getId();
		if(viewForm.getDatabaseFrom() != null){
			viewDatabaseFromID = viewForm.getDatabaseFrom().getId();
		}
		viewSystemID = viewForm.getSystem().getId();
		if(viewForm.getServerTypeFrom() != null){
			viewServerTypeFrom = viewForm.getServerTypeFrom().getID();
		}
		if(viewForm.getServerTypeTo() != null){
			viewServerTypeTo = viewForm.getServerTypeTo().getID();
		}
		createViewRequesterDownloadList();
		createViewPerformerDownloadList();
	}
		
//	 public void handleFileUpload(FileUploadEvent event) {
//	    	
//		System.out.println(event.getFile().getFileName());
//		    
//		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
//	    File result = new File(extContext.getRealPath("c:/upload") + "/" + event.getFile().getFileName());
//		File result = new File("c:/upload" + "/" + event.getFile().getFileName());
//	        
//		try {
//			FileOutputStream fileOutputStream = new FileOutputStream(result);
//
//		    byte[] buffer = new byte[Constants.BUFFER_SIZE];
//
//		    int bulk;
//		    InputStream inputStream = event.getFile().getInputstream();
//		    while (true) {
//		      bulk = inputStream.read(buffer);
//		      if (bulk < 0) {
//		             break;
//		             }
//		      fileOutputStream.write(buffer, 0, bulk);
//		      fileOutputStream.flush();
//		      }
//
//		     fileOutputStream.close();
//		     inputStream.close();
//			} catch (IOException e) {
//		            e.printStackTrace();
//			}	
//	 }
	 
	 public void saveFile(FileUploadEvent event){	
//		 File result = null;
		 Upload upload;
		 String filename;
		 InputStream stream;

		 try {	 
			 filename = String.valueOf(downloadedForm.getFormNumber()) + "_" + "requester" + "_" + event.getFile().getFileName();
			 stream = event.getFile().getInputstream();
			
//			 result = new File(Constants.UPLOAD_LOCATION + filename);		
//			 FileOutputStream fileOutputStream = new FileOutputStream(result);
//			 InputStream inputStream = event.getFile().getInputstream();
//			 IOUtils.copy(inputStream, fileOutputStream);
//			 IOUtils.closeQuietly(inputStream);
//			 IOUtils.closeQuietly(fileOutputStream);

			 boolean isSuccess = SVNUtils.uploadFile(stream, filename);
			 IOUtils.closeQuietly(stream);
		 
			 if(isSuccess == true){
				 upload = new Upload();
				 upload.setForm(downloadedForm);
				 upload.setLogicalName(filename);
				 upload.setPhysicalName(event.getFile().getFileName());
				 upload.setContentType(event.getFile().getContentType());
				 upload.setUploader(Role.REQUESTER);
				 formController.saveUpload(upload);
			 }
			 
			 createRequesterDownloadList();
		 }  catch (ControllerException e) {
				e.printStackTrace();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	  
	 public void createRequesterDownloadList(){
		 downloadList.clear();
		 Download download;
		 InputStream stream;
		 try {
			 ArrayList<Upload> uploadedFiles = formController.getRequesterUploadedFiles(downloadedForm);
			 
			 for(Upload upload : uploadedFiles){
				 download = new Download();
				 download.setForm(upload.getForm());
				 download.setLogicName(upload.getLogicalName());
				 download.setPhysicalName(upload.getPhysicalName());
				 
//				 stream = new FileInputStream(Constants.UPLOAD_LOCATION+upload.getLogicalName());
				 stream = SVNUtils.downloadFile(upload.getLogicalName());
				 download.setFile(new DefaultStreamedContent(stream, upload.getContentType(), upload.getPhysicalName()));
				 downloadList.add(download);
				 IOUtils.closeQuietly(stream);
			 }
			 
		 } catch (ControllerException e) {
			e.printStackTrace();
		 } catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	 }
	 
	 public void deleteFile(){
		 try {
//			 File file = new File(Constants.UPLOAD_LOCATION+deletedFile.getLogicName());
//			 file.delete();
			 SVNUtils.deleteFile(deletedFile.getLogicName());
			 
			 formController.deleteFile(deletedFile);
			 
			 createRequesterDownloadList();
			 
			 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","File "+deletedFile.getPhysicalName()+" has been deleted");  	          
		     FacesContext.getCurrentInstance().addMessage(null, message);
		 } catch (ControllerException e) {
			e.printStackTrace();
			 // TODO: handle exception
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void createViewRequesterDownloadList(){
		 viewRequesterDownloadList.clear();
		 Download download;
		 InputStream stream;
		 try {
			 ArrayList<Upload> uploadedFiles = formController.getRequesterUploadedFiles(viewForm);
			 
			 for(Upload upload : uploadedFiles){
				 download = new Download();
				 download.setForm(upload.getForm());
				 download.setLogicName(upload.getLogicalName());
				 download.setPhysicalName(upload.getPhysicalName());
				 
				 stream = SVNUtils.downloadFile(upload.getLogicalName());
//				 stream = new FileInputStream(Constants.UPLOAD_LOCATION+upload.getLogicalName());
				 download.setFile(new DefaultStreamedContent(stream, upload.getContentType(), upload.getPhysicalName()));
				 viewRequesterDownloadList.add(download);
				 IOUtils.closeQuietly(stream);
			 }
			 
		 } catch (ControllerException e) {
			e.printStackTrace();
		 } catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void createViewPerformerDownloadList(){
		 viewPerformerDownloadList.clear();
		 Download download;
		 InputStream stream;
		 try {
			 ArrayList<Upload> uploadedFiles = formController.getPerformerUploadedFiles(viewForm);			 
			 for(Upload upload : uploadedFiles){
				 download = new Download();
				 download.setForm(upload.getForm());
				 download.setLogicName(upload.getLogicalName());
				 download.setPhysicalName(upload.getPhysicalName());
				 
				 stream = SVNUtils.downloadFile(upload.getLogicalName());
//				 stream = new FileInputStream(Constants.UPLOAD_LOCATION+upload.getLogicalName());
				 download.setFile(new DefaultStreamedContent(stream, upload.getContentType(), upload.getPhysicalName()));
				 viewPerformerDownloadList.add(download);
				 IOUtils.closeQuietly(stream);
			 }
			 
		 } catch (ControllerException e) {
			e.printStackTrace();
		 } catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void createAllList(){
			createDatabaseSelectItemList();
			createSystemSelectItemList();
			createServerTypeSelectCheckboxList();
			createFormList();
			createViewFormList();
			
//			return "request?faces-redirect=true";
	 }
	 
	 public void setCalendarFlag(){
		 if(postDateOption.equals("1")){
			 isUseCurrentDate = true;
			 postDate = CalendarUtils.getDateTimeInstance().getTime();
		 } else {
			 isUseCurrentDate = false;
		 }
	 }
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
