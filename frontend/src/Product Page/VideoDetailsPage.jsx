import React, { useEffect, useState } from 'react'
import profile from '../assets/profile.png'
import './VideoPage.css'
import { useAuth } from '../AuthLogin'
import { useNavigate } from 'react-router-dom'

export default VideoDetailsPage

function VideoDetailsPage  ({VideoDetails})  {

    const { isLoggedIn } = useAuth();
    const navigate = useNavigate();

    const [like, setLike] = useState(VideoDetails?.likecount || 0);

    const PrintLike = () =>{

        let formattedLikes;

        switch (true) {
            case like >= 1000000:
                formattedLikes = (like / 1000000).toFixed(1).replace(/\.0$/, "") + "M";
                break;
            case like >= 1000:
                formattedLikes = (like / 1000).toFixed(1).replace(/\.0$/, "") + "K";
                break;
            default:
                formattedLikes = like.toString();
                break;
        }
        return formattedLikes;
    }

    const [isLiked, setIsLiked] = useState(false)
    const [isDisliked, setIsDisliked] = useState(false)

    useEffect(()=>{
        if (!isLoggedIn) return;
        const fetchStatus = async () => {
            const status = getReactionStatus({videoId:VideoDetails.videoId});
            if(status == "LIKE"){
                setIsLiked(true)
            }else if(status == "DISLIKE"){
                setIsDisliked(true)
            }
        }
        fetchStatus();
    },[VideoDetails])

    function handleReaction (type)  {
      if (!isLoggedIn){
        navigate("/Login")
        return;
      }

      reaction({videoId: VideoDetails.videoId, reactionType: type});
      if (type === "LIKE") {
        if (isLiked) {
          setLike(like - 1)
          setIsLiked(false);
        } else {
          setIsLiked(true);
          setLike(like + 1)
          setIsDisliked(false);
        }
      } else if (type === "DISLIKE") {
        if (isDisliked) {
          setIsDisliked(false);
        } else {
          setIsDisliked(true);
          setIsLiked(false);
        }
      }
    };

    
    const [channelData, setChannelData] = useState({})
    
    useEffect(()=>{
        const fetchChannelData = async ()=>{
            const data = await getChannelData({userId: VideoDetails.userId})
            setChannelData(data)
        }
        fetchChannelData();
    },[VideoDetails])
    

    const [followers, setFollowers] = useState(channelData.subs_count || 0);

    const follower = () =>{
        let formattedFollowers;

        switch (true) {
            case followers >= 1000000:
                formattedFollowers = (followers / 1000000).toFixed(1).replace(/\.0$/, "") + "M";
                break;
            case followers >= 1000:
                formattedFollowers = (followers / 1000).toFixed(1).replace(/\.0$/, "") + "K";
                break;
            default:
                formattedFollowers = followers.toString();
                break;
        }
        return formattedFollowers;
    }

    let [FollowAction, setFollowAction] = useState(false)

    useEffect(()=>{
        if(!isLoggedIn) return;

        const fetchUser= async ()=>{
            const data = await getFollowDetails({channelId: VideoDetails.userId})
            if(data.action == "FOLLOW"){
                setFollowAction(true)
            }else{
                setFollowAction(false)
            }
        }
        fetchUser();
    }, [VideoDetails])

    const handleFollowBtn = async () => {
        if(!isLoggedIn){
            navigate("/Login")
            return;
        }
        
        await followAction({ channelId: VideoDetails.userId });
        let data = await getFollowDetails({channelId: VideoDetails.userId})
        if(data.action == "FOLLOW"){
            setFollowAction(true)
                setFollowers(followers + 1) 
        }else{
            setFollowAction(false)
            if(followers > 0){setFollowers (followers - 1)} 
        }
    }

  return (
    <div className='details-block'>
        <h1 className='video-title'>{VideoDetails ? (VideoDetails.title) : "Title"}</h1>
        <p className='video-category'># {VideoDetails ? (VideoDetails.category) : "Category"}</p>
        <h5 className='caption'>{VideoDetails ? (VideoDetails.caption) : "Caption"}</h5>
                
        <div className='like-dislike-container'>
            <div onClick={()=>{handleReaction("LIKE")}}>
                <span className={`material-symbols-outlined ${isLiked ? "Fill" : ""}`}>thumb_up</span>
                <span>{PrintLike()}</span>
            </div>
            
            <div onClick={()=>{handleReaction("DISLIKE")}}>
                <span className={`material-symbols-outlined ${isDisliked ? "Fill" : ""}`}>thumb_down</span>
            </div>
        </div>
                
        <div className='channel-details'>
            <div className='flex'>
                <img src={channelData?.profile || profile} alt='profile' className='profile-picture' />
                <div>
                    <p className='channel-username'>{channelData ? (channelData.username) : "Username"}</p>
                    <p className='channel-followers'>{follower()} followers</p>
                </div>
            </div>
            <div className='text-center flex items-center'>
               {FollowAction? 
                        <button onClick={handleFollowBtn} className='px-2 py-2 bg-blue-500 text-white'>unfollow</button>
                        :
                        <button onClick={handleFollowBtn} className='px-2 py-2 bg-blue-500 text-white'>follow</button>
                }  
            </div>
        </div>
    
        <p className='description'>
            {VideoDetails ? (VideoDetails.description) : "Description"}
        </p>
    </div>
  )
}

async function getReactionStatus ({videoId}){
    try{
        const res = await fetch(`http://localhost:8080/video/status/reaction/${videoId}`,{
            method: "GET",
            headers :{
                "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
            }
        })

        if(res.ok){
            return res.json()
        }else{
            console.log(res.status);
        }
    }catch(error){
        console.log(error);
    }
    return null;
}

async function reaction ({videoId, reactionType}){
    try {
        
        const data = {
            "videoId" : videoId,
            "reactionType" : reactionType 
        }
        const res = await fetch("http://localhost:8080/video/videos/reaction",{
            method:"POST",
            headers :{
                "Content-Type": "application/json",
                "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
            },
            body: JSON.stringify(data)
        })
        if (!res.ok) {
          console.error("Request failed:", res.status);
        }
    }catch(error){
        console.error(error)
    }
}

async function getChannelData({userId}){
    try{
        const res = await fetch(`http://localhost:8080/user/channel/${userId}`,{
            method:"GET"
        })

        if(res.ok){
            return res.json()
        }else{
            console.log(res.status)
        }
    }catch(error){
        console.error(error)
    }
}

async function getFollowDetails({channelId}){
    try{
        const res = await fetch (`http://localhost:8080/follower/status/follow/${channelId}`,{
            method:"GET",
            headers :{
                "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
            }
        })

        if(res.ok){
            return res.json()
        }else{
            console.log(res.status);
        }
    }catch(error){
        console.log(error);
    }
    return null;
}

async function followAction ({ channelId }){
    try{
        const data = {
            "channelId" : channelId
        }
        console.log(data)
        const res = await fetch("http://localhost:8080/follower/follow-unfollow",{
            method:"POST",
            headers:{
                "Content-Type": "application/json",
                "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
            },
            
            body: JSON.stringify(data)
        })
        if(res.ok){
            console.log("channel successfully");
        }else{
            console.log(res.status)
            
        }
    }catch(error){
        console.error(error);
    }
}