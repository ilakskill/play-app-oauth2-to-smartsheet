package controllers;

import play.libs.Json;
import play.mvc.*;
import helpers.*;
import play.Logger;
import java.util.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.map.ObjectMapper;



public class SmartSheet extends Controller {

    public static Result home(){
        JsonNode node = null;
        Map<String, String> values = Utilities.getQueryParamters(request());
        if(values.size() == 0){    //TODO: add in token validation
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
            return ok(views.html.home.render());
        }
    }

    public static Result getAccount(){
        JsonNode user = Utilities.getRequest("https://api.smartsheet.com/1.1/user/me", session("access_token"));
        JsonNode userData = Utilities.getRequest("https://api.smartsheet.com/1.1/home?include=templates", session("access_token"));
        //Combine both home and user data
        if (userData instanceof ObjectNode) {
                ObjectNode node = (ObjectNode) userData;
                node.put("email", user.get("email").asText());
                node.put("first_name", user.get("firstName").asText());
                node.put("last_name", user.get("lastName").asText());
         }
           return ok(userData);
    }

}
