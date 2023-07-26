import React, { useEffect, useState } from "react";
import {
  Container,
  Row,
  Form,
  Col,
  Card,
  Button,
  Stack,
  Nav,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Settings = () => {
  const [workspaceName, setWorkspaceName] = useState("");
  const [workspaceDescription, setWorkspaceDescription] = useState("");

  const [workspace, setWorkspace] = useState([]);
  const [workspaceId, setWorkspaceId] = useState("");
  const [workspaceNameOld, setWorkspaceNameOld] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const selectedWorkspace = localStorage.getItem("selectedWorkspace");
    console.log(selectedWorkspace);
    if (selectedWorkspace) {
      setWorkspace(JSON.parse(selectedWorkspace));
      setWorkspaceId(workspace.id);
      setWorkspaceNameOld(workspace.name);
    }
  }, [workspaceId]);

  const handleSaveWorkspace = async (event) => {
    event.preventDefault();

    try {
      // Fetch workspace ID based on workspace name
      const response = await fetch(
        `http://localhost:8001/api/findWorkspaceIdByName?name=${workspaceNameOld}`
      );
      const id = await response;
      if (response.ok) {
        setWorkspaceId(id);

        // Modify workspace description
        const workspaceData = {
          id: workspaceId,
          name: workspaceName,
          description: workspaceDescription,
        };
        console.log(JSON.stringify(workspaceData));
        const modifyResponse = await fetch(
          "http://localhost:8001/api/modifyWorkspace",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Access-Control-Allow-Origin": "*",
              "Access-Crontrol-Allow-Methods": "POST,PATCH,OPTION",
            },
            body: JSON.stringify(workspaceData),
          }
        );

        if (modifyResponse.ok) {
          console.log("Workspace modified successfully");
          // Handle success case
        } else {
          console.error("Failed to modify workspace");
          // Handle error case
        }
      } else {
        console.error("Failed to fetch workspace ID");
        // Handle error case
      }
    } catch (error) {
      console.error("Request encountered an error:", error);
      // Handle network errors or other exceptions
    }
    navigate(`../Pages/Workspace.js?name=${workspaceName}`);
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
          <div>
            <Stack
              className="mx-auto"
              direction="horizontal"
              style={{ paddingTop: 38 }}
            >
              <div style={{ paddingBottom: 12 }}>
                <Card.Title>
                  <Nav.Link href="../Pages/Workspace.js">
                    &#60; Workspace
                  </Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <h2 style={{ paddingTop: 38, paddingBottom: 38 }}>
              Edit your workspace
            </h2>
            <Card>
              <Card.Body>
                <Form onSubmit={handleSaveWorkspace}>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Workspace name</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter a workspace name"
                        value={workspaceName}
                        onChange={handleWorkspaceNameChange}
                      />
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
                  <Button
                    variant="primary"
                    type="submit"
                    as={Col}
                    md="2"
                    onClick={handleSaveWorkspace} // Add onClick event
                  >
                    Save
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

export default Settings;
