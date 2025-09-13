import React, { useEffect, useState } from 'react'
import './mainSection.css'
import { useNavigate } from 'react-router-dom'

async function getData(text, type, value){
    let data = type;

    let res;

    const YourVideos = 'http://localhost:8080/video/me'
    const randomVideos = 'http://localhost:8080/video/random-videos'
    const category = `http://localhost:8080/video/category/${value}`
    const wishlist = 'http://localhost:8080/wishlist/videos'
    const searchedText = `http://localhost:8080/search?searchText=${text}`

    if (text && text.trim() !== "") {
        res = await fetch(searchedText, { method: "GET" });
        return await res.json();
    }else{
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
                    break;
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
    
}

function CardContainer({title, thumbnail, category, caption}){
    return(
    <div className="card">
			<img src={thumbnail} alt="picture" className='card-img'/>
		<div className="card-details">
			<span className="card-category"># {category}</span>
			<h4 className='card-title'>{title}</h4>
			<p className='card-caption'>{caption}</p>
		</div>
	</div>
    )
}

export const MainSection = ({searchText, type, value}) => {
    const [videos, setVideos] = useState([])

    useEffect(() => {  
        getData(searchText, type, value).then(data => {
            setVideos(data);
        });
    }, [searchText, type, value]);

    const navigate = useNavigate();

    const handleNavigation = (videoid) => {
        navigate(`/video-player/${videoid}`)
    }
  return (
    <div className='main-section'>
        <div className='container-box'>
            {videos.map((video, id) => (
                <CardContainer 
                    key={video.videoid}
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
