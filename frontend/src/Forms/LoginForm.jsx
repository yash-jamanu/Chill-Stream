import React from 'react'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthProvider, useAuth } from '../AuthLogin';
import "./form.css"
 
export const LoginForm = () => {
    const navigate = useNavigate();
    const [error, setError] = useState('');
    const { setIsLoggedIn } = useAuth();


    const [data, setData] = useState({
    email :'',
    password : ''
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
        setError('');

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
          const res = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { 
              "Content-Type": "application/json",
              "X-XSRF-TOKEN" : getCookieValue("XSRF-TOKEN")
             },
            body: JSON.stringify(data),
            credentials: "include"
          });
          console.log(res.status)
          if (res.ok) {
            navigate("/");
            setIsLoggedIn(true);
          } else if (res.status === 401) {
            setError("Invalid email or password");
          } else {
            setError(`Login failed. Error ${res.status}`);
          }
        } catch (err) {
          console.error(err);
          setError("Server is unreachable. Please try again later.");
        }
    }

    const navigateToRegisteration =() =>{
      navigate("/Register")
    }

  return (
    <>
        <form onSubmit={handleSubmit} className='Form'>
          {error && <div className="alert alert-danger">{error}</div>}
          <h2 className='my-3'>Login</h2>
            <div className="mb-3">
              <input type="email" className="form-control" id="email" name='email' value={data.email} onChange={handleData} autoComplete="off" placeholder='Email Address' required/>
            </div>
            <div className="mb-3">
              <input type="password" className="form-control" id="password" name='password' value={data.password} onChange={handleData} autoComplete='off' placeholder='Password' required/>
            </div>
          <button type="submit" className="FormSubmit-btn cursor-pointer">Submit</button>
          <p onClick={navigateToRegisteration} className='cursor-pointer'>Register me!</p>
        </form>
    </>
  )
}
