package edu.arizona.uas.glucose;

import android.content.Context;
import java.util.List;
import edu.arizona.uas.glucose.database.GlucoseDatabase;

public class GlucoseHistory {
    public static List<Glucose>  histories ;
    private static boolean isRead;


    public static  void addNewHistory(Glucose glucose) {
        for(int i=0; i<histories.size(); i++) {
            Glucose history = histories.get(i);

            if(history.date.toString().equals(glucose.date.toString())) {
                histories.set(i, glucose);
                GlucoseDatabase.updateGlucoseTable(glucose);
                return;
            }
        }

        histories.add(glucose);
        GlucoseDatabase.insertGlucoseTable(glucose);
        //histories.sort(Comparator.comparing(Glucose::getSortingCriteria).reversed());
    }


    public static void initialize(Context context) {
        if(isRead)
            return;

        GlucoseDatabase.initialize(context);
        histories = GlucoseDatabase.getAllGlucoseObjects();
        //histories.add(new Glucose(77,77,77,77, new MyDate(18,6,2019), "DDDDD"));
        isRead = true;
    }
}
