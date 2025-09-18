import React from 'react'
import { useLocation } from 'react-router-dom';
import './VideoPage.css'
import { useParams } from 'react-router-dom';
import  VideoPlayer  from './VideoPlayer';
import  VideoDetailsPage  from './VideoDetailsPage';
import  VideoComments  from './VideoComments';


export const VideoPage = () => {
    const location = useLocation();
    const {VideoDetails} = location.state || {}

     if (!VideoDetails) {
        return <div className="error">No video details provided</div>;
    }

    return (
      <>
        <div className='main-content'>
            <VideoPlayer filepath={VideoDetails.filepath}/>
            <VideoDetailsPage VideoDetails={VideoDetails}/> 
            <VideoComments VideoId={VideoDetails.videoId}/>
        </div>
      </>
    )
}




