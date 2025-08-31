import { useState } from 'react'
import { Home } from './Home'
import { Layout } from './layout'
import { UploadVideo } from './Pages/UploadVideo'
import { VideoPage } from './Product Page/VideoPage'
import { Profile } from './Profile'
import { LoginForm } from './Forms/LoginForm'
import { Register } from './Forms/register'
import { OTP } from './Forms/OTP'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';


import './App.css'

function App() {
  const [count, setCount] = useState(0)

  const [type, setMainType] = useState('')
  const [value, setValue] = useState('')

  return (
    <>
      <Router>
        <Layout setMainTypeLayout={setMainType} setValueLayout={setValue}/>
        <Routes>
          <Route path="/" element={<Home setMainType={type} setValue={value}/>} />
          <Route path="/UploadVideo" element={<UploadVideo />} />
          <Route path='/video-player' element = {<VideoPage />} />
          <Route path='/Profile' element = {<Profile />} />
          <Route path='/Login' element = {<LoginForm />} />
          <Route path='/Register' element = {<Register />} />
          <Route path='/OTP' element = {<OTP />} />
        </Routes>
      </Router>
    </>
  )
}

export default App
