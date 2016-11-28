import { NativeModules } from 'react-native'
import NativeEventEmitter from 'react-native/Libraries/EventEmitter/NativeEventEmitter'

//const { TPSStorageManager } = NativeModules
const { NativeStoreModule } = NativeModules
//const TPSStorageEventEmitter = new NativeEventEmitter(TPSStorageManager)
const TPSStorageEventEmitter = new NativeEventEmitter(NativeStoreModule)


if (__DEV__) {
  TPSStorageEventEmitter.addListener('storage:change', () => {})
}

class Storage {
  setState = state => (
    TPSStorageManager.setValue('main_state',state)
  )

  getState = () => (
    TPSStorageManager.getValue('setValue')
  )

  subscribe = listener => (
    TPSStorageEventEmitter.subscribe('storage:change', listener)
  )
}

export default new Storage()
