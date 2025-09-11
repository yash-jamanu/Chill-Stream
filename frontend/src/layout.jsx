import React, { useState } from 'react'
import { SideBar } from './components/Sidebar'
import { Navbar } from './components/Navbar'

export const Layout = ({searchText, setMainType, setValue}) => {
    const [isSideBarOpen, setSideBarOpen] = useState(false);

    const toggleSideBar = () =>{
        setSideBarOpen(!isSideBarOpen);
    }
  return (
  <>
    <Navbar onSearch={searchText} isSideBarOpen={isSideBarOpen} toggleSideBar={toggleSideBar} />
    {isSideBarOpen && <SideBar setMainType={setMainType} setValue={setValue}/>}
  </>
  )
}
