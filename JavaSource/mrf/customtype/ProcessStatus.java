package mrf.customtype;

public enum ProcessStatus {	
	REQUESTING("Requesting","Requesting"),
	PERFORMED("Performed","Performed"),
	APPROVED("Approved","Approved");
	
	private String id;
	private String value;
	
	ProcessStatus(String aID, String aValue){
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
	
	public static ProcessStatus find(String aID){
		for (ProcessStatus status : ProcessStatus.values()) {
			if (status.id.equals(aID)){
				return status;
			}
		}
		return null;
	}
}
