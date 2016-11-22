/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

export default class AwesomeProject extends Component {

//  state = {
//    js_js: 'Text for JS - JS',
//    js_native: 'Text for JS - Native',
//    native_js: 'Text for Native - JS',
//    native_native: 'Text for Native - Native',
//  }

    state = {
      js_js: 'None',
      js_native: 'None',
      native_js: 'None',
      native_native: 'None',
    }

  render() {

  const state = this.state

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Data from Android Native Store
        </Text>
        <Text style={styles.dataFile}>
          JS - JS : {state.js_js}
        </Text>
        <Text style={styles.dataFile}>
          JS - Native : {state.js_native}
        </Text>
        <Text style={styles.dataFile}>
          Native - JS : {state.native_js}
        </Text>
        <Text style={styles.dataFile}>
          Native - Native : {state.native_native}
        </Text>
        </View>
//        <Text style={styles.instructions}>
//          Double tap R on your keyboard to reload,{'\n'}
//          Shake or press menu button for dev menu
//        </Text>
//      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 23,
    color: '#000000',
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
    dataFile: {
      fontSize: 25,
      textAlign: 'center',
      color: '#008000',
      marginTop: 25,
      marginBottom: 25,
      marginLeft: 10,
      marginRight: 10,
    },
        dataFileTitle: {
          fontSize: 25,
          textAlign: 'center',
          color: '#333333',
          marginTop: 25,
          marginBottom: 25,
          marginLeft: 10,
          marginRight: 10,
        },
});

AppRegistry.registerComponent('AwesomeProject', () => AwesomeProject);
