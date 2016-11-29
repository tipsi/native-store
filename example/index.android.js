/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */


//import React, { Component } from 'react';
//import NativeStore from 'native-store';
//import {
//  AppRegistry,
//  StyleSheet,
//  Text,
//  View
//} from 'react-native';
//
//NativeStore.init(
//       {price: '100.00',
//        currency: 'USD',})
//
//export default class example extends Component {
//
//    state = {
//      js_js: 'None',
//      js_native: 'None',
//      native_js: 'None',
//      native_native: 'None',
//    }
//
//  render() {
//
//  const state = this.state
//
//    return (
//      <View style={styles.container}>
//        <Text style={styles.welcome}>
//          Data from Android Native Store 3
//        </Text>
//        <Text style={styles.dataFile}>
//          JS - JS : {state.js_js}
//        </Text>
//        <Text style={styles.dataFile}>
//          JS - Native : {state.js_native}
//        </Text>
//        <Text style={styles.dataFile}>
//          Native - JS : {state.native_js}
//        </Text>
//        <Text style={styles.dataFile}>
//          Native - Native : {state.native_native}
//        </Text>
//        </View>
////        <Text style={styles.instructions}>
////          Double tap R on your keyboard to reload,{'\n'}
////          Shake or press menu button for dev menu
////        </Text>
////      </View>
//    );
//  }
//}
//
//const styles = StyleSheet.create({
//  container: {
//    flex: 1,
//    justifyContent: 'center',
//    alignItems: 'center',
//    backgroundColor: '#F5FCFF',
//  },
//  welcome: {
//    fontSize: 23,
//    color: '#000000',
//    textAlign: 'center',
//    margin: 10,
//  },
//  instructions: {
//    textAlign: 'center',
//    color: '#333333',
//    marginBottom: 5,
//  },
//    dataFile: {
//      fontSize: 25,
//      textAlign: 'center',
//      color: '#008000',
//      marginTop: 25,
//      marginBottom: 25,
//      marginLeft: 10,
//      marginRight: 10,
//    },
//        dataFileTitle: {
//          fontSize: 25,
//          textAlign: 'center',
//          color: '#333333',
//          marginTop: 25,
//          marginBottom: 25,
//          marginLeft: 10,
//          marginRight: 10,
//        },
//});
//
//AppRegistry.registerComponent('example', () => example);

import React, { Component } from 'react'
import {
  AppRegistry,
  StyleSheet,
  TextInput,
  Text,
  View
} from 'react-native'
import Storage from 'native-store'

export default class example extends Component {

  state = {
    input: '',
    uuid: '',
  }

  async componentDidMount() {
    this.listener = Storage.subscribe(this.handleStateChange)

//     await Storage.setState({ test: 123, some: { nested: { value: 'here' } } })
//     const state = await Storage.getState()
//     console.log('RESULT:', state)
  }

  componentWillUnmount() {
    this.listener.remove()
  }

  handleStateChange = (state) => {
    console.log('STATE FROM JS:', state)
    console.log('STATE FROM JS PARSED:', state[0])
    this.setState({
      input: state[0].input,
      uuid: state[0].uuid,
    })
  }

  // updateValueFromNative = (value) => {
  //   this.setState({ fromNative: value })
  // }
  //
  // updateValueInput = (value) => {
  //   this.setState({ fromJS: value })
  // }
  //
  handleChangeTextInState = (text) => {
    Storage.setState({ input: text })
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Storage Example
        </Text>
        <Text style={styles.label}>
          Value from native:
        </Text>
        <Text style={styles.value}>
          {this.state.uuid}
        </Text>
        <Text style={styles.label}>
          Input result:
        </Text>
        <Text style={styles.value}>
          {this.state.input}
        </Text>
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.input}
            onChangeText={this.handleChangeTextInState}
          />
        </View>
        <Text style={styles.instructions}>
          subscribe (js) -> set state (js) -> print result (js)
        </Text>
        <Text style={styles.instructions}>
          Press Cmd+R to reload,{'\n'}
          Cmd+D or shake for dev menu
        </Text>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    justifyContent: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  label: {
    textAlign: 'center',
    color: '#333333',
    margin: 5,
  },
  value: {
    fontSize: 15,
    textAlign: 'center',
    margin: 5,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  inputContainer: {
    // justifyContent: 'center',
    // display: 'flex',
    // height: 30,
    flexDirection: 'row',
  },
  input: {
    height: 26,
    borderWidth: 0.5,
    borderColor: '#0f0f0f',
    flex: 1,
    fontSize: 13,
    padding: 5,
    margin: 5,
  },
})

AppRegistry.registerComponent('example', () => example)

