package com.mango.autumnleaves.util;

import android.graphics.Color;

import com.mango.autumnleaves.R;

public class EstimoteUtils {

    public static String getShortIdentifier(String deviceIdentifier) {
        return deviceIdentifier.substring(0, 4) + "..." + deviceIdentifier.substring(28, 32);
    }

    public static int getEstimoteColor(String colorName) {
        switch (colorName) {
            case "ice":
                return Color.rgb(109, 170, 199);

            case "blueberry":
                return Color.rgb(36, 24, 93);

            case "candy":
                return Color.rgb(219, 122, 167);

            case "mint":
                return Color.rgb(155, 186, 160);

            case "beetroot":
                return Color.rgb(84, 0, 61);

            case "lemon":
                return Color.rgb(195, 192, 16);

            default:
                return R.color.cleanwhite;
        }
    }
}

