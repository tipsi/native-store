//
//  TPSStoreTests.m
//  TPSStoreTests
//
//  Created by Anton Petrov on 30.11.16.
//  Copyright © 2016 Tipsi. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "TPSStore.h"

@interface TPSStoreTests : XCTestCase
{
    TPSStore *store;
}

@end

@implementation TPSStoreTests

- (void)setUp {
    [super setUp];
    store = [TPSStore new];
    // Put setup code here. This method is called before the invocation of each test method in the class.
}

- (void)tearDown {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    store = nil;
    [super tearDown];
}

- (void) testInitialState
{
    NSDictionary *currentState = [store getState];
    
    XCTAssertTrue(
        [[currentState allKeys] count] == 0,
        @"Initial state is empty"
    );
}

- (void) testUpdateState
{
    NSDictionary *nextState = @{ @"test": @(1) };
    [store setState:nextState];
    NSDictionary *currentState = [store getState];
    
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
    
    [store subscribe:^(NSDictionary *currentState) {
        XCTAssertEqualObjects(
            currentState,
            nextState,
            @"Сurrent state(%@) not equal to new state(%@))",
            currentState,
            nextState
        );
        [expectation fulfill];
    }];
    
    [store setState:nextState];
    
    [self waitForExpectationsWithTimeout:5.0 handler:^(NSError *error) {
        if(error)
        {
            XCTFail(@"Expectation Failed with error: %@", error);
        }
    }];
}

- (void) testSubscriptionCallbackQueueWithoutSpecifyCallbackQueue
{
    NSDictionary *nextState = @{ @"test": @(1) };
    XCTestExpectation *expectation = [self expectationWithDescription:@"Testing Async Method Works!"];
    
    const char * callbackQueueLabel = dispatch_queue_get_label(DISPATCH_CURRENT_QUEUE_LABEL);
    [store subscribe:^(NSDictionary *currentState) {
        const char * sut = dispatch_queue_get_label(DISPATCH_CURRENT_QUEUE_LABEL);
        XCTAssertEqual(sut, callbackQueueLabel, @"Callback queue label should be (%s) but is (%s)", callbackQueueLabel, sut);
        
        [expectation fulfill];
    }];
    
    [store setState:nextState];
    
    [self waitForExpectationsWithTimeout:5.0 handler:^(NSError *error) {
        if(error)
        {
            XCTFail(@"Expectation Failed with error: %@", error);
        }
    }];
}

- (void) testSubscriptionCallbackQueueWithCallbackQueue
{
    NSDictionary *nextState = @{ @"test": @(1) };
    XCTestExpectation *expectation = [self expectationWithDescription:@"Testing Async Method Works!"];
    
    dispatch_queue_t callbackQueue = dispatch_queue_create("com.tipsi.TPSStore.TPSStoreTestsCallbackQueue", DISPATCH_QUEUE_CONCURRENT);
    const char * callbackQueueLabel = dispatch_queue_get_label(callbackQueue);
    [store subscribe:^(NSDictionary *currentState) {
        const char * sut = dispatch_queue_get_label(DISPATCH_CURRENT_QUEUE_LABEL);
        XCTAssertEqual(sut, callbackQueueLabel, @"Callback queue label should be: (%s) but is: (%s)", callbackQueueLabel, sut);
        
        [expectation fulfill];
    } callbackQueue:callbackQueue];
    
    [store setState:nextState];
    
    [self waitForExpectationsWithTimeout:5.0 handler:^(NSError *error) {
        if(error)
        {
            XCTFail(@"Expectation Failed with error: %@", error);
        }
    }];
}

@end
