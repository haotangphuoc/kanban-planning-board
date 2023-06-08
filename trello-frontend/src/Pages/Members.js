import { React, useState } from "react";
import {
  Container,
  Row,
  Form,
  Col,
  Card,
  Button,
  Stack,
  Nav,
  ListGroup,
} from "react-bootstrap";

const Members = () => {
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
            <h1 style={{ paddingTop: 38, paddingBottom: 38 }}>
              Workspace Members
            </h1>
            <Card>
              <Card.Body>
                <Form>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Invite members to your workspace</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter email"
                      />
                    </Form.Group>
                  </Row>
                  <Button variant="primary" type="submit" as={Col} md="2">
                    Invite
                  </Button>
                </Form>
                <br />
                <h6>Current Members</h6>
                <ListGroup variant="flush">
                  <ListGroup.Item disabled>
                    Member Name 1 | email@dal.ca
                  </ListGroup.Item>
                  <ListGroup.Item>
                    Member Name 2 | email@dal.ca{" "}
                    <Button variant="danger" style={{ marginLeft: 25 }}>
                      Remove
                    </Button>
                  </ListGroup.Item>
                  <ListGroup.Item>
                    Member Name 3 | email@dal.ca{" "}
                    <Button variant="danger" style={{ marginLeft: 25 }}>
                      Remove
                    </Button>
                  </ListGroup.Item>
                </ListGroup>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Members;
