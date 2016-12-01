//
//  Dummy.m
//  example
//
//  Created by Anton Petrov on 23.11.16.
//  Copyright © 2016 Facebook. All rights reserved.
//

#import "Dummy.h"
#import "TPSStorage.h"

@implementation Dummy
{
  TPSStorage *storage;
}

- (id)init
{
  self = [super init];
  if (self != nil) {
    storage = [TPSStorage sharedInstance];
    [self startTest];
  }
  return self;
}

- (void)startTest
{
  [storage subscribe:^(NSDictionary* state) {
    NSLog(@"STATE CHANGED:%@", state);
  }];
  
  [NSTimer scheduledTimerWithTimeInterval:2.0 target:self selector:@selector(onTick:) userInfo:nil repeats:YES];
}

-(void)onTick:(NSTimer *)timer {
  NSMutableDictionary *nextState = [NSMutableDictionary dictionaryWithDictionary:[storage getState]];

  [nextState setValue:[[NSUUID UUID] UUIDString] forKey:@"uuid"];

  [[TPSStorage sharedInstance] setState:nextState];
}

@end
