import React from 'react'
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const LoginForm = () => {
    const navigate = useNavigate();
    const [error, setError] = useState('');

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

        try {
          const res = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
            credentials: "include" // ✅ ensures cookies/session are sent
          });
        
          if (res.ok) {
            navigate("/");
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

  return (
    <>
        <form onSubmit={handleSubmit}>
          {error && <div className="alert alert-danger">{error}</div>}
            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email address</label>
              <input type="email" className="form-control" id="email" name='email' value={data.email} onChange={handleData} required/>
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password</label>
              <input type="password" className="form-control" id="password" name='password' value={data.password} onChange={handleData} required/>
            </div>
          <button type="submit" className="btn btn-primary">Submit</button>
        </form>
    </>
  )
}
