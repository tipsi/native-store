import React, { Component } from 'react'
import {
  StyleSheet,
  TextInput,
  Text,
  View,
} from 'react-native'
import Storage from 'native-store'

export default class Root extends Component {
  state = {
    input: '',
    uuid: '',
  }

  componentDidMount() {
    this.unsubscribe = Storage.subscribe(this.handleStateChange)
  }

  componentWillUnmount() {
    this.unsubscribe()
  }

  handleStateChange = (state) => {
    this.setState({
      input: state.input,
      uuid: state.uuid,
    })
  }

  handleChangeTextInState = async (text) => {
    const state = await Storage.getState()
    Storage.setState({ ...state, input: text })
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Storage Example
        </Text>

        <View style={styles.box}>
          <Text style={styles.label}>
            Value from input:
          </Text>
          <Text
            accessible
            accessibilityLabel={'valueFromInput'}
            testID={'valueFromInput'}
            style={styles.value}>
            {this.state.input || 'empty value'}
          </Text>
          <View style={styles.inputContainer}>
            <TextInput
              accessible
              accessibilityLabel={'textInput'}
              testID={'textInput'}
              style={styles.input}
              onChangeText={this.handleChangeTextInState}
            />
          </View>
          <Text style={styles.instructions}>
            {'subscribe (js) -> set state (js) -> print result (js)'}
          </Text>
        </View>
        <Text style={styles.instructions}>
          {'Enter text in the input to change "input" field in state'}
        </Text>
        <View style={styles.box}>
          <Text style={styles.label}>
            Value from native:
          </Text>
          <Text
            accessible
            accessibilityLabel={'valueFromNative'}
            testID={'valueFromNative'}
            style={styles.value}>
            {this.state.uuid || 'empty value'}
          </Text>
          <Text style={styles.instructions}>
            {'set state (native) -> print result (js)'}
          </Text>
        </View>

        <Text style={styles.instructions}>
          {'The "uuid" field changes in the native code by timer'}
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
  box: {
    backgroundColor: 'white',
    borderRadius: 5,
    borderWidth: 1,
    borderColor: '#333333',
    padding: 10,
    margin: 20,
  },
  label: {
    fontSize: 16,
    textAlign: 'center',
    margin: 5,
  },
  value: {
    fontSize: 15,
    textAlign: 'center',
    color: '#333333',
    margin: 5,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  inputContainer: {
    flexDirection: 'row',
  },
  input: {
    height: 26,
    borderWidth: 0.5,
    borderColor: '#0f0f0f',
    borderRadius: 5,
    flex: 1,
    fontSize: 13,
    padding: 5,
    margin: 5,
  },
})
