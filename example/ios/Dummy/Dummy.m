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

- (id)init
{
  self = [super init];
  if (self != nil) {
    [self startTest];
  }
  return self;
}

- (void)startTest
{
  [[TPSStorage sharedInstance] subscribe:^(NSDictionary* state) {
    NSLog(@"STATE FROM NATIVE:%@", state);
  }];
  
  [NSTimer scheduledTimerWithTimeInterval:2.0 target:self selector:@selector(onTick:) userInfo:nil repeats:YES];
}

-(void)onTick:(NSTimer *)timer {
  NSDictionary *state = [[TPSStorage sharedInstance] getState];

  [state setValue:[[NSUUID UUID] UUIDString] forKey:@"uuid"];

  [[TPSStorage sharedInstance] setState:state];
}

@end
