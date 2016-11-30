//
//  TPSStorageTests.m
//  TPSStorageTests
//
//  Created by Anton Petrov on 30.11.16.
//  Copyright © 2016 Tipsi. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "TPSStorage.h"

@interface TPSStorageTests : XCTestCase
{
    TPSStorage *storage;
}

@end

@implementation TPSStorageTests

- (void)setUp {
    [super setUp];
    storage = [TPSStorage new];
    // Put setup code here. This method is called before the invocation of each test method in the class.
}

- (void)tearDown {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    storage = nil;
    [super tearDown];
}

- (void) testInitialState
{
    NSDictionary *currentState = [storage getState];

    XCTAssertTrue(
        [[currentState allKeys] count] == 0,
        @"Initial state is empty"
    );
}

- (void) testUpdateState
{
    NSDictionary *nextState = @{ @"test": @(1) };
    [storage setState:nextState];
    NSDictionary *currentState = [storage getState];

    XCTAssertEqualObjects(
        currentState,
        nextState,
        @"Сurrent state(%@) not equal to new state(%@))",
        currentState,
        nextState
    );

}

- (void) testSubscription
{
    NSDictionary *nextState = @{ @"test": @(1) };
    XCTestExpectation *expectation = [self expectationWithDescription:@"Testing Async Method Works!"];

    [storage subscribe:^(NSDictionary *currentState) {
        XCTAssertEqualObjects(
            currentState,
            nextState,
            @"Сurrent state(%@) not equal to new state(%@))",
            currentState,
            nextState
        );
        [expectation fulfill];
    }];

    [storage setState:nextState];

    [self waitForExpectationsWithTimeout:5.0 handler:^(NSError *error) {
        if(error)
        {
            XCTFail(@"Expectation Failed with error: %@", error);
        }
    }];
}

@end
