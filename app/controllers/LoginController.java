package controllers;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import naranjalab.common.UserUtilities;
import naranjalab.common.util.HasherUtility;
import naranjalab.descriptors.UserDescriptor;
import naranjalab.descriptors.UserDescriptorStatus;
import naranjalab.form.UserForm;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;


public class LoginController extends Controller {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Inject
	private FormFactory ff;

	@Inject
	private Configuration config;

	public Result showLogin() {
		Form<UserForm> form = ff.form(UserForm.class);
		return ok(views.html.login.render("Ingreso", form, flash()));
	}

	private void resetSession() {
		session().clear();
		session("attempts", "1");
		session("creation", Long.toString(new Date().getTime()));
	}

	public Result logout() {
		resetSession();
		Form<UserForm> form = ff.form(UserForm.class);
		return ok(views.html.login.render("Ingreso", form, flash()));
	}

	public Result doPost() {

		Form<UserForm> userForm = ff.form(UserForm.class).bindFromRequest();
		if (userForm.hasErrors())
			return loginError("Datos Incompletos");
		UserForm userform = userForm.get();
		String user = userform.getLogin();
		String pwd = userform.getPassword();
		logger.info("Username is: " + user);
		logger.info("Password is: " + pwd);
		logger.info("Remember me is: " + userform.getRememberMe());
		if (user == null || user.isEmpty())
			return loginError("Datos invalidos");

		if (pwd == null || pwd.isEmpty())
			return loginError("Datos invalidos");
		String attempts = session("attempts");
		int tries = -1;
		if (attempts != null && !attempts.isEmpty()) {
			try {
				tries = Integer.parseInt(attempts);
				if (tries >= 5) {
					String creation = session("creation");
					if (creation != null) {
						long time = Long.parseLong(creation);
						long timeNow = new Date().getTime();
						long diff = timeNow - time;
						long secs = diff / 1000l;
						int minSeconds = 30;
						if (config != null) {
							String timedelay = config.getString("retry.delay", "30");
							try {
								minSeconds = Integer.parseInt(timedelay);
							} catch (Exception e) {
								minSeconds = 30;
							}
						} else {
							logger.info("config is null");
						}
						if (minSeconds >= secs)
							return loginError("Se ha superado la cantidad de intentos");
						else {
							logger.info("Se esperaron {} segundos. clearing cookie", secs);
							resetSession();
						}
					} else
						resetSession();
				} else
					logger.info("Attempts is {}", tries);
			} catch (Exception e) {
				logger.warn("got invalid attempt", e);
				resetSession();
			}
		} else {
			logger.info("Session attempts is null");
			resetSession();
		}
		UserDescriptor result = UserUtilities.userLogin(user, pwd, logger);
		if (result == null || (result.getStatus() != UserDescriptorStatus.GOOD && result.getStatus() != UserDescriptorStatus.RESET_PASSWORD)) {
			tries++;
			session("attempts", String.valueOf(tries));
			session("creation", Long.toString(new Date().getTime()));
			return loginError(result.getErrMsg());
		}
		if(result.getStatus() == UserDescriptorStatus.RESET_PASSWORD) {
			if(willReset(userform)) {
				return resetUser(userform);
			}
			flash("resetit", "true");
			Form<UserForm> form = ff.form(UserForm.class);
			UserForm newform = new UserForm();
			newform.setLogin(result.getUsername());
			Form<UserForm> filled = ff.form(UserForm.class).fill(newform); 
			return ok(views.html.login.render("Resetear contraseña", filled, flash()));
		}
		String id = Long.toString(System.currentTimeMillis());
		id = HasherUtility.getHashedRandomVersion(id);
		logger.info("id is: {}", id);

		session("id", id);
		String userRole = UserUtilities.getUserRole(user);
		logger.info("User role is {}", userRole);
		session("role", userRole);
		session("auxIndx", String.valueOf(result.getId()));
		return redirect(controllers.routes.MainContainerController.mainContainer());
	}

	private boolean willReset(UserForm form) {
		return form.getLogin() != null && !form.getLogin().isEmpty() &&
				form.getNewpassword() != null && !form.getNewpassword().isEmpty() &&
				form.getNewpasswordconfirmation() != null && !form.getNewpasswordconfirmation().isEmpty() &&
				form.getNewpassword().equals(form.getNewpasswordconfirmation());
	}
	private Result resetUser(UserForm userform) {
		
		return redirect(controllers.routes.MainContainerController.mainContainer());
	}
	private Result loginError(String msg) {
		logger.info("Returning error msg: {}", msg);
		flash("err", msg);
		
		return ok(views.html.login.render("Ingreso", ff.form(UserForm.class), flash()));
	}
}
