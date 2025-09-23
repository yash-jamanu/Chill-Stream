// AuthContext.js
import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    fetch("http://localhost:8080/api/auth/status", {
      credentials: "include",
      method:"GET"
    })
      .then(res => res.json())
      .then(data => {
        setIsLoggedIn(data.loggedIn);
      })
      .catch(err => {
        console.error("Auth check failed:", err);
        setIsLoggedIn(false);
      });
  }, []);

  return (
    <AuthContext.Provider value={{ isLoggedIn, setIsLoggedIn }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
