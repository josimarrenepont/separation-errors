import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
// eslint-disable-next-line react-refresh/only-export-components
export default defineConfig({
  plugins: [react()],
})
