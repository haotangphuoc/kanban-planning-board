import React, { useState, useRef } from "react";
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

const CreateTasks = () => {
  const [validated, setValidated] = useState(false);
  const [date, setDate] = useState("");
  const dateInputRef = useRef(null);

  const handleSubmit = async (event) => {
    event.preventDefault();
    event.stopPropagation();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      // Form validation failed
      setValidated(true);
      return;
    }
    setValidated(true);
  };

  const handleChange = (e) => {
    setDate(e.target.value);
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
                  <Nav.Link href="../Pages/Board.js">&#60; Back</Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <h2 style={{ paddingTop: 38, paddingBottom: 38 }}>Create a Task</h2>
            <Card>
              <Card.Body>
                <Form noValidate validated={validated} onSubmit={handleSubmit}>
                  <Row className="mb-3">
                    <Form.Group as={Col} md="4" controlId="validationCustom01">
                      <Form.Label>Task name</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter a task name"
                      />
                      <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                      <Form.Control.Feedback type="invalid">
                        Please enter a task name.
                      </Form.Control.Feedback>
                    </Form.Group>
                  </Row>
                  <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationCustom02">
                      <Form.Label>Due Date: </Form.Label>
                      <input
                        style={{ marginLeft: 15 }}
                        type="date"
                        onChange={handleChange}
                        ref={dateInputRef}
                      />
                    </Form.Group>
                  </Row>
                  <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationCustom02">
                      <Form.Label>Assign to:</Form.Label>
                      <Form.Control
                        required
                        type="email"
                        placeholder="Enter an email"
                      />
                    </Form.Group>
                  </Row>
                  <Button variant="primary" type="submit">
                    Create
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

export default CreateTasks;
