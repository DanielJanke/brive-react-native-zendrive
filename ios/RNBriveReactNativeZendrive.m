
#import "RNBriveReactNativeZendrive.h"
#import <React/RCTLog.h>
#import <ZendriveSDK/Zendrive.h>
#import <React/RCTConvert.h>

@implementation RCTConvert (ZendriveDriveDetectionMode)
RCT_ENUM_CONVERTER(ZendriveDriveDetectionMode, (@{ @"ZendriveDriveDetectionModeAutoON" : @(ZendriveDriveDetectionModeAutoON),
                                                   @"ZendriveDriveDetectionModeAutoOff" : @(ZendriveDriveDetectionModeAutoOFF),
                                             }),
                   ZendriveDriveDetectionModeAutoON, integerValue)
@end


@implementation RNBriveReactNativeZendrive



- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (NSDictionary *)constantsToExport
{
    return @{ @"ZendriveDriveDetectionModeAutoON" : @(ZendriveDriveDetectionModeAutoON),
              @"ZendriveDriveDetectionModeAutoOff" : @(ZendriveDriveDetectionModeAutoOFF),
               };
};


RCT_EXPORT_MODULE()


RCT_EXPORT_METHOD(setup: 
    (NSString *)sdkApplicationKey 
    driverId:(NSString *)driverId
    firstName:(NSString *)firstName
    lastName:(NSString *)lastName
    group:(NSString *)group
    successCallback:(RCTResponseSenderBlock)successCallback
    errorCallback:(RCTResponseSenderBlock)errorCallback
)
{
    ZendriveConfiguration *configuration = [[ZendriveConfiguration alloc] init];
    configuration.applicationKey = sdkApplicationKey; // REQUIRED. This is your Zendrive SDK key.
    configuration.driverId = driverId; // REQUIRED

    ZendriveDriverAttributes *driverAttributes = [[ZendriveDriverAttributes alloc] init];
    [driverAttributes setFirstName: firstName];
    [driverAttributes setLastName: lastName];
    [driverAttributes setGroup: group];
    
    configuration.driverAttributes = driverAttributes;
    
    // configuration.driveDetectionMode = ZendriveDriveDetectionModeAutoOFF;

    [Zendrive setupWithConfiguration:configuration
                            delegate:nil // Can be nil
                            completionHandler:^(BOOL success, NSError *error) {
                       if(success) {
                           RCTLogInfo(@"setup sucess");
                          successCallback(@[[NSNull null]]);
                       } else {
                           RCTLogInfo(@"setup error");
                          errorCallback(@[[NSNull null]]);
                       }
                   }];
    
}

RCT_EXPORT_METHOD(
    startSession:
    (NSString *)sessionId
)
{   
    RCTLogInfo(@"startSession");
    RCTLogInfo(sessionId);
    [Zendrive startSession:sessionId];
}

RCT_EXPORT_METHOD(
    stopSession
)
{
    RCTLogInfo(@"stopSession");
    [Zendrive stopSession];
}

RCT_EXPORT_METHOD(
    startDrive:
    (NSString *)trackingId
)
{
    RCTLogInfo(@"startDrive");
    [Zendrive startDrive:trackingId];
}

RCT_EXPORT_METHOD(
    stopDrive
)
{
    RCTLogInfo(@"stopDrive");
    [Zendrive stopManualDrive];
}


RCT_EXPORT_METHOD(
  isSDKSetup:(RCTResponseSenderBlock)successCallback)
{
    bool isSDKSetup = [Zendrive isSDKSetup];
    successCallback(@[[NSNull null], [NSNumber numberWithBool:isSDKSetup]]);
    
}


RCT_EXPORT_METHOD(getEventSupportForDevice:(RCTResponseSenderBlock)callback)
{
    NSDictionary *eventSupport = [Zendrive getEventSupportForDevice];
    NSMutableDictionary *eventSupportConverted = [[NSMutableDictionary alloc] init];
    NSUInteger count = 0;
    
    for (id key in eventSupport) {
        [eventSupportConverted setObject:[eventSupport objectForKey:key] forKey:[@(count) stringValue]];
//        [eventSupportConverted setValue:[eventSupport objectForKey:key] forKey:[@(count) stringValue]];
        count++;
//        RCTLogInfo([]);
    }
    callback(@[[NSNull null], eventSupportConverted]);
}

RCT_EXPORT_METHOD(
                  setDriveDetectionMode:(ZendriveDriveDetectionMode)ZendriveDriveDetectionMode
)
{
    [Zendrive setDriveDetectionMode:ZendriveDriveDetectionMode];
}

RCT_EXPORT_METHOD(
    teardown
)
{
    RCTLogInfo(@"stopSession");
    [Zendrive teardownWithCompletionHandler:^{
        
    }];
}


@end
  
