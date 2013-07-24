package mrf.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import mrf.controller.FormController;
import mrf.customtype.ProcessStatus;
import mrf.customtype.Role;
import mrf.customtype.ServerType;
import mrf.exeption.ControllerException;
import mrf.ldap.LDAPConnect;
import mrf.ldap.LDAPUser;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.tmatesoft.svn.core.SVNException;

@ManagedBean(name="performManager")
@SessionScoped
public class PerformManager {
	private ArrayList<Form> formList = new ArrayList<Form>();
	private FormController formController = new FormController();
	private Form editForm = new Form();
	private Form editForm2 = new Form();
	private Form viewForm = new Form();
	private ArrayList<Form> viewFormList = new ArrayList<Form>();
	private ArrayList<SelectItem> databaseSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> systemSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> serverTypeSelectCheckboxList = new ArrayList<SelectItem>();
		
	private Long viewDatabaseToID = Long.valueOf(0);
	private Long viewDatabaseFromID = Long.valueOf(0);
	private Long viewSystemID = Long.valueOf(0);
	private String viewServerTypeFrom = null;
	private String viewServerTypeTo = null;
	
	private ArrayList<Download> viewRequesterDownloadList = new ArrayList<Download>();
	private ArrayList<Download> viewPerformerDownloadList = new ArrayList<Download>();
	private ArrayList<Download> viewRequesterDownloadList2 = new ArrayList<Download>();
	private ArrayList<Download> downloadPerformerList = new ArrayList<Download>();
	private Form downloadedForm = new Form();
	private Download deletedFile = new Download();
		
