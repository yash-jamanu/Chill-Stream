// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getStorage } from 'firebase/storage'
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyCn_Chw6AOvbi8b8xiw5YcnguBGumPawT0",
  authDomain: "video-streaming-8f50e.firebaseapp.com",
  projectId: "video-streaming-8f50e",
  storageBucket: "video-streaming-8f50e.firebasestorage.app",
  messagingSenderId: "603548460371",
  appId: "1:603548460371:web:219331539affa4572feda6"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const storage = getStorage(app)