import React, { useEffect, useState } from "react";
import { Container, Row, Nav, Stack, Card } from "react-bootstrap";

const Workspace = () => {
  const [workspaceName, setWorkspaceName] = useState("");
  const [workspaceDescription, setWorkspaceDescription] = useState("");

  useEffect(() => {
    const selectedWorkspace = localStorage.getItem("selectedWorkspace");
    if (selectedWorkspace) {
      const workspace = JSON.parse(selectedWorkspace);
      setWorkspaceName(workspace.name);
      setWorkspaceDescription(workspace.description);
    }
  }, []);

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
            <h4>{workspaceName}</h4>
            <p>{workspaceDescription}</p>

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
                <Card.Title>Board Title</Card.Title>
                <Card.Text>Description</Card.Text>
                <Card.Title>Board Title</Card.Title>
                <Card.Text>Description</Card.Text>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Workspace;