	public String getViewServerTypeFrom() {
		return viewServerTypeFrom;
	}
	public void setViewServerTypeFrom(String viewServerTypeFrom) {
		this.viewServerTypeFrom = viewServerTypeFrom;
	}
	public String getViewServerTypeTo() {
		return viewServerTypeTo;
	}
	public void setViewServerTypeTo(String viewServerTypeTo) {
		this.viewServerTypeTo = viewServerTypeTo;
	}
	public ArrayList<Download> getViewPerformerDownloadList() {
		return viewPerformerDownloadList;
	}
	public void setViewPerformerDownloadList(
			ArrayList<Download> viewPerformerDownloadList) {
		this.viewPerformerDownloadList = viewPerformerDownloadList;
	}
	public Long getViewDatabaseToID() {
		return viewDatabaseToID;
	}
	public void setViewDatabaseToID(Long viewDatabaseToID) {
		this.viewDatabaseToID = viewDatabaseToID;
	}
	public Long getViewDatabaseFromID() {
		return viewDatabaseFromID;
	}
	public void setViewDatabaseFromID(Long viewDatabaseFromID) {
		this.viewDatabaseFromID = viewDatabaseFromID;
	}
	public ArrayList<Download> getViewRequesterDownloadList2() {
		return viewRequesterDownloadList2;
	}
	public void setViewRequesterDownloadList2(
			ArrayList<Download> viewRequesterDownloadList2) {
		this.viewRequesterDownloadList2 = viewRequesterDownloadList2;
	}
	public ArrayList<Download> getViewRequesterDownloadList() {
		return viewRequesterDownloadList;
	}
	public void setViewRequesterDownloadList(
			ArrayList<Download> viewRequesterDownloadList) {
		this.viewRequesterDownloadList = viewRequesterDownloadList;
	}
	public Form getDownloadedForm() {
		return downloadedForm;
	}
	public void setDownloadedForm(Form downloadedForm) {
		this.downloadedForm = downloadedForm;
	}
	public ArrayList<Download> getDownloadPerformerList() {
		return downloadPerformerList;
	}
	public void setDownloadPerformerList(ArrayList<Download> downloadPerformerList) {
		this.downloadPerformerList = downloadPerformerList;
	}
	public Download getDeletedFile() {
		return deletedFile;
	}
	public void setDeletedFile(Download deletedFile) {
		this.deletedFile = deletedFile;
	}
	public Form getViewForm() {
		return viewForm;
	}
	public void setViewForm(Form viewForm) {
		this.viewForm = viewForm;
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
	public ArrayList<SelectItem> getServerTypeSelectCheckboxList() {
		return serverTypeSelectCheckboxList;
	}
	public void setServerTypeSelectCheckboxList(
			ArrayList<SelectItem> serverTypeSelectCheckboxList) {
		this.serverTypeSelectCheckboxList = serverTypeSelectCheckboxList;
	}
	public Long getViewSystemID() {
		return viewSystemID;
	}
	public void setViewSystemID(Long viewSystemID) {
		this.viewSystemID = viewSystemID;
	}
	public ArrayList<Form> getViewFormList() {
		return viewFormList;
	}
	public void setViewFormList(ArrayList<Form> viewFormList) {
		this.viewFormList = viewFormList;
	}
	public Form getEditForm2() {
		return editForm2;
	}
	public void setEditForm2(Form editForm2) {
		this.editForm2 = editForm2;
	}
	public ArrayList<Form> getFormList() {
		return formList;
	}
	public void setFormList(ArrayList<Form> formList) {
		this.formList = formList;
	}
	public Form getEditForm() {
		return editForm;
	}
	public void setEditForm(Form editForm) {
		this.editForm = editForm;
	}
	
	public PerformManager(){
		createFormList();
		createPerformedFormList();
		createDatabaseSelectItemList();
		createSystemSelectItemList();
		createServerTypeSelectCheckboxList();
	}
	
	public void createFormList(){
		try {
			if(formList != null) formList.clear();
			formList = formController.getAllRequestingForms();
			clearData();
		}catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void createPerformedFormList(){
		try {
			if(viewFormList != null) viewFormList.clear();
			viewFormList = formController.getAllPerformedForms();
		} catch (ControllerException e) {
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
	
	public void saveEditForm(){
		try {			
			editForm.setPerformDate(CalendarUtils.getDateTimeInstance().getTime());
			editForm.setProcessStatus(ProcessStatus.PERFORMED);
			editForm.setPerformer(getCurrentUser());
			Form form =formController.saveForm(editForm);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","You have already added the remarks to form number "+form.getFormattedFormNumber());  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        
	        createFormList();
	        createPerformedFormList();
	        
	        LDAPConnect connect = new LDAPConnect();
			LDAPUser ldapUser = connect.getUserData(form.getRequester().getUsername());
			connect.disconnect();
			
	       try {	    	  
	    	   Growl.sendNotification("Your request is done !!", "Performer: "+form.getPerformer().getFirstname()+" "+form.getPerformer().getLastname()+
		        		"\n"+"Request Number: "+form.getFormattedFormNumber()+
		        		"\n"+"System: "+form.getSystem().getName()+
		        		"\n"+"Database Name: "+form.getDatabaseTo().getName()+
		        		"\n"+"Server Type: "+form.getServerTypeTo().getValue(),getCurrentUserSession().getStaffCode(), StringUtils.leftPad(ldapUser.getEmployeeCode(), 6, "0"));
	       } catch (Exception e) {
	    	   e.printStackTrace();
			// TODO: handle exception
	       }
			
	       try {
	    	   IPMessage.sendMessage("Your request is done !!"+ 
	        			"\n"+"Performer: "+form.getPerformer().getFirstname()+" "+form.getPerformer().getLastname()+
		        		"\n"+"Request Number: "+form.getFormattedFormNumber()+
		        		"\n"+"System: "+form.getSystem().getName()+
		        		"\n"+"Database Name: "+form.getDatabaseTo().getName()+
		        		"\n"+"Server Type: "+form.getServerTypeTo().getValue(), StringUtils.leftPad(ldapUser.getEmployeeCode(), 6, "0"));
	       } catch (Exception e) {
	    	   e.printStackTrace();
			// TODO: handle exception
		}
	        
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void saveEditForm2(){
		try {
			Form form = formController.saveForm(editForm2);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","The remarks of form number "+form.getFormattedFormNumber()+" has been updated");  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);        
		} catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	private void clearData(){
		editForm = new Form();
		editForm2 = new Form();
	}
	
	public void setCurrentForm(){
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
		createRequesterDownloadList();
		createViewPerformerDownloadList();
		createViewRequesterDownloadList();
	}
	
	public void createRequesterDownloadList(){
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
		} /*catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void createPerformerDownloadList(){
		 downloadPerformerList.clear();
		 Download download;
		 InputStream stream;
		 try {
			 ArrayList<Upload> uploadedFiles = formController.getPerformerUploadedFiles(downloadedForm);
			 for(Upload upload : uploadedFiles){
				 download = new Download();
				 download.setForm(upload.getForm());
				 download.setLogicName(upload.getLogicalName());
				 download.setPhysicalName(upload.getPhysicalName());
				 
				 stream = SVNUtils.downloadFile(upload.getLogicalName());
//				 stream = new FileInputStream(Constants.UPLOAD_LOCATION+upload.getLogicalName());
				 download.setFile(new DefaultStreamedContent(stream, upload.getContentType(), upload.getPhysicalName()));
				 downloadPerformerList.add(download);
				 IOUtils.closeQuietly(stream);
			 }
			 
		 } catch (ControllerException e) {
			e.printStackTrace();
	     } catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (FileNotFoundException e) {
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
			 
			 createPerformerDownloadList();
			 
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
	 
	 public void saveFile(FileUploadEvent event){	
		 Upload upload;	
		 String filename;
		 File result;
		 InputStream stream;
		 try {
			  
			 filename = String.valueOf(downloadedForm.getFormNumber()) + "_" + "performer" + "_" + event.getFile().getFileName();
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
				 upload.setUploader(Role.PERFORMER);
				 formController.saveUpload(upload);
			 }
			 
			 createPerformerDownloadList();
		 } catch (IOException e) {
				e.printStackTrace();
		 }  catch (ControllerException e) {
				e.printStackTrace();
		 }
	 }
	 
	 public void createViewRequesterDownloadList(){
		 viewRequesterDownloadList2.clear();
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
				 viewRequesterDownloadList2.add(download);
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
		} /*catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	 }
	
	 public void createAllList(){
		 createFormList();
		createPerformedFormList();
		createDatabaseSelectItemList();
		createSystemSelectItemList();
		createServerTypeSelectCheckboxList();
		
//		return "perform?faces-redirect=true";
	 }	 
}
