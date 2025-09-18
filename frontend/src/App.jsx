import { useState, useEffect } from 'react'
import { Home } from './Home'
import { Layout } from './layout'
import { UploadVideo } from './Pages/UploadVideo'
import { VideoPage } from './Product Page/VideoPage'
import { Profile } from './Profile'
import { LoginForm } from './Forms/LoginForm'
import { Register } from './Forms/register'
import { OTP } from './Forms/OTP'
import { EditProfile } from './Forms/EditProfile'
import { UpdateVideo } from './Pages/UpdateVideo'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css'
import { AuthProvider } from './AuthLogin'



function App() {
  const [type, setMainType] = useState('')
  const [value, setValue] = useState('')
  const [searchText, setSearchText] = useState("")
  
  return (
    <>
      <AuthProvider>
        <Router>
          <Layout searchText={setSearchText} setMainType={setMainType} setValue={setValue}/>
          <Routes>
            <Route path="/" element={<Home searchText={searchText} setMainType={type} setValue={value}/>} />
            <Route path="/UploadVideo" element={<UploadVideo />} />
            <Route path='/video-player' element = {<VideoPage />} />
            <Route path='/Profile' element = {<Profile />} />
            <Route path='/Login' element = {<LoginForm />} />
            <Route path='/Register' element = {<Register />} />
            <Route path='/OTP' element = {<OTP />} />
            <Route path='/EditProfile' element ={<EditProfile />} />
            <Route path='/UpdateVideo' element ={<UpdateVideo />} />
          </Routes>
        </Router>
      </AuthProvider>  
    </>
  )
}

export default App
