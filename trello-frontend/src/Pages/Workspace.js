import React, { useEffect, useState } from "react";
import { Container, Row, Nav, Stack, Card } from "react-bootstrap";

const Workspace = () => {
  const [workspace, setWorkspace] = useState({});
  const [boards, setBoards] = useState([]);

  useEffect(() => {
    const selectedWorkspace = localStorage.getItem("selectedWorkspace");
    console.log(selectedWorkspace);
    if (selectedWorkspace) {
      setWorkspace(JSON.parse(selectedWorkspace));
      // Retrieve boards from local storage for this workspace
      fetchWorkspaceBoards();
    }
  }, []);

  const fetchWorkspaceBoards = async () => {

  };

  return (
      <div style={{ minHeight: "93vh" }}>
        <Container>
          <Row>
            <Stack
                className="mx-auto"
                direction="horizontal"
                style={{ paddingTop: 38 }}
            >
              <div style={{ paddingBottom: 12 }}>
                <Card.Title>
                  <Nav.Link href="../Pages/Homepage.js">&#60; Homepage</Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <div>
              <h2 style={{ paddingTop: 38 }}>Workspaces</h2>
              <br />
              <h4>{workspace.name}</h4>
              <p>{workspace.description}</p>

              <br />
              <Card>
                <Card.Header>
                  <Nav variant="tabs" defaultActiveKey="#boards">
                    <Nav.Item>
                      <Nav.Link href="#boards">Boards</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link href="../Pages/CreateBoards.js">
                        Create a board
                      </Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link href="../Pages/Members.js">Members</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                      <Nav.Link href="../Pages/Settings.js">Settings</Nav.Link>
                    </Nav.Item>
                  </Nav>
                </Card.Header>
                <Card.Body>
                  {boards.map((board) => (
                      <div key={board.id}>
                        <Card.Title>{board.title}</Card.Title>
                        <Card.Text>{board.description}</Card.Text>
                      </div>
                  ))}
                </Card.Body>
              </Card>
            </div>
          </Row>
        </Container>
      </div>
  );
};

export default Workspace;
