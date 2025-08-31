import React, { useEffect, useState } from 'react'
import './VideoPage.css'
import {app , storage } from '../firebaseConfig'
import { getStorage, ref } from "firebase/storage";

export const VideoPlayer = ({videoid}) => {

    const [videoUrl, setVideoUrl] = useState("")

    useEffect(() => {
        const fetchData = async () => {
            const data = await getVideoURL(videoid); 
            setVideoUrl(data);
        };
        fetchData();
    }, [videoUrl]); 
    

  return (
    <div className='video-player-block'>
        <video className='video-player' controls>
            <source src= {videoUrl} type="video/mp4"/>
        </video>
    </div>
  )
}

async function getVideoURL({videoid}){
    try {
        const res = await fetch(`http://127.0.0.1:8080/video/${videoid}`);    
        return await res.json();
    } catch (error) {
        console.error("Fetch error:", error);
        return null;
    }
}
