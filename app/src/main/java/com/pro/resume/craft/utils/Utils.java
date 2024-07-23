package com.pro.resume.craft.utils;

import android.app.Activity;
import android.view.WindowManager;

public class Utils {

    public static void disableInteraction(boolean isEnabled, Activity activity){
        if (isEnabled){
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
