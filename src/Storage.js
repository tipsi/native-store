import { NativeModules } from 'react-native'
import NativeEventEmitter from 'react-native/Libraries/EventEmitter/NativeEventEmitter'

const { TPSStorageManager } = NativeModules
const TPSStorageEventEmitter = new NativeEventEmitter(TPSStorageManager)

if (__DEV__) {
  TPSStorageEventEmitter.addListener('storage:change', () => {})
}

class Storage {
  setState = state => (
    TPSStorageManager.setState(state)
  )

  getState = () => (
    TPSStorageManager.getState()
  )

  subscribe = listener => (
    TPSStorageEventEmitter.addListener('storage:change', listener)
  )
}

export default new Storage()
