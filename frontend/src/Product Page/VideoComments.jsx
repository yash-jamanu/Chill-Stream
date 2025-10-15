import React from 'react'
import { useEffect, useState } from 'react'
import './VideoPage.css'

export default VideoComments

function VideoComments  ({VideoId}) {

    const [comment, setComment] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const data = {
            comment: comment,
            videoId: VideoId
        };

        try {
            const res = await fetch(`http://127.0.0.1:8080/comments/user/comment`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                    "Authorization" : `Bearer ${localStorage.getItem("jwt")}`
                },
                body: JSON.stringify(data)
            });

            if (res.ok) {
                setComment('');
                if (onCommentAdded) {
                    onCommentAdded(); // Trigger refresh
                }
            } else {
                console.log("Failed to add Comment.");
            }
        } catch (error) {
            console.error("Error posting comment:", error);
        }
    };

    
    const [commentData, setCommentData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const data = await getCommentData(VideoId); 
            setCommentData(data);
        };
        fetchData();
    }, [VideoId]);

  return (
    <div className='comments-block'>
            <form className='comment-input' onSubmit={handleSubmit}>
                <input
                    className='comment-input-bock'
                    type='text'
                    placeholder='Comment here'
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    required
                />
                <button className='comment-submit' type='submit'>Submit</button>
            </form>

            <div className='comments'>
                {commentData.length > 0 ? (
                    commentData.map((c, index) => (
                        <p key={index}>{c.comment}</p>
                    ))
                ) : (
                    <p>No comments yet.</p>
                )}
            </div>
        </div>
  )
}

async function getCommentData(VideoId){
    try{
        const res = await fetch(`http://localhost:8080/comments/Video/${VideoId}`,{
            method:"GET"
        });
        return res.json();
    }catch(error){
        console.error("Error", error);
    }
}