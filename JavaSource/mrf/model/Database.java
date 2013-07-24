package mrf.model;

import java.util.ArrayList;
import java.util.List;

import mrf.customtype.DatabaseType;
import mrf.customtype.Status;

public class Database {
	private Long id = null;
	private String name = null;
	private String description = null;
	private Status status = Status.NORMAL;
	private DatabaseType databaseType = null;
	private List<Form> forms = new ArrayList<Form>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Form> getForms() {
		return forms;
	}
	public void setForms(List<Form> forms) {
		this.forms = forms;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public DatabaseType getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(DatabaseType databaseType) {
		this.databaseType = databaseType;
	}
}
