import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';


import dotenv from 'dotenv';
dotenv.config();

export default defineConfig({
  plugins: [react()],
  define: {

    'process.env': {
      GOOGLE_MAPS_API_KEY: JSON.stringify(process.env.VITE_GOOGLE_MAPS_API_KEY),
    },
  },
  optimizeDeps: {
    include: ['firebase/app', 'firebase/analytics', 'firebase/firestore', 'firebase/storage'],
  },
});
