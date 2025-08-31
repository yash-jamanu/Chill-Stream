import React from 'react'
import './Navbar.css'
import profile from "../assets/profile.png"
import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react';

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
    <form className='flex items-center'>
      <input type='search' placeholder='Search' name='search' className='search pl-1.5'/>
      <button className='searchButton material-symbols-outlined cursor-pointer'>search</button>
    </form>
  )
}

// function RightNavbar({UserDetails, onProfileClick}){
//   return (
//     <div className='rightNavbar display'>
//       <InputSearch />
//       <div className='display ml-3 cursor-pointer' onClick={onProfileClick}>
//         {UserDetails?.profile ? (
//           <img
//             src={UserDetails.profile}
//             style={{ width: '1.5rem' }}
//             alt='Profile'
//           />
//         ) : (
//           <span className='material-symbols-outlined profile' style={{ fontSize: '1.5rem' }}>
//             person
//           </span>
//         )}
//       </div>
//     </div>
//   )
// }

export const Navbar = ({isSideBarOpen, toggleSideBar}) => {
  // const [authUser, setAuthUser] = useState({})

  // useEffect(() => {
  //   getUser().then((data) => {
  //     setAuthUser(data);
  //   });
  // }, []);

  // const handleProfileClick = () => {
  //   if (!authUser) {
  //     navigate('/login');
  //   } else {
  //     navigate('/Profile', { state: { authUser } });
  //   }
  // };

  return (
    <nav className=' navbar'>
        <LeftNavbar isSideBarOpen={isSideBarOpen} toggleSideBar={toggleSideBar}/>
        {/* <RightNavbar UserDetails={authUser} onProfileClick={handleProfileClick}/> */}
    </nav>
  )
}


// async function getUser () {
//   try{
//     const res = await fetch("http://localhost:8080/user/details",{
//       method: 'POST',
//       credentials: 'include'
//     })
//     if (res.status === 401 || res.status === 403) { 
//       return null;
//     }
//     return await res.json();
//   }catch(error){
//     console.error("Error",  error);
//     return null;
//   }
// }