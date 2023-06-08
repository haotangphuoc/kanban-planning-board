import { React, useState } from "react";
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

const Homepage = () => {
  const [validated, setValidated] = useState(false);

  const handleSubmit = (event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }

    setValidated(true);
  };
  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <Stack className="mx-auto" direction="horizontal" gap={1}>
              <div className="ms-auto" style={{ paddingBottom: 12 }}>
                <a href="/">Logout</a>
              </div>
            </Stack>
            <h2 style={{ paddingTop: 38, paddingBottom: 24 }}>Workspaces</h2>
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
                    <Nav.Link href="#">Workspace Title</Nav.Link>
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
                      <Form.Label>Workspace name</Form.Label>
                      <Form.Control
                        required
                        type="text"
                        placeholder="Enter a workspace name"
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
                        />
                      </Form.Group>
                    </Form.Group>
                  </Row>
                </Form>
                <Button variant="primary" type="submit" as={Col} md="2">
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
