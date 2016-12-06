//
//  TPSStorage.m
//  TPSStorage
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "TPSStore.h"

@implementation TPSStore
{
    EventEmitter *emitter;
    NSDictionary *state;
}

+ (instancetype)sharedInstance
{
    static TPSStore *sharedInstance = nil;
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
        state = [NSMutableDictionary new];
    }
    return self;
}

- (void)setState:(NSDictionary *)nextState
{
    state = nextState;
    
    [self notify];
}

- (NSDictionary *)getState
{
    return state;
}

- (void)subscribe:(EventEmitterDefaultCallback)callback
{
    [emitter on:@"state:change" callback:callback];
}

- (void)unsubscribe:(EventEmitterDefaultCallback) callback
{
    [emitter removeCallback:callback];
}

- (void)notify
{
    [emitter emit:@"state:change" data:state];
}

@end
