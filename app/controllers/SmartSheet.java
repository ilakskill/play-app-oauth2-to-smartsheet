package controllers;

import play.mvc.*;
import helpers.*;
import play.Logger;
import java.util.*;
import org.codehaus.jackson.JsonNode;



public class SmartSheet extends Controller {

    public static Result home(){
        JsonNode node = null;
        Map<String, String> values = Utilities.getQueryParamters(request());
        if(values.size() == 0){
            return redirect(Config.getAuthUrl());
        } else {
            if(session("access_token") == null) {
                node = Utilities.postRequest(Config.getTokenUrl(), Config.getTokenData(values));
                if (node == null) { //Refresh our token
                    Logger.debug("Refreshing token!");
                    node = Utilities.postRequest(Config.getTokenUrl(), Config.getRefreshTokenData(session("refresh_token")));
                }
                session("access_token", node.get("access_token").asText());
                session("refresh_token", node.get("refresh_token").asText());

            }
            Logger.debug("Current Access Token: " + session("access_token"));
            Logger.debug("Current Refresh Token: " + session("refresh_token"));
            return ok(views.html.home.render(session("access_token")));
        }
    }




}
