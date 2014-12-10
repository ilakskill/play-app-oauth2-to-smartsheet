package helpers;

import play.Play;
import play.Logger;


public class Config {

    /**
     * Gets the authorication url from application.conf
     * @return string of authorization url
     */
    public static String getAuthorizationUrl(){
        String url = Play.application().configuration().getString("smartsheet.authorization.url");
        if(url == null){
            Logger.error("Authorization url is null");
        }
         return url;
    }

    /**
     * This returns the client_id that was created in Smartsheet developer tools
     * @return string of client id from registered smartsheet app
     */
    public static String getAppClientId(){
        String client = Play.application().configuration().getString("smartsheet.client.id");
        if(client == null){
            Logger.error("Client Id is null");
        }
        return client;
    }

    /**
     * This returns the redirect_url that was created in Smartsheet developers tools.
     * @return string of the redirect url from registered smartsheet app
     */
    public static String getAppRedirectUrl(){
        String client = Play.application().configuration().getString("smartsheet.redirect.url");
        if(client == null){
            Logger.error("Redirect url is null");
        }
        return client;
    }

}
