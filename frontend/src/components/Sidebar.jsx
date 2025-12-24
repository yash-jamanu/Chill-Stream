import React, { useEffect, useState } from 'react'
import './sideBar.css'
import '../index.css'
import { MainSection } from '../MainSection'
import { useNavigate } from 'react-router-dom'
import { AuthProvider, useAuth } from '../AuthLogin'


export const SideBar = ({ setMainType, setValue }) => {
    const navigate = useNavigate();
    const {isLoggedIn} = useAuth();


  return (
    <div className='sideBar p-2'>
      <div className='p-2'>
        <div className='flex items-center p-1.5 list general'onClick={()=>{navigate('/')}}>
          <span className='material-symbols-outlined pr-2.5'>home</span> 
          Home
        </div>

        <div className='flex items-center p-1.5 list general' 
          onClick={() => {
            if(isLoggedIn){
              setMainType("me")
            }else{
              navigate("/Login")
            }
            }}>
          <span className='material-symbols-outlined pr-2.5'>smart_display</span> 
          Your Videos
        </div> 

        <div className='flex items-center p-1.5 list general'
          onClick={() => {
            if(isLoggedIn){
              navigate("/UploadVideo")
            }else{
              navigate('/Login')
            }}}>
          <span className='material-symbols-outlined pr-2.5'>add_circle</span>
          Upload Video
        </div>
      </div>

      <div className='p-2'>
        <p className='font-medium text-2xl' style={{color: "black"}}>Explore</p>
        <div>
          <ul className='flex-col items-center'>
            <li className='list' onClick={() => { setMainType("category"); setValue("Gaming"); }}>Gaming</li>
            <li className='list' onClick={() => { setMainType("category"); setValue("Sports"); }}>Sports</li>
            <li className='list' onClick={() => { setMainType("category"); setValue("Sci-fi"); }}>Sci-fi</li>
            <li className='list' onClick={() => { setMainType("category"); setValue("Romance"); }}>Romance</li>
          </ul>
        </div>
      </div>  
    </div>
  )
}
