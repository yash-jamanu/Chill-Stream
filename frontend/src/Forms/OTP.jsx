import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

export const OTP = () => {
    const navigate = useNavigate();
    const [OTP, setOTP] = useState("");
    
    const handleData = (e) => {
        setOTP(e.target.value);
    };

    const handleSubmit = async (e) =>{
        e.preventDefault(); 
        const res = await fetch(`http://localhost:8080/api/auth/request-OTP/${OTP}`,{
          method : "POST",
          headers: { "Content-Type": "application/json" },
          credentials: "include"
        })
        if(res.ok){
            navigate("/Login")
        }
    }

  return (
  <>
    <form className="row g-3" onSubmit={handleSubmit}>
        <div className="col-auto">
          <label htmlFor="OTP" className="visually-hidden">OTP</label>
          <input type="text" className="form-control" id="OTP" name='OTP' value={OTP} onChange={handleData} required/>
        </div>
        <div className="col-auto">
          <button type="submit" className="btn btn-primary mb-3">Verify OTP</button>
        </div>
    </form>
  </>
  )
}
