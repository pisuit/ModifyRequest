package mrf.manager;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mrf.controller.LoginController;
import mrf.customtype.Role;
import mrf.exeption.ControllerException;
import mrf.model.Authorization;
import mrf.model.User;
import mrf.session.UserSession;
import mrf.utils.Constants;
import mrf.utils.FacesUtils;

@ManagedBean(name="menuManager")
@RequestScoped
public class MenuManager {
	private boolean isRequester = false;
	private boolean isPerformer = false;
	private boolean isApprover = false;
	private boolean isAdmin = false;

	public MenuManager(){
		getRoleFromUserSession();
	}
	
	private void getRoleFromUserSession(){	
			UserSession userSession = (UserSession) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.AUTH_KEY);
			if(userSession != null) {
				for(Authorization auth : userSession.getAuthorizations()){
					if(auth.getRole().getID().equals(Role.REQUESTER.getID()))
						isRequester = true;
					if(auth.getRole().getID().equals(Role.PERFORMER.getID()))
						isPerformer = true;
					if(auth.getRole().getID().equals(Role.APPROVER.getID()))
						isApprover = true;
					if(auth.getRole().getID().equals(Role.ADMIN.getID()))
						isAdmin = true;
				}
			}
	}
	
	public String requestPage(){
		RequestManager requestManager = (RequestManager) FacesUtils.getSessionObject("requestManager", RequestManager.class);
		if(requestManager != null){
			requestManager.createAllList();
		}
		return "request?faces-redirect=true";
	}
	
	public String performPage(){
		PerformManager performManager = (PerformManager) FacesUtils.getSessionObject("performManager", PerformManager.class);
		if(performManager != null){
			performManager.createAllList();
		}
		return "perform?faces-redirect=true";
	}
	
	public String approvePage(){
		ApproveManager approveManager = (ApproveManager) FacesUtils.getSessionObject("approveManager", ApproveManager.class);
		if(approveManager != null){
			approveManager.createAllList();
		}
		return "approve?faces-redirect=true";
	}
	
	public String adminPage(){
		AdminManager adminManager = (AdminManager) FacesUtils.getSessionObject("adminManager", AdminManager.class);
		if(adminManager != null){
			adminManager.createAllList();
		}
		return "admin?faces-redirect=true";
	}
	
	public boolean isLoggedIn(){
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.AUTH_KEY) != null;
	}

	public boolean isRequester(){
		return isRequester;
	}
	public boolean isPerformer(){
		return isPerformer;
	}
	public boolean isApprover(){
		return isApprover;
	}
	public boolean isAdmin(){
		return isAdmin;
	}
}
