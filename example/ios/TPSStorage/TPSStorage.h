//
//  TPSStorage.h
//  example
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EventEmitter.h"

@interface TPSStorage : NSObject

- (void)setItem:(NSString *)value forKey:(NSString *)key;
- (NSString *)getItemForKey:(NSString *)key;
- (void)removeItemForKey:(NSString *)key;
- (void)subscribe:(EventEmitterDefaultCallback) callback;
- (void)subscribe:(NSString*)key callback:(EventEmitterDefaultCallback)callback;
- (void)unsubscribe:(EventEmitterDefaultCallback) callback;
- (void)unsubscribe:(NSString*)key callback:(EventEmitterDefaultCallback)callback;

+ (id)sharedInstance;

@end
