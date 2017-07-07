package naranjalab.form;

import play.data.validation.Constraints.Required;

public class NewUserDescriptor {
	@Required
	String emailinput;
	@Required
	String nombreinput;
	@Required
	String paternoinput;
	@Required
	String maternoinput;
	@Required
	String validinput;
	private String associatedUser;
	public NewUserDescriptor() {
	}

	public NewUserDescriptor(String emailinput, String nombreinput, String paternoinput, String maternoinput,
			String validinput) {
		this.emailinput = emailinput;
		this.nombreinput = nombreinput;
		this.paternoinput = paternoinput;
		this.maternoinput = maternoinput;
		this.validinput = validinput;
	}
	public String getEmailinput() {
		return emailinput;
	}
	public void setEmailinput(String emailinput) {
		this.emailinput = emailinput;
	}
	public String getNombreinput() {
		return nombreinput;
	}
	public void setNombreinput(String nombreinput) {
		this.nombreinput = nombreinput;
	}
	public String getPaternoinput() {
		return paternoinput;
	}
	public void setPaternoinput(String paternoinput) {
		this.paternoinput = paternoinput;
	}
	public String getMaternoinput() {
		return maternoinput;
	}
	public void setMaternoinput(String maternoinput) {
		this.maternoinput = maternoinput;
	}
	public String getValidinput() {
		return validinput;
	}
	public void setValidinput(String validinput) {
		this.validinput = validinput;
	}

	@Override
	public String toString() {
		return "NewUserDescriptor [emailinput=" + emailinput + ", nombreinput=" + nombreinput + ", paternoinput="
				+ paternoinput + ", maternoinput=" + maternoinput + ", validinput=" + validinput + "]";
	}

	public String getAssociatedUser() {
		return associatedUser;
	}

	public void setAssociatedUser(String associatedUser) {
		this.associatedUser = associatedUser;
	}
}
