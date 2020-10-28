package com.example.ctdemo;

import android.app.Application;
import android.app.NotificationManager;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;

import io.branch.referral.Branch;

public class MyApplication extends Application {
    //
    public static CleverTapAPI getCleverTapDefaultInstance() {
        return cleverTap;
    }

    private static final String WRITE_KEY = "4BzpCasQKJWsQhgzqSLhDNmshNvIJrm8"; //This you will receive under source in segment.
    private static final String CLEVERTAP_KEY = "CleverTap";
    public static boolean sCleverTapSegmentEnabled = false;
    private static CleverTapAPI cleverTap;

    @Override
    public void onCreate() {
        super.onCreate();

        CleverTapAPI.setUIEditorConnectionEnabled(true);//Set to false in production
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG); //Set to OFF in production
        ActivityLifecycleCallback.register(this);

//        Analytics analytics = new Analytics.Builder(getApplicationContext(), WRITE_KEY)
//                .logLevel(Analytics.LogLevel.DEBUG)
//                .use(CleverTapIntegration.FACTORY)
//                .build();
//
//        analytics.onIntegrationReady(CLEVERTAP_KEY, this::CleverTapIntegrationReady);

//        Analytics.setSingletonInstance(analytics);
        CleverTapIntegrationReady(CleverTapAPI.getDefaultInstance(this));
        addNotificationChannel();

        Branch branch = Branch.getInstance();
        branch.setRequestMetadata("$clevertap_attribution_id",
                CleverTapAPI.getDefaultInstance(getApplicationContext()).getCleverTapAttributionIdentifier());
    }

    private void CleverTapIntegrationReady(CleverTapAPI instance) {
        instance.enablePersonalization();
        sCleverTapSegmentEnabled = true;
        cleverTap = instance;
    }

    private void addNotificationChannel() {
        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());

        cleverTapAPI.createNotificationChannel(getApplicationContext(), "chnl_1",
                "Channel 1", "Channel 1",
                NotificationManager.IMPORTANCE_MAX, true);

        cleverTapAPI.createNotificationChannel(getApplicationContext(), "chnl_2",
                "Channel 2", "Channel 2",
                NotificationManager.IMPORTANCE_DEFAULT, true);
    }
}
