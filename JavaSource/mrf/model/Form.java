package mrf.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mrf.customtype.ProcessStatus;
import mrf.customtype.ServerType;
import mrf.customtype.Status;
import mrf.utils.Constants;

public class Form {
	private Long id = null;
	private String referForm = null;
	private Database databaseFrom = null;
	private Database databaseTo = null;
	private Systems system = null;
	private Date postDate = null;
	private ServerType serverTypeFrom = null;
	private ServerType serverTypeTo = null;
	private int formNumber = 0;
	
	private boolean isDeleteData = false;
	private boolean isInsertData = false;
	private boolean isUpdateData = false;
	private boolean isQueryData = false;
	private boolean isTransferData = false;
	private boolean isBackupData = false;
	
	private boolean isCreateTable = false;
	private boolean isCreateView = false;
	private boolean isCreateIndex = false;
	private boolean isModifyTable = false;
	private boolean isDropTable = false;
	private boolean isDropIndex = false;
	private boolean isDropView = false;
	
	private boolean isCreateLogin = false;
	private boolean isGrantData = false;
	private boolean isRevokeLogin = false;
	private boolean isRevokeData = false;
	
	private String reason = null;
	private Date performDate = null;
	private Date startPerform = null;
	private Date endPerform = null;
	private String performRemark = null;
	private String approveRemark = null;
	private User requester = null;
	private User performer = null;
	private User approver = null;
	private Date approveDate = null;
	private Status status = Status.NORMAL;
	private ProcessStatus processStatus = ProcessStatus.REQUESTING;
	private List<Upload> fileUpload = new ArrayList<Upload>();
	
	public User getPerformer() {
		return performer;
	}
	public void setPerformer(User performer) {
		this.performer = performer;
	}
	public ProcessStatus getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReferForm() {
		return referForm;
	}
	public void setReferForm(String referForm) {
		this.referForm = referForm;
	}
	public Systems getSystem() {
		return system;
	}
	public void setSystem(Systems system) {
		this.system = system;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public boolean isCreateTable() {
		return isCreateTable;
	}
	public void setCreateTable(boolean isCreateTable) {
		this.isCreateTable = isCreateTable;
	}
	public boolean isCreateLogin() {
		return isCreateLogin;
	}
	public void setCreateLogin(boolean isCreateLogin) {
		this.isCreateLogin = isCreateLogin;
	}
	public boolean isGrantData() {
		return isGrantData;
	}
	public void setGrantData(boolean isGrantData) {
		this.isGrantData = isGrantData;
	}
	public boolean isModifyTable() {
		return isModifyTable;
	}
	public void setModifyTable(boolean isModifyTable) {
		this.isModifyTable = isModifyTable;
	}
	public boolean isCreateView() {
		return isCreateView;
	}
	public void setCreateView(boolean isCreateView) {
		this.isCreateView = isCreateView;
	}
	public boolean isQueryData() {
		return isQueryData;
	}
	public void setQueryData(boolean isQueryData) {
		this.isQueryData = isQueryData;
	}
	public boolean isTransferData() {
		return isTransferData;
	}
	public void setTransferData(boolean isTransferData) {
		this.isTransferData = isTransferData;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getApproveRemark() {
		return approveRemark;
	}
	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}
	public User getRequester() {
		return requester;
	}
	public void setRequester(User requester) {
		this.requester = requester;
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User approver) {
		this.approver = approver;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getFormNumber() {
		return formNumber;
	}
	public void setFormNumber(int formNumber) {
		this.formNumber = formNumber;
	}
	public Date getPerformDate() {
		return performDate;
	}
	public void setPerformDate(Date performDate) {
		this.performDate = performDate;
	}
	public String getPerformRemark() {
		return performRemark;
	}
	public void setPerformRemark(String performRemark) {
		this.performRemark = performRemark;
	}
	public String getFormattedFormNumber(){
		return Constants.NUMBER_FORMATTER.format(formNumber);
	}
	public List<Upload> getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(List<Upload> fileUpload) {
		this.fileUpload = fileUpload;
	}
	public ServerType getServerTypeFrom() {
		return serverTypeFrom;
	}
	public void setServerTypeFrom(ServerType serverTypeFrom) {
		this.serverTypeFrom = serverTypeFrom;
	}
	public ServerType getServerTypeTo() {
		return serverTypeTo;
	}
	public void setServerTypeTo(ServerType serverTypeTo) {
		this.serverTypeTo = serverTypeTo;
	}
	public Date getStartPerform() {
		return startPerform;
	}
	public void setStartPerform(Date startPerform) {
		this.startPerform = startPerform;
	}
	public Date getEndPerform() {
		return endPerform;
	}
	public void setEndPerform(Date endPerform) {
		this.endPerform = endPerform;
	}
	public boolean isInsertData() {
		return isInsertData;
	}
	public void setInsertData(boolean isInsertData) {
		this.isInsertData = isInsertData;
	}
	public boolean isUpdateData() {
		return isUpdateData;
	}
	public void setUpdateData(boolean isUpdateData) {
		this.isUpdateData = isUpdateData;
	}
	public boolean isCreateIndex() {
		return isCreateIndex;
	}
	public void setCreateIndex(boolean isCreateIndex) {
		this.isCreateIndex = isCreateIndex;
	}
	public boolean isDropTable() {
		return isDropTable;
	}
	public void setDropTable(boolean isDropTable) {
		this.isDropTable = isDropTable;
	}
	public boolean isDropIndex() {
		return isDropIndex;
	}
	public void setDropIndex(boolean isDropIndex) {
		this.isDropIndex = isDropIndex;
	}
	public boolean isDropView() {
		return isDropView;
	}
	public void setDropView(boolean isDropView) {
		this.isDropView = isDropView;
	}
	public boolean isRevokeLogin() {
		return isRevokeLogin;
	}
	public void setRevokeLogin(boolean isRevokeLogin) {
		this.isRevokeLogin = isRevokeLogin;
	}
	public boolean isRevokeData() {
		return isRevokeData;
	}
	public void setRevokeData(boolean isRevokeData) {
		this.isRevokeData = isRevokeData;
	}
	public boolean isDeleteData() {
		return isDeleteData;
	}
	public void setDeleteData(boolean isDeleteData) {
		this.isDeleteData = isDeleteData;
	}
	public Database getDatabaseFrom() {
		return databaseFrom;
	}
	public void setDatabaseFrom(Database databaseFrom) {
		this.databaseFrom = databaseFrom;
	}
	public Database getDatabaseTo() {
		return databaseTo;
	}
	public void setDatabaseTo(Database databaseTo) {
		this.databaseTo = databaseTo;
	}
	public boolean isBackupData() {
		return isBackupData;
	}
	public void setBackupData(boolean isBackupData) {
		this.isBackupData = isBackupData;
	}
}
