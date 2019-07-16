package com.reactlibrary;
import android.content.Context;

import com.zendrive.sdk.*;

public class MyZendriveBroadcastReceiver extends ZendriveBroadcastReceiver {

    @Override
    public void onDriveStart(Context context, DriveStartInfo driveStartInfo) {

    }

    @Override
    public void onDriveResume(Context context, DriveResumeInfo resumeInfo) {

    }

    @Override
    public void onDriveEnd(Context context, EstimatedDriveInfo estimatedDriveInfo) {

    }

    @Override
    public void onDriveAnalyzed(Context context, AnalyzedDriveInfo analyzedDriveInfo) {

    }

    @Override
    public void onAccident(Context context, AccidentInfo accidentInfo) {

    }

    @Override
    public void onZendriveSettingsConfigChanged(Context context, boolean errorsFound, boolean warningsFound) {
        //RNBriveReactNativeZendriveModule.sendEvent(context);
    }

}
