import React, { useEffect, useState } from 'react'
import './mainSection.css'
import { useNavigate } from 'react-router-dom'
import { VideoDetails } from './Pages/videoDetails';


async function getData(text, type, value){
    let data = type;

    let res;

    const YourVideos = 'http://localhost:8080/video/my-videos'
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
                    res = await fetch(YourVideos, {
                        method:"GET",
                        headers :{
                          "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
                        }
                    });
                    break;
                }
                case "category": {
                    res = await fetch(category, {
                      method:"GET"
                    });
                    break;
                }
                case "wishlist" : {
                    res = await fetch(wishlist, {
                        method:"GET",
                        headers :{
                          "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
                        }
                    });
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

function CardContainer({video, onClick}){
    const[showMore ,setShowMore] = useState(false);
    const [letDelete, setLetDelete] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
      const authDelete = () => {
        const userId = localStorage.getItem("userID");
        if (video.userId == userId) {
          setLetDelete(true);
        }
      };
      authDelete();
    }, [video.userId]);

    const handleDelete = () =>{
      deleteVideo(video.videoId);
    }

    const handleEditVideo = () => {
      navigate("/UpdateVideo", {state:{VideoDetails:video.videoId}})
    }



    return(
    <div className="card" onClick={onClick}>
			<img src={`http://localhost:8080/video/thumbnail/${video.thumbnail}`} alt="picture" className='card-img'/>
		  <div className="card-details">
			  <span className="card-category"># {video.category}</span>
            
        {letDelete && (
        <span
          className="material-symbols-outlined moreInfo-btn"
          onClick={(e) => {
            e.stopPropagation(); // prevent triggering parent click
            setShowMore((prev) => !prev);
          }}
        >
          more_vert
        </span>
        )}

        {showMore && (
          <div className="moreInfo-card">
            <div className="delete-btn" onClick={handleDelete}>
              <span className="material-symbols-outlined info-icon">Delete</span>
              <span className="font-bold" style={{fontSize: "0.7rem"}}>Delete</span>
            </div>

            <div className="delete-btn" onClick={handleEditVideo}>
              <span className="material-symbols-outlined info-icon">edit</span>
              <span className="font-bold" style={{fontSize: "0.7rem"}}>Edit</span>
            </div>
          </div>

        )}
			  <h4 className='card-title'>{video.title}</h4>
			  <p className='card-caption'>{video.caption}</p>
		  </div>
	  </div>
  )
}

async function deleteVideo(videoId){
 
  try{
    const res = await fetch (`http://localhost:8080/video/delete/${videoId}`,{
      method:"DELETE",
      headers :{
        "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
      }
    })

    if(res.ok){
      alert("Video deleted");
    }else{
      console.log(res.status)
    }
  }catch(error){
    console.log("error", error)
  }
} 


export const MainSection = ({searchText, type, value}) => {
    const [videos, setVideos] = useState([])

    useEffect(() => {  
        getData(searchText, type, value).then(data => {
            setVideos(data);
        });
    }, [searchText, type, value]);

    const navigate = useNavigate();

    const handleNavigation = (videoDetails) => {
        navigate("/video-player", {state:{VideoDetails :videoDetails}})
    }
  return (
    <div className='main-section'>
        <div className='container-box'>
            {videos.map((video) => (
                <CardContainer
                  key={video.videoId}
                  video={video}
                  onClick={() =>{handleNavigation(video)}}
                />
            ))}        
        </div>
    </div>
  )
}
