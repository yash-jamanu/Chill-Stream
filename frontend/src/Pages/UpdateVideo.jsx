import React, { useState, useEffect, useRef } from 'react';
import { useLocation , useNavigate} from 'react-router-dom';

function UpdateThumbnail ({oldThumbnail, setThumbnail}) {
    const [isProcessing, setIsProcessing] = useState(false);
    const [file, setFile] = useState(null);
    const fileInputRef = useRef(null);
    const [fileStatus, setFileStatus] = useState(false)

    useEffect(() => {
        if (fileStatus && file) {
          uploadThumbnail();
        }
    }, [fileStatus, file]);

    const uploadThumbnail = async () => {
        setIsProcessing(true);

        const storageRef = ref(storage, `thumbnails/${Date.now()}-${file.name}`);
        const uploadTask = uploadBytesResumable(storageRef, file);

        uploadTask.on(
            "state_changed",
            null,
            console.log("thumbnail Uploading"),
            async () => {
              const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
              setThumbnail(downloadURL);
              alert("Thumbnail updated!");
              setIsProcessing(false);
            }
        );
    };

  return (
    <div>
        <div className='thumbnailUpdate-block'>
            {isProcessing && (
                <div className="processing-video">
                    <p className="text-2xl font-bold">Wait until profile is Uploading...</p>
                </div>
            )}

            <img src= { oldThumbnail || "" }  className='editThumbnail-img' alt='Thumbnail-img'/>
            
            <input type="file" accept="image/*" 
                ref={fileInputRef} 
                style={{ display: 'none' }} 
                onChange={(e) => {
                    setFile(e.target.files[0])
                    setFileStatus(true)
                }}
            />    

            <div className='material-symbols-outlined edit-icon'
                onClick={() => fileInputRef.current.click()}
            >
                Update
            </div>
        </div>
    </div>
  )
}

function EditDetails ({oldVideoDetails, setNewVideoData, onSubmit}){

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewVideoData((prev) => ({
          ...prev,
          [name]: value
        }));
    };

    return(
        <div>
            <form onSubmit={onSubmit} className='editDetails-form'>
                <input type="text" 
                    name="title"
                    placeholder={oldVideoDetails.title}
                    className='editDetails-input' 
                    onChange={handleChange} 
                />

                <input type='text' 
                    name='caption'
                    placeholder={oldVideoDetails.caption}
                    className='editDetails-input'
                    onChange={handleChange}
                />

                <input type='text' name='description'
                    placeholder={oldVideoDetails.description}
                    className='editDetails-input'
                    onChange={handleChange}
                />

                <input type='text' name='category'
                    placeholder={oldVideoDetails.category}
                    className='editDetails-input'
                    onChange={handleChange}
                />

                <select className="form-select form-select-lg mb-3 details-input h-full" 
                  aria-label="Large select example" 
                  name="videostatus" defaultValue={oldVideoDetails.videostatus} 
                  onChange={handleChange}
                  required
                >
                  <option value="private">Private</option>
                  <option value="public">Public</option>
                </select>
                
                <button type="submit" className='submit-btn'>Submit</button>
            </form>
        </div>
    )
}

export const UpdateVideo = () =>{
    const navigate = useNavigate();
    const location = useLocation();
    const {videoDetails} = location.state || {}
    console.log(videoDetails)

    const [videoData, setVideoData] = useState({})
    console.log(videoData)

    useEffect(() => {
      if (videoDetails) {
        oldVideo({video: videoDetails, setVideoData});
      }
    }, [videoDetails]);

    const [thumbnail, setThumbnail] = useState("") 
    const [newVideoData, setNewVideoData] = useState({}) 
    
    const handleUpdate = () =>{
        updateVideo({thumbnail, newVideoData}); 
    }

    return (    
        <div className='userEdit-main'>
            <div className='editProfile-block'>
                <UpdateThumbnail 
                    oldThumbnail={videoData?.thumbnail} 
                    setThumbnail={setThumbnail}
                />

                <EditDetails 
                    oldVideoDetails={videoData} 
                    setNewVideoData={setNewVideoData} 
                    onSubmit={handleUpdate}
                />
            </div>
                
        </div>
    )
}   

async function oldVideo (videoId, setVideoData){
    try {
        const res = await fetch (`http://localhost:8080/video/${videoId}`,{
            method : "GET",
        })
        if(res.ok){
            const data = await res.json();
            console.log(data)
            setVideoData(data);

        }else{
            console.error(res.status)
        }

    }catch(error){
        console.log("error", error)
    }
}

async function updateVideo (e ,{thumbnail, newVideoData}) {
    e.preventDefault();
    const navigate = useNavigate();
    try {
        const data = {
            'thumbnail' : thumbnail,
            'title' : newVideoData.title,
            'caption' : newVideoData.caption,
            'description' : newVideoData.description,
            'category' : newVideoData.category,
            'videostatus' : newVideoData.videostatus
        }
    
        const res = await fetch ("http://localhost:8080/video/update",{
            method : "PUT",
            headers: {
              'Content-Type': 'application/json',
              "Authorization" : `Bearer ${localStorage.getItem("jwt")}` 
            },
            body: JSON.stringify(data)
        })
        if(res.ok){
            console.log("video updated", res.status)
            navigate("/")
        }else{
            console.error(res.status)
        }

    }catch(error){
        console.log("error", error)
    }
    
}
