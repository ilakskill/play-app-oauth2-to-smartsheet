package controllers;

import play.mvc.*;



public class SmartSheet extends Controller {

    public static Result user(){

        return ok(views.html.home.render());


    }

}
