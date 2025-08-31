import React, { useState } from 'react'
import { MainSection } from './MainSection'

export const Home = ({setMainType, setValue} ) => { 

  return (
    <>
    <MainSection type={setMainType} value={setValue}/>
    </>
  )
}
