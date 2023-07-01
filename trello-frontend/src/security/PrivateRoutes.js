import { Outlet, Navigate } from 'react-router-dom'


const PrivateRoutes = () => {
    const storedValue = localStorage.getItem('globalState');

    return(
        storedValue ? <Outlet/> : <Navigate to="/Pages/Login.js"/>
    )
}

export default PrivateRoutes