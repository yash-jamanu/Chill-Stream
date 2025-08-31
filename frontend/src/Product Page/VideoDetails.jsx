import React, { useEffect, useState } from 'react'
import './VideoPage.css'

export const VideoDetails = ({videoId}) => {

    const [videoData, setVideoData] = useState(null);

    //get videoData like title, caption, description
    useEffect(() => {
        const fetchData = async () => {
            const data = await getVideoData(videoId); 
            setVideoData(data);
        };
        fetchData();
    }, [videoId]);


    //like
    const [like , setLike] = useState(0)

    const PrintLike = () =>{
        let setLike = videoData.likecount; 

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
            videoId: videoId
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
            setIsLiked(true);
            setIsDisliked(false); 
        }   
        
        const data = {
            videoId: videoId
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
        if (!videoData) return;
        const fetchData = async () => {
            const data = await getUserData(videoData.userid);
            setUserData(data);
        };
        fetchData();
    }, [videoData]);

    //Followers
    const [followers, setFollowers] = useState(0)

    const follower = () =>{
        let setFollowers = userData.subs_count; 

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
        <h1 className='video-title'>{videoData ? (videoData.title) : "Title"}</h1>
        <p className='video-category'># {videoData ? (videoData.category) : "Category"}</p>
        <h5 className='caption'>{videoData ? (caption) : "Caption"}</h5>
                
        <div className='like-dislike-container'>
            <div onClick={handleLike}>
                <span className={`material-symbols-outlined ${isLiked ? 'Fill' : ''}`} >thumb_up</span>
                <span>{PrintLike}</span>
            </div>
            <div onClick={handleDisLike}>
                <span className={`material-symbols-outlined ${isDisliked ? 'Fill' : ''}`}>thumb_down</span>
            </div>
        </div>
                
        <div className='channel-details'>
            <div className='flex'>
                <img src={userData.profile} alt='profile' className='profile-picture' />
                <div>
                    <p className='channel-username'>{userData ? (userData.username) : "Username"}</p>
                    <p className='channel-followers'>{follower} followers</p>
                </div>
            </div>
            <div className='text-center flex items-center'>
                <button onClick={handleFollowBtn} className='follow-btn'>
                    {followBtn ? "Unfollow" : "Follow"}
                </button>
            </div>
        </div>
    
        <p className='description'>
            {videoData ? (videoData.description) : "Description"}
        </p>
    </div>
  )
}


async function getVideoData(videoid) {
    try {
        const res = await fetch(`http://127.0.0.1:8080/video/${videoid}`);    
        return await res.json();
    } catch (error) {
        console.error("Fetch error:", error);
        return null;
    }
}

async function getUserData(userid) {
    try {
        const res = await fetch(`http://127.0.0.1:8080/user/${userid}`);    
        return await res.json();
    } catch (error) {
        console.error("Fetch error:", error);
        return null;
    }
}