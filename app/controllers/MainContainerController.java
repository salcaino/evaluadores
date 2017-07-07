package controllers;

import play.mvc.*;

import views.html.*;

public class MainContainerController extends Controller {

    public Result mainContainer() {
        return ok(maincontainer.render(blank.render(), flash(), session()));
    }

}
