package controllers;

import play.mvc.*;
import helpers.*;
import play.Logger;



public class SmartSheet extends Controller {

    public static Result home(){
        Logger.debug("Authorization Url: " + Config.getAuthUrl());
        return ok(views.html.home.render(Config.getAuthUrl()));
    }

}
