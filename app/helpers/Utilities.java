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
import org.codehaus.jackson.node.ObjectNode;
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
            Logger.debug(key + " " + value);
            values.put(key, value);
        }
        return values;
    }

    public static JsonNode postRequest(String url, String data){
        JsonNode jsonOutput = null;
        try {



            URL urlPath = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlPath.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

           // String input = json.toString();
            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            /*
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Logger.error("Error response from "+url+" status was " + conn.getResponseCode());
            } else {
                Logger.info("Connected to " + url +" status was " + conn.getResponseCode());
            }
            */
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder response = new StringBuilder();

            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();

            jsonOutput = Json.parse(response.toString());

            System.out.println(jsonOutput);
            System.out.println(response.toString());

               /*
            try {
                jsonOutput = Json.parse(response.toString());
            } catch(Exception e){
                String message = "Pachinko Response: " + response.toString() + " Error Message: " + e.getMessage();
                Logger.error(message);
              //  ObjectNode result = ErrorResultHandler.formatJson(null);
            //    result.put("message", message);
              //  jsonOutput = result;
            }    */


        } catch (MalformedURLException e) {
            Logger.error("Error could not connect to " + url + " " + e.getMessage());
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
        return jsonOutput;
    }


}
