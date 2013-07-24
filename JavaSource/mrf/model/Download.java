package mrf.model;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

public class Download {
	private StreamedContent file = null;
	private Form form = null;
	private String physicalName = null;
	private String logicName = null;
	

	public StreamedContent getFile() {
		return file;
	}
	public void setFile(StreamedContent file) {
		this.file = file;
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
	public String getLogicName() {
		return logicName;
	}
	public void setLogicName(String logicName) {
		this.logicName = logicName;
	}
}
