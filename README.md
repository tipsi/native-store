# native-store

[![npm version](https://img.shields.io/npm/v/native-store.svg?style=flat-square)](https://www.npmjs.com/package/native-store)
[![build status](https://img.shields.io/travis/tipsi/native-store/master.svg?style=flat-square)](https://travis-ci.org/tipsi/native-store)

Native store for react-native

## Requirements

### iOS

* Xcode 8+
* iOS 9+

### Android

* Minimum SDK 16

## Installation

Run `npm install --save native-store` to add the package to your app's dependencies.

### iOS

#### react-native cli

Run `react-native link native-store` so your project is linked against your Xcode project and all CocoaPods dependencies are installed.

#### CocoaPods

1. Open your project in Xcode workspace.
2. Drag the following folder into your project:
  * `node_modules/native-store/ios/TPSStore/`

#### Manual

1. Open your project in Xcode, right click on Libraries and click `Add Files to "Your Project Name"`.
2. Look under `node_modules/native-store/ios` and add `TPSStore.xcodeproj`.
3. Add `libTPSStore.a` to `Build Phases` -> `Link Binary With Libraries`.
4. Click on `TPSStore.xcodeproj` in Libraries and go the Build Settings tab. Double click the text to the right of `Header Search Paths` and verify that it has `$(SRCROOT)/../../react-native/React` - if they aren't, then add them. This is so Xcode is able to find the headers that the `TPSStore` source files are referring to by pointing to the header files installed within the `react-native` `node_modules` directory.
5. Whenever you want to use it within React code now you can:
  * `import Storage from 'native-store'`

### Android

#### react-native cli

Run `react-native link native-store` so your project is linked against your Android project

#### Manual

In your `app build.gradle` add:

```gradle
...
dependencies {
 ...
 compile project(':native-store')
}
```

In your `settings.gradle` add:

```gradle
...
include ':native-store'
project(':native-store').projectDir = new File(rootProject.projectDir, '../node_modules/native-store/android')
```
## Usage

Let's require `native-store` module:

```js
import Storage from 'native-store'
```

To change `store` state from React use:

```js
Storage.setState({...})
```

To get the current state of native `store` use:

```js
const state = await Storage.getState()
```

To subscribe on `store` state changes use:

```js
this.unsubscribe = Storage.subscribe(this.yourFunction)
```
As a result you will get `unsubscribe` function.

Don't forget to unsubscribe, typically you can do it in `componentWillUnmount`

```js
  componentWillUnmount() {
    this.unsubscribe()
  }
```

## Tests

#### Local CI

To run `native-store` unit tests and `example` app e2e tests for all platforms you can use `npm run ci` command.

#### Manual

1. Go to example folder `cd example`
2. Install npm dependencies `npm install`
3. Build project:
  * `npm run build:ios` - for iOS
  * `npm run build:android` - for Android
  * `npm run build` - for both iOS and Android
4. Open Appium in other tab `npm run appium`
5. Run tests:
  * `npm run test:ios` - for iOS
  * `npm run test:android` - for Android
  * `npm run test` - for both iOS and Android

#### Troubleshooting

You might encounter the following error while trying to run tests:

`An unknown server-side error occurred while processing the command. Original error: Command \'/bin/bash Scripts/bootstrap.sh -d\' exited with code 1`

You can fix it by installing `Carthage`:

```bash
brew install carthage
```

## Example

To see more of the `native-store` in action, you can check out the source in [example](https://github.com/tipsi/native-store/tree/master/example) folder.

##  License

native-store is available under the MIT license. See the [LICENSE](https://github.com/tipsi/native-store/tree/master/LICENSE) file for more info.
