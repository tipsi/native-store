//
//  TPSStorage.m
//  example
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "TPSStorage.h"

@implementation TPSStorage
{
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
    storage = [NSMutableDictionary new];
  }
  return self;
}

- (void)setItem:(NSString *)key value:(NSString *)value
{
  [storage setValue:value forKey:key];
}

- (NSString *)getItem:(NSString *)key
{
  return [storage valueForKey:key];
}

- (void)removeItem:(NSString *)key
{
  [storage removeObjectForKey:key];
}

@end
