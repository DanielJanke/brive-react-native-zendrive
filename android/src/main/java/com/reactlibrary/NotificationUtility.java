package com.reactlibrary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.location.LocationSettingsResult;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

public class NotificationUtility {
    public static final int FOREGROUND_MODE_NOTIFICATION_ID = 98;
    public static final int LOCATION_DISABLED_NOTIFICATION_ID = 99;
    public static final int LOCATION_PERMISSION_DENIED_NOTIFICATION_ID = 100;

    public static final int PSM_ENABLED_NOTIFICATION_ID = 101;
    public static final int BACKGROUND_RESTRICTION_NOTIFICATION_ID = 102;
    public static final int COLLISION_DETECTED_NOTIFICATION_ID = 103;
    public static final int WIFI_SCANNING_NOTIFICATION_ID = 104;
    public static final int GOOGLE_PLAY_SETTINGS_NOTIFICATION_ID = 105;

    private static final int locationDisabledRequestCode = 201;
    private static final int backgroundRestrictedRequestCode = 202;
    private static final int wifiScanningRequestCode = 203;
    private static final int googlePlaySettingsRequestCode = 204;
    private static final int locationPermissionRequestCode = 205;
    private static final int collisionActivityRequestCode = 206;
    private static final int psmEnabledRequestCode = PSM_ENABLED_NOTIFICATION_ID;

    private static final String FOREGROUND_CHANNEL_KEY = "Foreground";
    private static final String SETTINGS_CHANNEL_KEY = "Settings";
    private static final String COLLISION_CHANNEL_KEY = "Collision";

    public static Notification createLocationPermissionDeniedNotification(Context context) {
        createNotificationChannels(context);
        Intent actionIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        PendingIntent pi = PendingIntent.getActivity(context, locationPermissionRequestCode,
                actionIntent, FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Builder(context, SETTINGS_CHANNEL_KEY)
                .setContentTitle("Location Permission Denied")
                .setTicker("Location Permission Denied")
                .setContentText("Grant location permission to Zendrive app.")
                .setSmallIcon(R.drawable.ic_notification)
                .setOnlyAlertOnce(true)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
    }


    public static Notification createGooglePlaySettingsNotification(Context context,
                                                                    LocationSettingsResult result) {
        if (result.getStatus().isSuccess()) {
            return null;
        }
        createNotificationChannels(context);
        Intent actionIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        PendingIntent pi = PendingIntent.getActivity(context, 208,
                actionIntent, FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Builder(context, SETTINGS_CHANNEL_KEY)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Standortfreigabefehler")
                .setTicker("Standortfreigabefehler")
                .setContentText("Aktiviere Hohe Genauigkeit in den Einstellungen.")
                .setOnlyAlertOnce(true)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
    }

    public static Notification createLocationSettingDisabledNotification(Context context) {
        // TODO: use the result from the callback and show appropriate message and intent
        Intent callGPSSettingIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);

        callGPSSettingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 205,
                callGPSSettingIntent, 0);

        return new NotificationCompat.Builder(context.getApplicationContext(), "Settings")

                .setContentTitle("Standortfreigabe deaktiviert")
                .setTicker("Standortfreigabe deaktiviert")
                .setContentText("Aktiviere Hohe Genauigkeit in den Einstellungen")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setCategory(NotificationCompat.CATEGORY_ERROR)
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static Notification createPSMEnabledNotification(Context context, boolean isError) {
        createNotificationChannels(context);
        Intent actionIntent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, psmEnabledRequestCode,
                actionIntent, FLAG_CANCEL_CURRENT);

        String errorWarningPrefix = isError ? "Error: " : "Warning: ";

        return new NotificationCompat.Builder(context, SETTINGS_CHANNEL_KEY)
                .setContentTitle(errorWarningPrefix + "Power Saver Mode Enabled")
                .setTicker(errorWarningPrefix + "power Saver Mode Enabled")
                .setContentText("Disable power saver mode.")
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.ic_notification)
                .build();
    }

    @RequiresApi(Build.VERSION_CODES.P)
    public static Notification createBackgroundRestrictedNotification(Context context) {
        createNotificationChannels(context);
        Intent actionIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + context.getPackageName()));
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, backgroundRestrictedRequestCode,
                actionIntent, FLAG_CANCEL_CURRENT);

        return new Notification.Builder(context, SETTINGS_CHANNEL_KEY)
                .setContentTitle("Background Restricted")
                .setTicker("Background Restricted")
                .setContentText("Disable Background Restriction")
                .setOnlyAlertOnce(true)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notification)
                .build();
    }

    public static Notification createWifiScanningDisabledNotification(Context context) {
        createNotificationChannels(context);
        Intent actionIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        actionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, wifiScanningRequestCode,
                actionIntent, FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Builder(context, SETTINGS_CHANNEL_KEY)
                .setContentTitle("Wifi Scanning Disabled")
                .setTicker("Wifi Scanning Disabled")
                .setContentText("Tap to enable wifi radio.")
                .setOnlyAlertOnce(true)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notification)
                .build();
    }


    public static Notification createMaybeInDriveNotification(Context context) {
        createNotificationChannels(context);
        return new NotificationCompat.Builder(context, FOREGROUND_CHANNEL_KEY)
                .setContentTitle("BRIVE")
                .setDefaults(0)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentText("Mögliche Fahrt wird erkannt.")
                .build();
    }

    public static Notification createInDriveNotification(Context context) {
        createNotificationChannels(context);
        return new NotificationCompat.Builder(context, FOREGROUND_CHANNEL_KEY)
                .setContentTitle("BRIVE")
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentText("Fahrt wird aufgenommen.")
                .build();
    }

    private static void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel lowPriorityNotificationChannel = new NotificationChannel(FOREGROUND_CHANNEL_KEY,
                    "Zendrive trip tracking",
                    NotificationManager.IMPORTANCE_MIN);
            lowPriorityNotificationChannel.setShowBadge(false);
            manager.createNotificationChannel(lowPriorityNotificationChannel);

            NotificationChannel defaultNotificationChannel = new NotificationChannel
                    (SETTINGS_CHANNEL_KEY, "Problems",
                            NotificationManager.IMPORTANCE_HIGH);
            defaultNotificationChannel.setShowBadge(true);
            manager.createNotificationChannel(defaultNotificationChannel);

            NotificationChannel collisionDetectedNotificationChannel
                    = new NotificationChannel(COLLISION_CHANNEL_KEY, "Collision Detected",
                    NotificationManager.IMPORTANCE_HIGH);
            collisionDetectedNotificationChannel.setShowBadge(false);
            manager.createNotificationChannel(collisionDetectedNotificationChannel);
        }
    }

}
