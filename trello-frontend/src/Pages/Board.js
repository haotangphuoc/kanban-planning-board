import React, { useState } from 'react';
import {
  Container,
  Row,
  Stack,
  Nav,
  Form,
  Col,
  ListGroup,
  Card,
  Button,
} from 'react-bootstrap';

const Board = () => {
  const [boardId, setBoardId] = useState('');
  const [boardTitle, setBoardTitle] = useState('');

  const fetchBoardIdByTitle = async (boardTitle) => {
    try {
      const response = await fetch(
        `http://localhost:8001/api/findBoardIdByTitle?title=${boardTitle}`
      );

      if (response.ok) {
        const data = await response.json();
        setBoardId(data);
      } else {
        console.error('Failed to fetch board ID');
        // Handle error case
      }
    } catch (error) {
      console.error('Failed to fetch board ID:', error);
      // Handle error case
    }
  };

  const handleDeleteBoard = async (event) => {
    event.preventDefault();
    event.stopPropagation();

    try {
      console.log(JSON.stringify({ boardId }));
      const response = await fetch(
        `http://localhost:8001/api/deleteBoard?boardId=${boardId}`
      );

      if (response.ok) {
        console.log('Board deleted successfully');
        // Handle the response or update the state as needed
      } else {
        console.error('Failed to delete board');
        // Handle the error appropriately
      }
    } catch (error) {
      console.error('Failed to delete board:', error);
      // Handle network errors or other exceptions
    }
  };

  const handleBoardTitleChange = (event) => {
    setBoardTitle(event.target.value);
  };

  const handleBoardTitleSubmit = (event) => {
    event.preventDefault();
    console.log('Board Title:', boardTitle);
    fetchBoardIdByTitle(boardTitle); // Fetch board ID based on the title
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
                  <Nav.Link href="../Pages/Workspace.js">&#60; Workspace</Nav.Link>
                </Card.Title>
              </div>
            </Stack>

            <h3 style={{ paddingTop: 38 }}>Board Name</h3>
            <h6>Description</h6>
            <br />
            <Form onSubmit={handleBoardTitleSubmit}>
              <Row className="align-items-center">
                <Col xs="auto">
                  <Form.Label htmlFor="boardTitle" visuallyHidden>
                    Board Title
                  </Form.Label>
                  <Form.Control
                    type="text"
                    id="boardTitle"
                    placeholder="Enter board title"
                    value={boardTitle}
                    onChange={handleBoardTitleChange}
                  />
                </Col>
                <Col xs="auto">
                  <Button variant="success" type="submit">
                    Create list
                  </Button>
                </Col>
              </Row>
            </Form>

            <Row xs={1} md={2} className="g-4">
              <Col>
                <Card>
                  <Card.Header>List Name</Card.Header>
                  <ListGroup variant="flush">
                    <ListGroup.Item>
                      Task 1
                      <Form.Check type="checkbox" label="To-Do" />
                      <Form.Check type="checkbox" label="Doing" />
                      <Form.Check type="checkbox" label="Done" />
                    </ListGroup.Item>
                    <ListGroup.Item>
                      Task 2
                      <Form.Check type="checkbox" label="To-Do" />
                      <Form.Check type="checkbox" label="Doing" />
                      <Form.Check type="checkbox" label="Done" />
                    </ListGroup.Item>
                    <ListGroup.Item>
                      Task 3
                      <Form.Check type="checkbox" label="To-Do" />
                      <Form.Check type="checkbox" label="Doing" />
                      <Form.Check type="checkbox" label="Done" />
                    </ListGroup.Item>
                    <ListGroup.Item>
                      <Button variant="primary" style={{ marginRight: 2 }}>
                        Add a card
                      </Button>
                      <Button variant="danger" style={{ marginRight: 2 }}>
                        Delete list
                      </Button>
                    </ListGroup.Item>
                  </ListGroup>
                </Card>
              </Col>
              <Col>
                <Card>
                  <Card.Header>List Name</Card.Header>
                  <ListGroup variant="flush">
                    <ListGroup.Item>
                      Task 1
                      <Form.Check type="checkbox" label="To-Do" />
                      <Form.Check type="checkbox" label="Doing" />
                      <Form.Check type="checkbox" label="Done" />
                    </ListGroup.Item>
                    <ListGroup.Item>
                      Task 2
                      <Form.Check type="checkbox" label="To-Do" />
                      <Form.Check type="checkbox" label="Doing" />
                      <Form.Check type="checkbox" label="Done" />
                    </ListGroup.Item>
                    <ListGroup.Item>
                      Task 3
                      <Form.Check type="checkbox" label="To-Do" />
                      <Form.Check type="checkbox" label="Doing" />
                      <Form.Check type="checkbox" label="Done" />
                    </ListGroup.Item>
                    <ListGroup.Item>
                      <Button variant="primary" style={{ marginRight: 2 }}>
                        Add a card
                      </Button>
                      <Button variant="danger" style={{ marginRight: 2 }}>
                        Delete list
                      </Button>
                    </ListGroup.Item>
                  </ListGroup>
                </Card>
              </Col>
            </Row>
            <br />
            <Stack className="mx-auto" direction="horizontal" gap={1}>
              <div className="ms-auto" style={{ paddingBottom: 12 }}>
                <Button
                  variant="danger"
                  type="submit"
                  onClick={handleDeleteBoard}
                >
                  Delete board
                </Button>
              </div>
            </Stack>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Board;

