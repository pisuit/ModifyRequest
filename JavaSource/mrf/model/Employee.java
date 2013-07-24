package mrf.model;

public class Employee {
	
	private Long employeeID;
	private String employeeCode;
	private String engFirstName;
	private String engLastName;
	private String status;
	private String thaiFirstName;
	private String thaiLastName;
	
	public Long getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Long employeeID) {
		this.employeeID = employeeID;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEngFirstName() {
		return engFirstName;
	}
	public void setEngFirstName(String engFirstName) {
		this.engFirstName = engFirstName;
	}
	public String getEngLastName() {
		return engLastName;
	}
	public void setEngLastName(String engLastName) {
		this.engLastName = engLastName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getThaiFirstName() {
		return thaiFirstName;
	}
	public void setThaiFirstName(String thaiFirstName) {
		this.thaiFirstName = thaiFirstName;
	}
	public String getThaiLastName() {
		return thaiLastName;
	}
	public void setThaiLastName(String thaiLastName) {
		this.thaiLastName = thaiLastName;
	}
}
