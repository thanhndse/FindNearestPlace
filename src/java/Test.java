
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import thanhnd.checker.XmlSyntaxChecker;
import thanhnd.helper.argorithm.Point;
import thanhnd.helper.argorithm.WeiszfeldCalculator;
import thanhnd.repository.CategoryRepository;
import thanhnd.utils.HibernateUtil;
import thanhnd.utils.HttpUtils;
import thanhnd.utils.JsonUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thanh
 */
public class Test {

    public static void main(String[] args) {
//        try {
//            //        try {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> data = objectMapper.readValue(new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=228 Phan Văn Hân, Phường 17Bình Thạnh Hồ Chí Minh&inputtype=textquery&fields=formatted_address,name,geometry&key=AIzaSyC7qNZAW3LzFA2k1AxTtT7izMbKFwBAQKk"), Map.class);
////            System.out.println(data);
////        } catch (MalformedURLException ex) {
////            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
////        } catch (IOException ex) {
////            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
////        }
//
//            JSONObject jsonObject = JsonUtils.readJsonFromUrl("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=228%20Phan%20V%C4%83n%20H%C3%A2n,%20Ph%C6%B0%E1%BB%9Dng%2017B%C3%ACnh%20Th%E1%BA%A1nh%20H%E1%BB%93%20Ch%C3%AD%20Minh&inputtype=textquery&fields=formatted_address,name,geometry&key=AIzaSyC7qNZAW3LzFA2k1AxTtT7izMbKFwBAQKk");
//            System.out.println(jsonObject);
//        
//        } catch (IOException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }
        List<Point> points = new ArrayList<>();
        points.add(new Point(2, 3));
        points.add(new Point(2, 4));
        points.add(new Point(3, 2));
        points.add(new Point(8, 5));
        WeiszfeldCalculator weiszfeldCalculator = new WeiszfeldCalculator(points);
        System.out.println(weiszfeldCalculator.getGeometricMedianPoint().getX() + ", " + weiszfeldCalculator.getGeometricMedianPoint().getY());
    }
}
