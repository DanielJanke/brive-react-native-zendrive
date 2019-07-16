declare module 'RNBriveReactNativeZendrive' {
  export interface RNBriveReactNativeZendrive {
    setup(sdkApplicationKey: string, driverId: string, firstName: string, lastName: string, group: string, successCallback: () => void, errorCallback: () => void): void;
    startSession(sessionId: string):void;
    stopSession():void;
    startDrive(trackingId: string):void;
    stopDrive():void;
    isSDKSetup(successCallback: (isSDKSetup: boolean) => void ): void;
    getEventSupportForDevice(callback: ()=> void):void;
    setDriveDetectionMode(setDriveDetectionMode: string):void;
    teardown():void;
  }

  export default RNBriveReactNativeZendrive
}

