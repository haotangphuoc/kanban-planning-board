import React, { useState } from "react";
import {
  Container,
  Row,
  Nav,
  Stack,
  Button,
  ListGroup,
  Card,
} from "react-bootstrap";

const CreateWorkspace = () => {
  const [workspaceName, setWorkspaceName] = useState("");
  const [workspaceDescription, setWorkspaceDescription] = useState("");

  const handleSubmit = () => {
    const workspaceData = {
      id: "1", // Assuming a fixed user ID for testing purposes
      workspaces: [
        {
          name: workspaceName,
          description: workspaceDescription,
        },
      ],
    };

    fetch("http://localhost:8001/api/createWorkspace", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(workspaceData),
    })
      .then((response) => {
        if (response.ok) {
          console.log("Workspace created successfully");
          // Perform any additional actions after workspace creation
        } else {
          console.error("Failed to create workspace");
          // Handle error case
        }
      })
      .catch((error) => {
        console.error("Failed to create workspace:", error);
        // Handle error case
      });
  };

  const handleWorkspaceNameChange = (event) => {
    setWorkspaceName(event.target.value);
  };

  const handleWorkspaceDescriptionChange = (event) => {
    setWorkspaceDescription(event.target.value);
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
            <h4>Workspace Name</h4>
            <input
              type="text"
              value={workspaceName}
              onChange={handleWorkspaceNameChange}
            />
            <h6>Description</h6>
            <input
              type="text"
              value={workspaceDescription}
              onChange={handleWorkspaceDescriptionChange}
            />
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
                <Card.Title>
                  <Nav.Link href="../Pages/Board.js">Board Title</Nav.Link>
                </Card.Title>
                <Card.Text>Description</Card.Text>
                <Card.Title>
                  <Nav.Link href="#">Board Title</Nav.Link>
                </Card.Title>
                <Card.Text>Description</Card.Text>
              </Card.Body>
            </Card>
          </div>
          <Button variant="primary" onClick={handleSubmit}>
            Create Workspace
          </Button>
        </Row>
      </Container>
    </div>
  );
};

export default CreateWorkspace;
