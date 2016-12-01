//
//  TPSStorage.h
//  TPSStorage
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EventEmitter.h"

@interface TPSStorage : NSObject

- (void)setState:(NSDictionary *)nextState;
- (NSDictionary *)getState;
- (void)subscribe:(EventEmitterDefaultCallback) callback;
- (void)unsubscribe:(EventEmitterDefaultCallback) callback;

+ (id)sharedInstance;

@end
