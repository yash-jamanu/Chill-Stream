import React, { useState } from 'react'
import { MainSection } from './MainSection'

export const Home = ({searchText, setMainType, setValue} ) => { 

  return (
    <>
    <MainSection searchText={searchText} type={setMainType} value={setValue}/>
    </>
  )
}
