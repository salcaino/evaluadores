package controllers;

import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class BlankController extends Controller {

    public Result mainContainer() {
        return ok(blank.render());
    }
}
