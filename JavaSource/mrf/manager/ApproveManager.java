package mrf.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.tmatesoft.svn.core.SVNException;

import mrf.controller.FormController;
import mrf.customtype.ProcessStatus;
import mrf.customtype.ServerType;
import mrf.exeption.ControllerException;
import mrf.model.Database;
import mrf.model.Download;
import mrf.model.Form;
import mrf.model.Systems;
import mrf.model.Upload;
import mrf.model.User;
import mrf.session.UserSession;
import mrf.utils.CalendarUtils;
import mrf.utils.Constants;
import mrf.utils.SVNUtils;

@ManagedBean(name="approveManager")
@SessionScoped
public class ApproveManager {
	private ArrayList<SelectItem> databaseSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> systemSelectItemList = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> serverTypeSelectCheckboxList = new ArrayList<SelectItem>();
	private FormController formController = new FormController();

	private Long viewDatabaseFromID = Long.valueOf(0);
	private Long viewDatabaseToID = Long.valueOf(0);
	private Long viewSystemID = Long.valueOf(0);
	private String viewServerTypeFrom = null;
	private String viewServerTypeTo = null;
	
	private ArrayList<Form> viewApprovedFormList = new ArrayList<Form>();
	private ArrayList<Form>	viewPerformedFormList = new ArrayList<Form>();
	private Form viewForm = new Form();
	private Form editForm = new Form();
	private Form editForm2 = new Form();
	
	private ArrayList<Download> viewRequesterDownloadList = new ArrayList<Download>();
	private ArrayList<Download> viewRequesterDownloadList2 = new ArrayList<Download>();
	private ArrayList<Download> viewPerformerDownloadList = new ArrayList<Download>();
	private ArrayList<Download> viewPerformerDownloadList2 = new ArrayList<Download>();
	
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
	public ArrayList<Download> getViewRequesterDownloadList() {
		return viewRequesterDownloadList;
	}
	public void setViewRequesterDownloadList(
			ArrayList<Download> viewRequesterDownloadList) {
		this.viewRequesterDownloadList = viewRequesterDownloadList;
	}
	public ArrayList<Download> getViewRequesterDownloadList2() {
		return viewRequesterDownloadList2;
	}
	public void setViewRequesterDownloadList2(
			ArrayList<Download> viewRequesterDownloadList2) {
		this.viewRequesterDownloadList2 = viewRequesterDownloadList2;
	}
	public ArrayList<Download> getViewPerformerDownloadList() {
		return viewPerformerDownloadList;
	}
	public void setViewPerformerDownloadList(
			ArrayList<Download> viewPerformerDownloadList) {
		this.viewPerformerDownloadList = viewPerformerDownloadList;
	}
	public ArrayList<Download> getViewPerformerDownloadList2() {
		return viewPerformerDownloadList2;
	}
	public void setViewPerformerDownloadList2(
			ArrayList<Download> viewPerformerDownloadList2) {
		this.viewPerformerDownloadList2 = viewPerformerDownloadList2;
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
	public ArrayList<Form> getViewApprovedFormList() {
		return viewApprovedFormList;
	}
	public void setViewApprovedFormList(ArrayList<Form> viewApprovedFormList) {
		this.viewApprovedFormList = viewApprovedFormList;
	}
	public ArrayList<Form> getViewPerformedFormList() {
		return viewPerformedFormList;
	}
	public void setViewPerformedFormList(ArrayList<Form> viewPerformedFormList) {
		this.viewPerformedFormList = viewPerformedFormList;
	}
	public Form getViewForm() {
		return viewForm;
	}
	public void setViewForm(Form viewForm) {
		this.viewForm = viewForm;
	}
	
	public Form getEditForm() {
		return editForm;
	}
	public void setEditForm(Form editForm) {
		this.editForm = editForm;
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
	public Form getEditForm2() {
		return editForm2;
	}
	public void setEditForm2(Form editForm2) {
		this.editForm2 = editForm2;
	}
	public ApproveManager(){
		createApprovedFormList();
		createPerformedFormList();
		createDatabaseSelectItemList();
		createServerTypeSelectCheckboxList();
		createSystemSelectItemList();
	}
	
	public void createPerformedFormList(){
		try {
			if(viewPerformedFormList != null) viewPerformedFormList.clear();
			viewPerformedFormList = formController.getAllPerformedForms();
			clearData();
		}catch (ControllerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
	public void createApprovedFormList(){
		try {
			if(viewApprovedFormList != null) viewApprovedFormList.clear();
			viewApprovedFormList = formController.getAllApprovedForms();
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
	
	public void saveEditForm(){
		try {			
			editForm.setApproveDate(CalendarUtils.getDateTimeInstance().getTime());
			editForm.setProcessStatus(ProcessStatus.APPROVED);
			editForm.setApprover(getCurrentUser());
			Form form =formController.saveForm(editForm);
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful !!","You have already added the remarks to form number "+form.getFormattedFormNumber());  	          
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        
	        createApprovedFormList();
	        createPerformedFormList();
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
		if(viewForm.getDatabaseFrom() != null){
			viewDatabaseFromID = viewForm.getDatabaseFrom().getId();
		}	
		viewDatabaseToID = viewForm.getDatabaseTo().getId();
		viewSystemID = viewForm.getSystem().getId();
		if(viewForm.getServerTypeFrom() != null){
			viewServerTypeFrom = viewForm.getServerTypeFrom().getID();
		}
		if(viewForm.getServerTypeTo() != null){
			viewServerTypeTo = viewForm.getServerTypeTo().getID();
		}		
		createViewPerformerDownloadList();
		createViewPerformerDownloadList2();
		createViewRequesterDownloadList();
		createViewRequesterDownloadList2();
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
		} /*catch (FileNotFoundException e) {
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
	 
	 public void createViewRequesterDownloadList2(){
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
	 
	 public void createViewPerformerDownloadList2(){
		 viewPerformerDownloadList2.clear();
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
				 viewPerformerDownloadList2.add(download);
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
		createApprovedFormList();
		createPerformedFormList();
		createDatabaseSelectItemList();
		createServerTypeSelectCheckboxList();
		createSystemSelectItemList();
		
//		return "approve?faces-redirect=true";
	 }
}
