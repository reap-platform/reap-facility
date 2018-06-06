const config = function () {
  // 应用配置
  const configurations = {
    // 配置功能码
    functions: [
      { code: 'REAPFA0001', description: '参数维护' },
      { code: 'REAPFA0002', description: '路由维护' },
    ],
  }

  if (process.env.NODE_ENV === 'development') {
    Object.assign(configurations, require('../mock/devConfig'))
  }

  return configurations
}

export default config()
