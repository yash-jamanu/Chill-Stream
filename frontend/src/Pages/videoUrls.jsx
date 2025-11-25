import React, { useState, useRef } from "react";
import './UploadVideo.css';


export function Video({ onNext , close, setVideoURL}) {
    const fileInputRef = useRef(null);
    const [fileStatus, setFileStatus] = useState(false)
    const [file, setFile] = useState(null);
    const [isProcessing, setIsProcessing] = useState(false);

    if(fileStatus){
      document.getElementById("select-file").style.display="none"
      document.getElementById("upload-file").style.display="inline"
    }

    const handleFileUpload = async (e) => {
      e.preventDefault();
      if(fileStatus == true){
        setIsProcessing(true)
        const path = await storevideo({video:file});
        setVideoURL(path)
        onNext()
      }else{
        console.log("failure happened")
      }
    }


  return (
    <div className="video-block">
      {isProcessing && (
        <div className="processing-video">
          <p className="text-2xl font-bold">Wait until video is Uploading...</p>
        </div>
      )}

      <div className="flex items-center justify-between">
        <h2 className='px-3'>Video</h2>
        <div className='material-symbols-outlined close-btn' onClick={close}>close</div>
      </div>
      <div className="icon-box">
        <span className="material-symbols-outlined upload-icon">upload</span> 
      </div>
      <form onSubmit={handleFileUpload} className="uploadFile-form">
        <div id="select-file">
          <button type="button"
            className="videoUpload-btn" 
            onClick={() =>{
              fileInputRef.current.click()
            }}
          >
            Select File
          </button>
        </div>
          
        <input type="file"
         accept="video/*" 
         ref={fileInputRef} 
         style={{ display: 'none' }} 
         onChange={(e) => {setFile(e.target.files[0])
          setFileStatus(true)
         }} 
        />

        <div>
          <button className="videoUpload-btn" 
            type="submit" 
            id="upload-file"
            style={{display:"none"}} 
          >
            Upload
          </button>
        </div>
        
      </form>
      
    </div>
  );
}


async function storevideo({video}){
  const formData = new FormData();
  formData.append("file", video)
  try{
    const res = await fetch("http://localhost:8080/video/file/video", {
      method:"POST",
      headers :{
        "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
      },
      body: formData
    }
    )

    if(res.ok){
      const data = res.text()
      console.log(data)
      return data;
    }
  }catch(error){
    console.error(error)
  }
}