import React from 'react'
import './VideoPage.css'
import { useParams } from 'react-router-dom';
import { VideoPlayer } from './VideoPlayer';
import { VideoDetails } from './VideoDetails';
import { VideoComments } from './VideoComments';


export const VideoPage = () => {
    const { videoid } = useParams();
    return (
      <>
          <div className='main-content'>
              <VideoPlayer videoid={videoid}/>
              <VideoDetails videoId={videoid}/>
              <VideoComments videoId={videoid}/>
          </div>
      </>
    )
}




