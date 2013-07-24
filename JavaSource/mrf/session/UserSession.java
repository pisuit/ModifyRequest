package mrf.session;

import java.util.ArrayList;
import java.util.List;

import mrf.model.Authorization;

public class UserSession {
	private String username = null;
	private String password = null;
	private String firstname = null;
	private String lastname = null;
	private String staffCode = null;
	private List<Authorization> authorizations = new ArrayList<Authorization>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Authorization> getAuthorizations() {
		return authorizations;
	}
	public void setAuthorizations(List<Authorization> authorizations) {
		this.authorizations = authorizations;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
}
