import { NativeModules } from 'react-native'
import NativeEventEmitter from 'react-native/Libraries/EventEmitter/NativeEventEmitter'

const { TPSStorageManager } = NativeModules
const TPSStorageEventEmitter = new NativeEventEmitter(TPSStorageManager)

if (__DEV__) {
  TPSStorageEventEmitter.addListener('storage:change', () => {})
}

class Storage {
  setItem = (key, value) => (
    TPSStorageManager.setItem(key, value)
  )

  getItem = (key) => (
    TPSStorageManager.getItem(key)
  )

  removeItem = (key) => (
    TPSStorageManager.removeItem(key)
  )

  subscribe = (...args) => {
    let listener

    if (args.length > 1) {
      listener = (data) => (
        data.key === args[0] && args[1](data.value)
      )
    } else {
      listener = args[0]
    }

    return TPSStorageEventEmitter.addListener(
      'storage:change',
      listener
    )
  }
}

export default new Storage()
