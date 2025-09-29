import React from 'react'
import './Profile.css'
import profile from './assets/profile.png'
import { useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from './AuthLogin'

function ProfileDetails(){
    const location = useLocation();
    const {userData} = location.state || {}
    const navigate = useNavigate();
    const {setIsLoggedIn} = useAuth();

    const handleLogout = async () => {
        
        function getCookieValue(cookieName) {
          const cookies = document.cookie.split('; ');
          for (let cookie of cookies) {
            const [name, value] = cookie.split('=');
            if (name === cookieName) {
              return decodeURIComponent(value);
            }
          }
          return null; // Return null if the cookie is not found
        }
        try{
            const response = await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            credentials: "include"
            });

            if(response.ok){
                navigate("/")
                localStorage.removeItem("userID");
                setIsLoggedIn(false)
                console.log(response.status);
            }else{
                console.log(response.status)
            }
        }catch (error) {
            console.error("Error logging out:", error);
        }
    }

    const handleProfileEdit = () =>{
        navigate ("/EditProfile", {state:{userDetails:userData}})
    }

    return(
        <div className='main'>
            <div className='profile-block '>
                <div className='edit-block'>
                    <button className='edit-button material-symbols-outlined' onClick={handleProfileEdit}>edit</button>
                </div>

                <div className='profile-img-block '>
                    <div className='img-block '>
                        <img src={userData?.profile || profile} className='profile-img' alt='profile-img'/>
                    </div>
                </div> 
                
                <div className='details-block '>
                    <p className='font-bold text-3xl my-1'>{userData.username || 'Username'}</p>
                    <p className='email text-center'>{userData?.email || 'Example@gmail.com'}</p>
                </div>
                
                <div className="profile-buttons" onClick={handleLogout}>
                    <button className="profile-btn primary">Logout</button>
                </div>
            </div>
        </div>
    )
}

export const Profile = () => {
  return (
    <div>
        <ProfileDetails />
    </div>
  )
}