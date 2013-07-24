package mrf.model;

import java.util.ArrayList;
import java.util.List;

import mrf.customtype.Status;

public class User {
	private Long id = null;
	private String username = null;
	private String firstname = null;
	private String lastname = null;
	private String position = null;
	private Status status = Status.NORMAL;
	private List<Authorization> authorizations = new ArrayList<Authorization>();
	private List<Form> requester = new ArrayList<Form>();
	private List<Form> performer = new ArrayList<Form>();
	private List<Form> approver = new ArrayList<Form>();
	private String roleAsString = null;
	
	public List<Form> getPerformer() {
		return performer;
	}
	public void setPerformer(List<Form> performer) {
		this.performer = performer;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<Authorization> getAuthorizations() {
		return authorizations;
	}
	public void setAuthorizations(List<Authorization> authorizations) {
		this.authorizations = authorizations;
	}
	public List<Form> getRequester() {
		return requester;
	}
	public void setRequester(List<Form> requester) {
		this.requester = requester;
	}
	public List<Form> getApprover() {
		return approver;
	}
	public void setApprover(List<Form> approver) {
		this.approver = approver;
	}
	public ArrayList<String> getAuthorizationsAsStringList() {
		ArrayList<String> list = new ArrayList<String>();
		for (Authorization authorization : authorizations) {
			list.add(authorization.getRole().getID());
		}
		return list;
	}
	public String getRoleAsString() {
		return roleAsString;
	}
	public void setRoleAsString(String roleAsString) {
		this.roleAsString = roleAsString;
	}
}
