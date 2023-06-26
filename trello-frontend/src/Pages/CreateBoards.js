import React, { useState } from 'react';
import {
  Container,
  Row,
  Form,
  Col,
  Card,
  Button,
  Stack,
  Nav,
} from 'react-bootstrap';

const CreateBoards = () => {
  const [validated, setValidated] = useState(false);
  const [boardName, setBoardName] = useState('');
  const [boardDescription, setBoardDescription] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    event.stopPropagation();
  
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      // Form validation failed
      setValidated(true);
      return;
    }
  
    // Connect to the database and save the board
    const boardData = {
      name: boardName,
      description: boardDescription,
    };
  
    try {
      console.log(JSON.stringify(boardData));
      const response = await fetch("http://localhost:8001/api/createBoard", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "POST,PATCH,OPTION",
        },
        body: JSON.stringify(boardData),
      });
  
      if (response.ok) {
        const data = await response.json();
        console.log("Board created:", data);
        // Handle the response or update the state as needed
      } else {
        console.error("Failed to create board");
        // Handle the error appropriately
      }
    } catch (error) {
      console.error("Failed to create board:", error);
      // Handle network errors or other exceptions
    }
  
    setValidated(true);
  };

  return (
    <div style={{ minHeight: '93vh' }}>
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
                      <br />
                      <Form.Group controlId="validationCustom01">
                        <Form.Label>Board description</Form.Label>
                        <Form.Control
                          as="textarea"
                          aria-label="With textarea"
                          value={boardDescription}
                          onChange={(e) => setBoardDescription(e.target.value)}
                        />
                      </Form.Group>
                    </Form.Group>
                  </Row>
                  <Button variant="primary" type="submit" as={Col} md="2">
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

