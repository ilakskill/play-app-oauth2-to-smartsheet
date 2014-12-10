package helpers;

import play.Logger;

import java.util.*;
import play.mvc.Http.Request;

public class Utilities {


    /**
     *
     * @param request
     * @return
     */
    public static Map<String, String> getQueryParamters(Request request){
        HashMap<String, String> values = new HashMap<String, String>();
        final Set<Map.Entry<String,String[]>> entries = request.queryString().entrySet();
        for (Map.Entry<String,String[]> entry : entries) {
            final String key = entry.getKey();
            final String value = Arrays.toString(entry.getValue());
            Logger.debug(key + " " + value);
            values.put(key, value);
        }
        return values;
    }


}
