import React from 'react'
import './Navbar.css'
import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react';
import { AuthProvider, useAuth } from '../AuthLogin';

function LeftNavbar ({isSideBarOpen, toggleSideBar}){
  return(
    <div className='leftNavbar display'>
      <span
        className='material-symbols-outlined cursor-pointer toggleSidebarButton'
        onClick={toggleSideBar}
      >
        {isSideBarOpen ? 'close' : 'menu'}
      </span>
      <h2 style={{ fontSize: '1.5rem', textAlign: 'center' }}>Navbar</h2>
    </div>
  )
}

function InputSearch(){
  return(
    <form className='flex items-center' onSubmit={(e) =>{e.preventDefault()}}>
      <input type='search' placeholder='Search' name='search' className='search pl-1.5'/>
      <button className='searchButton material-symbols-outlined cursor-pointer'type="submit">search</button>
    </form>
  )
}

function RightNavbar({onProfileClick}){

  const {isLoggedIn} = useAuth();
  const [data, setdata] = useState({})
  const navigate = useNavigate();

  useEffect(()=>{
    const fetchDetails = async () => {
      if (isLoggedIn) {
        const user = await getUserDetails();
        setdata(user);
      }
      fetchDetails();
    };
  },[isLoggedIn])

  const handleProfileClick = () =>{
    if(isLoggedIn){
      navigate("/Profile");
    }else{
      navigate("/Login")
    }
  }

  return (
    <div className='rightNavbar display'>
      <InputSearch />
      <div className='display ml-3 cursor-pointer' onClick={handleProfileClick}>
        {data?.profile ? (
          <img
            src={data.profile}
            style={{ width: '1.5rem' }}
            alt='Profile'
          />
        ) : (
          <span className='material-symbols-outlined profile' style={{ fontSize: '1.5rem' }}>
            person
          </span>
        )}
      </div>
    </div>
  )
}

export const Navbar = ({isSideBarOpen, toggleSideBar}) => {  
  return (
    <nav className=' navbar'>
        <LeftNavbar isSideBarOpen={isSideBarOpen} toggleSideBar={toggleSideBar}/>
        <RightNavbar onProfileClick={handleProfileClick}/> 
    </nav>
  )
}

async function getUserDetails(){
  try{
    const res = await fetch("http://localhost:8080/user/details",
      {
        credentials:"include"
      }
    )
    if(res.ok){
      return await res.json();
    }else{
      console.error("Failed to fetch user details:", res.status);
      return {};
    }
  }catch(err){
    console.error("Error fetching user details:", err);
    return {};
  }
}