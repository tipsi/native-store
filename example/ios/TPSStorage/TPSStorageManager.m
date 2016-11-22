//
//  TPSStorage.m
//  example
//
//  Created by Anton Petrov on 22.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "TPSStorageManager.h"
#import "RCTLog.h"

@implementation TPSStorageManager
{
  NSMutableDictionary *storage;
}

RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents
{
  return @[@"storage:change"];
}

RCT_EXPORT_METHOD(init:(NSDictionary *)options)
{
  storage = [[NSMutableDictionary alloc] initWithDictionary:options];
}

RCT_EXPORT_METHOD(setItem:(NSString *)key
                    value:(NSString *)value
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)

{
  [storage setValue:value forKey:key];
  
  [self notifyReactNativeAboutChange:key forValue:value];
  
  resolve(nil);
}

RCT_EXPORT_METHOD(getItem:(NSString *)key
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)

{
  NSString *value = [storage valueForKey:key];

  resolve(value);
}

RCT_EXPORT_METHOD(removeItem:(NSString *)key
                    resolver:(RCTPromiseResolveBlock)resolve
                    rejecter:(RCTPromiseRejectBlock)reject)

{
  [storage removeObjectForKey:key];
  
  [self notifyReactNativeAboutChange:key forValue:nil];
  
  resolve(nil);
}

- (void)notifyReactNativeAboutChange:(NSString *)key forValue:(NSString *)value
{
  [self sendEventWithName:@"storage:change" body:@{ @"key": key, @"value": value }];
}


@end
