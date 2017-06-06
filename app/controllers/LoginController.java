package controllers;

import play.*;
import play.mvc.*;
import naranjalab.*;
import play.data.*;
import javax.inject.Inject;
import views.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginController extends Controller {
	
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);
	public Result showLogin(){
		return ok(index.render());
	}
	
	public Result doPost() {
    	
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
    	logger.info("Username is: " + dynamicForm.get("login"));
    	logger.info("Password is: " + dynamicForm.get("password"));
    	return redirect(controllers.routes.MainContainerController.mainContainer());
//        return ok("ok, I recived POST data. That's all...");
	}

	private Result remove(play.mvc.Http.Request request) {
	  // TODO
	  return ok("implement your business here");
	}

	private Result edit(play.mvc.Http.Request request) {
	  // TODO
	  return ok("login");
	}
	
}

