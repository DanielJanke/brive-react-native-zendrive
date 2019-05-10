
package com.reactlibrary;

import android.content.Context;

import java.util.Map;
import java.util.HashMap;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;

import com.reactlibrary.MyZendriveNotificationProvider;

import com.zendrive.sdk.*;

public class RNBriveReactNativeZendriveModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

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
    driverAttributes.setGroup(group);
    
    ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(sdkApplicationKey, driverId);
    zendriveConfiguration.setDriverAttributes(driverAttributes);

    Zendrive.setup(this.reactContext, zendriveConfiguration, null, MyZendriveNotificationProvider.class,
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
  public void setDriveDetectionMode(String zendriveDriveDetectionMode) {
    final ZendriveDriveDetectionMode zendriveDriveDetectionModeJava;
    if (zendriveDriveDetectionMode == "ZendriveDriveDetectionModeAutoOn") {
      zendriveDriveDetectionModeJava = ZendriveDriveDetectionMode.AUTO_ON;
    } else {
      zendriveDriveDetectionModeJava = ZendriveDriveDetectionMode.AUTO_OFF;
    }

    Zendrive.setZendriveDriveDetectionMode(this.reactContext, zendriveDriveDetectionModeJava, new ZendriveOperationCallback() {
      @Override
      public void onCompletion(ZendriveOperationResult result) {
        if (result.isSuccess()) {
          // successCallback.invoke();
        }
        else {
          // errorCallback.invoke(result.getErrorMessage());
        }
      }
    } );
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
  public void isSDKSetup(
    final Callback callback
  )
  {
    boolean isSDKSetup = Zendrive.isSDKSetup(this.reactContext);
    callback.invoke(isSDKSetup);
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

}