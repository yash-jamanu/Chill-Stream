import React, { useState, useRef} from 'react';
import { useNavigate } from 'react-router-dom'
import './UploadVideo.css';
import { Video } from './videoUrls';
import { VideoDetails } from './videoDetails';
import { Thumbnail } from './thumbnail';

export const UploadVideo = () => {

  const [page, setPage] = useState(1);
  const navigate = useNavigate();
  const [videoURL, setVideoURL] = useState('');
  const [details, setDetails] = useState({
    title : '',
    caption : '',
    category : '',
    description : '',
    videostatus : 'private'
  });

  const handleClose = () =>{ navigate ('/') }

  const handleFinish = async (downloadURL) => {
    await createVideo({ videoURL, details, thumbnail:downloadURL });
    console.log(details)
    alert('Video created!');
    navigate('/');
  };

 const renderSwitch = () => {
    switch (page) {
      case 1 : return <Video onNext={() => setPage(2)} 
                        close={handleClose} 
                        setVideoURL={setVideoURL}/>

      case 2 : return <VideoDetails onBack={() => setPage(1)} 
                        onNext={() => setPage(3)} 
                        close={handleClose} 
                        details={details} 
                        setDetails={setDetails}/>

      case 3 : return <Thumbnail  onBack={() => setPage(2)} 
                        close={handleClose} 
                        onFinish={handleFinish}/>

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



async function createVideo({videoURL, details, thumbnail}){
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
    console.log(details)
    const payload = {
      'filepath' : videoURL,
      'thumbnail' : thumbnail,
      'title' : details.title,
      'caption' : details.caption,
      'category' : details.category,
      'description' : details.description,
      'videostatus' : details.videostatus
    }

    console.log("details :", payload )

    const res = await fetch ('http://localhost:8080/video/create',{
      method : "POST",
      headers: {
        'Content-Type': 'application/json',
        "X-XSRF-TOKEN": getCookieValue("XSRF-TOKEN")
      },
      credentials:"include",
      body: JSON.stringify(payload)
    })
    console.log("video api status:", res.status)
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }
  } catch (error) {
    console.error('Error creating video:', error);
  }
}