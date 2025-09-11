import React from 'react'
import './Navbar.css'
import '../index.css'
import LOGO  from '../assets/LOGO.png'
import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react';
import { AuthProvider, useAuth } from '../AuthLogin';

function LeftNavbar ({isSideBarOpen, toggleSideBar}){
  return(
    <div className='leftNavbar display'>
      <span
        className='material-symbols-outlined cursor-pointer toggleSidebarButton'
        onClick={toggleSideBar} 
        style={{fontSize:"2rem"}}
      >
        {isSideBarOpen ? 'close' : 'menu'}
      </span>
      <img src={LOGO}  className='logo'/>
    </div>
  )
}

export function searchText (){
  let text;
  return text;
}

function InputSearch({onSearch}){
  const [text, setText] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(text);  
  };
   

  return(
    <>
      <form className="flex" role="search" onSubmit={handleSubmit}>
        <input className="search" type="search" placeholder="Search" aria-label="Search" onChange={(e)=>{setText(e.target.value)}}/>
        <button className="material-symbols-outlined search-button display" type="submit">Search</button>
      </form>
    </>
  )
}

function RightNavbar({onSearch}){

  const {isLoggedIn} = useAuth();
  const [data, setdata] = useState({})
  const navigate = useNavigate();

  useEffect(()=>{
    const fetchDetails = async () => {
      if (isLoggedIn) {
        const user = await getUserDetails();
        setdata(user);
      }
    };
    fetchDetails();
  },[isLoggedIn])

  const handleProfileClick = () =>{
    if(isLoggedIn){
      navigate("/Profile", {state:{userData:data}});
    }else{
      navigate("/Login")
    }
  }

  return (
    <div className='rightNavbar display'>
      <InputSearch onSearch={onSearch}/>
      <div className='display ml-3 cursor-pointer' onClick={handleProfileClick}>
        {data?.profile ? (
          <img
            src={data.profile}
            style={{ width: '2rem' }}
            alt='Profile'
          />
        ) : (
          <span className='material-symbols-outlined profile' style={{ fontSize: '2rem'}}>
            person
          </span>
        )}
      </div>
    </div>
  )
}

export const Navbar = ({onSearch, isSideBarOpen, toggleSideBar}) => {  
  return (
    <nav className=' navbar'>
        <LeftNavbar isSideBarOpen={isSideBarOpen} toggleSideBar={toggleSideBar}/>
        <RightNavbar onSearch={onSearch}/> 
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
      const data = await res.json();
      console.log(data, res.status);
      return data;
    }else{
      console.error("Failed to fetch user details:", res.status);
      return {};
    }
  }catch(err){
    console.error("Error fetching user details:", err);
    return {};
  }
}

async function searchByText({text}){
  const searchText = text
  try{
    const res = await fetch(`http://localhost:8080/search/${searchText}`)
    
    if(res.ok){
      return res.json();
    }else{
      console.log(res.status)
    }
  }catch(error){
    console.error(error);
    
  }
}