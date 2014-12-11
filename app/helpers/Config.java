package helpers;

import play.Play;
import play.Logger;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.security.*;
import org.apache.commons.codec.binary.Hex;




public class Config {

    public static String getRefreshTokenData(String refreshToken){
        String buildUrl = null;
        if(getTokenUrl() != null){
            try {
                buildUrl = "grant_type=refresh_token&" +
                        "client_id="+getAppClientId()+"&"+
                        "refresh_token="+refreshToken+"&"+
                        "redirect_uri="+URLEncoder.encode(getAppRedirectUrl(), "UTF-8")+"&"+
                        "hash="+buildSecretHash(refreshToken)+"";
            } catch (UnsupportedEncodingException e) {
                Logger.error(e.getMessage());
            }
        }
        return buildUrl;
    }


    public static String getTokenData(Map<String, String> values){
        String buildUrl = null;
        if(getTokenUrl() != null && values.size() != 0){
            try {
                buildUrl = "grant_type=authorization_code&" +
                "code="+values.get("code")+"&" +
                "client_id="+getAppClientId()+"&"+
                "redirect_uri="+URLEncoder.encode(getAppRedirectUrl(), "UTF-8")+"&"+
                "hash="+buildSecretHash(values.get("code"))+"";
            } catch (UnsupportedEncodingException e) {
                Logger.error(e.getMessage());
            }
         }
        return buildUrl;
    }

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
                "client_id=" + getAppClientId() + "&" +
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
     * Gets the token url from application.conf
     * @return string of token url
     */
    public static String getTokenUrl(){
        String url = Play.application().configuration().getString("smartsheet.token.url");
        if(url == null){
            Logger.error("Token url is null");
        }
        return url;
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
     * This returns the application secret key that was created in Smartsheet developer tools
     * @return string of secret key from registered smartsheet app
     */
    public static String getAppSecret(){
        String client = Play.application().configuration().getString("smartsheet.secret.key");
        if(client == null){
            Logger.error("Secret key is null");
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

    /**
     * This builds the secret hash to use to obtain token.
     * @param code code from the authorization GET
     * @return String
     */
    public static String buildSecretHash(String code){
        String doHash = getAppSecret() + "|" + code;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Your JVM does not support SHA-256, which is required for OAuth with Smartsheet.", e);
        }

        byte[] digest;
        try {
            digest = md.digest(doHash.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return new String(Hex.encodeHex(digest));
    }
}
