import React, { useState, useEffect } from "react";
import {
  Container,
  Stack,
  Row,
  Form,
  Col,
  Card,
  Button,
  Nav,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Homepage = () => {
  const navigate = useNavigate();
  const [validated, setValidated] = useState(false);

  const [workspaceName, setWorkspaceName] = useState("");
  const [workspaceDescription, setWorkspaceDescription] = useState("");
  const [userId, setUserId] = useState("");

  useEffect(() => {
    const userData = localStorage.getItem("userData");
    if (userData) {
      const { email } = JSON.parse(userData);
      fetchUserIdByEmail(email);
    }
  }, []);

  const fetchUserIdByEmail = async (email) => {
    try {
      const response = await fetch(`http://localhost:8001/api/findUserIdByEmail?email=${email}`);
      const data = await response.json();

      if (response.ok && data >= 0) {
        setUserId(data);
      } else {
        console.error("Failed to fetch user ID");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to fetch user ID:", error);
      // Handle error case
    }
  };

  const handleSubmit = async() => {

    const workspaceData = {
      id: userId,
      workspaces: [
        {
          name: workspaceName,
          description: workspaceDescription,
        },
      ],
    };

    try {
      console.log(JSON.stringify(workspaceData))
      const response = await fetch("http://localhost:8001/api/createWorkspace", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "POST,PATCH,OPTION",
        },
        body: JSON.stringify(workspaceData),
      });

      if (response.ok) {
        console.log("Workspace created successfully");
        localStorage.setItem("workspaceName", workspaceName);
        // Update the state with the new workspace
        // Perform any additional actions after workspace creation
      } else {
        console.error("Failed to create workspace");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to create workspace:", error);
      // Handle error case
    }
  };

  const handleWorkspaceNameChange = (event) => {
    setWorkspaceName(event.target.value);
  };

  const handleWorkspaceDescriptionChange = (event) => {
    setWorkspaceDescription(event.target.value);
  };

  const logout = async () => {
    localStorage.clear();
    navigate("/");
  };
  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <Stack className="mx-auto" direction="horizontal" gap={1}>
              <div className="ms-auto" style={{ paddingTop: 8 }}>
                <Button variant="danger" onClick={logout}>
                  LOGOUT
                </Button>
              </div>
            </Stack>
            <h2 style={{ paddingTop: 24, paddingBottom: 24 }}>Workspaces</h2>
            <Card>
              <Card.Body>
                <Row className="mb-3">
                  <Card.Title>
                    <Nav.Link href="../Pages/Workspace.js">
                      Workspace Name
                    </Nav.Link>
                  </Card.Title>
                  <Card.Text>Description</Card.Text>
                  <Card.Title>
                    <Nav.Link href="../Pages/Workspace.js">
                      Workspace Name
                    </Nav.Link>
                  </Card.Title>
                  <Card.Text>Description</Card.Text>
                </Row>
              </Card.Body>
            </Card>
            <h4 style={{ paddingTop: 38, paddingBottom: 24 }}>
              Let's build a Workspace
            </h4>
            <Card>
              <Card.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Workspace Name</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter a workspace name"
                        value={workspaceName}
                        onChange={handleWorkspaceNameChange}
                      />
                      <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                      <Form.Control.Feedback type="invalid">
                        Please enter a workspace name.
                      </Form.Control.Feedback>
                      <br />
                      <Form.Group controlId="validationCustom01">
                        <Form.Label>Workspace description</Form.Label>
                        <Form.Control
                          as="textarea"
                          aria-label="With textarea"
                          value={workspaceDescription}
              onChange={handleWorkspaceDescriptionChange}
                        />
    
                      </Form.Group>
                    </Form.Group>
                  </Row>
                </Form>
                <Button variant="primary" type="submit" as={Col} md="2" onClick={handleSubmit}>
                  Submit
                </Button>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Homepage;
