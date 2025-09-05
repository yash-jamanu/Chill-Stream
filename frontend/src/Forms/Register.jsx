import React from 'react'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

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
    const res = await fetch("http://localhost:8080/api/auth/register/request-OTP",{
      method : "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(data)
    })
    console.log(res.status)
    if(res.ok){
      console.log(data);
        navigate("/OTP")
    }
  }

  return (
    <>
        <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="username" className="form-label">Username</label>
              <input type="text" className="form-control" id="username" name='username' value={data.username}  onChange={handleData} autoComplete="off" required/>
            </div>
            <div className="mb-3">
              <label htmlFor="firstname" className="form-label">Firstname</label>
              <input type="text" className="form-control" id="firstname" name='firstname' value={data.firstname} onChange={handleData}  required/>
            </div>
            <div className="mb-3">
              <label htmlFor="lastname" className="form-label">Lastname</label>
              <input type="text" className="form-control" id="lastname" name='lastname' value={data.lastname} onChange={handleData} required/>
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">Email</label>
              <input type="email" className="form-control" id="email" name='email' value={data.email} onChange={handleData} autoComplete="off" required/>
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">Password</label>
              <input type="text" className="form-control" id="password" name='password' value={data.password} onChange={handleData} required/>
            </div>
            <div className="mb-3">
              <label htmlFor="birthdate" className="form-label">Birthdate</label>
              <input type="date" className="form-control" id="birthdate" name='birthdate' value={data.birthdate} onChange={handleData} required/>
            </div>
            
            <button type="submit" className="btn btn-primary">Submit</button>
        </form>
    </>
  )
}
