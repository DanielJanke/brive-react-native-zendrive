package com.reactlibrary;

import android.support.annotation.NonNull;
import android.content.Context;
import com.zendrive.sdk.ZendriveNotificationContainer;
import com.zendrive.sdk.ZendriveNotificationProvider;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import android.support.v4.app.NotificationCompat;

public class MyZendriveNotificationProvider implements ZendriveNotificationProvider{

    public MyZendriveNotificationProvider() {
        super();
      }

    // @Override
    @NonNull
    public ZendriveNotificationContainer getMaybeInDriveNotificationContainer(@NonNull Context context) { 
        return new ZendriveNotificationContainer(
            1,
            new NotificationCompat.Builder(context, "Foreground").build()
        );
    }

    // @Override
    @NonNull
    public ZendriveNotificationContainer getInDriveNotificationContainer(@NonNull Context context) {
        return new ZendriveNotificationContainer(
            1,
            new NotificationCompat.Builder(context, "Foreground").build()
        );
    }
}
