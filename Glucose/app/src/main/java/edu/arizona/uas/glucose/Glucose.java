package edu.arizona.uas.glucose;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Glucose implements Serializable {

    public int fasting_val,breakfast_val, lunch_val, dinner_val;
    public  String fasting_status, breakfast_status, lunch_status, dinner_status;
    public String note;
    public MyDate date;
    public boolean normal;
    public int average;

    public Glucose(int fasting, int breakfast,  int lunch, int dinner, MyDate date, String note) {
        fasting_val = fasting;
        breakfast_val = breakfast;
        lunch_val = lunch;
        dinner_val = dinner;

        this.date = date;
        this.note = note;

        fasting_status = getFastingStatus(fasting_val);
        breakfast_status = getNonFastingStatus(breakfast_val);
        lunch_status = getNonFastingStatus(lunch_val);
        dinner_status = getNonFastingStatus(dinner_val);

        average =  (fasting_val  + breakfast_val + lunch_val + dinner_val) / 4;

        normal = getNormalStatus();
    }


    private String getFastingStatus(int val) {
        if( val >= 70 && val <= 99)
            return "Normal";
        return "Abnormal";
    }

    private String getNonFastingStatus(int val) {
        if (val < 70)
            return "Hypoglycemic";
        else if (val > 140)
            return "Abnormal";
        return "Normal";
    }

    private boolean getNormalStatus()  {
        if(fasting_status.equals("Normal") && breakfast_status.equals("Normal") &&
                lunch_status.equals("Normal") && dinner_status.equals("Normal")) {
            return true;
        }
        return false;
    }



    public String getJsonString() {

        JSONObject json = new JSONObject();
        try {
            json.put("fasting", fasting_val);
            json.put("breakfast", breakfast_val);
            json.put("lunch", lunch_val);
            json.put("dinner", dinner_val);
            json.put("note", note);
            json.put("date", date);
        } catch (JSONException e) {
            System.err.println(e);

        }
        return json.toString();
    }



    public MyDate getSortingCriteria() {
        return date;
    }
}
