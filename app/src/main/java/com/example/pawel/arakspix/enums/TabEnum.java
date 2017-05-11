package com.example.pawel.arakspix.enums;

/**
 * Created by Pawel on 2016-11-11.
 */

// Couldn't use a proper enum because of 'tab.getPosition()' at ConversionActivity
// switch statement based on tab layout

public abstract class TabEnum {
    public static final int DEFAULT = 0;
    public static final int OVERLAY = 1;
    public static final int BORDER = 2;
    public static final int SETTINGS = 3;
    public static final int ROTATE = 4;
    public static final int SAVE = 5;
}
