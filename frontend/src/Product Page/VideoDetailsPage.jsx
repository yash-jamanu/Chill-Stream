import React, { useEffect, useState } from 'react'
import profile from '../assets/profile.png'
import './VideoPage.css'

export default VideoDetailsPage

function VideoDetailsPage  ({VideoDetails})  {

    const [like , setLike] = useState(VideoDetails?.likecount || 0)

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
    const [isDisliked, setIsDisliked] = useState (false)
    
    const handleLike = async (e) => {
        e.preventDefault();

        if (!isLiked) {
            setLike(like + 1);
            setIsLiked(true);
            setIsDisliked(false); 
        }

        const data = {
            videoId: VideoDetails.videoId
        };

        try {
            const res = await fetch('http://127.0.0.1:8080/like', {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
        } catch (error) {
            console.error("Error posting comment:", error);
        }
    }
    
    const handleDisLike = async (e) => {
        e.preventDefault();

        if (isLiked) {
            setLike(like - 1);
            setIsLiked(false);
            setIsDisliked(true);
        }
 
        
        const data = {
            videoId: VideoDetails.videoId
        };

        try {
            const res = await fetch('http://127.0.0.1:8080/dislike', {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
        } catch (error) {
            console.error("Error posting comment:", error);
        }
    }

    //Follow
    const [followBtn, setFollowBtn] = useState(false)

    const handleFollowBtn = () => {
        setFollowBtn(prevState => !prevState);
    };

    //user details
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        if (!VideoDetails) return;
        let userId = VideoDetails.userId
        const fetchData = async () => {
            const res = await fetch(`http://127.0.0.1:8080/user/channel/${userId}`,{
                method : "GET"
            });
            const data = await res.json();
            setUserData(data);
        };
        fetchData();
    }, [VideoDetails]);

    //Followers
    const [followers, setFollowers] = useState(0)

    useEffect(() => {
        if (userData) {
          setFollowers(userData.subs_count || 0);
        }
    }, [userData]);

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

  return (
    <div className='details-block'>
        <div className='save-video material-symbols-outlined'>bookmark</div>
        <h1 className='video-title'>{VideoDetails ? (VideoDetails.title) : "Title"}</h1>
        <p className='video-category'># {VideoDetails ? (VideoDetails.category) : "Category"}</p>
        <h5 className='caption'>{VideoDetails ? (VideoDetails.caption) : "Caption"}</h5>
                
        <div className='like-dislike-container'>
            <div onClick={handleLike}>
                <span className={`material-symbols-outlined ${isLiked ? 'Fill' : ''}`} >thumb_up</span>
                <span>{PrintLike()}</span>
            </div>
            <div onClick={handleDisLike}>
                <span className={`material-symbols-outlined ${isDisliked ? 'Fill' : ''}`}>thumb_down</span>
            </div>
        </div>
                
        <div className='channel-details'>
            <div className='flex'>
                <img src={userData?.profile || profile} alt='profile' className='profile-picture' />
                <div>
                    <p className='channel-username'>{userData ? (userData.username) : "Username"}</p>
                    <p className='channel-followers'>{follower()} followers</p>
                </div>
            </div>
            <div className='text-center flex items-center'>
                <button onClick={handleFollowBtn} className='follow-btn'>
                    {followBtn ? "Unfollow" : "Follow"}
                </button>
            </div>
        </div>
    
        <p className='description'>
            {VideoDetails ? (VideoDetails.description) : "Description"}
        </p>
    </div>
  )
}