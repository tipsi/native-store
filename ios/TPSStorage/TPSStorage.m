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

- (void)setItem:(NSString *)value forKey:(NSString *)key
{
    [storage setValue:value forKey:key];
    
    [self notify:key value:value];
}

- (NSString *)getItemForKey:(NSString *)key
{
    return [storage valueForKey:key];
}

- (void)removeItemForKey:(NSString *)key
{
    [storage removeObjectForKey:key];
    
    [self notify:key value:nil];
}

- (void)subscribe:(EventEmitterDefaultCallback)callback
{
    [emitter on:@"storage:all" callback:callback];
}

- (void)subscribe:(NSString *)key callback:(EventEmitterDefaultCallback)callback
{
    [emitter on:[NSString stringWithFormat:@"storage:key:%@", key] callback:callback];
}

- (void)unsubscribe:(EventEmitterDefaultCallback) callback
{
    [emitter removeCallback:callback];
}

- (void)unsubscribe:(NSString*)key callback:(EventEmitterDefaultCallback)callback
{
    [emitter removeListener:key callback:callback];
}

- (void)notify:(NSString *)key value:(NSString *)value
{
    NSDictionary *data = @{ @"key": key, @"value": value };
    
    [emitter emit:@"storage:all" data:data];
    [emitter emit:[NSString stringWithFormat:@"storage:key:%@", key] data:data];
}

@end
