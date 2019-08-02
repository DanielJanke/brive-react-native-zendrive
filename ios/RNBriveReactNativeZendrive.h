
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#endif

@interface RNBriveReactNativeZendrive : RCTEventEmitter <RCTBridgeModule>

@property (strong) id zendriveDelegate;

- (void)eventReceived:(NSString *)event;

@end

