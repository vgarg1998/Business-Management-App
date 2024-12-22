package edu.northeastern.MrManage.utility;

import android.content.Context;

public class AppContextHolder {
    private static Context context;

    public static void init(Context context) {
        AppContextHolder.context = context.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
