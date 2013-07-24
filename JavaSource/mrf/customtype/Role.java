package mrf.customtype;

public enum Role {
	REQUESTER("Requester","Requester"),
	PERFORMER("Performer","Performer"),
	APPROVER("Approver","Approver"),
	ADMIN("Admin","Admin");
	
	private String id;
	private String value;
	
	Role(String aID, String aValue){
		id = aID;
		value = aValue;
	}
	
	public String getID(){
		return id;
	}
	
	public String getValue(){
		return value;
	}

	public String toString() {
		return value;
	}
	
	public static Role find(String aID){
		for (Role role : Role.values()) {
			if (role.id.equals(aID)){
				return role;
			}
		}
		return null;
	}
}
