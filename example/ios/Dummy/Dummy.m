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
  [[TPSStorage sharedInstance] subscribe:@"from_js" callback:^(NSDictionary* data) {
    NSLog(@"VALUE FROM JS:%@", [data valueForKey:@"value"]);
  }];
  
  [NSTimer scheduledTimerWithTimeInterval:2.0 target:self selector:@selector(onTick:) userInfo:nil repeats:YES];
}

-(void)onTick:(NSTimer *)timer {
  [[TPSStorage sharedInstance] setItem:[[NSUUID UUID] UUIDString] forKey:@"from_native"];
}

@end
