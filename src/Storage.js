import { NativeModules, NativeEventEmitter } from 'react-native'

const { TPSStoreManager } = NativeModules
const TPSStoreEmitter = new NativeEventEmitter(TPSStoreManager)

if (__DEV__) {
  // Fix warning about empty listeners in DEV
  TPSStoreEmitter.addListener(
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
    return TPSStoreManager.setState(state)
  }

  getState = () => (
    TPSStoreManager.getState()
  )

  subscribe = (listener) => {
    const result = TPSStoreEmitter.addListener(
      'state:change',
      listener
    )
    return () => result.remove()
  }
}

export default new Storage()
