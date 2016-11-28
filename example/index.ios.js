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

    // await Storage.setState({ test: 123, some: { nested: { value: 'here' } } })
    // const state = await Storage.getState()
    // console.log('RESULT:', state)
  }

  componentWillUnmount() {
    this.listener.remove()
  }

  handleStateChange = (state) => {
    console.log('STATE FROM JS:', state)
    this.setState({
      input: state.input,
      uuid: state.uuid,
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
