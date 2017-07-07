package naranjalab.descriptors;

public class UserDescriptor {
	private String username, pwd, role, valid;
	private String associatedUser;
	private UserDescriptorStatus status = UserDescriptorStatus.GOOD;
	private String errMsg;
	private int id;
	private String nombre, paterno, materno;
	public UserDescriptor(String username, String pwd, String role, String valid) {
		this.username = username;
		this.pwd = pwd;
		this.role = role;
		this.valid = valid;
	}
	
	
	
	public UserDescriptor(String errMsg) {
		this.status = UserDescriptorStatus.ERROR;
		this.errMsg = errMsg;
	}
	public UserDescriptor(UserDescriptorStatus status, String errMsg) {
		this.status = status;
		this.errMsg = errMsg;
	}

	public UserDescriptor() {
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getAssociatedUser() {
		return associatedUser;
	}
	public void setAssociatedUser(String associatedUser) {
		this.associatedUser = associatedUser;
	}
	public UserDescriptorStatus getStatus() {
		return status;
	}
	public void setStatus(UserDescriptorStatus status) {
		this.status = status;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "UserDescriptor [username=" + username + ", pwd=" + pwd + ", role=" + role + ", valid=" + valid
				+ ", associatedUser=" + associatedUser + ", status=" + status + ", errMsg=" + errMsg + ", id=" + id
				+ "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}
}
