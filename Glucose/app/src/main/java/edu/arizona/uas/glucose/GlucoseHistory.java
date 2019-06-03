package edu.arizona.uas.glucose;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GlucoseHistory {
    public static List<Glucose>  histories = new ArrayList<>();
    private static boolean isDoneBuilding = false;

    public static  void addNewHistory(Glucose history) {
        histories.add(history);
        histories.sort(Comparator.comparing(Glucose::getSortingCriteria).reversed());
    }

    public static void buildSampleHistories() {
        if(isDoneBuilding)
            return;

        histories.add(new Glucose(100,50,59,59, new GregorianCalendar(2019, Calendar.MAY, 13).getTime(), "just testing"));
        histories.add(new Glucose(80,90,90,101, new GregorianCalendar(2019, Calendar.JUNE, 1).getTime(), "Another testing for same date"));
        histories.add(new Glucose(65,65,65,65, new GregorianCalendar(2019, Calendar.JUNE, 1).getTime(), "Another testing for same date"));
        isDoneBuilding = true;
    }
}
