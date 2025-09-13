import React, { useState, useRef } from "react";
import './UploadVideo.css';
import { app, storage } from '../firebaseConfig'
import { ref, getDownloadURL, uploadBytesResumable } from 'firebase/storage'


export function Video({ onNext , close, setVideoURL}) {
    const fileInputRef = useRef(null);
    const [fileStatus, setFileStatus] = useState(false)
    const [file, setFile] = useState(null);
    const [isProcessing, setIsProcessing] = useState(false);

    if(fileStatus){
      document.getElementById("select-file").style.display="none"
      document.getElementById("upload-file").style.display="inline"
    }

    const uploadVideo = async (e) => {
      e.preventDefault();
      setIsProcessing(true);

      const storageRef = ref(storage, `videos/${Date.now()}-${file.name}`);
      const uploadTask = uploadBytesResumable(storageRef, file);

      uploadTask.on('state_changed', null,
        console.log("video Uploading"),
        async () => {
        const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
        setVideoURL(downloadURL);
        alert('Video uploaded!');
        console.log(downloadURL)
        onNext()  
      });
    };

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
      <form onSubmit={uploadVideo} className="uploadFile-form">
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