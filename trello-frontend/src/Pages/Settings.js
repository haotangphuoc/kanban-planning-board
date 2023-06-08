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
} from "react-bootstrap";

const Settings = () => {
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
              Edit your workspace
            </h1>
            <Card>
              <Card.Body>
                <Form>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Workspace name</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter a board name"
                      />
                      <br />
                      <Form.Group controlId="validationCustom01">
                        <Form.Label>Workspace description</Form.Label>
                        <Form.Control
                          as="textarea"
                          aria-label="With textarea"
                        />
                      </Form.Group>
                    </Form.Group>
                  </Row>
                </Form>
                <Button variant="primary" type="submit" as={Col} md="2">
                  Save
                </Button>
              </Card.Body>
            </Card>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Settings;
