package com.example.ctdemo;

import android.app.Application;
import android.app.NotificationManager;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;

public class MyApplication extends Application {
    private static CleverTapAPI cleverTapDefaultInstance;

    public static CleverTapAPI getCleverTapDefaultInstance() {
        return cleverTapDefaultInstance;
    }

    @Override
    public void onCreate() {
        CleverTapAPI.setUIEditorConnectionEnabled(true);//Set to false in production
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG); //Set to OFF in production
        ActivityLifecycleCallback.register(this);


//        JSONObject configSetting = new JSONObject();
//        try {
//            configSetting.put("accountId", "TEST-549-W7Z-775Z");
//            configSetting.put("accountToken", "TEST-1a0-c45");
//            configSetting.put("enableABTesting", true);
//            configSetting.put("enableUIEditor", true);
//            configSetting.put("debugLevel", 2);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        CleverTapInstanceConfig config = CleverTapInstanceConfig.createInstance(getApplicationContext(),
//                "TEST-549-W7Z-775Z", "TEST-1a0-c45");
//        config.setAnalyticsOnly(false);
//
//        config.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
//        config.setEnableABTesting
        super.onCreate();



//        cleverTapDefaultInstance = CleverTapAPI.instanceWithConfig(getApplicationContext(), config);

        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        CleverTapAPI.createNotificationChannelGroup(getApplicationContext(), "grp_1", "Group 1");

        CleverTapAPI.createNotificationChannel(getApplicationContext(), "chnl_1", "Channel 1",
                "Demo notifications from CleverTap dashboard", NotificationManager.IMPORTANCE_MAX, "grp_1", true);
    }
}
