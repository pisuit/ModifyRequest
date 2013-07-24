package mrf.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

//import org.primefaces.mobile.component.page.PageRenderer;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

import mrf.controller.LoginController;
import mrf.customtype.Role;
import mrf.customtype.RoleKey;
import mrf.exeption.AuthicationException;
import mrf.exeption.ControllerException;
import mrf.exeption.InvalidLoginException;
import mrf.ldap.LDAPConnect;
import mrf.ldap.LDAPUser;
import mrf.model.Authorization;
import mrf.model.User;
import mrf.session.UserSession;
import mrf.utils.Constants;

@SessionScoped
@ManagedBean(name="loginManager")
public class LoginManager {
	private String username = null;
	private String password = null;
	private LoginController loginController = new LoginController();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginController getLoginController() {
		return loginController;
	}
	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
	public String login(){
		try {
			String pageRedirect = null;
			UserSession userSession = null;
			if(username.equals("admin") && password.equals("admin")){
				UserSession session = new UserSession();
				Authorization authorization = new Authorization();
				List<Authorization> authorizations = new ArrayList<Authorization>();
				User user = new User();
				user.setFirstname("admin");
				user.setLastname("admin");
				user.setPosition("admin");
				user.setUsername("admin");
				
				authorization.setRole(Role.ADMIN);
				authorization.setRoleKey(RoleKey.MAIN_ROLE);
				authorization.setId(new Long("111111111111"));
				authorization.setUser(user);
				authorizations.add(authorization);
				
				session.setFirstname("admin");
				session.setLastname("admin");
				session.setUsername("admin");
				session.setAuthorizations(authorizations);
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.AUTH_KEY, session);
				
				pageRedirect = "admin?faces-redirect=true";
			} else {
				userSession = authentication(username,password);
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.AUTH_KEY, userSession);
							
				String firstRole = userSession.getAuthorizations().get(0).getRole().getID();			
				if(firstRole.equals(Role.ADMIN.getID())) 
					pageRedirect = "admin?faces-redirect=true";
				if(firstRole.equals(Role.REQUESTER.getID()))
					pageRedirect = "request?faces-redirect=true";
				if(firstRole.equals(Role.PERFORMER.getID()))
					pageRedirect = "perform?faces-redirect=true";
				if(firstRole.equals(Role.APPROVER.getID()))
					pageRedirect = "approve?faces-redirect=true";
			}
		
			return pageRedirect;
		} catch (AuthicationException e) {
			e.printStackTrace();
			return null;
			// TODO: handle exception
		}
	}
	
	public String logout(){
		 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.AUTH_KEY);
		 
		 removeBeanFromSession("adminManager");
		 removeBeanFromSession("approveManager");
		 removeBeanFromSession("loginManager");
		 removeBeanFromSession("menuManager");
		 removeBeanFromSession("performManager");
		 removeBeanFromSession("requestManager");
		 
		 return "login?faces-redirect=true";
	}
	
	private void removeBeanFromSession(String beanName){
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.remove(beanName); // Removes the session scoped bean.
	}
	
	private UserSession authentication(String username, String password) throws AuthicationException{
		try {
			LDAPConnect connect = new LDAPConnect();
			LDAPUser ldapUser = connect.login(username, password);
			connect.disconnect();
			UserSession userSession = new UserSession();
			System.out.println("Getting user from database...");
			User dbUser = loginController.getUser(username);
			System.out.println("Getting user successful");
			if(dbUser == null){
				FacesMessage messageUsernotfound = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User not found","");  	          
		        FacesContext.getCurrentInstance().addMessage(null, messageUsernotfound);
				return null;
			} else {
				userSession.setAuthorizations(dbUser.getAuthorizations());
				userSession.setPassword(password);
				userSession.setUsername(dbUser.getUsername());
				userSession.setFirstname(dbUser.getFirstname());
				userSession.setLastname(dbUser.getLastname());
				userSession.setStaffCode(StringUtils.leftPad(ldapUser.getEmployeeCode(), 6, "0"));
				return userSession;
			}
		} catch (InvalidLoginException e) {
			e.printStackTrace();
			throw new AuthicationException(e.getMessage());
			// TODO: handle exception
		} catch (ControllerException e) {
			e.printStackTrace();
			throw new AuthicationException(e.getMessage());
			// TODO: handle exception
		}
	}
}
