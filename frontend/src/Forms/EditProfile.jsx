import React, {useEffect, useState } from 'react'
import { useRef } from 'react';
import profile from '../assets/profile.png'
import { app, storage } from '../firebaseConfig'
import { useNavigate, useLocation } from 'react-router-dom';
import './EditProfile.css'
import { ref, getDownloadURL, uploadBytesResumable } from 'firebase/storage'

function EditProfilePic ({oldProfilePic, setProfilePic}){

    const fileInputRef = useRef(null);
    const [file, setFile] = useState(null);
    const [fileStatus, setFileStatus] = useState(false)
    const [isProcessing, setIsProcessing] = useState(false);

    useEffect(() => {
      if (fileStatus && file) {
        uploadProfile();
      }
    }, [fileStatus, file]);

    const uploadProfile = async () => {
        setIsProcessing(true);

        const storageRef = ref(storage, `profile/${Date.now()}-${file.name}`);
        const uploadTask = uploadBytesResumable(storageRef, file);

        uploadTask.on(
            "state_changed",
            null,
            (error) => console.error("Upload error:", error),
            async () => {
              const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
              setProfilePic(downloadURL);
              alert("Profile uploaded!");
              console.log(downloadURL);
              setIsProcessing(false);
            }
        );
    };


    return(
        <div className='image-block'>
            {isProcessing && (
                <div className="processing-video">
                    <p className="text-2xl font-bold">Wait until profile is Uploading...</p>
                </div>
            )}

            <img src= { oldProfilePic || profile }  className='editProfile-img' alt='Profile-pic'/>
            
            <input type="file" accept="image/*" 
                ref={fileInputRef} 
                style={{ display: 'none' }} 
                onChange={(e) => {
                    setFile(e.target.files[0])
                    setFileStatus(true)
                }}
            />    

            <div className='material-symbols-outlined edit-icon'
                onClick={() => fileInputRef.current.click()}
            >
                edit
            </div>
        </div>
    )
}

function EditDetails ({oldUserDetails, setNewUserData, onSubmit}){
    const [OldData, setOldData] = useState({})
    useEffect(() => {
      setOldData(oldUserDetails);
    }, [oldUserDetails]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewUserData((prev) => ({
          ...prev,
          [name]: value
        }));
    };

    return(
        <div>
            <form onSubmit={onSubmit} className='editDetails-form'>
                <input type="text" name="username"
                    placeholder='Username'
                    className='editDetails-input' 
                    onChange={handleChange} 
                />

                <input type='text' name='firstname'
                    placeholder='Firstname'
                    className='editDetails-input'
                    onChange={handleChange}
                />

                <input type='text' name='lastname'
                    placeholder='Lastname'
                    className='editDetails-input'
                    onChange={handleChange}
                />

                <input type='date' name='birthdate'
                    className='editDetails-input'
                    onChange={handleChange}
                />

                <button type="submit" className='submit-btn'>Submit</button>
            </form>
        </div>
    )
}


export const EditProfile = () => {
    const location = useLocation();

    const {userDetails} = location.state || {}
    const [userData, setUserData] = useState({})
    useEffect(() => {
      setUserData(userDetails);
    }, [userDetails]);

    const [profilePic, setProfilePic] = useState("") 
    const [newUserData, setNewUserData] = useState({}) 
    
    const handleUpdate = () =>{
       updateUser({profilePic, newUserData}); 
    }

    return (    
        <div className='userEdit-main'>
            <div className='editProfile-block'>
                <EditProfilePic 
                    oldProfilePic={userData?.profile} 
                    setProfilePic={setProfilePic}
                />

                <EditDetails 
                    oldUserDetails={userData} 
                    setNewUserData={setNewUserData} 
                    onSubmit={handleUpdate}
                />
            </div>
                
        </div>
    )
}

async function updateUser ({profilePic, newUserData}) {
    const navigate = useNavigate();

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

    try {
        const data = {
            'profile' : profilePic,
            'username' : newUserData.username,
            'firstname' : newUserData.firstname,
            'lastname' : newUserData.lastname,
            'birthdate' : newUserData.birthdate
        }
    
        const res = await fetch ("http://localhost:8080/user/update",{
            method : "PUT",
            headers: {
              'Content-Type': 'application/json'
            },
            credentials:"include",
            body: JSON.stringify(data)
        })
        if(res.ok){
            console.log("user updated", res.status)
            navigate("/Profile")
        }else{
            console.error(res.status)
        }

    }catch(error){
        console.log("error", error)
    }
    
}