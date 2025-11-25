import React, { useState, useRef} from 'react';
import './UploadVideo.css';

export function Thumbnail({ onBack, close, onFinish}) {
  const [file, setFile] = useState(null);
  const fileInputRef = useRef(null);
  const [isProcessing, setIsProcessing] = useState(false);
  const [fileStatus, setFileStatus] = useState(false)

  if(fileStatus){
      document.getElementById("select-file").style.display="none"
      document.getElementById("upload-file").style.display="inline"
  }

  const handleFileUpload = async (e) => {
    e.preventDefault();
    if(fileStatus == true){
      setIsProcessing(true)
      const path = await storeThumbnail({thumbnail:file});
      onFinish(path)
    }else{
      console.log("failure happened")
    }
  }


 return (
    <div className="video-block">
      {isProcessing && (
        <div className="processing-video">
          <p className='text-2xl font-bold'>Wait until Thumbnail is Uploading...</p>
        </div>
      )}

      <div className='flex items-center justify-between'>
        <div onClick={onBack} className='material-symbols-outlined close-btn'>arrow_back</div>
        <h2 className='px-3'>Thumbnail</h2>
        <div className="material-symbols-outlined close-btn" onClick={close}>close</div>
      </div>

      <div className="icon-box">
        <span className="material-symbols-outlined upload-icon">upload</span> 
      </div>

      <form onSubmit={handleFileUpload} className='uploadFile-form'>
        <button type="button" 
          onClick={() => fileInputRef.current.click()}
          id='select-file'
          className='videoUpload-btn'
          >
          Select File
        </button>

        <input type="file" accept="image/*" 
          ref={fileInputRef} 
          style={{ display: 'none' }} 
          onChange={(e) => {
            setFile(e.target.files[0])
            setFileStatus(true)
          }
          }
 
        />

        <button type="submit" id='upload-file' className='videoUpload-btn' style={{display:"none"}} 
          >Upload</button>
      </form>
    </div>
   );
}

async function storeThumbnail({thumbnail}){
  const formData = new FormData();
  formData.append("file", thumbnail)
  try{
    const res = await fetch("http://localhost:8080/video/file/thumbnail", {
      method:"POST",
      headers :{
        "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
      },
      body: formData
    }
    )

    if(res.ok){
      const data = await res.text()
      return data;
    }
  }catch(error){
    console.erro(error)
  }
}
