package controllers;

import play.mvc.*;
import helpers.*;



public class SmartSheet extends Controller {

    public static Result home(){

        System.out.println(Config.getAuthorizationUrl());



        return ok(views.html.home.render("http://www.google.com/fuck"));
    }

}
