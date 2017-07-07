package controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import naranjalab.form.IngresoForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.ingreso;
import views.html.maincontainer;

public class IngresoController extends Controller {
	private final Logger logger = LoggerFactory.getLogger(IngresoController.class);
	@Inject
	private FormFactory ff;
	
	public Result displayForm(){
		
		return ok(maincontainer.render(ingreso.render(ff.form(IngresoForm.class)), flash(), session()));
	}
	
	public Result doPost(){
		return ok(maincontainer.render(ingreso.render(ff.form(IngresoForm.class)), flash(), session()));
	}
}