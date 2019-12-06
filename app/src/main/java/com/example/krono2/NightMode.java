package com.example.krono2;

public class NightMode {
    static boolean isNightModeEnabled =false;

    public static boolean getIsNightModeEnabled() {
        return isNightModeEnabled;
    }

    public static void Enabled() {
        NightMode.isNightModeEnabled =true;
    }
    public static void Disabled() {
        NightMode.isNightModeEnabled =false;
    }

}
