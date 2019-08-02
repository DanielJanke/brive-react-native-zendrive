package com.reactlibrary;
import android.content.Context;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.zendrive.sdk.*;

public class MyZendriveBroadcastReceiver extends ZendriveBroadcastReceiver {

    @Override
    public void onDriveStart(Context context, DriveStartInfo driveStartInfo) {
        WritableMap params = Arguments.createMap();
        params.putString("message", "onDriveStart");
    }

    @Override
    public void onDriveResume(Context context, DriveResumeInfo resumeInfo) {
        WritableMap params = Arguments.createMap();
        params.putString("message", "onDriveResume");
    }

    @Override
    public void onDriveEnd(Context context, EstimatedDriveInfo estimatedDriveInfo) {
        WritableMap params = Arguments.createMap();
        params.putString("message", "onDriveEnd");
    }

    @Override
    public void onDriveAnalyzed(Context context, AnalyzedDriveInfo analyzedDriveInfo) {
        WritableMap params = Arguments.createMap();
        params.putString("message", "onDriveAnalyzed");
    }

    @Override
    public void onAccident(Context context, AccidentInfo accidentInfo) {
        WritableMap params = Arguments.createMap();
        params.putString("message", "onAccident");
    }

    @Override
    public void onZendriveSettingsConfigChanged(Context context, boolean errorsFound, boolean warningsFound) {
        Log.d("BRIVE DEBUG", "onZendriveSettingsConfigChanged");

        RNBriveReactNativeZendriveModule.getZendriveSettings(null);
        WritableMap params = Arguments.createMap();
        params.putString("message", "onZendriveSettingsConfigChanged");
    }

}
