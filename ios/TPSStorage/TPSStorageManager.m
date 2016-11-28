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
        // Subscribe on store updates
        [storage subscribe:^(NSDictionary* state) {
            [self sendEventWithName:@"storage:change" body:state];
        }];
    }
    return self;
}

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"storage:change"];
}

RCT_EXPORT_METHOD(setState:(NSDictionary *)state
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)

{
    [storage setState:state];
    
    resolve(nil);
}

RCT_EXPORT_METHOD(getState:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)

{
    NSDictionary *state = [storage getState];
    
    resolve(state);
}

@end
