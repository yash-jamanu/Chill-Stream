import React, { useState } from 'react'
import { SideBar } from './components/sideBar'
import { Navbar } from './components/Navbar'

export const Layout = ({setMainTypeLayout, setValueLayout}) => {
    const [isSideBarOpen, setSideBarOpen] = useState(false);

    const toggleSideBar = () =>{
        setSideBarOpen(!isSideBarOpen);
    }
  return (
  <>
    <Navbar isSideBarOpen={isSideBarOpen} toggleSideBar={toggleSideBar} />
    {isSideBarOpen && <SideBar setMainType={setMainTypeLayout} setValue={setValueLayout} />}
  </>
  )
}
