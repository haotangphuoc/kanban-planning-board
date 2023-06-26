import React, { useState } from "react";
import { Container, Row, Form, Button, Card, Stack, Nav } from "react-bootstrap";

const ForgotPassword = () => {
  const [validated, setValidated] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [answer, setAnswer] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    
    if (form.checkValidity() === false) {
      event.stopPropagation();
    } else {
      try {
        const data = {
          email: email,
          password: password,
          questionAns: answer,
        };
        console.log(JSON.stringify(data))
        const response = await fetch("http://localhost:8001/api/resetPassword", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Crontrol-Allow-Methods": "POST,PATCH,OPTION",
          },
          body: JSON.stringify(data),
        });

        if (response.ok) {
          console.log("Reset password successfully");
          // Perform any additional actions after resetting the password
        } else {
          const errorMessage = "Reset password failed";
          setErrorMessage(errorMessage);
        }
      } catch (error) {
        console.error("Request encountered an error:", error);
        const errorMessage = "Reset password failed";
        setErrorMessage(errorMessage);
      }
    }

    setValidated(true);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
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

  const handleAnswerChange = (event) => {
    setAnswer(event.target.value);
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
             {/* There should be an email function here, but I checked and found that it did not seem to be added, I modified the code of this place. */}
            <Form noValidate validated={validated} onSubmit={handleSubmit}>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email Address</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Enter your email"
                  required
                  value={email}
                  onChange={handleEmailChange}
                />
                <Form.Control.Feedback type="invalid">
                  Please enter a valid email address.
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group className="mb-3" controlId="formSecurityQuestion">
                <Form.Label>
                  Security Question: What's your favorite color?
                </Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter your answer"
                  required
                  value={answer}
                  onChange={handleAnswerChange}
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
