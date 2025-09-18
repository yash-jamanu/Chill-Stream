import React from 'react'
import './VideoPage.css'

export default VideoPlayer

function VideoPlayer  ({filepath})  {

  return (
    <div className='video-player-block'>
        <video className='video-player' controls>
            <source src= {filepath} type="video/mp4"/>
        </video>
    </div>
  )
}
