import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Form,
  Col,
  Card,
  Button,
  Stack,
  Nav, ListGroup,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const CreateBoards = () => {
  const [validated, setValidated] = useState(false);
  const [boardName, setBoardName] = useState("");
  const [workspaceId, setWorkspaceId] = useState("");
  const [workspaceName, setWorkspaceName] = useState("");
  const [users, setUsers] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    const selectedWorkspace = localStorage.getItem("selectedWorkspace");
    if (selectedWorkspace) {
      const workspace = JSON.parse(selectedWorkspace);
      setWorkspaceId(workspace.id); // Extract workspace id from selected workspace
      setWorkspaceName(workspace.name);
      setUsers(workspace.user);
    }
  }, [workspaceId]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    event.stopPropagation();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      // Form validation failed
      setValidated(true);
      return;
    }

    // Generate a unique ID for the new board
    const boardId = new Date().getTime().toString();

    const boardData = {
      id: workspaceId,
      users: users,
      boards: [
        {
          title: boardName,
        },
      ],
    };

    try {
      console.log(boardName);
      const response = await fetch("http://localhost:8001/api/createBoard", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(boardData),
      });
      console.log(response);

      if (response.ok) {
        console.log("Workspace created successfully");

        // Clear the form fields
        setBoardName("");
      } else {
        console.error("Failed to create workspace");
        // Handle error case
      }
    } catch (error) {
      console.error("Failed to create workspace:", error);
      // Handle error case
    }

    // Navigate back to the workspace
    navigate(`../Pages/Workspace.js?name=${workspaceName}`);

    setValidated(true);
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
                  <Nav.Link
                    href={`../Pages/Workspace.js?name=${workspaceName}`}
                  >
                    &#60; Workspace
                  </Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <h2 style={{ paddingTop: 38, paddingBottom: 38 }}>
              Create a board
            </h2>
            <Card>
              <Card.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Board name</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter a board name"
                        value={boardName}
                        onChange={(e) => setBoardName(e.target.value)}
                      />
                      <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                      <Form.Control.Feedback type="invalid">
                        Please enter a board name.
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

export default CreateBoards;



