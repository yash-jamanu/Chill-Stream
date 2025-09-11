import React, { useState, useRef} from 'react';
import './UploadVideo.css';
import { app, storage } from '../firebaseConfig'
import { ref, getDownloadURL, uploadBytesResumable } from 'firebase/storage'


export function Thumbnail({ onBack, close, onFinish}) {
  const [file, setFile] = useState(null);
  const fileInputRef = useRef(null);
  const [isProcessing, setIsProcessing] = useState(false);
  const [fileStatus, setFileStatus] = useState(false)

  if(fileStatus){
      document.getElementById("select-file").style.display="none"
      document.getElementById("upload-file").style.display="inline"
  }

  const uploadThumb = async (e) => {
    e.preventDefault();
    setIsProcessing(true)

    const storageRef = ref(storage, `thumbnails/${Date.now()}-${file.name}`);
    const uploadTask = uploadBytesResumable(storageRef, file);

    uploadTask.on('state_changed', null, 
      console.log("thumbnail Uploading"),
      async () => {
        const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
        alert('Thumbnail uploaded!');
        onFinish(downloadURL);
      }
    );
  };

 return (
    <div className="video-block">
      {isProcessing && (
        <div className="processing-video">
          <p className='text-2xl font-bold'>Wait until video is Uploading...</p>
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

      <form onSubmit={uploadThumb} className='uploadFile-form'>
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
