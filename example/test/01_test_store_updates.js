import test from 'tape-async'
import helper from './utils/helper'

const { driver, idFromAccessId } = helper

test('Test store updates', async (t) => {
  const textInputId = idFromAccessId('textInput')
  const valueFromInputId = idFromAccessId('valueFromInput')
  const valueFromNativeId = idFromAccessId('valueFromNative')
  const emptyValue = 'empty value'
  const testInputValue = 'test_string'

  try {
    await driver.waitForVisible(textInputId, 30000)
    t.pass('User should see text input')

    await driver.setValue(textInputId, testInputValue)

    await helper.hideKeyboard()

    t.pass('User should be able to enter text in input')

    const inputValueResult = await driver.getText(valueFromInputId)
    t.equal(inputValueResult, testInputValue, 'Value from input should be equal with entered text')

    const nativeValueResult = await driver.getText(valueFromNativeId)
    t.notEqual(nativeValueResult, emptyValue, 'Value from native should not be empty')

    await driver.pause(2000)
    const nextNativeValueResult = await driver.getText(valueFromNativeId)
    t.notEqual(nextNativeValueResult, nativeValueResult, 'Value from native should be updated every two seconds')
  } catch (error) {
    await helper.screenshot()
    await helper.source()

    throw error
  }
})
