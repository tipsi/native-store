//
//  TPSStore.h
//  TPSStore
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EventEmitter.h"

@interface TPSStore : NSObject

- (void)setState:(NSDictionary *)nextState;
- (NSDictionary *)getState;
- (void)subscribe:(EventEmitterDefaultCallback) callback;
- (void)subscribe:(EventEmitterDefaultCallback) callback callbackQueue:(dispatch_queue_t) callbackQueue;
- (void)unsubscribe:(EventEmitterDefaultCallback) callback;

+ (id)sharedInstance;

@end
