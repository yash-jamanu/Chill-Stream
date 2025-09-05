import React, { useState } from 'react'
import { SideBar } from './components/Sidebar'
import { Navbar } from './components/Navbar'

export const Layout = ({setMainType, setValue}) => {
    const [isSideBarOpen, setSideBarOpen] = useState(false);

    const toggleSideBar = () =>{
        setSideBarOpen(!isSideBarOpen);
    }
  return (
  <>
    <Navbar isSideBarOpen={isSideBarOpen} toggleSideBar={toggleSideBar} />
    {isSideBarOpen && <SideBar setMainType={setMainType} setValue={setValue}/>}
  </>
  )
}
