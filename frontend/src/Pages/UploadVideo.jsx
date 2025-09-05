
import React, { useState, useRef} from 'react';
import { useNavigate } from 'react-router-dom'
import './UploadVideo.css';
import { app, storage } from '../firebaseConfig'
import { ref, getDownloadURL, uploadBytesResumable } from 'firebase/storage'

  

 function Video({ onNext , close, setVideoURL}) {
    const fileInputRef = useRef(null);
    const [file, setFile] = useState(null);
    const [data, setData] = useState({})
    const handleFileBtnClick = () => {
      fileInputRef.current.click();
    };

    const uploadVideo = async (e) => {
      e.preventDefault();
      if (!file) return alert('Select a video first!');
      const storageRef = ref(storage, `videos/${file.name}`);
      const uploadTask = uploadBytesResumable(storageRef, file);

      uploadTask.on('state_changed', null, console.error, async () => {
        const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
        setVideoURL(downloadURL);
        alert('Video uploaded!');
        onNext();
      });
    };


  return (
    <div className="video-block">
      <div className='material-symbols-outlined close-btn' onClick={close}>close</div>
       <form onSubmit={uploadVideo}>
        <button type="button" onClick={() => fileInputRef.current.click()}>Select File</button>
        <input type="file" accept="video/*" ref={fileInputRef} style={{ display: 'none' }} onChange={(e) => setFile(e.target.files[0])} />
        <button type="submit">Upload</button>
      </form>
    </div>
  );
}

function VideoDetails({ onBack, onNext, close, details, setDetails }) {

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDetails((prev) => ({ ...prev, [name]: value }));
  };
  return (
    <div className="video-block">
      <div className="material-symbols-outlined close-btn" onClick={close}>close</div>
      <h2>Details</h2>
      <form onSubmit={(e) => { e.preventDefault(); onNext(); }}>
        <input type="text" name="title" placeholder="Title" value={details.title || ''} onChange={handleChange} />
        <textarea name="description" placeholder="Description" value={details.description || ''} onChange={handleChange} />
        <div>
          <select className="form-select form-select-lg mb-3" aria-label="Large select example" name='status' value={details.status || "private"} onChange={handleChange} required>
            <option value="private">Private</option>
            <option value="public">Public</option>
          </select>  
        </div>
        <button type="submit">Next</button>
      </form>
      <button onClick={onBack}>Back</button>
    </div>
   );
  }

 function Thumbnail({ onBack, close, setThumbnail, onFinish}) {
  const [file, setFile] = useState(null);
  const fileInputRef = useRef(null);

  const uploadThumb = async (e) => {
    e.preventDefault();
    if (!file) return alert('Select a thumbnail first!');
    const storageRef = ref(storage, `thumbnails/${file.name}`);
    const uploadTask = uploadBytesResumable(storageRef, file);

    uploadTask.on('state_changed', null, console.error, async () => {
      const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
      setThumbnail(downloadURL);
      alert('Thumbnail uploaded!');
      onFinish();
    });
  };

 return (
    <div className="video-block">
      <div className="material-symbols-outlined close-btn" onClick={close}>close</div>
      <h6>Select thumbnail</h6>
      <form onSubmit={uploadThumb}>
        <button type="button" onClick={() => fileInputRef.current.click()}>Select File</button>
        <input type="file" accept="image/*" ref={fileInputRef} style={{ display: 'none' }} onChange={(e) => setFile(e.target.files[0])} />
        <button type="submit">Upload</button>
      </form>
      <button onClick={onBack}>Back</button>
    </div>
   );
}


export const UploadVideo = () => {
  const [page, setPage] = useState(1);
  const navigate = useNavigate();
  const [videoURL, setVideoURL] = useState('');
  const [details, setDetails] = useState({});
  const [thumbnail, setThumbnail] = useState('');

  const handleClose = () =>{
    navigate ('/')
  }

  const handleFinish = async () => {
    await createVideo({ videoURL, data: details, thumbnail });
    alert('Video created!');
    navigate('/');
  };

 const renderSwitch = () => {
    switch (page) {
      case 1 : return <Video onNext={() => setPage(2)} close={handleClose} setVideoURL={setVideoURL}/>
      case 2 : return <VideoDetails onBack={() => setPage(1)} onNext={() => setPage(3)} close={handleClose} details={details} setDetails={setDetails}/>
      case 3 : return <Thumbnail  onBack={() => setPage(2)} close={handleClose} setThumbnail={setThumbnail} onFinish={handleFinish}/>
      default : return null;
    }
  } 

  return (
    <>
      <div className='video-main'>
        <div className='upload-block'>{renderSwitch()}</div>
      </div>
    </> 
  )
};



async function createVideo({videoURL, data, thumbnail}){
  try{
    const payload = {
      ...data, filepath: videoURL, thumbnail
    }

    const res = await fetch ('http://localhost:8080/video/create',{
      method : "POST",
      headers: {
        'Content-Type': 'application/json;charset=utf-8'
      },
      body: JSON.stringify(payload)
    })
    console.log(res.status)
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }
    const result = await res.json(); // ✅ get response data
    console.log(result);
    return result;

  } catch (error) {
    console.error('Error creating video:', error);
  }
}