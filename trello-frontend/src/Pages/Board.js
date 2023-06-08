import { React, useState } from "react";
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
} from "react-bootstrap";

const Board = () => {
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

            <h3 style={{ paddingTop: 38 }}>Board Name</h3>
            <h6>Description</h6>
            <br />
            <Stack className="mx-auto" direction="horizontal" gap={1}>
              <div className="ms-auto" style={{ paddingBottom: 12 }}>
                <Button variant="success" type="submit">
                  Create list
                </Button>
              </div>
            </Stack>
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
                <Button variant="danger" type="submit">
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
