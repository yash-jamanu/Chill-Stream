import React from 'react'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import './form.css'

export const Register = () => {
    const navigate = useNavigate();
    const [data, setData] = useState({
        username :'',
        firstname:'',
        lastname:'',
        email:'',
        password :'',
        birthdate:''
    })

   const handleData = (e) => {
    const { name, value } = e.target;
    setData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) =>{
    e.preventDefault();

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
      const res = await fetch("http://localhost:8080/api/auth/register/request-OTP",{
        method : "POST",
        headers: { 
          "Content-Type": "application/json", 
          "X-XSRF-TOKEN": getCookieValue("XSRF-TOKEN")
        },
        credentials: "include",
        body: JSON.stringify(data)
      })
      console.log(res.status)
      if(res.ok){
        console.log(data);
          navigate("/OTP")
      }
    }catch(error){
      console.error(error);
    }
  }

  return (
    <>
        <form onSubmit={handleSubmit} className='Form'>
            <h2 className='my-3'>Register</h2>
            <div className="mb-3">
              <input type="text" className="form-control" id="username" name='username' value={data.username} placeholder='Username' onChange={handleData} autoComplete="off" required/>
            </div>
            <div className="mb-3">
              <input type="text" className="form-control" id="firstname" name='firstname' placeholder='Firstname' value={data.firstname} onChange={handleData}  required/>
            </div>
            <div className="mb-3">
              <input type="text" className="form-control" id="lastname" name='lastname' placeholder='Lastname' value={data.lastname} onChange={handleData} required/>
            </div>
            <div className="mb-3">
              <input type="email" className="form-control" id="email" name='email' placeholder='Email' value={data.email} onChange={handleData} autoComplete="off" required/>
            </div>
            <div className="mb-3">
              <input type="text" className="form-control" id="password" name='password' placeholder='Password' value={data.password} onChange={handleData} required/>
            </div>
            <div className="mb-3">
              <label htmlFor="birthdate" className="form-label">Birthdate</label>
              <input type="date" className="form-control" id="birthdate" name='birthdate' value={data.birthdate} onChange={handleData} required/>
            </div>
            
            <button type="submit" className="FormSubmit-btn">Submit</button>
        </form>
    </>
  )
}
