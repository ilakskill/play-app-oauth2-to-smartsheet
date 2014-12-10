package helpers;

import play.Play;
import play.Logger;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.F;
import play.libs.Json;
import play.libs.WS;
import play.Play;




public class Config {


    /**
     * This builds and urlencodes the authorization url to smartsheet.
     * @return full url string of the authorization url
     */
    public static String getAuthUrl(){
        String buildUrl = null;

        if(getAuthorizationUrl() != null && getAppClientId() != null && getAppRedirectUrl() != null){
            try {
                buildUrl = getAuthorizationUrl() + "?" +
                "response_type=code&" +
                "client_id=" + URLEncoder.encode(getAppClientId(), "UTF-8") + "&" +
                "redirect_uri="  + URLEncoder.encode(getAppRedirectUrl(), "UTF-8") + "&" +
                "scope=READ_SHEETS%20WRITE_SHEETS&" +
                "state=MY_STATE";

            } catch (UnsupportedEncodingException e) {
                Logger.error(e.getMessage());
            }
        } else {
              Logger.error("Could not build authorization url");
        }
        return buildUrl;
    }

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
