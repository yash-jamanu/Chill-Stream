import React, { useState, useRef} from 'react';
import './UploadVideo.css';



export function VideoDetails({ onBack, onNext, close, details, setDetails }) {

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDetails((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onNext(); 
  };

  return (
    <>
      <div className="video-block">

        <div className='flex items-center justify-between'>
          <h2 className='px-3'>Details</h2>
          <div className="material-symbols-outlined close-btn" onClick={close}>close</div>
        </div>

        <form onSubmit={handleSubmit} className='details-form'>

          <input type="text" name="title"
            className='details-title details-input' 
            placeholder="Title" 
            value={details.title || ''} 
            onChange={handleChange} 
          />

          <input type='text' name='category'
            className='details-category details-input'
            placeholder='Category'
            value={details.category || ''}
            onChange={handleChange}
          />

          <input type='text' name='caption'
            className='details-caption details-input'
            placeholder='caption'
            value={details.caption || ''}
            onChange={handleChange}
          />

          <select className="form-select form-select-lg mb-3 details-input h-full" 
            aria-label="Large select example" 
            name="videostatus" value={details.videostatus} 
            onChange={handleChange}
            required
          >
            <option value="private">Private</option>
            <option value="public">Public</option>
          </select> 

          <textarea name="description" 
            className='details-description  details-input'
            placeholder="Description" 
            value={details.description || ''} 
            onChange={handleChange} 
          /> 

          <button onClick={onBack} className='detailsUpload-btn'>Back</button>
          <div className='w-full flex justify-end'>
            <button type="submit" className='detailsUpload-btn next-btn'>Next</button>
          </div>
        </form>
      </div>
    </>
   );
  }
