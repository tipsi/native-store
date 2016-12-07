import { NativeModules , DeviceEventEmitter } from 'react-native'

const { NativeStoreModule } = NativeModules


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
    const result = DeviceEventEmitter.addListener('storage:change', listener)
    return () => result.remove()
  }
}

export default new Storage()
