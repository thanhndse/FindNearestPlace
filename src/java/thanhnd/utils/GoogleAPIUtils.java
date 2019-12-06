/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import thanhnd.helper.googleapi.GoogleAPIData;

/**
 *
 * @author thanh
 */
public class GoogleAPIUtils {

    public static GoogleAPIData getGoogleAPIData(String rawAddress) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="
                    + URLEncoder.encode(rawAddress, "UTF-8")
                    + "&inputtype=textquery&fields=formatted_address,name,geometry&key=AIzaSyDpl8lxu_ioB1OrUMrboA1lFbkrXrHx7u4";
            return mapper.readValue(new URL(url), GoogleAPIData.class);
        } catch (IOException ex) {
            Logger.getLogger(GoogleAPIUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
