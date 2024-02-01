// vue.config.js
module.exports = {
  // https://cli.vuejs.org/config/#devserver-proxy
  devServer: {
    port: 3000,
    proxy: {
      '/auth': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/archive-records': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/registration': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/purchase': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/reg': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/sales': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/event-management': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/future': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/organizers': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '/applications': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  }
}