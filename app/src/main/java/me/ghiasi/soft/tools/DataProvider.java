package me.ghiasi.soft.tools;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.ghiasi.soft.io.InputFileReader;

public class DataProvider {

    public static List<Person> request(int mode, int page) {
        // TODO
        ArrayList<Person> data = new ArrayList<>();
        int start = page * 10;
        int last = start + 10;
        InputFileReader inputFileReader = new InputFileReader();

        String json = inputFileReader.readURL("http://sneed.ir/SP/api/SPusers/" + mode + "/" + page);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        } catch (Exception e) {

        }
        int i = 0;
        if (jsonArray != null)
            for (; start < last; start++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int SPid = jsonObject.getInt("SPid");
                    int Cid = jsonObject.getInt("Cid");
                    String name = jsonObject.getString("name");
                    String profileImg = jsonObject.getString("profileImg");
                    String pictuers = jsonObject.getString("pictuers");
                    String startWorkTime = jsonObject.getString("startWorkTime");
                    String endWorkTime = jsonObject.getString("endWorkTime");
                    String discreption = jsonObject.getString("discreption");
                    String phoneNumber = jsonObject.getString("phoneNumber");
                    int vote = jsonObject.getInt("vote");
                    int busy = jsonObject.getInt("busy");
                    int status = jsonObject.getInt("status");
                    data.add(new Person(SPid, Cid, name, profileImg, pictuers, startWorkTime, endWorkTime, discreption, phoneNumber, vote, busy, status));
                    i++;
                } catch (Exception e) {

                }
            }
        return data;
    }
}