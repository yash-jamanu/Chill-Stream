import React, { useEffect, useState } from 'react'
import './mainSection.css'
import { useNavigate } from 'react-router-dom'

async function getData(type, value){
    let data = type;

    let res;

    const YourVideos = 'http://localhost:8080/video/me'
    const randomVideos = 'http://localhost:8080/video/random-videos'
    const category = `http://localhost:8080/video/category/${value}`
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
        console.log(res.status)
        return await res.json();

    }catch (error){
        console.error("Fetch error:", error);
        return [];
    }
    
}

function CardContainer({title, thumbnail, category, caption}){
    return(
    <div className="product-card">
		<div className="badge material-symbols-outlined">bookmark</div>
		<div className="product-tumb">
			<img src={thumbnail} alt="picture" />
		</div>
		<div className="product-details">
			<span className="product-catagory">{category}</span>
			<h4>{title}</h4>
			<p>{caption}</p>
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
            {videos.map((video, id) => (
                <CardContainer 
                    key={video.videoid || id}
                    title={video.title}
                    thumbnail={video.thumbnail}
                    caption={video.caption || "caption"}
                    category={video.category || "category"} 
                    onClick={() =>{handleNavigation(video.videoid)}}
                />
            ))}        
        </div>
    </div>
  )
}
