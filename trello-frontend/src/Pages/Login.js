import React, { useState } from "react";
import {
  Container,
  Row,
  Form,
  Card,
  Nav,
  Stack,
  Button,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();

  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Get the email and password values from the form
    const email = event.target.elements.email.value;
    const password = event.target.elements.password.value;

    try {
      console.log(JSON.stringify({ email, password }));
      // Perform the database query or validation
      const response = await fetch("http://localhost:8001/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "POST,PATCH,OPTION",
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        // Successful response, user is authenticated
        // Save user data to localStorage
        localStorage.setItem("userData", JSON.stringify({ email }));
        console.log("User is authenticated");
        navigate("/Pages/Homepage.js");
      } else {
        // Invalid credentials or other error
        const data = await response;
        const errorMessage = data.message || "Login failed";
        setErrorMessage(errorMessage);
      }
    } catch (error) {
      // Handle network errors or other exceptions
      console.error("An error occurred:", error);
      setErrorMessage("Login failed");
    }
  };

  return (
    <div style={{ minHeight: "93vh" }}>
      <Container>
        <Row>
          <div>
            <Stack
              className="mx-auto"
              direction="horizontal"
              style={{ paddingTop: 24 }}
            >
              <div style={{ paddingBottom: 12 }}>
                <Card.Title>
                  <Nav.Link href="/">&#60; Back</Nav.Link>
                </Card.Title>
              </div>
            </Stack>
            <h1 style={{ paddingTop: 24, paddingBottom: 38 }}>Login</h1>
            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email address</Form.Label>
                <Form.Control
                  type="email"
                  name="email"
                  placeholder="Enter email"
                />
                <Form.Text className="text-muted"></Form.Text>
              </Form.Group>

              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  placeholder="Password"
                />
              </Form.Group>

              <Stack className="mx-auto" direction="horizontal" gap={1}>
                <div className="ms-auto" style={{ paddingBottom: 12 }}>
                  <a href="../Pages/ForgotPassword.js">Forgot password?</a>
                </div>
              </Stack>

              {errorMessage && (
                <div className="error-message">{errorMessage}</div>
              )}

              <Button variant="primary" type="submit">
                Login
              </Button>
            </Form>
          </div>
        </Row>
      </Container>
    </div>
  );
};

export default Login;
