import react from "@vitejs/plugin-react"
import { defineConfig,loadEnv } from "vite"

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '');
  return {
    define: {
      'process.env.STRIPE_SECRET_KEY': JSON.stringify(env.STRIPE_SECRET_KEY)
    },
    plugins: [react()],
    server: {
      port: 3000,
      host: "0.0.0.0",
      proxy: {
          '/api': {
            target: 'http://localhost:4242',
            changeOrigin: true,
            rewrite: (path) => path.replace(/^\/api/, '')
          }
      }
    },
    build: {
      outDir: "build",
    },
  }
})
