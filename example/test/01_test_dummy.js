import test from 'tape-async'
import helper from './utils/helper'

const { driver, idFromAccessId } = helper

test('Test if user can use Apple Pay', async (t) => {
  const textInputId = idFromAccessId('textInput')

  try {
    await driver.waitForVisible(textInputId, 10000)

    t.pass('User should see text input')
  } catch (error) {
    await helper.screenshot()
    await helper.source()

    throw error
  }
})
