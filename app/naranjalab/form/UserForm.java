package naranjalab.form;

import java.util.regex.Pattern;

import play.data.validation.Constraints.Required;

public class UserForm {
	
	@Required
    protected String login;
	@Required
    protected String password;
	protected String newpassword;
	protected String newpasswordconfirmation;
	protected String rememberMe;
    

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String validate() {
    	Pattern regex = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");
        if(login != null && !login.isEmpty() && !login.matches(regex.toString())) {
            return null;
        }
        return "Invalid User or password";
    }

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getNewpasswordconfirmation() {
		return newpasswordconfirmation;
	}

	public void setNewpasswordconfirmation(String newpasswordconfirmation) {
		this.newpasswordconfirmation = newpasswordconfirmation;
	}
    
}