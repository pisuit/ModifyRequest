package mrf.exeption;

public class ControllerException extends Exception {
	public ControllerException(Exception e) {
		super(e);
	}
	
	public ControllerException(String msg) {
		super(msg);
	}
}
