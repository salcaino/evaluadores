package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.data.format.Formatters;
import play.data.format.Formatters.SimpleFormatter;
import play.mvc.Controller;
import play.mvc.Result;
import naranjalab.common.UserUtilities;
import naranjalab.form.UserForm;

import java.text.ParseException;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import views.html.*;

public class LoginController extends Controller {
	
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Inject private FormFactory ff;

	public Result showLogin(){
		return ok(index.render());
	}
	
	public Result doPost() {
		
		Form<UserForm> userForm = ff.form(UserForm.class).bindFromRequest();
		
		if(userForm.hasErrors()) return loginError("Datos Incompletos");
		UserForm userform = userForm.get();
		String user = userform.getlogin();
    	String pwd = userform.getPassword();
    	logger.info("Username is: " + user);
    	logger.info("Password is: " + pwd);
    	logger.info("Remember me is: " + userform.getRememberMe());
    	if(user == null || user.isEmpty())
    		return loginError("Datos invalidos");
    	
    	if(pwd == null || pwd.isEmpty())
    		return loginError("Datos invalidos");
    	String result = UserUtilities.userLogin(user, pwd, logger);
    	if(result != null) return loginError(result);
    	return redirect(controllers.routes.MainContainerController.mainContainer());
	}
	private Result loginError(String msg) {
	  // TODO
	  return ok("login error! GOT: " + msg);
	}
	
}

