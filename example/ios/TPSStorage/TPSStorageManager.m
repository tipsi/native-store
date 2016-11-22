//
//  TPSStorage.m
//  example
//
//  Created by Anton Petrov on 22.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "TPSStorageManager.h"
#import "TPSStorage.h"
#import "RCTLog.h"

@implementation TPSStorageManager

RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"storage:change"];
}

RCT_EXPORT_METHOD(init:(NSDictionary *)options)
{

}

RCT_EXPORT_METHOD(setItem:(NSString *)key
                    value:(NSString *)value
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)

{
  [[TPSStorage sharedInstance] setItem:key value:value];
  
  [self notifyReactNativeAboutChange:key forValue:value];
  
  resolve(nil);
}

RCT_EXPORT_METHOD(getItem:(NSString *)key
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)

{
  NSString *value = [[TPSStorage sharedInstance] getItem:key];

  resolve(value);
}

RCT_EXPORT_METHOD(removeItem:(NSString *)key
                    resolver:(RCTPromiseResolveBlock)resolve
                    rejecter:(RCTPromiseRejectBlock)reject)

{
  [[TPSStorage sharedInstance] removeItem:key];
  
  [self notifyReactNativeAboutChange:key forValue:nil];
  
  resolve(nil);
}

- (void)notifyReactNativeAboutChange:(NSString *)key forValue:(NSString *)value
{
  [self sendEventWithName:@"storage:change" body:@{ @"key": key, @"value": value }];
}

@end
