//
//  TPSStorage.m
//  TPSStorage
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "TPSStorage.h"

@implementation TPSStorage
{
    EventEmitter *emitter;
    NSMutableDictionary *storage;
}

+ (instancetype)sharedInstance
{
    static TPSStorage *sharedInstance = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        sharedInstance = [self new];
    });
    
    return sharedInstance;
}

- (id)init
{
    self = [super init];
    if (self != nil) {
        emitter = [EventEmitter new];
        storage = [NSMutableDictionary new];
    }
    return self;
}

- (void)setState:(NSDictionary *)state
{
    [storage setDictionary:state];
    
    [self notify];
}

- (NSDictionary *)getState
{
    return storage;
}

- (void)subscribe:(EventEmitterDefaultCallback)callback
{
    [emitter on:@"storage:change" callback:callback];
}

- (void)unsubscribe:(EventEmitterDefaultCallback) callback
{
    [emitter removeCallback:callback];
}

- (void)notify
{
    [emitter emit:@"storage:change" data:storage];
}

@end
