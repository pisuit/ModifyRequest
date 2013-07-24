package mrf.utils;

import java.text.DecimalFormat;

public class Constants {
	public static final String SERVER_TYPE_DOCUMENT = "SERVER_TYPE_DOCUMENT";
	public static final String SERVER_TYPE_DEVELOPMENT = "SERVER_TYPE_DEVELOPMENT";
	public static final String SERVER_TYPE_QUALITY_ASSURANCE = "SERVER_TYPE_QUALITY_ASSURANCE";
	public static final String SERVER_TYPE_PRODUCTION = "SERVER_TYPE_PRODUCTION";
	
	public static final String MAIN_ROLE = "MAIN_ROLE";
	
	public static final String ROLE_REQUESTER = "ROLE_REQUESTER";
	public static final String ROLE_ACTOR = "ROLE_ACTOR";
	public static final String ROLE_APPROVER = "ROLE_APPROVER";
	
	public static final DecimalFormat NUMBER_FORMATTER = new DecimalFormat("0000");
	
	public static final String AUTH_KEY = "user_key";
	
	public static final int BUFFER_SIZE = 6124;
	
//	public static final String UPLOAD_LOCATION = "/opt/jboss/files/maintenance";
	public static final String UPLOAD_LOCATION = "c:/upload/";
}
