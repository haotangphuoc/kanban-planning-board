import "./App.css";
import Header from "./Components/Header";
import Dashboard from "./Pages/Dashboard";
import Login from "./Pages/Login.js";
import Register from "./Pages/Register.js";
import Homepage from "./Pages/Homepage";
import CreateWorkspace from "./Pages/Workspace";
import CreateBoards from "./Pages/CreateBoards";
import Board from "./Pages/Board";
import { BrowserRouter, Routes, Route } from "react-router-dom";

const App = () => (
  <BrowserRouter>
    <Header />
    <main>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/Pages/Login.js" element={<Login />} />
        <Route path="/Pages/Homepage.js" element={<Homepage />} />
        <Route path="/Pages/Register.js" element={<Register />} />
        <Route path="/Pages/Workspace.js" element={<CreateWorkspace />} />
        <Route path="/Pages/CreateBoards.js" element={<CreateBoards />} />
        <Route path="/Pages/Board.js" element={<Board />} />
      </Routes>
    </main>
  </BrowserRouter>
);

export default App;
