package edu.arizona.uas.BMI;

public enum BMI_Level_Indicator {
    UNDERWEIGHT("Underweight"),
    NORMAL("Normal"),
    OVERWEIGHT("Overweight"),
    OBESE("Obese");

    String level;

    BMI_Level_Indicator(String level) {
        this.level = level;
    }
    public String  toString() {
        return level;
    }
}
