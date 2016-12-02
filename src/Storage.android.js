import { NativeModules , DeviceEventEmitter } from 'react-native'
import NativeEventEmitter from 'react-native/Libraries/EventEmitter/NativeEventEmitter'

const { NativeStoreModule } = NativeModules
const TPSStorageEventEmitter = new NativeEventEmitter(NativeStoreModule)


if (__DEV__) {
  // Fix warning about empty listeners in DEV
  TPSStorageEmitter.addListener(
    'state:change',
    () => {}
  )
}

class Storage {
  setState = state => (
    NativeStoreModule.setValue('main_state', state)
  )

  getState = () => (
    NativeStoreModule.getValue('main_state')
  )

  subscribe = listener => {
    NativeStoreModule.subscribe('storage:change')
    DeviceEventEmitter.addListener('storage:change', listener)
    return () => {
      DeviceEventEmitter.removeListener('storage:change', listener)
      NativeStoreModule.unsubscribe('storage:change')
    }
  }

}

export default new Storage()
