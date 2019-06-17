package edu.arizona.uas.glucose;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GlucoseHistory {
    public static List<Glucose>  histories = new ArrayList<>();
    private static boolean isDoneBuilding = false;

    public static  void addNewHistory(Glucose history) {
        histories.add(history);
        //histories.sort(Comparator.comparing(Glucose::getSortingCriteria).reversed());
    }

    public static void buildSampleHistories() {
        if(isDoneBuilding)
            return;

        histories.add(new Glucose(100,50,59,59, new MyDate(new Date()), "just testing"));
        histories.add(new Glucose(80,90,90,101, new MyDate(30, 12, 2022), "Another testing for same date"));
        histories.add(new Glucose(65,65,65,65, new MyDate(30, 5, 2019), "Another testing for same date"));
        isDoneBuilding = true;
    }
}
