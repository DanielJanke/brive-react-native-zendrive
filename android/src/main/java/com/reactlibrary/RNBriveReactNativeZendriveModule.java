
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

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
  public void setup(
    String sdkApplicationKey,
    String driverId,
    String fistName,
    String lastName,
    String group
  ) {
    ZendriveDriverAttributes driverAttributes = new ZendriveDriverAttributes();
    driverAttributes.setFirstName(firstName);
    driverAttributes.setLastName(lastName);
    driverAttributes.setGroup(group);
    
    ZendriveConfiguration zendriveConfiguration = new ZendriveConfiguration(zendriveApplicationKey, driverId);
    zendriveConfiguration.setDriverAttributes(driverAttributes);

    Zendrive.setup(this.getApplicationContext(), zendriveConfiguration, MyZendriveNotificationProvider.class,
    new ZendriveOperationCallback() {
        @Override
        public void onCompletion(ZendriveOperationResult result) {
          if (result.isSuccess()) {

              }
          else {

          }
        }
      } 
    );
  }
}