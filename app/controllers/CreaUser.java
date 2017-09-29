package controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import naranjalab.common.util.UserUtilities;
import naranjalab.form.NewUserDescriptor;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;

public class CreaUser extends Controller{
	
	private final Logger logger = LoggerFactory.getLogger(CreaUser.class);
	@Inject private FormFactory ff;
	public Result displayForm(){
		Form<NewUserDescriptor> userForm = ff.form(NewUserDescriptor.class);
		return ok(views.html.maincontainer.render(views.html.creausuario.render(userForm), flash(), session()));
	}

	public Result postNewUser(){
		logger.info("Received new user form");
		Form<NewUserDescriptor> userForm = ff.form(NewUserDescriptor.class).bindFromRequest();
		NewUserDescriptor newuser;
		if(userForm.hasErrors()){
			logger.info("new user form has errors");
			Map<String, List<ValidationError>> errors = userForm.errors();
			Iterator<Entry<String, List<ValidationError>>> iterator = errors.entrySet().iterator();
			Map.Entry<java.lang.String,java.util.List<play.data.validation.ValidationError>> entry;
			while (iterator.hasNext()) {
				entry = (Map.Entry<java.lang.String,java.util.List<play.data.validation.ValidationError>>) iterator
						.next();
				logger.info("new err: {}:{}", entry.getKey(), entry.getValue().toString());
			}
			userForm.discardErrors();
			flash("err", "Por favor ingrese todos los datos");
			Map<String, String> data = userForm.data();
			for (Map.Entry<String, String> tmp : data.entrySet()) {
				logger.info("{}:{}", tmp.getKey(), tmp.getValue());
			}
			newuser = UserUtilities.getUser(data);
			Form<NewUserDescriptor> filled;
			if(newuser != null) {
				logger.info("newuser {}", newuser);
				filled = userForm.fill(newuser);
				for (Map.Entry<String, String> tmp : filled.data().entrySet()) {
					logger.info("filled{}:{}", tmp.getKey(), tmp.getValue());
				}	
			} else {
				logger.info("Creating newuser form");
				filled = ff.form(NewUserDescriptor.class);
			}
			
			return ok(views.html.maincontainer.render(views.html.creausuario.render(filled), flash(), session()));
		}
		NewUserDescriptor user = userForm.get();
		String usuariocreador = session("auxIndx");
		if(usuariocreador == null || usuariocreador.isEmpty()){
			return redirect("/login");
		}
		user.setAssociatedUser(usuariocreador);
		String resultado = UserUtilities.creaUser(user, logger);
		if(resultado != null){
			logger.warn("No se pudo guardar el usuario {}. Error: {}", user.getEmailinput(), resultado);
			flash("err", resultado);
			newuser = UserUtilities.getUser(userForm.data());
			Form<NewUserDescriptor> filled = userForm.fill(newuser);
			return ok(views.html.maincontainer.render(views.html.creausuario.render(filled), flash(), session()));
		} else
			flash("msg", "Usuario guardado exitosamente");
			return ok(views.html.maincontainer.render(views.html.creausuario.render(userForm), flash(), session()));
	}
}
