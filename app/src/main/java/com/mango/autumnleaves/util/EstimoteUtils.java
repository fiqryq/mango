package com.mango.autumnleaves.util;

import android.graphics.Color;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mango.autumnleaves.R;
import com.mango.autumnleaves.model.Jadwal;
import com.mango.autumnleaves.remote.Koneksi;
import com.mango.autumnleaves.remote.Volley;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

