import React from 'react'
import './Profile.css'
import profile from './assets/profile.png'

function ProfileDetails({UserDetails , logout}){
    return(
        <div className='main'>
            <div className='profile-block '>
                <div className='edit-block'>
                    <button className='edit-button material-symbols-outlined'>edit</button>
                </div>

                <div className='profile-img-block '>
                    <div className='img-block '>
                        
                        <img src={UserDetails?.profile || profile} className='profile-img' alt='profile-img'/>
                    </div>
                </div> 
                
                <div className='details-block '>
                    <p className='font-bold text-3xl my-1'>{UserDetails?.username || 'Username'}</p>
                    <p className='email text-center'>{UserDetails?.email || 'Example@gmail.com'}</p>
                </div>
                
                <div className="profile-buttons" onClick={logout}>
                    <button className="profile-btn primary">Logout</button>
                </div>
            </div>
        </div>
    )
}

export const Profile = ({UserDetails}) => {
    const logout = async () =>{
        try{
            const response = await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            credentials: "include",
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }});

            if(response.ok){
                console.log(response.status);
            }
        }catch (error) {
            console.error("Error logging out:", error);
        }
    }
  return (
    <div>
        <ProfileDetails UserDetails={UserDetails} logout={logout} />
    </div>
  )
}

