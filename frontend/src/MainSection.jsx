import React, { useEffect, useState } from 'react'
import './mainSection.css'
import thumbnail from './assets/M5.jpg'
import { VideoPage } from './Product Page/VideoPage'
import { useNavigate } from 'react-router-dom'

async function getData(type, value){
    let data = type;

    let res;

    const YourVideos = 'http://127.0.0.1:8080/video/me'
    const randomVideos = 'http://127.0.0.1:8080/video/random-videos'
    const category = `http://127.0.0.1:8080/video/category/${value}`
    const wishlist = 'http://localhost:8080/wishlist/videos'

    try{
        switch(data){
            case "me": {
                res = await fetch(YourVideos);
                break;
            }
            case "category": {
                res = await fetch(category);
                break;
            }
            case "wishlist" : {
                res = await fetch(wishlist);
            }
            default: {
                res = await fetch(randomVideos);
            }
                break;
        }
         return await res.json();

    }catch (error){
        console.error("Fetch error:", error);
        return [];
    }
    
}

function CardContainer({title, thumbnail}){
    return(
         <div className="card">
            <img src={thumbnail} alt='thumbnail' />
            <div className="card-body">
                <h5 className="card-title">{title}</h5>
            </div>
        </div>
    )
}

export const MainSection = ({type, value}) => {
    const [videos, setVideos] = useState([])

    useEffect(() => {
        
        getData(type, value).then(data => {
            setVideos(data);
        });
    }, [type, value]);

    const navigate = useNavigate();

    const handleNavigation = (videoid) => {
        navigate(`/video-player/${videoid}`)
    }
  return (
    <div className='main-section'>
        <div className='container-box'>
            {videos.map((video, idx) => (
                <CardContainer 
                    key={video.videoid || idx}
                    title={video.title}
                    thumbnail={video.thumbnail || thumbnail}
                    videoid={video.videoid} 
                    onClick={() =>{handleNavigation(video.videoid)}}
                />
            ))}        
        </div>
    </div>
  )
}
