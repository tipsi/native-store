import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  TextInput,
  Text,
  View
} from 'react-native'
import Storage from './src/Storage'

export default class example extends Component {

  state = {
    result: '',
    input: '',
  }

  componentDidMount() {
    this.listener = Storage.subscribe(
      'input',
      this.handleChangeTextInState
    )

    // await Storage.setItem('test', 'some string')
    // const value = await Storage.getItem('test')
    // console.log('RESULT:', value)
  }

  componentWillUnmount() {
    this.listener.remove()
  }

  handleChangeTextInState = (text) => {
    this.setState({ result: text })
    console.log(text)
  }

  handleChangeTextInStorage = (text) => {
    Storage.setItem('input', text)
    this.setState({ input: text })
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.welcome}>
          Result: {this.state.result}
        </Text>
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.input}
            value={this.state.input}
            onChangeText={this.handleChangeTextInStorage}
          />
        </View>
        <Text style={styles.instructions}>
          To get started, edit index.ios.js
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
    margin: 5
  },
})

AppRegistry.registerComponent('example', () => example)
