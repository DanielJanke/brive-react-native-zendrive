
package com.reactlibrary;

import android.content.Context;

import java.util.Map;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

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
    driverAttributes.setFirstName(firstName);
    driverAttributes.setLastName(lastName);
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

  @ReactMethod
  public void setDriveDetectionMode(/* TODO: Add input params */) {
    
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
    // WritableMap resultData = new WritableNativeMap();
    // for (Map.Entry<ZendriveEventType, Bool>) {

    // }
    callback.invoke();
  }

}