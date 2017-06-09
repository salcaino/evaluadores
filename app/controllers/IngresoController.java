package controllers;


import play.mvc.*;

import views.html.*;

public class IngresoController extends Controller {
	
	public Result displayForm(){
		return ok(maincontainer.render(ingreso.render()));
	}
	
	public Result doPost(){
		return ok("OK WE GOT IT");
	}
}