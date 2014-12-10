package controllers;

import play.mvc.*;
import helpers.*;
import play.Logger;
import java.util.*;
import play.libs.*;
import play.libs.F.Promise;
import play.libs.WS;
import play.Play;



public class SmartSheet extends Controller {

    public static Result home(){
        Logger.debug("Authorization Url: " + Config.getAuthUrl());
        Map<String, String> values = Utilities.getQueryParamters(request());
        if(values.size() == 0){
            return redirect(Config.getAuthUrl());
        } else {
            return ok(views.html.home.render(Config.getAuthUrl()));
        }
    }


}
