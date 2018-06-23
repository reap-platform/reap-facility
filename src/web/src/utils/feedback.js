import { notification as antdNotification } from 'antd'

antdNotification.config({
  placement: 'topRight',
  top: 32,
  duration: 3,
})

export default {
  notification: {
    error: (result) => {
      antdNotification.error({
        message: `交易失败：错误码 ${result.responseCode}`,
        description: `详细原因：${result.responseMessage}`,
      })
    },
    success: (message, description) => antdNotification.success({ message, description }),
    warning: (message, description) => antdNotification.warning({ message, description }),
  },
}
