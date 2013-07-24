package mrf.model;

import mrf.customtype.Role;

public class Upload {
	private Long id = null;
	private Form form = null;
	private String physicalName = null;
	private String logicalName = null;
	private String contentType = null;
	private Role uploader = Role.REQUESTER;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	public String getPhysicalName() {
		return physicalName;
	}
	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}
	public String getLogicalName() {
		return logicalName;
	}
	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Role getUploader() {
		return uploader;
	}
	public void setUploader(Role uploader) {
		this.uploader = uploader;
	}
}
