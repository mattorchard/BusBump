package com.orchard.seg.busbump.dialog;

import android.support.annotation.ColorRes;
import android.util.Log;

public enum DirectionColor {
    NORTHBOUND("Northbound", android.R.color.holo_red_light),
    EASTBOUND("Eastbound", android.R.color.holo_green_light),
    SOUTHBOUND("Southbound", android.R.color.holo_blue_dark),
    WESTBOUND("Westbound", android.R.color.holo_orange_dark),
    INBOUND("Inbound", android.R.color.holo_purple),
    OUTBOUND("Outbound", android.R.color.holo_orange_light),
    NORTH("North", android.R.color.holo_red_light),
    EAST("East", android.R.color.holo_green_light),
    SOUTH("South", android.R.color.holo_blue_dark),
    WEST("West", android.R.color.holo_orange_dark),
    IN("In", android.R.color.holo_purple),
    OUT("Out", android.R.color.holo_orange_light),
    DEFAULT("Default", android.R.color.darker_gray);

    private final String mName;
    private final int mColorId;

    DirectionColor(String name, @ColorRes int colorId) {
        mName = name;
        mColorId = colorId;
    }

    @ColorRes
    public static int getColor(String name) {
        for (DirectionColor headingColor : values()) {
            if (headingColor.mName.equals(name)) {
                Log.d("BarBaz", headingColor.mName + headingColor.mColorId);
                return headingColor.mColorId;
            }
        }
        Log.d("BarBaz", DEFAULT.mName + DEFAULT.mColorId);
        return DEFAULT.mColorId;
    }
}