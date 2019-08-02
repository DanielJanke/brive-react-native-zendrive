//
//  ZendriveDelegateManager.m
//  RNBriveReactNativeZendrive
//
//  Created by Daniel Janke on 22.07.19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <ZendriveSDK/Zendrive.h>
#import "ZendriveDelegateManager.h"
#import "RNBriveReactNativeZendrive.h"


@implementation ZendriveDelegateManager

- (void)processStartOfDrive:(ZendriveDriveStartInfo *)startInfo {
    NSLog(@"Start of Drive invoked");
}

- (void)processResumeOfDrive:(ZendriveDriveResumeInfo *)drive {
    NSLog(@"Resume of Drive invoked");
}

- (void)processEndOfDrive:(ZendriveDriveInfo *)drive {
    NSLog(@"End of Drive invoked");
}

- (void)processAnalysisOfDrive:(ZendriveAnalyzedDriveInfo *)drive {
    NSLog(@"Analysis of drive invoked");
}

- (void)processLocationDenied {
    NSLog(@"User denied Location to Zendrive SDK.");
}

- (void)processLocationApproved {
    NSLog(@"User approved Location to Zendrive SDK.");
}

- (void)processAccidentDetected:(ZendriveAccidentInfo *)accidentInfo {
    NSLog(@"Accident detected by Zendrive SDK.");
}

@end
