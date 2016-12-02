import { NativeModules , DeviceEventEmitter } from 'react-native'
import NativeEventEmitter from 'react-native/Libraries/EventEmitter/NativeEventEmitter'

const { NativeStoreModule } = NativeModules
const TPSStorageEventEmitter = new NativeEventEmitter(NativeStoreModule)


if (__DEV__) {
  // Fix warning about empty listeners in DEV
  DeviceEventEmitter.addListener(
    'state:change',
    () => {}
  )
}

function validateState(state) {
  if (typeof state !== 'object') {
    throw new Error('State should be an Object')
  }
  try {
    JSON.stringify(state)
  } catch (error) {
    throw new Error('State should be serializable')
  }
}

class Storage {
  setState = (state) => {
    validateState(state)
    NativeStoreModule.setState(state)
  }

  getState = () => (
    NativeStoreModule.getState()
  )

  subscribe = listener => {
//    NativeStoreModule.subscribe('storage:change')
    const result = DeviceEventEmitter.addListener('storage:change', listener)
    return () => {
//      DeviceEventEmitter.removeListener('storage:change', listener)
//      NativeStoreModule.unsubscribe('storage:change')
      result.remove();
    }
  }

}

export default new Storage()
