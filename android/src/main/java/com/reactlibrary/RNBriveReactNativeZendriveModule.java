
package com.reactlibrary;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Map;
import java.util.HashMap;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;

import com.reactlibrary.MyZendriveNotificationProvider;
import com.facebook.react.modules.core.DeviceEventManagerModule;


import com.zendrive.sdk.*;
import com.zendrive.sdk.debug.ZendriveDebug;
//import com.zendrive.sdk.testing.mockdrive.MockDrive;
//import com.zendrive.sdk.testing.mockdrive.MockZendriveOperationResult;
//import com.zendrive.sdk.testing.mockdrive.PresetTripType;

import javax.annotation.Nullable;

public class RNBriveReactNativeZendriveModule extends ReactContextBaseJavaModule {

  private static ReactApplicationContext reactContext;

  public RNBriveReactNativeZendriveModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNBriveReactNativeZendrive";
  }

  @ReactMethod
  public void test(
    String message,
    Callback callback
  ) {
    callback.invoke();
  }

  @ReactMethod
  public void setup(
    String sdkApplicationKey,
    String driverId,
    String firstName,
    String lastName,
    String group,
    final Callback successCallback,
    final Callback errorCallback
  ) {
    ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();

    driverAttributes.setCustomAttribute("firstName", firstName);
    driverAttributes.setCustomAttribute("lastName", lastName);
    driverAttributes.setAlias(firstName + " " + lastName);
    driverAttributes.setGroup(group);
    
    ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(sdkApplicationKey, driverId);
    zendriveConfiguration.setDriverAttributes(driverAttributes);

    Zendrive.setup(this.reactContext, zendriveConfiguration, MyZendriveBroadcastReceiver.class, MyZendriveNotificationProvider.class,
    new ZendriveOperationCallback() {
        @Override
        public void onCompletion(ZendriveOperationResult result) {


          if (result.isSuccess()) {
            successCallback.invoke();
          }
          else {
            errorCallback.invoke(result.getErrorMessage());
          }
        }
      } 
    );
  }

  @ReactMethod
  public void teardown(
    // final Callback successCallback,
    // final Callback errorCallback
  ) {
    Zendrive.teardown(this.reactContext, 
     new ZendriveOperationCallback() {
      @Override
      public void onCompletion(ZendriveOperationResult result) {
        // if (result.isSuccess()) {
        //   successCallback.invoke();
        // }
        // else {
        //   errorCallback.invoke(result.getErrorMessage());
        // }
      }
    } );
  }


  private static final String AUTO_OFF = "ZendriveDriveDetectionModeAutoOff";
  private static final String AUTO_ON = "ZendriveDriveDetectionModeAutoOn";

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(AUTO_OFF, "ZendriveDriveDetectionMode.AUTO_OFF");
    constants.put(AUTO_ON, "ZendriveDriveDetectionMode.AUTO_ON");
    return constants;
  }


  @ReactMethod
  public void setDriveDetectionMode(String zendriveDriveDetectionMode, @Nullable final Callback callback) {
    final ZendriveDriveDetectionMode zendriveDriveDetectionModeJava;
    if (zendriveDriveDetectionMode.equals("ZendriveDriveDetectionMode.AUTO_ON")) {
      zendriveDriveDetectionModeJava = ZendriveDriveDetectionMode.AUTO_ON;
    } else {
      zendriveDriveDetectionModeJava = ZendriveDriveDetectionMode.AUTO_OFF;
    }

    Zendrive.setZendriveDriveDetectionMode(this.reactContext, zendriveDriveDetectionModeJava, new ZendriveOperationCallback() {
      @Override
      public void onCompletion(ZendriveOperationResult result) {
        if (result.isSuccess()) {
          callback.invoke(null, "Success");
        }
        else {
          callback.invoke(result.getErrorMessage(), null);
        }
      }
    } );
  }

  @ReactMethod
  public void activeDriveInfo(
    final Callback callback
  ) {
    ActiveDriveInfo activeDriveInfo = Zendrive.getActiveDriveInfo(this.reactContext);
    // String activeDriveInfo = Zendrive.getBuildVersion();

    if(activeDriveInfo == null) {
      callback.invoke(null, null);
    } else {
      callback.invoke(null, activeDriveInfo.driveId);
    }
  
    // callback.invoke(activeDriveInfo.driveId, activeDriveInfo.driveId);
  }

    private ZendriveStateCallback zendriveStateCallback(final Callback callback) {
        return new ZendriveStateCallback() {
            @Override
            public void onComplete(@android.support.annotation.Nullable ZendriveState zendriveState) {
                callback.invoke(zendriveState.isDriveInProgress, zendriveState.isForegroundService, zendriveState.zendriveConfiguration.toJson().toString());
            }
        };
    }

  @ReactMethod
  public void getZendriveState(
          final Callback callback
  ) {
      Zendrive.getZendriveState(this.reactContext, zendriveStateCallback(callback));
  }



  @ReactMethod
  public void startSession(
    String sessionId
  ) {
    Zendrive.startSession(this.reactContext, sessionId);
  }

  @ReactMethod
  public void stopSession()
  {
    Zendrive.stopSession(this.reactContext);
  }

  @ReactMethod
  public void startDrive(@Nullable String trackingId, final Callback callback) {
      Zendrive.startDrive(this.reactContext, trackingId, new ZendriveOperationCallback() {
          @Override
          public void onCompletion(ZendriveOperationResult zendriveOperationResult) {
              callback.invoke(zendriveOperationResult.getErrorMessage());
          }
      });
  }

  @ReactMethod
  public void stopDrive() {
      Zendrive.stopManualDrive(this.reactContext, null);
  }

  @ReactMethod
  public void isSDKSetup(
    final Callback callback
  )
  {
    boolean isSDKSetup = Zendrive.isSDKSetup(this.reactContext);
    callback.invoke(isSDKSetup);
  }

  @ReactMethod
  public static void getZendriveSettings(
      @Nullable final Callback callback
  ) {
    Zendrive.getZendriveSettings(reactContext, new ZendriveSettingsCallback() {
        @Override
        public void onComplete(ZendriveSettings zendriveSettings) {
          WritableArray map = Arguments.createArray();
          if (zendriveSettings == null) {
            callback.invoke(null);
          }
            NotificationManager notificationManager =
                    (NotificationManager) reactContext.
                            getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager == null) {
                return;
            }

          else {
            for (ZendriveSettingError error : zendriveSettings.errors) {
                switch (error.type) {
                    case POWER_SAVER_MODE_ENABLED: {
                        Log.d("BRIVE DEBUG", "POWER_SAVER_MODE_ENABLED");
                        map.pushString("POWER_SAVER_MODE_ENABLED");

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            notificationManager.notify(NotificationUtility.
                                            PSM_ENABLED_NOTIFICATION_ID,
                                    NotificationUtility.
                                            createPSMEnabledNotification(reactContext, true));
                        } else {
                            throw new RuntimeException("Power saver mode " +
                                    "error on OS version < Lollipop.");
                        }

                        break;
                    }
                    case BACKGROUND_RESTRICTION_ENABLED: {
                        Log.d("BRIVE DEBUG", "BACKGROUND_RESTRICTION_ENABLED");
                        map.pushString("BACKGROUND_RESTRICTION_ENABLED");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            notificationManager.notify(NotificationUtility.
                                            BACKGROUND_RESTRICTION_NOTIFICATION_ID,
                                    NotificationUtility.
                                            createBackgroundRestrictedNotification(reactContext));
                        } else {
                            throw new RuntimeException("Background restricted " +
                                    "callback on OS version < P.");
                        }
                        break;
                    }
                    case GOOGLE_PLAY_SETTINGS_ERROR: {

                        GooglePlaySettingsError e = (GooglePlaySettingsError) error;
                        Notification notification =
                                NotificationUtility.
                                        createGooglePlaySettingsNotification(reactContext,
                                                e.googlePlaySettingsResult);
                        if (notification != null) {
                            notificationManager.
                                    notify(208, notification);
                        }

                        map.pushString("GOOGLE_PLAY_SETTINGS_ERROR");
                        break;
                    }
                    case GOOGLE_PLAY_CONNECTION_ERROR: {
                        Log.d("BRIVE DEBUG", "GOOGLE_PLAY_CONNECTION_ERROR");
                        map.pushString("GOOGLE_PLAY_CONNECTION_ERROR");
                        break;
                    }
                    case LOCATION_PERMISSION_DENIED: {
                        Log.d("BRIVE DEBUG", "LOCATION_PERMISSION_DENIED");
                        map.pushString("LOCATION_PERMISSION_DENIED");
                        notificationManager.notify(NotificationUtility.
                                        LOCATION_PERMISSION_DENIED_NOTIFICATION_ID,
                                NotificationUtility.
                                        createLocationPermissionDeniedNotification(reactContext));
                        break;
                    }
                    case LOCATION_SETTINGS_ERROR: {
                        Log.d("BRIVE DEBUG", "LOCATION_SETTINGS_ERROR");
                        map.pushString("LOCATION_SETTINGS_ERROR");
                        notificationManager.notify(NotificationUtility.
                                LOCATION_DISABLED_NOTIFICATION_ID, NotificationUtility.
                                createLocationSettingDisabledNotification(reactContext));
                        break;
                    }
                    case WIFI_SCANNING_DISABLED: {
                        Log.d("BRIVE DEBUG", "WIFI_SCANNING_DISABLED");
                        map.pushString("WIFI_SCANNING_DISABLED");
                        notificationManager.notify(NotificationUtility.
                                WIFI_SCANNING_NOTIFICATION_ID, NotificationUtility.
                                createWifiScanningDisabledNotification(reactContext));
                        break;
                    }
                }
            }
            for (ZendriveSettingWarning warning: zendriveSettings.warnings) {
                switch (warning.type) {
                    case POWER_SAVER_MODE_ENABLED: {
                        Log.d("BRIVE DEBUG", "POWER_SAVER_MODE_ENABLED");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            notificationManager.notify(NotificationUtility.
                                            PSM_ENABLED_NOTIFICATION_ID,
                                    NotificationUtility.createPSMEnabledNotification(reactContext, false));
                        } else {
                            throw new RuntimeException("Power saver mode " +
                                    "warning on OS version < Lollipop.");
                        }
                        map.pushString("POWER_SAVER_MODE_ENABLED");
                        break;
                    }
                    case BACKGROUND_RESTRICTION_ENABLED: {
                        Log.d("BRIVE DEBUG", "BACKGROUND_RESTRICTION_ENABLED");
                        map.pushString("BACKGROUND_RESTRICTION_ENABLED");
                        break;
                    }
                    case GOOGLE_PLAY_SETTINGS_ERROR: {
                        Log.d("BRIVE DEBUG", "GOOGLE_PLAY_SETTINGS_ERROR");
                        map.pushString("GOOGLE_PLAY_SETTINGS_ERROR");
                        break;
                    }
                    case GOOGLE_PLAY_CONNECTION_ERROR: {
                        Log.d("BRIVE DEBUG", "GOOGLE_PLAY_CONNECTION_ERROR");
                        map.pushString("GOOGLE_PLAY_CONNECTION_ERROR");
                        break;
                    }
                    case LOCATION_PERMISSION_DENIED: {
                        Log.d("BRIVE DEBUG", "LOCATION_PERMISSION_DENIED");
                        map.pushString("LOCATION_PERMISSION_DENIED");
                        break;
                    }
                    case LOCATION_SETTINGS_ERROR: {
                        Log.d("BRIVE DEBUG", "LOCATION_SETTINGS_ERROR");
                        map.pushString("LOCATION_SETTINGS_ERROR");
                        break;
                    }
                    case WIFI_SCANNING_DISABLED: {
                        Log.d("BRIVE DEBUG", "WIFI_SCANNING_DISABLED");
                        map.pushString("WIFI_SCANNING_DISABLED");
                        break;
                    }
                    default: {
                        Log.d("BRIVE DEBUG", "DEFAULT" + warning.type.toString());
                        map.pushString("WARNING_DEFAULT");
                        break;
                    }
                }
            }
                if (map.size() == 0) {
                    callback.invoke(null);
                } else {
                    callback.invoke(map);
                }
          }
        }
    });
  }

    @ReactMethod
    public void createLocationSettingDisabledNotification(
    )
    {
        NotificationUtility.createLocationSettingDisabledNotification(this.reactContext);
    }

  @ReactMethod
  public void getEventSupportForDevice(
    final Callback callback
  )
  {
    Map<ZendriveEventType,Boolean> eventSupport = Zendrive.getEventSupportForDevice(this.reactContext);
    final Map<String, Boolean> eventSupportConverted = new HashMap<>();

    WritableMap map = Arguments.createMap();
    WritableArray array = Arguments.createArray();

    for (Map.Entry<ZendriveEventType, Boolean> entry : eventSupport.entrySet()) {
      // System.out.println(entry.getKey() + "/" + entry.getValue());
      
      map.putBoolean(entry.getKey().toString(), entry.getValue());
    }

    // for ( int faktor = 1; faktor <= 3; faktor ++ ) {
      // map.putBoolean(Integer.toString(faktor), true);
      // array.pushMap(map); 
    // }
    callback.invoke(map);
  }

  @ReactMethod
  public void sendEventTest() {
      WritableMap params = Arguments.createMap();
      params.putString("message", "Test");
      this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onZendriveSettingsConfigChanged", params);
  }

  @ReactMethod
  public void uploadAllZendriveData(
          String sdkApplicationKey,
          String driverId,
          String firstName,
          String lastName,
          String group,
          final Callback callback
  ) {
      ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();
      driverAttributes.setAlias(firstName + " " + lastName);
      driverAttributes.setGroup(group);

      ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(sdkApplicationKey, driverId);
      zendriveConfiguration.setDriverAttributes(driverAttributes);
      ZendriveNotificationContainer zendriveNotificationContainer = new ZendriveNotificationContainer(
              1,
              new NotificationCompat.Builder(reactContext, "Foreground").setContentText("BRIVE Fahrt läuft").build()
      );

      ZendriveOperationCallback zendriveOperationCallback = new ZendriveOperationCallback() {
          @Override
          public void onCompletion(ZendriveOperationResult zendriveOperationResult) {
                  if (zendriveOperationResult.isSuccess()) {
                      callback.invoke("SUCCESS");
                  }
                  else {
                      callback.invoke(zendriveOperationResult.getErrorMessage());
                  }
              }
          };

      ZendriveDebug.uploadAllZendriveData(reactContext, zendriveConfiguration, zendriveNotificationContainer, zendriveOperationCallback);
  }


    public static void sendEvent(
            String eventName,
            @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}

