import logo from "./logo.svg";

import "./App.css";

function App() {
  return (
    <div className="App">
      <nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">
            Trello-Clone
          </a>

          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNavAltMarkup"
            aria-controls="navbarNavAltMarkup"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
              <a class="nav-link" href="/">
                Home
              </a>

              <a class="nav-link" href="/about">
                About
              </a>
            </div>

            <div class="navbar-nav ms-auto">
              <a class="nav-link" href="/login">
                Login
              </a>

              <a class="nav-link" href="/register">
                Register
              </a>

              <a class="nav-link" href="/logout">
                Logout
              </a>
            </div>
          </div>
        </div>
      </nav>
    </div>
  );
}

export default App;
