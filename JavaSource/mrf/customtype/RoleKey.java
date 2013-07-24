package mrf.customtype;

public enum RoleKey {
	MAIN_ROLE("MAIN_ROLE","MainRole");
	
	private String id;
	private String value;
	
	RoleKey(String aID, String aValue){
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
	
	public static RoleKey find(String aID){
		for (RoleKey role : RoleKey.values()) {
			if (role.id.equals(aID)){
				return role;
			}
		}
		return null;
	}
}
