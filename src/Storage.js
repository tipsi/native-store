import { NativeModules, NativeEventEmitter } from 'react-native'

const { TPSStorageManager } = NativeModules
const TPSStorageEmitter = new NativeEventEmitter(TPSStorageManager)

if (__DEV__) {
  // Fix warning about empty listeners in DEV
  TPSStorageEmitter.addListener(
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
    return TPSStorageManager.setState(state)
  }

  getState = () => (
    TPSStorageManager.getState()
  )

  subscribe = (listener) => {
    const result = TPSStorageEmitter.addListener(
      'state:change',
      listener
    )
    return () => result.remove()
  }
}

export default new Storage()
