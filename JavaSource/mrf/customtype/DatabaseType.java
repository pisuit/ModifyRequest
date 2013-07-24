package mrf.customtype;

public enum DatabaseType {
	SyBase("SyBase","SyBase"),
	Oracle("Oracle","Oracle");
	
	private String id;
	private String value;
	
	DatabaseType(String aID, String aValue){
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
	
	public static DatabaseType find(String aID){
		for (DatabaseType type : DatabaseType.values()) {
			if (type.id.equals(aID)){
				return type;
			}
		}
		return null;
	}
}
