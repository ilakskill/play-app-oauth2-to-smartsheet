package controllers;

import play.mvc.*;
import helpers.*;
import play.Logger;
import java.util.*;
import play.libs.*;
import play.libs.F.Promise;
import play.libs.WS;
import play.Play;
import org.codehaus.jackson.JsonNode;



public class SmartSheet extends Controller {

    public static String accessToken = null;
    public static String refreshToken = null;


    public static Result home(){
        Map<String, String> values = Utilities.getQueryParamters(request());
        if(values.size() == 0){
            return redirect(Config.getAuthUrl());
        } else {

            if(refreshToken == null){
                JsonNode node = Utilities.postRequest(Config.getTokenUrl(), Config.getTokenData(values));
                if(node != null) {
                    refreshToken = node.get("refresh_token").asText();
                    accessToken = node.get("access_token").asText();
                }
            }

            System.out.println(refreshToken);
            System.out.println(accessToken);

            return ok(views.html.home.render());
        }
    }


}
