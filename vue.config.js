const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 5653,
    proxy: {

      '/api': {
        target: 'http://123.249.103.199:8080',
        changeOrigin: true,
        pathRewrite: {
          "^/api": "/api"
        }
      },

      '/postFile': {
        target: 'http://chkbigevent.oss-cn-beijing.aliyuncs.com',
        changeOrigin: true,
        pathRewrite: {
          "^/postFile": ""
        }
      }

    }
  },
})
