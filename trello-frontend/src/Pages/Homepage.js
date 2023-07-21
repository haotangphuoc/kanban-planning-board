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
  const [workspaces, setWorkspaces] = useState([]);
  const [workspaceName, setWorkspaceName] = useState("");
  const [workspaceDescription, setWorkspaceDescription] = useState("");
  const [userId, setUserId] = useState("");

  useEffect(() => {
    const userData = localStorage.getItem("userData");
    if (userData) {
      const { email } = JSON.parse(userData);
      fetchUserIdByEmail(email);
    }

    const storedWorkspaces = localStorage.getItem("workspaces");
    if (storedWorkspaces) {
      setWorkspaces(JSON.parse(storedWorkspaces));
    } else if (userId) {
      fetchWorkspaces();
    }
  }, [userId]);

  const fetchWorkspaces = async () => {
    try {
      const response = await fetch(
        `http://localhost:8001/api/getUserWorkspaces?id=${userId}`
      );
      const data = await response.json();

      if (response.ok) {
        setWorkspaces(data);
      } else {
        console.error("Failed to fetch user workspaces");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to fetch user workspaces:", error);
      // Handle error case
    }
  };

  const fetchUserIdByEmail = async (email) => {
    try {
      const response = await fetch(
        `http://localhost:8001/api/findUserIdByEmail?email=${email}`
      );
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

  const handleSubmit = async (event) => {
    event.preventDefault();

    const form = event.currentTarget;
    if (!form.checkValidity()) {
      event.stopPropagation();
      setValidated(true);
      return;
    }

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
      const response = await fetch(
        "http://localhost:8001/api/createWorkspace",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(workspaceData),
        }
      );

      if (response.ok) {
        console.log("Workspace created successfully");
        localStorage.setItem("workspaceName", workspaceName);

        const updatedWorkspaces = [...workspaces, workspaceData.workspaces[0]];
        setWorkspaces(updatedWorkspaces);
        localStorage.setItem("workspaces", JSON.stringify(updatedWorkspaces));

        // Clear the form fields
        setWorkspaceName("");
        setWorkspaceDescription("");
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

  const logout = () => {
    localStorage.removeItem("userData");
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
                {workspaces.map((workspace, index) => (
                  <Row className="mb-3" key={index}>
                    <Card.Title>
                      <Nav.Link
                        href={`../Pages/Workspace.js?name=${workspace.name}`}
                        onClick={() => {
                          localStorage.setItem(
                            "selectedWorkspace",
                            JSON.stringify(workspace)
                          );
                        }}
                      >
                        {workspace.name}
                      </Nav.Link>
                    </Card.Title>
                    <Card.Text>{workspace.description}</Card.Text>
                  </Row>
                ))}
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
                    </Form.Group>
                  </Row>
                  <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationCustom02">
                      <Form.Label>Workspace Description</Form.Label>
                      <Form.Control
                        as="textarea"
                        required
                        placeholder="Enter a workspace description"
                        value={workspaceDescription}
                        onChange={handleWorkspaceDescriptionChange}
                      />
                      <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                      <Form.Control.Feedback type="invalid">
                        Please enter a workspace description.
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Row>
                  <Button variant="primary" type="submit">
                    Submit
                  </Button>
                </Form>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Homepage;
