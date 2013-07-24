package mrf.customtype;

public enum ServerType {
	DEVELOPMENT("Development","Development"),
	QUALITY_ASSURANCE("QualityAssurance","Quality Assurance"),
	PRODUCTION("Production","Production");
	
	private String id;
	private String value;
	
	ServerType(String aID, String aValue){
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
	
	public static ServerType find(String aID){
		for (ServerType type : ServerType.values()) {
			if (type.id.equals(aID)){
				return type;
			}
		}
		return null;
	}
}
