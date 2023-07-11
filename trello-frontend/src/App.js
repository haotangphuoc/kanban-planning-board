import "./App.css";
import Header from "./Components/Header";
import Dashboard from "./Pages/Dashboard";
import Login from "./Pages/Login.js";
import Register from "./Pages/Register.js";
import Homepage from "./Pages/Homepage";
import CreateWorkspace from "./Pages/Workspace";
import CreateBoards from "./Pages/CreateBoards";
import Board from "./Pages/Board";
import Settings from "./Pages/Settings";
import Members from "./Pages/Members";
import ForgotPassword from "./Pages/ForgotPassword";
import CreateTasks from "./Pages/CreateTasks";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import PrivateRoutes from "./security/PrivateRoutes";

const App = () => (
  <BrowserRouter>
    <Header />
    <main>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/Pages/Login.js" element={<Login />} />
        <Route path="/Pages/Register.js" element={<Register />} />
        <Route path="/Pages/ForgotPassword.js" element={<ForgotPassword />} />

        <Route element={<PrivateRoutes />}>
          <Route path="/Pages/Homepage.js" element={<Homepage />} />
          <Route path="/Pages/Workspace.js" element={<CreateWorkspace />} />
          <Route path="/Pages/CreateBoards.js" element={<CreateBoards />} />
          <Route path="/Pages/Board.js" element={<Board />} />
          <Route path="/Pages/Settings.js" element={<Settings />} />
          <Route path="/Pages/Members.js" element={<Members />} />
          <Route path="/Pages/CreateTasks.js" element={<CreateTasks />} />
        </Route>
      </Routes>
    </main>
  </BrowserRouter>
);

export default App;
