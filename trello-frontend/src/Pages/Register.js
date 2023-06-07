import { React, useState } from "react";
import {
  Container,
  Row,
  Form,
  Stack,
  Button,
  Col,
  Card,
} from "react-bootstrap";
import InputGroup from "react-bootstrap/InputGroup";

const Register = () => {
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
            <h1 style={{ paddingTop: 38, paddingBottom: 38 }}>Sign-Up</h1>
            <Form noValidate validated={validated} onSubmit={handleSubmit}>
              <Row className="mb-3">
                <Form.Group as={Col} md="4" controlId="validationCustom01">
                  <Form.Label>First name</Form.Label>
                  <Form.Control required type="text" placeholder="First name" />
                  <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                  <Form.Control.Feedback type="invalid">
                    Please enter a first name.
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group as={Col} md="4" controlId="validationCustom02">
                  <Form.Label>Last name</Form.Label>
                  <Form.Control required type="text" placeholder="Last name" />
                  <Form.Control.Feedback>Looks good!</Form.Control.Feedback>
                  <Form.Control.Feedback type="invalid">
                    Please enter a last name.
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group
                  as={Col}
                  md="4"
                  controlId="validationCustomUsername"
                >
                  <Form.Label>Username</Form.Label>
                  <InputGroup hasValidation>
                    <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
                    <Form.Control
                      type="text"
                      placeholder="Username"
                      aria-describedby="inputGroupPrepend"
                      required
                    />
                    <Form.Control.Feedback type="invalid">
                      Please choose a username.
                    </Form.Control.Feedback>
                  </InputGroup>
                </Form.Group>
              </Row>

              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control type="email" placeholder="Enter email" required />
                <Form.Control.Feedback type="invalid">
                  Please enter an email.
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" placeholder="Password" required />
                <Form.Control.Feedback type="invalid">
                  Please enter a password.
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                className="mb-3"
                controlId="formBasicCheckbox"
              ></Form.Group>

              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>
                  Security Question: What's your favourite colour?
                </Form.Label>
                <Form.Control type="text" placeholder="Enter answer" required />
              </Form.Group>

              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Form>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Register;
