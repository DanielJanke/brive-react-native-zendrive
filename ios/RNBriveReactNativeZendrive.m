
#import "RNBriveReactNativeZendrive.h"
#import <React/RCTLog.h>
#import <ZendriveSDK/Zendrive.h>
#import <ZendriveSDK/ZendriveDebug.h>
#import <React/RCTConvert.h>
#import "ZendriveDelegateManager.h"

@implementation RCTConvert (ZendriveDriveDetectionMode)
RCT_ENUM_CONVERTER(ZendriveDriveDetectionMode, (@{ @"ZendriveDriveDetectionModeAutoOn" : @(ZendriveDriveDetectionModeAutoON),
                                                   @"ZendriveDriveDetectionModeAutoOff" : @(ZendriveDriveDetectionModeAutoOFF),
                                             }),
                   ZendriveDriveDetectionModeAutoON, integerValue)
@end



@implementation RNBriveReactNativeZendrive

- (NSString*)formatEventSupport:(NSString*)eventType {
    NSString *result = nil;

        switch ([eventType intValue]) {
            case 0:
                result = @"ZendriveEventHardBrake";
                break;
            case 1:
                result = @"ZendriveEventAggressiveAcceleration";
                break;
            case 2:
                result = @"ZendriveEventPhoneHandling";
                break;
            case 3:
                result = @"ZendriveEventOverSpeeding";
                break;
            case 4:
                result = @"ZendriveEventAccident";
                break;
            case 5:
                result = @"ZendriveEventHardTurn";
                break;
            case 6:
                result = @"ZendriveEventPhoneScreenInteraction";
                break;
        default:
            result = @"Unexpected FormatType";
            break;
    }
    return result;
};

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (NSDictionary *)constantsToExport
{
    return @{ @"ZendriveDriveDetectionModeAutoOn" : @(ZendriveDriveDetectionModeAutoON),
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
    configuration.driveDetectionMode = ZendriveDriveDetectionModeAutoON;

    ZendriveDriverAttributes *driverAttributes = [[ZendriveDriverAttributes alloc] init];
    
    [driverAttributes setAlias: [NSString stringWithFormat:@"%@ %@", firstName, lastName]];
    [driverAttributes setGroup: group];
    
    configuration.driverAttributes = driverAttributes;
    
    id<ZendriveDelegateProtocol> zdDelegate = [[ZendriveDelegateManager alloc] init];
    self.zendriveDelegate = zdDelegate; // keep a strong reference to the delegate object

    [Zendrive setupWithConfiguration:configuration
                            delegate:zdDelegate
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
  callback:(RCTResponseSenderBlock)callback
)
{
    RCTLogInfo(@"startDrive");
    [Zendrive startManualDrive:trackingId completionHandler:^(BOOL success, NSError *error) {
        if(success) {
            RCTLogInfo(@"setup sucess");
            callback(@[@""]);
        } else {
            RCTLogInfo(@"setup error");
            callback(@[@"Start Drive Error"]);
        }
    }];
    [self sendEventWithName:@"delegateEvent" body:@{@"message": @"startDrive"}];
}

RCT_EXPORT_METHOD(
    stopDrive
)
{
    RCTLogInfo(@"stopDrive");
    [Zendrive stopManualDrive:^(BOOL success, NSError *error) {
        if(success) {
            RCTLogInfo(@"setup sucess");
//            successCallback(@[[NSNull null]]);
        } else {
            RCTLogInfo(@"setup error");
//            errorCallback(@[[NSNull null]]);
        }
    }];
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
    
    RCTLogInfo(@"My dictionary is %@", eventSupport);
    
    for (NSString* key in eventSupport) {
        
        RCTLogInfo(@"KEY");
        RCTLogInfo(@"%@", key);
        NSString* convertedEventSupportType = [self formatEventSupport:key];
        RCTLogInfo(@"%@", convertedEventSupportType);
        [eventSupportConverted setObject:[eventSupport objectForKey:key] forKey:convertedEventSupportType];
        count++;
//       RCTLogInfo(eventSupportConverted);
        
    }
//    callback(@[[NSNull null], eventSupportConverted]);
        callback(@[eventSupportConverted]);
}

RCT_EXPORT_METHOD(
                  setDriveDetectionMode:
                  (ZendriveDriveDetectionMode)ZendriveDriveDetectionMode
                  callback:(RCTResponseSenderBlock)callback
                  
)
{
    [Zendrive setDriveDetectionMode:ZendriveDriveDetectionMode completionHandler:^(BOOL success, NSError *error) {
        if(success) {
            RCTLogInfo(@"setup sucess");
            callback(@[[NSNull null], @"Success"]);
        } else {
            RCTLogInfo(@"setup error");
            callback(@[@"Error", [NSNull null]]);
        }
    }];
}

RCT_EXPORT_METHOD(
    activeDriveInfo:(RCTResponseSenderBlock)successCallback
) {
    RCTLogInfo(@"activeDriveInfo");
    ZendriveActiveDriveInfo *activeDriveInfo = [Zendrive activeDriveInfo];
    if (activeDriveInfo == nil) {
        RCTLogInfo(@"activeDriveInfo == Nil");
        successCallback(@[@"No active drive", [NSNull null]]);
    }
    else {
        RCTLogInfo(activeDriveInfo.driveId);
        successCallback(@[[NSNull null], activeDriveInfo.driveId]);
    }
}

RCT_EXPORT_METHOD(
                  getZendriveState:(RCTResponseSenderBlock)successCallback
  )
{
    RCTLogInfo(@"stopSession");
    [Zendrive getZendriveState:^(ZendriveState * _Nullable zendriveState) {
        successCallback(@[[NSNumber numberWithBool:zendriveState.isDriveInProgress], [NSNumber numberWithBool:false], @"{}"]);
    }];
}


RCT_EXPORT_METHOD(
    teardown
)
{
    RCTLogInfo(@"stopSession");
    [Zendrive teardownWithCompletionHandler:^{
        
    }];
}

RCT_EXPORT_METHOD(
    debug
)
{

}

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"delegateEvent"];
}

- (void)eventReceived:(NSString *)event
{
    [self sendEventWithName:@"delegateEvent" body:@{@"message": event}];
}

@end
  
