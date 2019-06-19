package edu.arizona.uas.glucose.database;

public class GlucoseSchema {

    public static class GlucoseTabel {
        public static final String NAME = "daily_glucose";

        public static class Col {
            public static final String DATE = "date";
            public static final String NOTE = "note";
            public static final String FASTING = "fasting";
            public static final String BREAKFAST = "breakfast";
            public static final String LUNCH = "lunch";
            public static final String DINNER = "dinner";
        }
    }
}
