//
//  TPSStorage.h
//  example
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TPSStorage : NSObject

- (void)setItem:(NSString *)key value:(NSString *)value;
- (NSString *)getItem:(NSString *)key;
- (void)removeItem:(NSString *)key;

+ (id)sharedInstance;

@end
