import { NativeModules } from 'react-native'
import NativeEventEmitter from 'react-native/Libraries/EventEmitter/NativeEventEmitter'

const { TPSStorageManager } = NativeModules
const TPSStorageEmitter = new NativeEventEmitter(TPSStorageManager)

if (__DEV__) {
  // Fix warning about empty listeners in DEV
  TPSStorageEmitter.addListener(
    'storage:change',
    () => {}
  )
}

class Storage {
  setState = state => (
    TPSStorageManager.setState(state)
  )

  getState = () => (
    TPSStorageManager.getState()
  )

  subscribe = (listener) => {
    const result = TPSStorageEmitter.addListener(
      'storage:change',
      listener
    )
    return () => result.remove()
  }
}

export default new Storage()
