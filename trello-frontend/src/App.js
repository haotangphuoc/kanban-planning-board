import "./App.css";
import Header from "./Components/Header";
import Dashboard from "./Pages/Dashboard";
import Login from "./Pages/Login.js";
import Register from "./Pages/Register.js";
import { BrowserRouter, Routes, Route } from "react-router-dom";

const App = () => (
  <BrowserRouter>
    <Header />
    <main>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/Pages/Login.js" element={<Login />} />

        <Route path="/Pages/Register.js" element={<Register />} />
      </Routes>
    </main>
  </BrowserRouter>
);

export default App;
