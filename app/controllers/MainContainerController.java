package controllers;

import javax.inject.Inject;
import naranjalab.form.UserForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;

import play.data.FormFactory;
import play.mvc.*;
import static play.mvc.Controller.flash;
import static play.mvc.Results.ok;
import play.twirl.api.Html;

import views.html.*;

public class MainContainerController extends Controller {
	protected final Logger logger = LoggerFactory.getLogger(IngresoController.class);
	@Inject
	protected FormFactory ff;
    public Result mainContainer() {
        return ok(maincontainer.render(blank.render(), flash(), session()));
    }

    public Result sendErrorMsg(String msg, Html contenido) {
        logger.info("Returning error msg: {}", msg);
        flash("err", msg);
        return ok(maincontainer.render(contenido, flash(), session()));
    }
    public Result sendToLogin(){
        Form<UserForm> form = ff.form(UserForm.class);
        return ok(views.html.login.render("Ingreso", form, flash()));
    }
}
