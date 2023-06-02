package com.example.mainprojectyourresults;

import java.util.Date;

public class PointValue {
    long xValue;
    int yValue;

    public PointValue(Date allDay, double timeInSecondsPlusMillisecondsInt) {

    }

    public PointValue(long xValue, int yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public long getxValue() {
        return xValue;
    }

    public int getyValue() {
        return yValue;
    }
}