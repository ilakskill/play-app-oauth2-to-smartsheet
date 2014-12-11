package helpers;

import play.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import play.mvc.Http.Request;
import org.codehaus.jackson.JsonNode;
import play.libs.Json;

public class Utilities {


    /**
     * Url params into hashmap.
     * @param request http request
     * @return Map
     */
    public static Map<String, String> getQueryParamters(Request request){
        HashMap<String, String> values = new HashMap<String, String>();
        final Set<Map.Entry<String,String[]>> entries = request.queryString().entrySet();
        for (Map.Entry<String,String[]> entry : entries) {
            final String key = entry.getKey();
            final String value = Arrays.toString(entry.getValue())
                    .replace("[", "")
                    .replace("]", "");
            values.put(key, value);
        }
        return values;
    }

    /**
     * This does a POST request using Java HttpURLConnection
     * @param url url to POST to
     * @param data POST data you are passing
     * @return JSON
     */
    public static JsonNode postRequest(String url, String data){
        JsonNode jsonOutput = null;
        try {
            URL urlPath = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlPath.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder response = new StringBuilder();

            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Logger.debug("Connected to " + url +" status was " + conn.getResponseCode());
                jsonOutput = Json.parse(response.toString());
            }
        } catch (MalformedURLException e) {
            Logger.error("Error could not connect to " + url + " " + e.getMessage());
        } catch (IOException e) {
            Logger.error("ERROR " + e.getMessage());
        }
        return jsonOutput;
    }

    public static JsonNode getRequest(String url, String token){
        JsonNode jsonOutput = null;
        try {
            URL urlPath = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlPath.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder response = new StringBuilder();

            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Logger.debug("Connected to " + url +" status was " + conn.getResponseCode());
                jsonOutput = Json.parse(response.toString());
            }
        } catch (MalformedURLException e) {
            Logger.error("Error could not connect to " + url + " " + e.getMessage());
        } catch (IOException e) {
            Logger.error("ERROR " + e.getMessage());
        }
        return jsonOutput;
    }


}
