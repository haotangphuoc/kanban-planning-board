import { React, useState } from "react";
import {
  Container,
  Row,
  Form,
  Button,
  Card,
  Stack,
  Nav,
} from "react-bootstrap";

const ForgotPassword = () => {
  const [validated, setValidated] = useState(false);
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = (event) => {
    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    }

    setValidated(true);
  };

  const handlePasswordChange = (event) => {
    const newPassword = event.target.value;

    // Password requirements check
    const hasUpperCase = /[A-Z]/.test(newPassword);
    const hasLowerCase = /[a-z]/.test(newPassword);
    const hasDigit = /\d/.test(newPassword);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(newPassword);
    const isLongEnough = newPassword.length >= 8;

    // Set password state and display error message
    setPassword(newPassword);
    setErrorMessage(
      !hasUpperCase ||
        !hasLowerCase ||
        !hasDigit ||
        !hasSpecialChar ||
        !isLongEnough
        ? "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long."
        : ""
    );
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
                  <Nav.Link href="/">&#60; Back</Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <h1 style={{ paddingTop: 38, paddingBottom: 38 }}>
              Reset Password
            </h1>
            <Form noValidate validated={validated} onSubmit={handleSubmit}>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>
                  Security Question: What's your favourite colour?
                </Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter your answer"
                  required
                />
                <Form.Control.Feedback type="invalid">
                  Please enter your answer to the security question.
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-3" controlId="validationCustomPassword">
                <Form.Label>New Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Password"
                  required
                  value={password}
                  onChange={handlePasswordChange}
                  isInvalid={errorMessage !== ""}
                />
                <Form.Control.Feedback type="invalid">
                  {errorMessage}
                </Form.Control.Feedback>
              </Form.Group>

              <Button variant="primary" type="submit">
                Save
              </Button>
            </Form>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default ForgotPassword;
