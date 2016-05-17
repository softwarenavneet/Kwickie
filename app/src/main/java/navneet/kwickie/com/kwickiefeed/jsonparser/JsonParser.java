package navneet.kwickie.com.kwickiefeed.jsonparser;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import navneet.kwickie.com.kwickiefeed.Kwickie.Kwickie;

public class JsonParser {
    public static List<Kwickie> parse(String json) {
        try {
            List<Kwickie> kwickieList = new ArrayList<>();
            JSONArray arr = new JSONArray(json);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                Kwickie kwickie = new Kwickie();
                JSONObject vidObj = obj.getJSONObject("kwickieVideo");
                kwickie.setThumbnailUrl(vidObj.getString("thumbUrl"));
                kwickie.setVideoUrl(vidObj.getString("lowQualityUrl"));

                JSONObject questionUserObj = obj.getJSONObject("questionUser");
                String firstPersonName = questionUserObj.getString("firstName") + " " + questionUserObj.getString("lastName");
                kwickie.setFirstPersonName(firstPersonName);

                JSONObject answerUserObj = obj.getJSONObject("answerUser");
                String secondPersonName = answerUserObj.getString("firstName") + " " + answerUserObj.getString("lastName");
                kwickie.setSecondPersonName(secondPersonName);

                kwickieList.add(kwickie);
            }

            return kwickieList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseID(String loginJson) {
        try {
            JSONObject obj = new JSONObject(loginJson);

            return obj.getString("id");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}