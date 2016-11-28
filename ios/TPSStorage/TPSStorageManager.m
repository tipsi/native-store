//
//  TPSStorageManager.m
//  TPSStorage
//
//  Created by Anton Petrov on 22.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "TPSStorageManager.h"
#import "TPSStorage.h"
#import "RCTLog.h"

@implementation TPSStorageManager
{
    TPSStorage *storage;
}

RCT_EXPORT_MODULE();

- (id)init
{
    self = [super init];
    if (self != nil) {
        storage = [TPSStorage sharedInstance];
        // Subscribe on global updates
        [storage subscribe:^(NSDictionary* data) {
            [self sendEventWithName:@"storage:change" body:data];
        }];
    }
    return self;
}

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"storage:change"];
}

RCT_EXPORT_METHOD(setItem:(NSString *)key
                  value:(NSString *)value
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)

{
    [storage setItem:value forKey:key];
    
    resolve(nil);
}

RCT_EXPORT_METHOD(getItem:(NSString *)key
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)

{
    NSString *value = [storage getItemForKey:key];
    
    resolve(value);
}

RCT_EXPORT_METHOD(removeItem:(NSString *)key
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)

{
    [storage removeItemForKey:key];
    
    resolve(nil);
}

@end
